/*
  $Id: ConnectionConfig.java 2885 2014-02-05 21:28:49Z dfisher $

  Copyright (C) 2003-2014 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision: 2885 $
  Updated: $Date: 2014-02-05 16:28:49 -0500 (Wed, 05 Feb 2014) $
*/
package com.sldt.sdp.ldap.ldaptive;

import org.ldaptive.Connection;
import org.ldaptive.ConnectionInitializer;
import org.ldaptive.ssl.SslConfig;

import com.sldt.sdp.ldap.util.PdsCfgMgr;

/**
 * Contains all the configuration data needed to control connections.
 *
 * @author  Middleware Services
 * @version  $Revision: 2885 $ $Date: 2014-02-05 16:28:49 -0500 (Wed, 05 Feb 2014) $
 */
public class ConnectionConfig extends org.ldaptive.ConnectionConfig
{

  /** URL to the LDAP(s). */
  private String ldapUrl;

  /** Amount of time in milliseconds that connects will block. */
  private long connectTimeout = -1;

  /** Amount of time in milliseconds to wait for responses. */
  private long responseTimeout = -1;

  /** Configuration for SSL and startTLS connections. */
  private SslConfig sslConfig;

  /** Connect to LDAP using SSL protocol. */
  private boolean useSSL;

  /** Connect to LDAP using startTLS. */
  private boolean useStartTLS;

  /** Connection initializer to execute on {@link Connection#open()}. */
  private ConnectionInitializer connectionInitializer;


  /** Default constructor. */
  public ConnectionConfig() {}


  /**
   * Creates a new connection config.
   *
   * @param  url  to connect to
   */
  public ConnectionConfig(final String url)
  {
    setLdapUrl(url);
  }


  /**
   * Returns the ldap url.
   *
   * @return  ldap url
   */
  public String getLdapUrl()
  {
    return ldapUrl;
  }


  /**
   * Sets the ldap url.
   *
   * @param  url  of the ldap
   */
  public void setLdapUrl(final String url)
  {
	  String realVal=PdsCfgMgr.getUrl(url);
    checkImmutable();
    checkStringInput(realVal, true);
    logger.trace("setting ldapUrl: {}", realVal);
    ldapUrl = realVal;
  }


  /**
   * Returns the connect timeout. If this value is <= 0, then connects will wait
   * indefinitely.
   *
   * @return  timeout
   */
  public long getConnectTimeout()
  {
    return connectTimeout;
  }


  /**
   * Sets the maximum amount of time in milliseconds that connects will block.
   *
   * @param  time  timeout for connects
   */
  public void setConnectTimeout(final long time)
  {
    checkImmutable();
    logger.trace("setting connectTimeout: {}", time);
    connectTimeout = time;
  }


  /**
   * Returns the response timeout. If this value is <= 0, then operations will
   * wait indefinitely for a response.
   *
   * @return  timeout
   */
  public long getResponseTimeout()
  {
    return responseTimeout;
  }


  /**
   * Sets the maximum amount of time in milliseconds that operations will wait
   * for a response.
   *
   * @param  time  timeout for responses
   */
  public void setResponseTimeout(final long time)
  {
    checkImmutable();
    logger.trace("setting responseTimeout: {}", time);
    responseTimeout = time;
  }


  /**
   * Returns the ssl config.
   *
   * @return  ssl config
   */
  public SslConfig getSslConfig()
  {
    return sslConfig;
  }


  /**
   * Sets the ssl config.
   *
   * @param  config  ssl config
   */
  public void setSslConfig(final SslConfig config)
  {
    checkImmutable();
    logger.trace("setting sslConfig: {}", config);
    sslConfig = config;
  }


  /**
   * Returns whether the SSL protocol will be used for connections.
   *
   * @return  whether the SSL protocol will be used
   */
  public boolean getUseSSL()
  {
    return useSSL;
  }


  /**
   * Sets whether the SSL protocol will be used for connections.
   *
   * @param  b  whether the SSL protocol will be used
   */
  public void setUseSSL(final boolean b)
  {
    checkImmutable();
    logger.trace("setting useSSL: {}", b);
    useSSL = b;
  }


  /**
   * Returns whether startTLS will be used for connections.
   *
   * @return  whether startTLS will be used
   */
  public boolean getUseStartTLS()
  {
    return useStartTLS;
  }


  /**
   * Sets whether startTLS will be used for connections.
   *
   * @param  b  whether startTLS will be used
   */
  public void setUseStartTLS(final boolean b)
  {
    checkImmutable();
    logger.trace("setting useStartTLS: {}", b);
    useStartTLS = b;
  }


  /**
   * Returns the connection initializer.
   *
   * @return  connection initializer
   */
  public ConnectionInitializer getConnectionInitializer()
  {
    return connectionInitializer;
  }


  /**
   * Sets the connection initializer.
   *
   * @param  initializer  connection initializer
   */
  public void setConnectionInitializer(final ConnectionInitializer initializer)
  {
    checkImmutable();
    logger.trace("setting connectionInitializer: {}", initializer);
    connectionInitializer = initializer;
  }


  /**
   * Returns a connection config initialized with the supplied config.
   *
   * @param  config  connection config to read properties from
   *
   * @return  connection config
   */
  public static ConnectionConfig newConnectionConfig(
    final ConnectionConfig config)
  {
    final ConnectionConfig cc = new ConnectionConfig();
    cc.setLdapUrl(config.getLdapUrl());
    cc.setConnectTimeout(config.getConnectTimeout());
    cc.setResponseTimeout(config.getResponseTimeout());
    cc.setSslConfig(config.getSslConfig());
    cc.setUseSSL(config.getUseSSL());
    cc.setUseStartTLS(config.getUseStartTLS());
    cc.setConnectionInitializer(config.getConnectionInitializer());
    return cc;
  }


  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return
      String.format(
        "[%s@%d::ldapUrl=%s, connectTimeout=%s, responseTimeout=%s, " +
        "sslConfig=%s, useSSL=%s, useStartTLS=%s, connectionInitializer=%s]",
        getClass().getName(),
        hashCode(),
        ldapUrl,
        connectTimeout,
        responseTimeout,
        sslConfig,
        useSSL,
        useStartTLS,
        connectionInitializer);
  }
}
