package com.syder.itservices.filesmanager.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	public static String getStackTrace(Throwable t) {
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    t.printStackTrace(printWriter);
	    return result.toString();
	  }
	
	public static String getSessionInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder("\nSESSION INFO:\n\n");
		
		Enumeration<String> enames = request.getHeaderNames();
		while (enames.hasMoreElements()) {
			String name = (String) enames.nextElement();
			String value = request.getHeader(name);
			sb.append("\theader: ").append(name).append("=").append(value).append("\n");
		}
		
		enames = request.getSession().getAttributeNames();
		while (enames.hasMoreElements()) {
			String name = (String) enames.nextElement();
			String value = request.getSession().getAttribute(name) == null ? "<null>" : request.getSession().getAttribute(name).toString();
			sb.append("\tsession attr: ").append(name).append("=").append(value).append("\n");
		}
		
		sb.append("\nSESSION INFO END");
		return sb.toString();
	}
}
