package service;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MemberDao;
import model.MemberDto;

public class LoginCommand implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if("get".equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		MemberDao dao = new MemberDao();
		MemberDto dto = dao.login(id);
		
		if(dto != null && BCrypt.checkpw(pwd, dto.getPwd())) {
			HttpSession session = request.getSession(); //세션 만들기
			session.setAttribute("loginMember", dto);
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}else {
			request.setAttribute("loginFail", "아이디 또는 비밀번호를 확인하세요");
			//어떤 처리 된 값을 jsp로 출력하려면 request 또는 session 속석에 저장해서 forwarding 한다
			return true;
		}
		
	}
	
	
	
	
	

}
