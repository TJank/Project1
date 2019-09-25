package com.revature.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getMethod().equals("GET")) {
			ObjectMapper mappy = new ObjectMapper();

			response.getOutputStream()
					.write(mappy.writeValueAsBytes(FrontControllerHelper.processGet(request, response)));
		}

		if (request.getMethod().equals("POST")) {
			// the user has "posted" data to the server
			// should only be LOGIN & NEW REQUEST functionality
			Object returnedObj = FrontControllerHelper.processPost(request, response);
			if(returnedObj != null) {
				if(returnedObj.equals("Login error. Email or password incorrect")) {
					// send user to same page with Error message???
					RequestDispatcher dispatchy = request.getRequestDispatcher("./login.html");
					ObjectMapper mappy = new ObjectMapper();

					response.getOutputStream()
							.write(mappy.writeValueAsBytes("Error: Email or Password Incorrect"));
					dispatchy.forward(request, response);
//					response.sendRedirect("./login.html");
				} else {
					if(returnedObj.equals("Error Submitting New Request")) {
						RequestDispatcher dispatchy = request.getRequestDispatcher("./views/reimbursementForm.html");
						ObjectMapper mappy = new ObjectMapper();

						response.getOutputStream()
								.write(mappy.writeValueAsBytes("Error Submitting New Request"));
						dispatchy.forward(request, response);
					}
					// just redirect because we do not need to send data after post
					// AJAX will handle retrieving / populating with session details.
					response.sendRedirect("/TJJProject1/views/home.html");
				}
				
			} 
//			response.getOutputStream().print(mappy.writeValueAsString(FrontControllerHelper.processPost(request, response)));
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// do stuff??
		// call doGet??
		doGet(request, response);
	}

}
