package service;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MemberDao;
import model.MemberDto;

public class MemberInsert implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//get방식으로 요청이 들어오면 다음 코딩을 실행하지 않게 된다 
		if("get".equalsIgnoreCase(request.getMethod())) {//request.getMethod() 요청방식
			return true;
		}
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		
		MemberDto dto = new MemberDto();
		dto.setId(id);
		dto.setPwd(hashedPwd);
		dto.setNickname(nickname);
		dto.setEmail(email);
		
		MemberDao dao = new MemberDao();
		dao.memberInsert(dto);
		
		response.sendRedirect(request.getContextPath()+"/member/login");
		//sendRedirect 가 있으면 false, 없으면 ture를 라턴함
		
		return false;
	}

}
