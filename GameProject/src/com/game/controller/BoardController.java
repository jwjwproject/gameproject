package com.spring.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.board.service.BoardService;
import com.spring.board.vo.BoardVO;
import com.spring.common.file.FileUploadUtil;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	Logger logger = Logger.getLogger("BoardController.class");
	
	@Autowired
	private BoardService boardService;
	
	/************************************************************************************************************
	 * 글목록 구현하기
	 * *********************************************************************************************************/
	
	@RequestMapping(value="/boardList", method= RequestMethod.GET)
	public String boardList(Model model) {
		logger.info("boardList 호출 성공");
		
		List<BoardVO> boardList = boardService.boardList();
		model.addAttribute("boardList", boardList);
		
		return "board/boardList";
	}
	
	/************************************************************************************************************
	 * 글쓰기 폼 출력하기
	 * *********************************************************************************************************/
	
	@RequestMapping(value="/writeForm.do")
	public String writeForm() {
		logger.info("writeForm 호출 성공");
		
		return "board/writeForm";
	}
	
	/************************************************************************************************************
	 * 글쓰기 구현하기
	 * *********************************************************************************************************/
/*	@RequestMapping(value="/boardInsert.do", method=RequestMethod.POST)
	public String boardInsert(@ModelAttribute BoardVO bvo) {
		logger.info("boardInsert 호출 성공");
		
		int result = 0;
		String url ="";
		
		result = boardService.boardInsert(bvo);
		if(result == 1) {
			url = "/board/boardList.do";
		}
		return "redirect:"+url;
	}*/
	
	/************************************************************************************************************
	 * 첨부파일 업로드
	 * @throws IOException 
	
	************************************************************************************************************/
	@RequestMapping(value="/boardInsert",method=RequestMethod.POST)
	public String boardInsert(@ModelAttribute BoardVO bvo, HttpServletRequest request) throws IllegalStateException, IOException {
		logger.info("boardInsert 호출 성공");
		logger.info("fileName : " + bvo.getFile().getOriginalFilename());
		logger.info("b_title : " + bvo.getB_title());
		
		int result = 0;
		String url="" ;
		
		String b_file = FileUploadUtil.fileUpload(bvo.getFile(), request);
		bvo.setB_file(b_file);
		
		result = boardService.boardInsert(bvo);
		if(result == 1) {
			url = "/board/boardList.do";
		}
		return "redirect:"+url;
	}
	 /*
	  
	 * 글 상세 페이지 구현
	 * *********************************************************************************************************/
	@RequestMapping(value="/boardDetail.do", method=RequestMethod.GET)
	public String boardDetail(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardDetail 호출 성공");
		logger.info("b_num = " + bvo.getB_num());
		
		BoardVO detail = new BoardVO();
		detail = boardService.boardDetail(bvo);
		
		if(detail!=null && (!detail.equals(""))) {
			detail.setB_content(detail.getB_content().toString().replaceAll("\n", "<br>"));
		}
		
		model.addAttribute("detail", detail);
		
		return "board/boardDetail";
	}
	
	/************************************************************************************************************
	 * 비밀번호 확인
	 * @param b_num
	 * @param b_pwd
	 * @return int
	 * 참고 : @ResponseBody는 전달된 뷰를 통해서 출력하는 것이 아니라 HTTP Response Body에 직접 출력하는 방식이다.
	 * *********************************************************************************************************/
	
	@ResponseBody		//어노테이션 추가 (결과를 보여줄 페이지)
	@RequestMapping(value="/pwdConfirm.do", method=RequestMethod.POST)
	public String pwdConfirm(@ModelAttribute BoardVO bvo) {
		logger.info("pwdConfirm 호출 성공");
		
		//아래 변수에는 입력 성공에 대한 상태값 저장(1 or 0)
		int result = 0;
		result = boardService.pwdConfirm(bvo);
		logger.info("result = " + result);
		
		return result+"";
	}
	
	/************************************************************************************************************
	 * 글 수정 구현
	 * *********************************************************************************************************/
	
	@RequestMapping(value="/updateForm.do", method=RequestMethod.POST)
	public String updateForm(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("updateForm 호출 성공");
		logger.info("b_num = " + bvo.getB_num());
		
		BoardVO updateData = new BoardVO();
		updateData = boardService.boardDetail(bvo);
		
		model.addAttribute("updateData", updateData);
		return "board/updateForm";
	}
	
	/*@RequestMapping(value="/boardUpdate.do", method=RequestMethod.POST)
	public String boardUpdate(@ModelAttribute BoardVO bvo) {
		logger.info("boardUpdate 호출 성공");
		
		int result = 0;
		String url="";
		result = boardService.boardUpdate(bvo);
		
		if(result==1){
			url = "/board/boardDetail.do?b_num="+ bvo.getB_num();
		}
		
		return "redirect:"+url;
	}*/
	
	@RequestMapping(value="/boardUpdate.do")
	public String boardUpdate(@ModelAttribute BoardVO bvo, HttpServletRequest request) throws IllegalStateException,IOException {
		logger.info("boardUpdate 호출 성공");
		
		int result = 0;
		String url="";
		String b_file="";
		
		if(!bvo.getFile().isEmpty()) {
			logger.info("==========b_file = " + bvo.getB_file());
			FileUploadUtil.fileDelete(bvo.getB_file(), request);
			b_file = FileUploadUtil.fileUpload(bvo.getFile(), request);
			bvo.setB_file(b_file);
		} else {
			logger.info("첨부파일 없음");
			bvo.setB_file("");
		}
		logger.info("===============b_file = " + bvo.getB_file());
		result = boardService.boardUpdate(bvo);
		

		if(result==1){
			url = "/board/boardDetail.do?b_num="+ bvo.getB_num();
		}
		return "redirect:"+url;
	}
	
	
	/************************************************************************************************************
	 * 글 삭제 구현
	 * *********************************************************************************************************/
	
/*	@RequestMapping(value="/boardDelete.do", method=RequestMethod.POST)
	public String boardDelete(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardDelete 호출 성공");
		
		//아래 변수에는 입력 성공에 대한 상태값 담습니다.(1 or 0)
		int result = 0;
		String url = "";
		
		result = boardService.boardDelete(bvo.getB_num());
		if(result == 1) {
			url="/board/boardList.do";
		}
		
		return "redirect:"+url;		
	}	*/
	
	@RequestMapping(value="/boardDelete")
	public String boardDelete(@ModelAttribute BoardVO bvo, HttpServletRequest request) throws IOException {
		logger.info("boardDelete 호출 성공");
		
		//아래 변수에는 입력 성공에 대한 상태값 담습니다.(1 or 0)
		int result = 0;
		String url = "";
		
		FileUploadUtil.fileDelete(bvo.getB_file(), request);
		result = boardService.boardDelete(bvo.getB_num());
		if(result == 1){
			url="/board/boardList.do";
		}
		return "redirect:"+url;
		
	}
}
