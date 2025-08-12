/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.DAO;
import objects.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    /**
     * 
     * Remember to change Parameter names
     * 
     */
    
    private static final String USERNAME = "userID";
    private static final String PASSWORD = "password";
    private static final String ERROR_MESSAGE = "errorMessage";
    
    //FAILURE_URL loops failed login attempts back at the login page.
    private static final String FAILURE_URL = "login.jsp";
    
    //SUCCESS_URL redirects successful login attempts to some page.
    private static final String SUCCESS_URL = "welcome.jsp";
    
    //SESSION USER saves an attribute from the User Object to display on Welcome page.
    private static final String SESSION_USER_ATTRIBUTE = "user";
    
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String userID = request.getParameter(USERNAME);
            String password = request.getParameter(PASSWORD);
            
//            //OPTIONAL CHECKS
//            if(userID == null || userID.isEmpty())
//            {
//                request.setAttribute(ERROR_MESSAGE, "userID can't be empty");
//                request.getRequestDispatcher(FAILURE_URL).forward(request, response);            
//            }
//            if(password == null || userID.isEmpty())
//            {
//                request.setAttribute(ERROR_MESSAGE, "password can't be empty");
//                request.getRequestDispatcher(FAILURE_URL).forward(request, response);            
//            }
            
            DAO dao = new DAO();
            User user = new User(userID,password);
            ArrayList<User> result = dao.validateUser(user);
            
            if (result.isEmpty()) //No valid user is found.
            {
                request.setAttribute(ERROR_MESSAGE, "Incorrect userID or password");
                request.getRequestDispatcher(FAILURE_URL).forward(request, response); 
            }
            else 
            {
                HttpSession session = request.getSession();
                session.setAttribute(SESSION_USER_ATTRIBUTE, userID);
                request.getRequestDispatcher(SUCCESS_URL).forward(request, response);
            }
                
            
        } catch (Exception e) {
            e.printStackTrace();
        }
 
           
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
