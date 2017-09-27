package com.syder.itservices.filesmanager.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Locale;
import java.util.ResourceBundle;

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
@WebServlet(name = "GetFile", urlPatterns = {"/GetFile"})
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(GetFile.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFile() {
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
		ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", new Locale("es", "ES"));
		
		try {
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String password = request.getParameter("password");

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
						request.setAttribute("error", messages.getString("getfile.error.ftpfile"));
						request.getRequestDispatcher("getFile.jsp").forward(request, response);
					}
				} else {
					logger.warn("wrong id file: " + id);
					request.setAttribute("error", messages.getString("getfile.error.file"));
					request.setAttribute("id", id);
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			} else {
				logger.info("wrong user credentials: " + username + "/" + password);
				request.setAttribute("error", messages.getString("getfile.error.user"));
				request.setAttribute("id", id);
				request.getRequestDispatcher("getFile.jsp").forward(request, response);
			}
		} catch (Exception e) {
			logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
			request.setAttribute("error", messages.getString("global.error"));
			request.getRequestDispatcher("getFile.jsp").forward(request, response);
		}
		
		logger.info("END");
	}

}
