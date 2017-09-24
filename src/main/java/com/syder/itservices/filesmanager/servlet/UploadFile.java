package com.syder.itservices.filesmanager.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.syder.itservices.filesmanager.utils.Utils;

/**
 * Servlet implementation class UploadFile
 */
@MultipartConfig
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(UploadFile.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFile() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Only POST is supported for this application");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("BEGIN");
		logger.debug(Utils.getRequestInfo(request));
		
		try {
		    UUID uuid = UUID.randomUUID();
		    logger.info("generado uuid: " + uuid);
		    String filelocation = "D:/temp/file_" + uuid + ".txt";
			
			Part filePart = request.getPart("file");
			logger.debug("filePart: " + filePart);
		    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		    logger.info("recuperado nombre de fichero: " + fileName);
		    InputStream fileContent = filePart.getInputStream();
		    
		    byte[] buffer = new byte[fileContent.available()];
		    fileContent.read(buffer);
		 
		    File targetFile = new File(filelocation);
		    targetFile.createNewFile();
		    OutputStream outputStream = new FileOutputStream(targetFile);
		    outputStream.write(buffer);
		    outputStream.close();
		    request.setAttribute("newfileurl", "https://www.servicios-syder.es/getFile.jsp?idFile=" + uuid);
			request.getRequestDispatcher("pages/UploadResult.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
		}
		
		logger.info("END");
	}

}
