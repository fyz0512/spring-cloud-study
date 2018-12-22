package org.finchley.study.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	private static MessageDigest getDigest() {
		
		
		 MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return md5;
	}

	
	private static String toHex(byte[] b) {
		
		StringBuilder sb = new StringBuilder();
		for(byte x : b) {
			if(x<16 && x>=0)
				sb.append('0');
			sb.append(Integer.toHexString(0xff & x));
		}
		return sb.toString();
	}
	
	
	public static String string2MD5(String str) {
		
		MessageDigest md = getDigest();
		if(md!=null) {
			byte[] b = md.digest(str.getBytes());
			return toHex(b);
			
		}
		throw new IllegalStateException("not found MD5 MessageDigest in system.");
	}
	
	public static String file2MD5(String filePath) {
		
		MessageDigest md = getDigest();
		if(md!=null) {
			
			InputStream ins = null;
			try {
				ins = new FileInputStream(filePath);
				byte[] buff = new byte[1024];
				int len = 0;
				while((len = ins.read(buff))>0) {
					md.update(buff, 0, len);
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(ins!=null)try {ins.close();}catch(Exception e) {}
			}
			
			
			byte[] b = md.digest();
			return toHex(b);
			
		}
		throw new IllegalStateException("not found MD5 MessageDigest in system.");
		
		
	}
	
}
