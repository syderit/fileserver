package com.syder.itservices.filesmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.syder.itservices.filesmanager.db.DBAccess;
import com.syder.itservices.filesmanager.utils.Utils;

/**
 * Servlet implementation class CheckUser
 */
public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(CheckUser.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUser() {
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
		logger.info("CheckUser BEGIN");
		logger.debug(Utils.getRequestInfo(request));
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		logger.info("param username=" + username);
		logger.info("param password=" + password);
		try {
			boolean user_exists = DBAccess.checkUser(username, password);
			logger.info("user_exists=" + user_exists);
			
			String result = user_exists ? "1" : "0";
			request.setAttribute("result", result);
			request.getRequestDispatcher("pages/checkUserResult.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error("Se ha producido una excepcion: " + Utils.getStackTrace(e));
		}
		
		logger.info("CheckUser END");
	}

}
