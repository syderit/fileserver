package com.syder.itservices.filesmanager.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syder.itservices.filesmanager.db.DBAccess;
import com.syder.itservices.filesmanager.utils.Utils;

/**
 * Servlet implementation class CheckUser
 */
public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckUser BEGIN");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("param username=" + username);
		System.out.println("param password=" + password);
		try {
			boolean user_exists = DBAccess.checkUser(username, password);
			System.out.println("user_exists=" + user_exists);
			
			String result = user_exists ? "1" : "0";
			request.setAttribute("result", result);
			request.getRequestDispatcher("pages/checkUserResult.jsp").forward(request, response);
		} catch (Exception e) {
			System.err.println("Se ha producido una excepcion: " + Utils.getStackTrace(e));
		}
		
		System.out.println("CheckUser END");
	}

}
