package com.sldt.activiti.process.dao.impl;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.sldt.activiti.process.dao.IProcessDao;

@Repository(value="processDao")
public class ProcessDaoImpl implements IProcessDao {
	@Resource
	private SessionFactory sessionFactory;


}
