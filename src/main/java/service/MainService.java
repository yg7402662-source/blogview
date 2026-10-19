package service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BoardDto;
import model.MainDao;

public class MainService implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		
		List<BoardDto> list = new MainDao().selectPopularMain();
		List<BoardDto> list2 = new MainDao().selectFastMain();
		
		System.out.println("인기글 조회 결과: " + list.size());
		System.out.println("최신글 조회 결과: " + list2.size());
		
		request.setAttribute("popListMain", list);
		request.setAttribute("fastListMain", list2);
		
		return true;
	}

}
