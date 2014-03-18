package com.tutorconnect.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String GUEST_PAGE="/GuestHome.jsp";
	private static String USER_PAGE="/UserHome.jsp";
	private static String ABOUT_PAGE="/About.jsp";
	private static String CONTACT_PAGE="/Contact.jsp";
	Controller controller = new Controller();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Servlet get method implementation
		String uri = request.getRequestURI();
		String pageName = uri.substring(uri.lastIndexOf("/")+1);
		
		@SuppressWarnings("unchecked")
	    Map parameters = request.getParameterMap();
		if (parameters.containsKey("load")){
				//First function call to load the home page
				controller.loadWebpage();
			
			} else if (parameters.containsKey("login")){
				//Call for login method 
				pageName = USER_PAGE;
			} else if (parameters.containsKey("search")){
		    	//Call for search method, maintain same pageName
		    } else if (parameters.containsKey("about")){
			    //Load about page
		    	pageName = ABOUT_PAGE;
		    } else if (parameters.containsKey("contact")){
			    //Load contact page
		    	pageName = CONTACT_PAGE;
		    } else if (parameters.containsKey("home")){
			    //Refresh home page, maintain same page
		    } else {
		    	//Refresh home page if no action specified
		    }
		    RequestDispatcher view = request.getRequestDispatcher(pageName);
		    view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
