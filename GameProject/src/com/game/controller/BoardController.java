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
	 * �۸�� �����ϱ�
	 * *********************************************************************************************************/
	
	@RequestMapping(value="/boardList", method= RequestMethod.GET)
	public String boardList(Model model) {
		logger.info("boardList ȣ�� ����");
		
		List<BoardVO> boardList = boardService.boardList();
		model.addAttribute("boardList", boardList);
		
		return "board/boardList";
	}
	
	/************************************************************************************************************
	 * �۾��� �� ����ϱ�
	 * *********************************************************************************************************/
	
	@RequestMapping(value="/writeForm.do")
	public String writeForm() {
		logger.info("writeForm ȣ�� ����");
		
		return "board/writeForm";
	}
	
	/************************************************************************************************************
	 * �۾��� �����ϱ�
	 * *********************************************************************************************************/
/*	@RequestMapping(value="/boardInsert.do", method=RequestMethod.POST)
	public String boardInsert(@ModelAttribute BoardVO bvo) {
		logger.info("boardInsert ȣ�� ����");
		
		int result = 0;
		String url ="";
		
		result = boardService.boardInsert(bvo);
		if(result == 1) {
			url = "/board/boardList.do";
		}
		return "redirect:"+url;
	}*/
	
	/************************************************************************************************************
	 * ÷������ ���ε�
	 * @throws IOException 
	
	************************************************************************************************************/
	@RequestMapping(value="/boardInsert",method=RequestMethod.POST)
	public String boardInsert(@ModelAttribute BoardVO bvo, HttpServletRequest request) throws IllegalStateException, IOException {
		logger.info("boardInsert ȣ�� ����");
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
	  
	 * �� �� ������ ����
	 * *********************************************************************************************************/
	@RequestMapping(value="/boardDetail.do", method=RequestMethod.GET)
	public String boardDetail(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardDetail ȣ�� ����");
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
	 * ��й�ȣ Ȯ��
	 * @param b_num
	 * @param b_pwd
	 * @return int
	 * ���� : @ResponseBody�� ���޵� �並 ���ؼ� ����ϴ� ���� �ƴ϶� HTTP Response Body�� ���� ����ϴ� ����̴�.
	 * *********************************************************************************************************/
	
	@ResponseBody		//������̼� �߰� (����� ������ ������)
	@RequestMapping(value="/pwdConfirm.do", method=RequestMethod.POST)
	public String pwdConfirm(@ModelAttribute BoardVO bvo) {
		logger.info("pwdConfirm ȣ�� ����");
		
		//�Ʒ� �������� �Է� ������ ���� ���°� ����(1 or 0)
		int result = 0;
		result = boardService.pwdConfirm(bvo);
		logger.info("result = " + result);
		
		return result+"";
	}
	
	/************************************************************************************************************
	 * �� ���� ����
	 * *********************************************************************************************************/
	
	@RequestMapping(value="/updateForm.do", method=RequestMethod.POST)
	public String updateForm(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("updateForm ȣ�� ����");
		logger.info("b_num = " + bvo.getB_num());
		
		BoardVO updateData = new BoardVO();
		updateData = boardService.boardDetail(bvo);
		
		model.addAttribute("updateData", updateData);
		return "board/updateForm";
	}
	
	/*@RequestMapping(value="/boardUpdate.do", method=RequestMethod.POST)
	public String boardUpdate(@ModelAttribute BoardVO bvo) {
		logger.info("boardUpdate ȣ�� ����");
		
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
		logger.info("boardUpdate ȣ�� ����");
		
		int result = 0;
		String url="";
		String b_file="";
		
		if(!bvo.getFile().isEmpty()) {
			logger.info("==========b_file = " + bvo.getB_file());
			FileUploadUtil.fileDelete(bvo.getB_file(), request);
			b_file = FileUploadUtil.fileUpload(bvo.getFile(), request);
			bvo.setB_file(b_file);
		} else {
			logger.info("÷������ ����");
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
	 * �� ���� ����
	 * *********************************************************************************************************/
	
/*	@RequestMapping(value="/boardDelete.do", method=RequestMethod.POST)
	public String boardDelete(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardDelete ȣ�� ����");
		
		//�Ʒ� �������� �Է� ������ ���� ���°� ����ϴ�.(1 or 0)
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
		logger.info("boardDelete ȣ�� ����");
		
		//�Ʒ� �������� �Է� ������ ���� ���°� ����ϴ�.(1 or 0)
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
