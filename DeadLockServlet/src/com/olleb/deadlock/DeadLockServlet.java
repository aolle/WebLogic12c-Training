package com.olleb.deadlock;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Àngel Ollé Blázquez (web@olleb.com)
 * 
 *         Servlet implementation class DeadLockServlet
 * 
 */
public class DeadLockServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2890102269927288695L;
	/**
	 * 
	 */

	private static Object resource1 = new Object(),
			resource2 = new Object();
	private static int sleep = 15000;
	private static boolean psswitch = false;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		psswitch ^= true;

		try {
			if (psswitch) {
				// Thread1 lock resource1
				synchronized (resource1) {
					// grace time to Thread2 for lock resource2
					Thread.sleep(sleep);
					// Thread1 wait for resource2 locked by Thread2
					synchronized (resource2) {

					}
				}
			} else {
				// Thread2 lock resource2
				synchronized (resource2) {
					// Thread2 wait for resource1 locked by Thread1
					synchronized (resource1) {
					}
				}
			}

		}catch (InterruptedException e){
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


}
