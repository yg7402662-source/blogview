package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Command;
import service.IdCheckCommand;
import service.LoginCommand;
import service.LogoutCommand;
import service.MemberInsert;
import service.MypageCommand;


@WebServlet("/member/*")
//URL을 통해서 요청하는 주소가 /member로 시작하는 모든 요청(*)은 이 클래스가 자동으로 실행한다
public class MemberController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	//직렬화 관련 버전 번호
       
    public MemberController() {
    	//생성자
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(): get방식으로 넘어온 데이터를 처리하는 메서드. 주소창에 id pw 가 보이는 방식
		//request: 사용자가 보내온 데이터를 요청 할 때 사용
		//response:사용자가 처리한 데이터를 응답 할 때 사용
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doPost(): post방식으로 넘어온 데이터를 처리하는 메서드. 주소창에 id pw 가 안보이는 방식
		doAction(request,response);

	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String pathInfo = request.getPathInfo();
		//localhost:8088/blogProject/member/join
		System.out.println("pathInfo: "+pathInfo);
		
		String page = null;
		Command command = null;
		
		switch(pathInfo) {
		case "/join" :
			command = new MemberInsert(); //다형성. 부모타입의 참조변수로 자식타입의 객체를 받을 수 있다
			page="/mem/join.jsp";
			break;
		case "/idCheck" :  //localhost:8088/blogProject/member/idCheck
			command = new IdCheckCommand();
			page = null;
			break;
		case "/login" :  //localhost:8088/blogProject/member/login
			command = new LoginCommand();
			page="/mem/login.jsp";
			break;
		case "/logout" :
			command = new LogoutCommand();
			page=null;
			break;
		case "/mypage" :
			command = new MypageCommand();
			page = "/mem/mypage.jsp";
			break;
		}
		
		boolean needForward = command.doCommand(request, response);
		
		if(needForward&&page != null) {
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);   
		}
	}

}
