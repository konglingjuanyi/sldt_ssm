/**
 * @author chenbo
 * @date 2013-12-01
 *
 */
package com.sldt.dmc.ssm.parms.dao;

import java.util.List;

import com.sldt.dmc.ssm.parms.bean.Announce;

public interface IAnnounceDao {
	
	/**
	 * 查找最新发表的公告   根据公告的状态2，，按时间降序排列
	 * @param sql
	 * @return
	 */
	public List<Announce> findNewsRelease();
}
