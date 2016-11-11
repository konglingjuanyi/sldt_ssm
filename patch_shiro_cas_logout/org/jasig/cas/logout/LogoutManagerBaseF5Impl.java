/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.logout;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.SingleLogoutService;
import org.jasig.cas.services.LogoutType;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This logout manager handles the Single Log Out process.
 *
 * @author Jerome Leleu
 * @since 4.0.0
 */
public final class LogoutManagerBaseF5Impl implements LogoutManager {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutManagerBaseF5Impl.class);

    /** ASCII character set. */
    private static final Charset ASCII = Charset.forName("ASCII");

    /** The services manager. */
    @NotNull
    private final ServicesManager servicesManager;

    /** An HTTP client. */
    @NotNull
    private final HttpClient httpClient;

    @NotNull
    private final LogoutMessageCreator logoutMessageBuilder;
    
    /** Whether single sign out is disabled or not. */
    private boolean disableSingleSignOut = false;

    /**
     * Build the logout manager.
     * @param servicesManager the services manager.
     * @param httpClient an HTTP client.
     * @param logoutMessageBuilder the builder to construct logout messages.
     */
    public LogoutManagerBaseF5Impl(final ServicesManager servicesManager, final HttpClient httpClient,
                             final LogoutMessageCreator logoutMessageBuilder) {
        this.servicesManager = servicesManager;
        this.httpClient = httpClient;
        this.logoutMessageBuilder = logoutMessageBuilder;
    }

    /**
     * Perform a back channel logout for a given ticket granting ticket and returns all the logout requests.
     *
     * @param ticket a given ticket granting ticket.
     * @return all logout requests.
     */
    @Override
    public List<LogoutRequest> performLogout(final TicketGrantingTicket ticket) {
        final Map<String, Service> services;
        // synchronize the retrieval of the services and their cleaning for the TGT
        // to avoid concurrent logout mess ups
        synchronized (ticket) {
            services = ticket.getServices();
            ticket.removeAllServices();
        }
        ticket.markTicketExpired();

        final List<LogoutRequest> logoutRequests = new ArrayList<LogoutRequest>();
        // if SLO is not disabled
        if (!disableSingleSignOut) {
            // through all services
            for (final String ticketId : services.keySet()) {
                final Service service = services.get(ticketId);
                // it's a SingleLogoutService, else ignore
                if (service instanceof SingleLogoutService) {
                    final SingleLogoutService singleLogoutService = (SingleLogoutService) service;
                    // the logout has not performed already
                    if (!singleLogoutService.isLoggedOutAlready()) {
                        final LogoutRequest logoutRequest = new LogoutRequest(ticketId, singleLogoutService);
                        // always add the logout request
                        logoutRequests.add(logoutRequest);
                        final RegisteredService registeredService = servicesManager.findServiceBy(service);
                        // the service is no more defined, or the logout type is not defined or is back channel
                        if (registeredService == null || registeredService.getLogoutType() == null
                                || registeredService.getLogoutType() == LogoutType.BACK_CHANNEL) {
                            // perform back channel logout
                            if (performBackChannelLogout(logoutRequest)) {
                                logoutRequest.setStatus(LogoutRequestStatus.SUCCESS);
                            } else {
                                logoutRequest.setStatus(LogoutRequestStatus.FAILURE);
                                LOGGER.warn("Logout message not sent to [{}]; Continuing processing...",
                                        singleLogoutService.getId());
                            }
                        }
                    }
                }
            }
        }

        return logoutRequests;
    }

    /**
     * zzg 20141019
     * 服务来源请求基础地址的替换原始正则表达式
     */
    private String serviceOriginalBaseUrlSourceRegex="";
    /**
     *  zzg 20141019
     * 服务来源请求基础地址的替换目标字符串
     */
    private String serviceOriginalBaseUrlTargetReplacement="";
    
    
    public String getServiceOriginalBaseUrlSourceRegex() {
		return serviceOriginalBaseUrlSourceRegex;
	}

	public void setServiceOriginalBaseUrlSourceRegex(
			String serviceOriginalBaseUrlSourceRegex) {
		this.serviceOriginalBaseUrlSourceRegex = serviceOriginalBaseUrlSourceRegex;
	}

	public String getServiceOriginalBaseUrlTargetReplacement() {
		return serviceOriginalBaseUrlTargetReplacement;
	}

	public void setServiceOriginalBaseUrlTargetReplacement(
			String serviceOriginalBaseUrlTargetReplacement) {
		this.serviceOriginalBaseUrlTargetReplacement = serviceOriginalBaseUrlTargetReplacement;
	}

	/**
     * Log out of a service through back channel.
     *
     * @param request the logout request.
     * @return if the logout has been performed.
     */
    private boolean performBackChannelLogout(final LogoutRequest request) {
        final String logoutRequest = this.logoutMessageBuilder.create(request);
        request.getService().setLoggedOutAlready(true);

        LOGGER.debug("Sending logout request for: [{}]", request.getService().getId());
        //zzg 20141019 修改，支持经过F5的退出调用，不能调用来源客户端的URL，
        //因为客户机器的URL访问的F5后面的机器和服务器机器访问的URL可能实质访问的机器不相同
        //F5的请求分发策略对不同的来源分配的结果可能不相同。
        //一般地，退出机制应该是，客户机访问运行该段程序的机器，只需要退出和本机器相同的其他应用的会话，即可保证客户机层面是完全退出的。
        //该情况在单机环境可能不会出现问题，但经过F5的机器肯定会存在风险。
        //对于cas_3.4.11,单点退出不是在LogoutManager中，通过ticket.expire()触发，在AbstractWebApplicationService中的logOutOfService方法最终实现
        //对于cas_3.4.11,在org.jasig.cas.authentication.principal。AbstractWebApplicationService的151行
        //151行：            return this.httpClient.sendMessageToEndPoint(getOriginalUrl(), logoutRequest, true);
        //一样存在无法正常单点退出的情况，是受到F5等负载均衡器的逻辑影响。
        //通过将SSM模块升级为PAS4.0，将不再有类似问题。
        
        
        
        //    return this.httpClient.sendMessageToEndPoint(request.getService().getOriginalUrl(), logoutRequest, true);
        String serviceOriginalUrl=request.getService().getOriginalUrl();
        //serviceOriginalUrl=serviceOriginalUrl.replaceAll("http://dmp.sunline.cn:8080/", "http://127.0.0.1:8080/");
        //结合配置文件执行替换程序
        serviceOriginalUrl=serviceOriginalUrl.replaceAll(serviceOriginalBaseUrlSourceRegex, serviceOriginalBaseUrlTargetReplacement);
        //zzg 20160707 
        //为了避免/ssm应用的退出引起问题，这里定制规避，不对/ssm执行服务器端发起的后台退出，避免/logout应用出错
        if(serviceOriginalUrl.indexOf("/ssm")!=-1){//如果服务是/ssm
           System.out.println("[back_channel_SLO]service为/ssm应用,忽略后台SLO,serviceOriginalUrl="+serviceOriginalUrl);
           return true;
        }else{
        	//For back channel SLO.
          System.out.println("[back_channel_SLO] serviceOriginalUrl="+serviceOriginalUrl);
          return this.httpClient.sendMessageToEndPoint(serviceOriginalUrl, logoutRequest, true);
        }
    }

    /**
     * Create a logout message for front channel logout.
     *
     * @param logoutRequest the logout request.
     * @return a front SAML logout message.
     */
    public String createFrontChannelLogoutMessage(final LogoutRequest logoutRequest) {
        final String logoutMessage = this.logoutMessageBuilder.create(logoutRequest);
        final Deflater deflater = new Deflater();
        deflater.setInput(logoutMessage.getBytes(ASCII));
        deflater.finish();
        final byte[] buffer = new byte[logoutMessage.length()];
        final int resultSize = deflater.deflate(buffer);
        final byte[] output = new byte[resultSize];
        System.arraycopy(buffer, 0, output, 0, resultSize);
        return Base64.encodeBase64String(output);
    }

    /**
     * Set if the logout is disabled.
     *
     * @param disableSingleSignOut if the logout is disabled.
     */
    public void setDisableSingleSignOut(final boolean disableSingleSignOut) {
        this.disableSingleSignOut = disableSingleSignOut;
    }
}
