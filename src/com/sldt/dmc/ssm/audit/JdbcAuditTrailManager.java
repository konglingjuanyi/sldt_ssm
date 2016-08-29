package com.sldt.dmc.ssm.audit;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.inspektr.audit.AuditActionContext;
import com.github.inspektr.audit.AuditTrailManager;
import com.github.inspektr.audit.support.NoMatchWhereClauseMatchCriteria;
import com.github.inspektr.audit.support.WhereClauseMatchCriteria;
import com.github.inspektr.common.Cleanable;

/**
 * <p>
 * 实现{@link com.github.inspektr.audit.AuditTrailManager} 接口，
 * 将审计日志存储到数据库，并完成数据归档清理工作
 * 目前基于Oracle数据库处理，如果切换为DB2数据，需要调整SQL语法
 * </p>
 * <pre>
 * CREATE TABLE T_SM_AUDIT_TRAIL_HIS
 * (
 *  AUD_USER      VARCHAR2(100) NOT NULL,
 *  AUD_CLIENT_IP VARCHAR(15)   NOT NULL,
 *  AUD_SERVER_IP VARCHAR(15)   NOT NULL,
 *  AUD_RESOURCE  VARCHAR2(100) NOT NULL,
 *  AUD_ACTION    VARCHAR2(100) NOT NULL,
 *  APPLIC_CD     VARCHAR2(5)   NOT NULL,
 *  AUD_DATE      TIMESTAMP     NOT NULL
 * )
 * </pre>
 *
 * @author zzg 2012-04-29
 * @version 1.0 
 */
public final class JdbcAuditTrailManager extends SimpleJdbcDaoSupport implements AuditTrailManager, Cleanable, DisposableBean {

    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s " +
            "(AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String MARK_DELETE_SQL_TEMPLATE = "UPDATE  %s SET ARC_STS=9 %s";//标识记录即将归档
    private static final String ARCHIVE_SQL_TEMPLATE = "INSERT INTO %s " +
    		"(AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE,ARC_STS)" +
    		" select AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE,1" +
    		"  FROM %s WHERE ARC_STS=9";//删除所有标志为9的记录
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s WHERE ARC_STS=9";//删除所有标志为9的记录

    private static final int DEFAULT_COLUMN_LENGTH = 100;

    /**
     * Logger instance
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Instance of TransactionTemplate to manually execute a transaction since
     * threads are not in the same transaction.
     */
    @NotNull
    private final TransactionTemplate transactionTemplate;

    @NotNull
    @Size(min = 1)
    private String tableName = "T_SM_AUDIT_TRAIL"; //审计日志表
    @NotNull
    @Size(min = 1)
    private String hisTableName = "T_SM_AUDIT_TRAIL_HIS";//历时归档表，清楚的日志存储于该表
    @Min(50)
    private int columnLength = DEFAULT_COLUMN_LENGTH;

    /**
     * ExecutorService that has one thread to asynchronously save requests.
     *
     * You can configure one with an {@link org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean}.
     */
    @NotNull
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private boolean defaultExecutorService = true;

    /**
     * Criteria used to determine records that should be deleted on cleanup
     */
    private WhereClauseMatchCriteria cleanupCriteria = new NoMatchWhereClauseMatchCriteria();


    public JdbcAuditTrailManager(final TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void record(final AuditActionContext auditActionContext) {
        this.executorService.execute(new LoggingTask(auditActionContext, this.transactionTemplate, this.columnLength));
    }

    protected class LoggingTask implements Runnable {

        private final AuditActionContext auditActionContext;

        private final TransactionTemplate transactionTemplate;

        private final int columnLength;

        public LoggingTask(final AuditActionContext auditActionContext, final TransactionTemplate transactionTemplate, final int columnLength) {
            this.auditActionContext = auditActionContext;
            this.transactionTemplate = transactionTemplate;
            this.columnLength = columnLength;
        }

        public void run() {
            this.transactionTemplate
                    .execute(new TransactionCallbackWithoutResult() {
                        protected void doInTransactionWithoutResult(final TransactionStatus transactionStatus) {
                            final String userId = auditActionContext.getPrincipal().length() <= columnLength ? auditActionContext.getPrincipal() : auditActionContext.getPrincipal().substring(0, columnLength);
                            final String resource = auditActionContext.getResourceOperatedUpon().length() <= columnLength ? auditActionContext.getResourceOperatedUpon() : auditActionContext.getResourceOperatedUpon().substring(0, columnLength);
                            final String action = auditActionContext.getActionPerformed().length() <= columnLength ? auditActionContext.getActionPerformed() : auditActionContext.getActionPerformed().substring(0, columnLength);

                            getSimpleJdbcTemplate()
                                    .update(
                                            String.format(INSERT_SQL_TEMPLATE, tableName),
                                            userId,
                                            auditActionContext.getClientIpAddress(),
                                            auditActionContext.getServerIpAddress(),
                                            resource,
                                            action,
                                            auditActionContext.getApplicationCode(),
                                            auditActionContext.getWhenActionWasPerformed());
                        }
                    });
        }
    }

    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    public void setCleanupCriteria(final WhereClauseMatchCriteria criteria) {
        this.cleanupCriteria = criteria;
    }

    public void setExecutorService(final ExecutorService executorService) {
        this.executorService = executorService;
        this.defaultExecutorService = false;
    }

    public void setColumnLength(final int columnLength) {
        this.columnLength = columnLength;
    }

    /**
     * We only shut down the default executor service.  We assume, that if you've injected one, its being managed elsewhere.
     */
    public void destroy() throws Exception {
        if (this.defaultExecutorService) {
            this.executorService.shutdown();
        }
    }

    public void clean() {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            protected void doInTransactionWithoutResult(final TransactionStatus transactionStatus) {
            	 final String mark_delete_sql = String.format(MARK_DELETE_SQL_TEMPLATE, tableName, cleanupCriteria);
                final String archive_sql = String.format(ARCHIVE_SQL_TEMPLATE, hisTableName, tableName );
                final String delete_sql = String.format(DELETE_SQL_TEMPLATE, tableName);
                final List<?> params = cleanupCriteria.getParameterValues();
                JdbcAuditTrailManager.this.logger.info("标识归档语句： " + mark_delete_sql);
                JdbcAuditTrailManager.this.logger.info("归档语句： " + archive_sql);
                JdbcAuditTrailManager.this.logger.info("清理语句： " + delete_sql);
                JdbcAuditTrailManager.this.logger.debug("查询条件参数: " + params);
                
                final int mark_count = getSimpleJdbcTemplate().update(mark_delete_sql, params.toArray());
                JdbcAuditTrailManager.this.logger.info(mark_count + " 行记录被标识为归档");
                final int archive_count = getSimpleJdbcTemplate().update(archive_sql);
                JdbcAuditTrailManager.this.logger.info(archive_count + " 行记录被归档");
                final int delete_count = getSimpleJdbcTemplate().update(delete_sql);
                JdbcAuditTrailManager.this.logger.info(delete_count + " 行记录从当前表被删除");
            }
        });
    }
}
