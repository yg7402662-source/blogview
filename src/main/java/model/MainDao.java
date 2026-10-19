package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBmanager;

public class MainDao {
	
	//인덱스 페이지 인기글 3개 출력
		public List<BoardDto> selectPopularMain(){
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = "select * from (select b.bno, b.title, m.nickname as writer_nickname, to_char(b.reg_date,'YYYY-MM-DD') as reg_date,\r\n"
					+ "    b.view_cnt, b.like_cnt, b.img_path\r\n"
					+ "from board b\r\n"
					+ "join member m\r\n"
					+ "on b.writer = m.id\r\n"
					+ "order by b.like_cnt desc, b.view_cnt desc, b.bno desc)\r\n"
					+ "where rownum <= 3";
			
			List <BoardDto> list = new ArrayList<BoardDto>();
			try {
				conn = DBmanager.getInstance();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					BoardDto dto = new BoardDto();
					dto.setBno(rs.getInt("bno"));
					dto.setTitle(rs.getString("title"));
					dto.setWriterNickname(rs.getString("writer_nickname"));
					dto.setRegDate(rs.getString("reg_date"));
					dto.setViewCnt(rs.getInt("view_cnt"));
					dto.setLikeCnt(rs.getInt("like_cnt"));
					dto.setImgPath(rs.getString("img_path"));
					list.add(dto);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs!=null)rs.close();
					if(pstmt!=null)pstmt.close();
					if(conn!=null)conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		
		
		//인덱스 페이지 최신글 3개 출력
				public List<BoardDto> selectFastMain(){
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					
					String sql = "select * from (select b.bno, b.title, m.nickname as writer_nickname, to_char(b.reg_date,'YYYY-MM-DD') as reg_date,\r\n"
							+ "    b.view_cnt, b.like_cnt, b.img_path\r\n"
							+ "from board b\r\n"
							+ "join member m\r\n"
							+ "on b.writer = m.id\r\n"
							+ "order by b.reg_date desc, b.bno desc)\r\n"
							+ "where rownum <= 3";
					
					List <BoardDto> list = new ArrayList<BoardDto>();
					try {
						conn = DBmanager.getInstance();
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						while(rs.next()) {
							BoardDto dto = new BoardDto();
							dto.setBno(rs.getInt("bno"));
							dto.setTitle(rs.getString("title"));
							dto.setWriterNickname(rs.getString("writer_nickname"));
							dto.setRegDate(rs.getString("reg_date"));
							dto.setViewCnt(rs.getInt("view_cnt"));
							dto.setLikeCnt(rs.getInt("like_cnt"));
							dto.setImgPath(rs.getString("img_path"));
							list.add(dto);
						}
						
					}catch(Exception e) {
						e.printStackTrace();
					}finally {
						try {
							if(rs!=null)rs.close();
							if(pstmt!=null)pstmt.close();
							if(conn!=null)conn.close();
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
					return list;
				}
		
		
		
		
		

}
