<%final String uName = request.getSession().getAttribute("SSO_USER_NAME") == null?"":request.getSession().getAttribute("SSO_USER_NAME").toString();
final String tmpPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"";
String redirct_url="/";
if(uName==null||uName.length()==0){
   redirct_url=tmpPath+"/ssm/ssoclient/login?return_url=/cgbssogateway.jsp";
}else{

com.sldt.dmc.ssm.pac4j.cgbsso.util.CgbssoUtil.setSSOLoginedUser(request,response,"/ssm",false,-1,uName);
request.getSession().setAttribute("SSO_USER_NAME",""); 

redirct_url = tmpPath + "/ssm/login?client_name=CgbssoClient";
System.out.println("-uName["+uName+"]----------url222:---------------"+redirct_url);
}
//response.sendRedirect(response.encodeURL(url));%>
 <html>
 <head>
 </head> 	
 <body>
 
 </body>
 </html>
 <script>
   //alert("1-2"+"<%=uName%>"+">>>"+"<%=redirct_url%>");
   window.location.href="<%=redirct_url%>";
 </script>
