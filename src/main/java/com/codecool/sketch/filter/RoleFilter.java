/*package com.codecool.web.filter;


import com.codecool.web.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter("/rolefilter")
public class RoleFilter implements Filter {

    private ServletContext context;
    private List<String> studentURLs = new ArrayList<>(Arrays.asList("/student", "/curriculum-student", "/stats"));
    private List<String> mentorURLs = new ArrayList<>(Arrays.asList("student.html", "curriculum-student.html", "stats.html"));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        System.out.println("RoleFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String[] requestedURIArray = req.getRequestURI().split("/");
        String requestedURI = requestedURIArray[requestedURIArray.length-1];
        *//*
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if(user instanceof Student && studentURLs.contains(requestedURI)) {
                chain.doFilter(request, response);
            } else if(user instanceof Mentor  && mentorURLs.contains(requestedURI)) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("noauth.jsp");
            }
        }
        *//*

    }

}*/
