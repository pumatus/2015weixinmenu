package com.hhd.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.dao.OpenDao;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		OpenDao fm = new OpenDao();
		String sessionOpenId = request.getSession()
				.getAttribute("sessionOpenId").toString();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");

		boolean yn = fm.getUser(phone, password);
		if (yn == true) {
			boolean update=fm.updateOpenId(sessionOpenId, phone);
			request.setAttribute("update", update);
			request.setAttribute("sessionOpenId", sessionOpenId);
			System.out.println("OK");
			request.getRequestDispatcher("/OK.jsp").forward(request, response);
		} else {
			System.out.println("NO");
			request.getRequestDispatcher("/NO.jsp").forward(request, response);
		}

	}

}
