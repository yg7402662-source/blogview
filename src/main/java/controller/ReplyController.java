package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Command;
import service.ReplyDelete;
import service.ReplyModify;
import service.ReplyWrite;

/**
 * Servlet implementation class ReplyController
 */
@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyController() {
        super();
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    		doAction(request, response);
    		}

    protected void doPost(HttpServletRequest request,
             HttpServletResponse response) throws ServletException, IOException {
			doAction(request, response);
			}

		
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String pathInfo = request.getPathInfo();
			//localhost:8088/blogProject/member/join
			System.out.println("pathInfo: "+pathInfo);
			 
			Command command = null;
			//댓글은 view페이지 안에서 비동기식으로 데이터를 주고 받기 떄문에(=ajax) forwarding 할 필요가 없다. 고로 page 변수도 필요 없다
			
			switch(pathInfo) {
			case "/write":
				command = new ReplyWrite();
				break;
			case "/delete":
				command = new ReplyDelete();
				break;
			case "/modify":
				command = new ReplyModify();
				break;
			
			}
			
			command.doCommand(request, response);
			//모든 reply 기능은 ajax 라서 forward 할 필요가 없다
		}

}
