package com.spring.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.vo.BoardVO;

@Repository
public class BoardDaoImpl implements BoardDao {
	
	@Autowired
	private SqlSession session;
	
	//글목록 구현
	@Override
	public List<BoardVO> boardList() {
		return session.selectList("boardList");
	}
	
	//글쓰기 구현
	@Override
	public int boardInsert(BoardVO bvo) {
		return session.insert("boardInsert");
	}
	
	/*글 상세페이지 구현*/
	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		return (BoardVO)session.selectOne("boardDetail");
	}
	
	/*비밀번호 확인 구현*/
	@Override
	public int pwdConfirm(BoardVO bvo) {
		return (Integer)session.selectOne("pwdConfirm");
	}
	
	/*글 수정 구현*/
	@Override
	public int boardUpdate(BoardVO bvo) {
		return session.update("boardUpdate");
	}
	
	/*글 삭제 구현*/
	@Override
	public int boardDelete(int b_num) {
		
		return session.delete("boardDelete", b_num);
	}

}
