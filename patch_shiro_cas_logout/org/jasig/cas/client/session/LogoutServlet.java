package org.jasig.cas.client.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import javax.servlet.ServletRequest;

/**
 * 通用的一些后台接口，如每个应用中需要统一调用，以便减少对web.xml文件的修改
 * @author chenbo
 *
 */
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -5434541161824887054L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");// 获取查询类型
		
		if("ssoLogOut".equals(type)){//如果是单点登录退出，则对应用中的session 进行销毁
			doLogout(request);
			System.out.println("-------->>>>>>注销应用中的session");
			//response.setContentType("text/json; charset=utf-8"); 
			response.setCharacterEncoding("utf-8");
			response.getWriter().print("操作成功");
		}else {
			response.setContentType("text/json; charset=utf-8"); 
			response.getWriter().write("{success:false,info:'操作失败'}");
		}
		
	}
	public void doLogout(HttpServletRequest request){
    	HttpServletRequest originReq=null;
    	if(request instanceof ShiroHttpServletRequest){
    		ShiroHttpServletRequest shiroReq=(ShiroHttpServletRequest)request;
    		boolean isHttpSession=shiroReq.isHttpSessions();
    		if(!isHttpSession){//不是HttpSession才执行
    			ServletRequest servletReq=shiroReq.getRequest();
    			if(servletReq instanceof HttpServletRequest){
    				originReq=(HttpServletRequest)servletReq;
    			}
    		}
    	}
    	if(originReq!=null){
           HttpSession httpSession = originReq.getSession(true);
           System.out.println("httpSession:"+httpSession);
           if(httpSession!=null){
               httpSession.invalidate();
           }
        }
        
         HttpSession reqSession = request.getSession(true);
          System.out.println("reqSession:"+reqSession);
         if(reqSession!=null){
            reqSession.invalidate();
         }
	}
}
