package com.syder.itservices.filesmanager.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class Utils {
	public static String getStackTrace(Throwable t) {
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    t.printStackTrace(printWriter);
	    return result.toString();
	  }
}
