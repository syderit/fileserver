package com.syder.itservices.filesmanager.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.syder.itservices.filesmanager.db.DBAccess;
import com.syder.itservices.filesmanager.utils.Utils;

/**
 * Servlet implementation class GetFile
 */
@WebServlet(name = "GetFileBasicAuth", urlPatterns = {"/GetFileBasicAuth/*"})
public class GetFileBasicAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(GetFileBasicAuth.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFileBasicAuth() {
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
			String auth = request.getHeader("Authorization");
			logger.info("auth=" + auth);
			
			String userCredentials = getUserCredentials(auth);
			if (userCredentials != null) {
				String username = userCredentials.substring(0, userCredentials.indexOf(":"));
				String password = userCredentials.substring(userCredentials.indexOf(":")+1);
				String id = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
	
				logger.info("requested file id=" + id);
				logger.info("username=" + username);
				logger.info("password=" + password);
				
				int idUser = DBAccess.checkUser(username, password);
				if (idUser != -1) {
					com.syder.itservices.filesmanager.model.File fileDB = DBAccess.getFile(id);
					if (fileDB != null) {
						DBAccess.saveAccess(idUser, fileDB.getId());
						String mimeType = URLConnection.guessContentTypeFromName(fileDB.getOriginalname());
						logger.info("mimeType=" + mimeType);
						if (mimeType == null) {
							logger.info("manually set mime type to application/pdf");
							mimeType = "application/pdf";
						}
						
						File file = new File(fileDB.getLocation());
						if (file.exists()) {
							logger.debug("file exists in ftp server");
							OutputStream out = response.getOutputStream();
							FileInputStream in = new FileInputStream(file);
							byte[] buffer = new byte[4096];
							int length;
							while ((length = in.read(buffer)) > 0){
								out.write(buffer, 0, length);
							}
							logger.debug("file read");
							response.setContentType(mimeType);
							response.setHeader("Content-disposition","attachment; filename=" + fileDB.getOriginalname());
							
							in.close();
							out.flush();
						} else {
							logger.error("El fichero accedido ya no existe en el servidor FTP");
							response.sendError(HttpServletResponse.SC_GONE);
						}
					} else {
						logger.warn("wrong id file: " + id);
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				} else {
					logger.info("wrong user credentials: " + username + "/" + password);
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
			} else {
				logger.info("invalid authentication method");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		logger.info("END");
	}
	
	private String getUserCredentials(String auth) throws IOException {
        if (auth == null || !auth.toUpperCase().startsWith("BASIC ")) {
            return null;
        }
        // Get encoded user and password, comes after "BASIC "
        String userpassEncoded = auth.substring(6);
        byte[] decodedBytes = Base64.getDecoder().decode(userpassEncoded);
        String decoded = new String(decodedBytes);
     
        logger.debug("decodedBytes=" + decodedBytes);
        logger.debug("decoded=" + decoded);
        
        return decoded;
    }

}
