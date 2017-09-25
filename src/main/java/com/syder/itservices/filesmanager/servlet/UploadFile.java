package com.syder.itservices.filesmanager.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.syder.itservices.filesmanager.db.DBAccess;
import com.syder.itservices.filesmanager.utils.Utils;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet(name = "UploadFile", urlPatterns = {"/UploadFile"})
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
			// find file received
			String originalFilename = request.getParameter("originalfilename");
		    logger.debug("recibido fichero: " + originalFilename);
			Part filePart = request.getPart("file");
		    String submittedFileName = filePart.getSubmittedFileName();
		    logger.debug("submittedFileName: " + submittedFileName);
		    
		    // create or use right folder for this new upload
		    Date date = new Date();
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM");
		    String subfolder = formatter.format(date);
		    String foldername = "SYDER-DATA" + "/" + subfolder;
		    logger.info("using folder: " + foldername);
		    File folder = new File(foldername);
		    folder.mkdir();
		    
		    // generate a new uid
		    UUID uuid = UUID.randomUUID();
		    String uid = uuid.toString();
		    logger.info("generado uuid: " + uid);
		    
		    // stablish the right extension
		    int extensionPoint = originalFilename.lastIndexOf(".");
			String extension = originalFilename.substring(extensionPoint+1);
			if (extensionPoint == -1 || extension == null || extension.trim().equals("")) {
				logger.debug("no se ha podido recuperar extensión: asumimos xml");
				extension = "xml";
			}
		    logger.debug("extension: " + extension);
		    
		    // determine final file location
		    String fileLocation = foldername + "/" + uid + "." + extension;
		    logger.info("generated new file location=" + fileLocation);
		    File targetFile = new File(fileLocation);
		    targetFile.createNewFile();
		    logger.info("new file created");
		    
		    // store new file
		    String catalinaBase = System.getProperty("catalina.base");
		    logger.debug("catalinaBase=" + catalinaBase);
		    logger.info("new file will be stored at: " + targetFile.getAbsolutePath());
		    InputStream fileContent = filePart.getInputStream();
		    byte[] buffer = new byte[fileContent.available()];
		    fileContent.read(buffer);
		    OutputStream outputStream = new FileOutputStream(targetFile);
		    outputStream.write(buffer);
		    outputStream.close();
		    logger.info("file saved to disk");
		    
		    // update in db
		    int id = DBAccess.insertNewFile(originalFilename, uid, fileLocation);
		    logger.info("new file saved in db id=" + id);
		    
		    // return result
		    request.setAttribute("newfileurl", "https://www.servicios-syder.es/fileserver/getFile.jsp?id=" + uid);
			request.getRequestDispatcher("pages/UploadResult.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
		}
		
		logger.info("END");
	}

}
