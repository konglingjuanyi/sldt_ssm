/**
 * @author chenbo
 * @date 2013-12-01
 *
 */
package com.sldt.dmc.ssm.parms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sldt.dmc.ssm.parms.bean.Announce;
import com.sldt.dmc.ssm.parms.dao.IAnnounceDao;


public class AnnounceDaoImpl implements IAnnounceDao {

	private static Log logger = LogFactory.getLog(AnnounceDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Announce> findNewsRelease() {
		List<Announce> al = null;
        //String sql = "SELECT * FROM PMS_ANNOUNCE t WHERE t.STATE='2' ORDER BY  t.CREATE_DATE  DESC";
        String sql = "SELECT t.*,u.file_index FROM PMS_ANNOUNCE t LEFT JOIN P_MS_ANNOUNCE_SORTINDEX u ON t.ID=u.FILE_ID WHERE t.state ='2'";
        
       sql += "order by CASE WHEN u.file_index Is NULL Then 0 Else 1 End, u.file_index,t.publish_date desc";
        
		List<Map<String, Object>> maps = null;
		try {
			maps = getJdbcTemplate().queryForList(sql);
			if (maps != null && maps.size() > 0) {
				al = new ArrayList<Announce>();
				for (Map map : maps) {
					Announce announce = initAnnounce(map);
					al.add(announce);
				}
			}
		} catch (Exception e) {
			logger.error("公告数据访问层执行sql=" + sql + "失败，失败原因：" + e.getMessage());
			return al;
		}
		return al;
	}
	
	private Announce initAnnounce(Map map) {
		Announce announce = new Announce();
		announce.setId((String) map.get("id"));
		announce.setTitle((String) map.get("title"));
		announce.setContent((String) map.get("content"));
		announce.setState((String) map.get("state"));
		announce.setOrigin((String) map.get("origin"));
		announce.setKind((String) map.get("kind"));
		announce.setLevel((String) map.get("level"));
		announce.setMailTag((String) map.get("mailtag"));
		announce.setMailGroupId((String) map.get("mailgroup_id"));
		announce.setTipTag((String) map.get("tiptag"));
		announce.setCreaterId((String) map.get("creater_id"));
		announce.setCreaterName((String) map.get("creater_name"));
		announce.setCreateDate((String) map.get("create_date"));
		announce.setLatestUpdateDate((String) map.get("latest_update_date"));
		announce.setArchiverId((String) map.get("archiver_id"));
		announce.setArchiverName((String) map.get("archiver_name"));
		announce.setArchiveDate((String) map.get("archive_date"));
		announce.setPublishDate((String) map.get("publish_date"));
		announce.setRemember((String) map.get("remember"));
		announce.setValidDate((String) map.get("valid_date"));
		return announce;
	}

}
