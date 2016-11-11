package org.apache.shiro.cas.dmp;

import org.jasypt.util.text.BasicTextEncryptor;


public class DmpEncryptUtil{
	
   private static String  EncryptorKey="SunlineSSM";
	
	

   /**
    * 解密
    * @param encryPassw
    * @return
    */
   public static  String getDecryPassw(String encryPassw){
	    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
	    textEncryptor.setPassword(EncryptorKey);
	    String result = textEncryptor.decrypt(encryPassw);
	    System.out.println("decrypt [" + result + "]");
	    return result;
   }
   
   /**
    * 加密
    * @param srcPwd
    * @return
    */
   public static  String getEncPassw(String srcPwd){
	    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
	    textEncryptor.setPassword(EncryptorKey);
	    String result = textEncryptor.encrypt(srcPwd);
	    System.out.println("encrypt ： [" + result + "]");
	    return result;
   }
   
   
	
 public static void main(String[] args){
	String plainText = "mds12345";  
	String myEncryptedText = getEncPassw(plainText);
    System.out.println("加密：[" + myEncryptedText + "]");
    myEncryptedText = "saG+CBGrOQctqo91uClcaCWjQ1CLoltk";
    System.out.println("解密：[" + getDecryPassw(myEncryptedText) + "]");
   
  }
}