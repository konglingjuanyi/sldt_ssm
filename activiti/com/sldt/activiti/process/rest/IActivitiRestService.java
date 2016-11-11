package com.sldt.activiti.process.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface IActivitiRestService {
	/**
	 *统计待办任务
	 * @return
	 */
	@GET
	@Path("/getStatisticsWfRuTaskInst")
	public String getStatisticsWfRuTaskInst(@QueryParam("userId")String userId,@QueryParam("callback") String callback);
}
