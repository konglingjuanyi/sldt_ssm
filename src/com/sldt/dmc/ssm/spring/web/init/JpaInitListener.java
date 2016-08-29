package com.sldt.dmc.ssm.spring.web.init;

import javax.persistence.spi.PersistenceProviderResolverHolder;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.sldt.dmc.ssm.persistence.CachingPersistenceProviderResolver;

public final class JpaInitListener implements ServletContextListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public void contextInitialized(final ServletContextEvent sce) {
    	 //lzp修改，算定义JPA持久化提供者
        System.out.println("动态设置PersistenceProviderResolver!");
        PersistenceProviderResolverHolder.setPersistenceProviderResolver(new CachingPersistenceProviderResolver());
      //lzp修改结束
    }

    public void contextDestroyed(final ServletContextEvent sce) {
       
    }

}
