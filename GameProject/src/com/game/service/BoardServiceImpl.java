package com.spring.board.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.board.dao.BoardDao;
import com.spring.board.vo.BoardVO;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	Logger logger = Logger.getLogger(BoardServiceImpl.class);
	
	@Autowired
	private BoardDao boardDao;
	
	//글 목록 구현
	@Override
	public List<BoardVO> boardList() {
		List<BoardVO> myList = null;
		myList = boardDao.boardList();
		return myList;
	}
	
	//글 쓰기 구현
	@Override
	public int boardInsert(BoardVO bvo) {
		int result = 0;
		result = boardDao.boardInsert(bvo);
		return result;
	}
	
	//글 상세 페이지 구현
	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		BoardVO detail = null;
		detail = boardDao.boardDetail(bvo);
		return detail;
	}
	
	//비밀번호 확인 구현
	@Override
	public int pwdConfirm(BoardVO bvo) {		
		int result = 0;
		result = boardDao.pwdConfirm(bvo);
		return result;
	}
	
	//글 수정 구현
	@Override
	public int boardUpdate(BoardVO bvo) {
		int result = 0;
		result = boardDao.boardUpdate(bvo);
		return result;
	}
	
	//글 삭제 구현
	@Override
	public int boardDelete(int b_num) {
		int result = 0;
		result = boardDao.boardDelete(b_num);
		return result;
	}
}
