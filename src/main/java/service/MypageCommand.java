package service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BoardDao;
import model.BoardDto;
import model.MemberDto;
import model.ReplyDao;
import model.ReplyDto;

public class MypageCommand implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		//session 객체 생성
		//loginMember 속성을 MemberDto 객체로 형변환
		//loginMember가 null과 같으면 로그인 페이지로 이동시키고 그렇지 않으면 loginMember에 있는 id를 변수에 저장
		//id를 ReplyDao, BoardDao 객체를 생성하고 메서드를 호출하면서 id를 전송
		//메서드를 처리하고 리턴된 가변배열의 값 3개를 속성에 담는다
		
		 // session 객체 생성
        HttpSession session = request.getSession();

        // loginMember 속성을 MemberDto 객체로 형변환
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        // loginMember가 null과 같으면 로그인 페이지로 이동
        if (loginMember == null) {
            response.sendRedirect("로그인페이지경로");
            return false;
        }

        // loginMember에 있는 id를 변수에 저장
        String id = loginMember.getId();

        // ReplyDao, BoardDao 객체 생성
        ReplyDao replyDao = new ReplyDao();
        BoardDao boardDao = new BoardDao();

        // id를 전송하면서 메서드 호출
        // 메서드의 반환 타입과 이름은 실제 DAO 코드에 맞게 작성
        List<BoardDto> myPosts = boardDao.selectListByWriter(id);
        List<BoardDto> myLike = boardDao.selectLikedList(id);
        List<ReplyDto> myReplies = replyDao.selectListByWriter(id);

        // 리턴된 값을 속성에 담기
        request.setAttribute("myPosts", myPosts);
        request.setAttribute("myReplies", myReplies);
        request.setAttribute("myLike", myLike);

		
		
		
		
		return true;
	}

}
