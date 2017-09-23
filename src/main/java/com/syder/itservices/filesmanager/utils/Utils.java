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
	
	public static String getRequestInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder("\nREQUEST INFO:\n\n");
		
		Enumeration<String> enames;
		String name;
		String value;
		
		enames = request.getHeaderNames();
		while (enames.hasMoreElements()) {
			name = (String) enames.nextElement();
			value = request.getHeader(name);
			sb.append("\theader: ").append(name).append("=").append(value).append("\n");
		}
		
		enames = request.getParameterNames();
		while (enames.hasMoreElements()) {
			name = (String) enames.nextElement();
			value = request.getParameter(name) == null ? "<null>" : request.getParameter(name).toString();
			sb.append("\tparam: ").append(name).append("=").append(value).append("\n");
		}
		
		enames = request.getSession().getAttributeNames();
		while (enames.hasMoreElements()) {
			name = (String) enames.nextElement();
			value = request.getSession().getAttribute(name) == null ? "<null>" : request.getSession().getAttribute(name).toString();
			sb.append("\tsession attr: ").append(name).append("=").append(value).append("\n");
		}
		
		sb.append("\nREQUEST INFO END");
		return sb.toString();
	}
}
