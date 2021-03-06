//
//  GetParameters.java
//  search
//
//  Created by sule
//
package com.oss;

import com.sun.prism.shader.AlphaOne_RadialGradient_AlphaTest_Loader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class GetParameters extends HttpServlet {

	private static final long serialVersionUID = 1L;

	NLPProcesses process = new NLPProcesses();

	public GetParameters() throws SQLException, ClassNotFoundException {
        super();
        // TODO Auto-generated constructor stub
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");

        String repository = request.getParameter("repo");

        if (request.getParameter("repo").equalsIgnoreCase("github") && request.getParameter("pl") == null ) {
            request.setAttribute("err", "Please select programming language for Github and try again.");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request,response);
        }

        String selectedPL = request.getParameter("pl");
        String topics = request.getParameter("topic");

        System.out.println("Repo: " + repository);
        System.out.println("PL: " + selectedPL);
        System.out.println("Features: " + topics);

        ArrayList<ArrayList<String>> results = process.processAndResults(topics,selectedPL,repository);
        
        if (results.size() == 0) {
            ArrayList<String> repo = new ArrayList<String>() ;
        	repo = new ArrayList<String>();
        	repo.add("No results found.");
        	repo.add("");
        	results.add(repo);
        } else {
            ArrayList<String> repo = new ArrayList<String>() ;
        	repo.add("Url");
        	repo.add("Probability");
        	results.add(0, repo);
        }
       
        request.setAttribute("results", results);
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request,response);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        doGet(request,response);
    }
} 
