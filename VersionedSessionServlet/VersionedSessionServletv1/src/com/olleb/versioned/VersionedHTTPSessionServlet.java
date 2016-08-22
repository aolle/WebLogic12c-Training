package com.olleb.versioned;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Àngel Ollé Blázquez (web@olleb.com)
 * 
 * Servlet versioned implementation with manifest. 
 * Example to try weblogic hot deployment and http sessions.
 * param = 1  : login.  create http session.
 * param != 1 : logout. invalidate http session. Weblogic will retire old app. version.
 * 
 */
public class VersionedHTTPSessionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897802459345210017L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int param  = Integer.parseInt(request.getParameter("param"));
		boolean login = (param == 1);
		HttpSession session;
		
		if(login) {
			session = request.getSession();
			if(request.isRequestedSessionIdValid()) {
				response.getWriter().print("Already logged!");
			} else {
				response.getWriter().print("Logged in. HttpSession created");
				response.getWriter().print(session.getId());
			}
		} else {
			session = request.getSession(false);
			if(session != null && request.isRequestedSessionIdValid()) {
				request.getSession().invalidate();
				response.getWriter().print("Logged out");
			} else
				response.getWriter().print("Not logged in");						
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
