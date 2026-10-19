package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Command;
import service.MainService;

@WebServlet("/main/*")
public class MainController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MainController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doAction(request, response);
    }

    protected void doAction(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String page = null;
    	Command command = null;

        String pathInfo = request.getPathInfo();
        System.out.println("pathInfo : " + pathInfo);

        if ("/index".equals(pathInfo)) {
            command = new MainService();
            page = "/index.jsp";
        }
            
            
        boolean needForward = command.doCommand(request, response);
            
        if(needForward && page != null) {
    			request.getRequestDispatcher(page).forward(request, response);
            
       
        }
    }

      
 
}
