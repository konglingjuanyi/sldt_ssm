/*
  $Id: Credential.java 2885 2014-02-05 21:28:49Z dfisher $

  Copyright (C) 2003-2014 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision: 2885 $
  Updated: $Date: 2014-02-05 16:28:49 -0500 (Wed, 05 Feb 2014) $
*/
package com.sldt.sdp.ldap.ldaptive;

import java.nio.charset.Charset;

import com.sldt.sdp.ldap.util.PdsCfgMgr;

/**
 * Provides convenience methods for converting the various types of passwords
 * into a byte array.
 *
 * @author  Middleware Services
 * @version  $Revision: 2885 $ $Date: 2014-02-05 16:28:49 -0500 (Wed, 05 Feb 2014) $
 */
public class Credential extends org.ldaptive.Credential
{

  /** UTF-8 character set. */
  private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

  /** Credential stored as a byte array. */
  private final byte[] bytes;


  /**
   * Creates a new credential.
   *
   * @param  password  converted from UTF-8 to a byte array
   */
  public Credential(final String password)
  {
	  super(PdsCfgMgr.getPassword(password));
	//zzg 20141017
	  String realVal=PdsCfgMgr.getPassword(password);
      bytes = realVal.getBytes(UTF8_CHARSET);
  }


  /**
   * Creates a new credential.
   *
   * @param  password  converted from UTF-8 to a byte array
   */
  public Credential(final char[] password)
  {
	super(password);
    bytes = new String(password).getBytes(UTF8_CHARSET);
  }


  /**
   * Creates a new credential.
   *
   * @param  password  to store
   */
  public Credential(final byte[] password)
  {	
	super(password);
    bytes = password;
  }


  /**
   * Returns this credential as a byte array.
   *
   * @return  credential bytes
   */
  public byte[] getBytes()
  {
    return bytes;
  }


  /**
   * Returns this credential as a string.
   *
   * @return  credential string
   */
  public String getString()
  {
    return new String(bytes, UTF8_CHARSET);
  }


  /**
   * Returns this credential as a character array.
   *
   * @return  credential characters
   */
  public char[] getChars()
  {
    return getString().toCharArray();
  }


  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return
      String.format(
        "[%s@%d::bytes=%s]",
        getClass().getName(),
        hashCode(),
        new String(bytes, UTF8_CHARSET));
  }
}
