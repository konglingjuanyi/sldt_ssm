package bingo.sso.client.web.utils;

import bingo.sso.client.web.ISingleSignOnConfig;
import bingo.sso.client.web.SingleSignOnConfigHolder;
import bingo.sso.client.web.SingleSignOnException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class HttpUtil
{
  public static final int DEFAULT_TIMEOUT = 10000;
  private static final int MAX_SIZE = 10240;
  private static final String CONTENT = "Content";
  private static final String RESPONSE_CODE = "ResponseCode";
  private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  
  public static Map request(String url, String method, String accept, String formPostData, int timeOutInMilliseconds)
  {
    HttpURLConnection hc = null;
    InputStream input = null;
    OutputStream output = null;
    try
    {
      hc = (HttpURLConnection)new URL(url).openConnection();
      hc.setRequestMethod(method);
      hc.setDoOutput("POST".equals(method));
      hc.setUseCaches(false);
      hc.setConnectTimeout(timeOutInMilliseconds);
      hc.setReadTimeout(timeOutInMilliseconds);
      hc.addRequestProperty("Accept", accept);
      hc.addRequestProperty("Accept-Encoding", "gzip");
      if (hc.getDoOutput())
      {
        output = hc.getOutputStream();
        output.write(formPostData.getBytes("UTF-8"));
        output.flush();
      }
      hc.connect();
      int code = hc.getResponseCode();
      


      boolean gzip = "gzip".equals(hc.getContentEncoding());
      input = hc.getInputStream();
      ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
      boolean overflow = false;
      int read = 0;
      byte[] buffer = new byte[1024];
      for (;;)
      {
        int n = input.read(buffer);
        if (n == -1) {
          break;
        }
        byteArrayOutput.write(buffer, 0, n);
        read += n;
        if (read > 10240)
        {
          overflow = true;
          break;
        }
      }
      byteArrayOutput.close();
      if (overflow) {
        throw new RuntimeException();
      }
      byte[] data = byteArrayOutput.toByteArray();
      if (gzip) {
        data = gunzip(data);
      }
      Map map = new HashMap();
      map.put("Cache-Control", hc.getHeaderField("Cache-Control"));
      map.put("Content-Type", hc.getHeaderField("Content-Type"));
      map.put("Content-Encoding", hc.getHeaderField("Content-Encoding"));
      map.put("Expires", hc.getHeaderField("Expires"));
      map.put("Content", data);
      map.put("ResponseCode", new Integer(code));
      return map;
    }
    catch (Exception e)
    {
      throw new SingleSignOnException("Request failed: " + url, e);
    }
    finally
    {
      if (output != null) {
        try
        {
          output.close();
        }
        catch (IOException e) {}
      }
      if (input != null) {
        try
        {
          input.close();
        }
        catch (IOException e) {}
      }
      if (hc != null) {
        hc.disconnect();
      }
    }
  }
  
  static long getMaxAge(Map map)
  {
    String cache = (String)map.get("Cache-Control");
    if (cache == null) {
      return 0L;
    }
    int pos = cache.indexOf("max-age=");
    if (pos != -1)
    {
      String maxAge = cache.substring(pos + "max-age=".length());
      pos = maxAge.indexOf(',');
      if (pos != -1) {
        maxAge = maxAge.substring(0, pos);
      }
      try
      {
        return Integer.parseInt(maxAge) * 1000L;
      }
      catch (Exception e) {}
    }
    return 0L;
  }
  
  public static String getContent(Map map)
    throws UnsupportedEncodingException
  {
    byte[] data = (byte[])map.get("Content");
    String charset = getCharset(map);
    if (charset == null) {
      charset = "UTF-8";
    }
    return new String(data, charset);
  }
  
  public static int getResopnseCode(Map map)
    throws UnsupportedEncodingException
  {
    return ((Integer)map.get("ResponseCode")).intValue();
  }
  
  public static byte[] gunzip(byte[] data)
  {
	ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
    GZIPInputStream input = null;
    try
    {
      input = new GZIPInputStream(new ByteArrayInputStream(data));
      byte[] buffer = new byte[1024];
      int n = 0;
      for (;;)
      {
        n = input.read(buffer);
        if (n <= 0) {
          break;
        }
        byteOutput.write(buffer, 0, n);
      }
      return byteOutput.toByteArray();
    }
    catch (IOException e)
    {
      throw new SingleSignOnException(e);
    }
    finally
    {
      if (input != null) {
        try
        {
          input.close();
        }
        catch (IOException ioe) {}
      }
    }
  }
  
  public static String mid(String s, String startToken, String endToken)
  {
    return mid(s, startToken, endToken, 0);
  }
  
  public static String mid(String s, String startToken, String endToken, int fromStart)
  {
    if ((startToken == null) || (endToken == null)) {
      return null;
    }
    int start = s.indexOf(startToken, fromStart);
    if (start == -1) {
      return null;
    }
    int end = s.indexOf(endToken, start + startToken.length());
    if (end == -1) {
      return null;
    }
    String sub = s.substring(start + startToken.length(), end);
    return sub.trim();
  }
  
  public static String getCharset(Map map)
  {
    String contentType = (String)map.get("Content-Type");
    String charset = null;
    if (contentType != null)
    {
      int pos = contentType.indexOf("charset=");
      if (pos != -1)
      {
        charset = contentType.substring(pos + "charset=".length());
        int sp = contentType.indexOf(';');
        if (sp != -1) {
          contentType = contentType.substring(0, sp).trim();
        }
      }
    }
    return charset;
  }
  
  public static String urlEncode(String s)
    throws UnsupportedEncodingException
  {
    return URLEncoder.encode(s, "UTF-8");
  }
  
  public static String buildQuery(List list)
  {
    if (!list.isEmpty())
    {
      StringBuffer sb = new StringBuffer(1024);
      try
      {
        for (int i = 0; i < list.size(); i++)
        {
          String s = (String)list.get(i);
          int n = s.indexOf('=');
          sb.append(s.substring(0, n + 1)).append(URLEncoder.encode(s.substring(n + 1), "UTF-8")).append('&');
        }
        sb.deleteCharAt(sb.length() - 1);
      }
      catch (UnsupportedEncodingException e)
      {
        throw new SingleSignOnException(e);
      }
      return sb.toString();
    }
    return "";
  }
  
  public static String toHexString(byte[] bytes)
  {
    int length = bytes.length;
    StringBuffer sb = new StringBuffer(length * 2);
    int x = 0;
    int n1 = 0;int n2 = 0;
    for (int i = 0; i < length; i++)
    {
      if (bytes[i] >= 0) {
        x = bytes[i];
      } else {
        x = 256 + bytes[i];
      }
      n1 = x >> 4;
      n2 = x & 0xF;
      sb = sb.append(HEX[n1]);
      sb = sb.append(HEX[n2]);
    }
    return sb.toString();
  }
  
  private static String regularUrl(String url)
  {
    url = url + "/";
    if ((url.startsWith("https")) || (url.startsWith("HTTPS"))) {
      url = url.replaceFirst(":443/", "/");
    } else {
      url = url.replaceFirst(":80/", "/");
    }
    return url.substring(0, url.length() - 1);
  }
  
  public static String convertFullPathUrl(HttpServletRequest request, String url)
  {
    String returnUrl;
    if (url.startsWith("http")) {
      returnUrl = url.replaceFirst("\\{host\\}", request.getServerName()).replaceFirst("\\{port\\}", String.valueOf(request.getServerPort()));
    } else {
      returnUrl = getServerUrl(request) + url;
    }
    return regularUrl(returnUrl);
  }
  
  public static String getServerName(HttpServletRequest request)
  {
    String host = request.getHeader("Host");
    if ((host != null) && (!"".equals(host)))
    {
      int index = host.indexOf(":");
      if (index > 0) {
        return host.substring(0, index);
      }
      return host;
    }
    return request.getServerName();
  }
  
  public static String getServerUrl(HttpServletRequest request)
  {
    String host = request.getHeader("Host");
    if ((host != null) && (!"".equals(host))) {
      return regularUrl(( requestIsSecure(request) ? "https://" : "http://") + host);
    }
    return regularUrl(( requestIsSecure(request)? "https://" : "http://") + request.getServerName() + ":" + request.getServerPort());
  }
  
  public static String getCookieValue(HttpServletRequest request, String cookieName)
    throws Exception
  {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }
    for (int i = 0; i < cookies.length; i++) {
      if (decode(cookies[i].getName()).equals(cookieName)) {
        return decode(cookies[i].getValue());
      }
    }
    return null;
  }
  
  public static String xssEncode(String s)
  {
    if ((s == null) || ("".equals(s))) {
      return s;
    }
    StringBuffer sb = new StringBuffer();
    if ((s.indexOf('>') != -1) || (s.indexOf('<') != -1) || (s.indexOf('\'') != -1) || (s.indexOf('(') != -1) || (s.indexOf(')') != -1)) {
      for (int i = 0; i < s.length(); i++)
      {
        char c = s.charAt(i);
        switch (c)
        {
        case '>': 
          sb.append(65310);
          break;
        case '<': 
          sb.append(65308);
          break;
        case '\'': 
          sb.append('‘');
          break;
        case '(': 
          sb.append(65288);
          break;
        case ')': 
          sb.append(65288);
          break;
        default: 
          sb.append(c);
        }
      }
    } else {
      sb.append(s);
    }
    return sb.toString();
  }
  
  public static String encode(String value)
  {
    try
    {
      return URLEncoder.encode(value, "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static String decode(String value)
  {
    try
    {
      return URLDecoder.decode(value, "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      throw new RuntimeException(e);
    }
  }
  /**
   * zzg 20141029 修改IsSecure判断逻辑，适应F5
   * 根据http head中的客户端ip地址信息，判断是否通过F5访问应用
   * @param request
   * @return
   */
  public static boolean requestIsSecure(HttpServletRequest request){
	    ISingleSignOnConfig singleSignOnConfig=SingleSignOnConfigHolder.get();
	    if(singleSignOnConfig!=null){//启用F5判断逻辑
			if(singleSignOnConfig.isForceUseHttpsRedirect()){
				return true;
			}
			//根据http head中的客户端ip地址信息，判断是否通过F5访问应用
	       String ip = request.getHeader(singleSignOnConfig.getF5OriginalClientIpHttpHeaderKey());//"x-forwarded-for"
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	   ip = request.getHeader(singleSignOnConfig.getF5OriginalClientIpHttpHeaderKeyEx());
	       }else{
	    	   return true;//如果发现目标http head，返回true
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	          return false;
	       }else{
	    	  return true;//如果发现目标http head，返回true
	       }
	   }else{//默认实现
		   return request.isSecure();
	   }
      //return request.isSecure();
	 // return true;
  }
}
