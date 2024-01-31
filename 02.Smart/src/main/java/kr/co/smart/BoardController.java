package kr.co.smart;


import kr.co.smart.board.BoardService;
import kr.co.smart.board.BoardVO;
import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.FileVO;
import kr.co.smart.common.PageVO;
import kr.co.smart.notice.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller @RequestMapping("/board")
public class BoardController {
 @Autowired private BoardService service;
 @Autowired private CommonUtility common;
//    방명록 목록화면 요청
    @RequestMapping("/list")
    public String list(PageVO page, Model model, HttpSession session){
//         DB에서 방명록 글을 한 페이지 정보를 조회해와 화면에 출력할 수 있도록 Model에 담기
        session.setAttribute("category", "bo");
       model.addAttribute("page", service.board_list(page));
        return "board/list";
    }

    //    방명록 정보 화면 요청
    @RequestMapping("/info")
    public String info(int id, Model model, PageVO page){
        service.board_read(id);
//        선택한 공지 글 정보를 DB에서 조회해 와 화면에 출력 할 수 있도록 Model 객체에 담기
        model.addAttribute("vo", service.board_info(id));
        model.addAttribute("page", page);
        model.addAttribute("crlf", "\r\n");

        return "board/info";
    }

    //    공지 글 등록 화면 요청
    @RequestMapping("/register")
    public String register(){
        return "board/register";
    }

    @RequestMapping("/insert")
    public String insert(BoardVO vo, MultipartFile[] files, HttpServletRequest request){
//        파일을 첨부한 경우
//        if(!file.isEmpty()){
//            filevo.setFilename(file.getOriginalFilename());
//            filevo.setFilepath(common.fileUpload("board", file, request));
//
//
//        }
//        화면에서 입력한 정보로 DB에 신규 저장처리 후 목록화면으로 연결\
        vo.setFileList(common.multipleFileUpload("board", files, request));
        service.board_register(vo);
        return "redirect:list";
    }

    @RequestMapping("/download")
    public void download(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        해당 파일 정보를 조회해와 클라이언트에 다운로드 하기
       FileVO vo = service.board_file_info(id);
       common.fileDownload(vo.getFilename(), vo.getFilepath(), request, response);
    }

    @RequestMapping("/delete")
    public String delete(int id, PageVO page, HttpServletRequest request, Model model){
//        해당 방명록 글을 DB에서 삭제하기
//        첨부 파일이 있다면 물리적인 파일을 삭제할 수 있도록 파일정보를 조회해둔다
        List<FileVO> list = service.board_file_list(id);
       if(service.board_delete(id) == 1) {
           for(FileVO vo : list){
               common.fileDelete(vo.getFilepath(), request);
           }
       }
//       삭제 후 목록 화면으로 연결
        model.addAttribute("page", page);
        model.addAttribute("id", id);
        model.addAttribute("url","list");
    return "include/redirect";}

    @RequestMapping("/modify")
    public String modify(int id, Model model, PageVO page){

        model.addAttribute("vo",service.board_info(id));
        model.addAttribute("page", page);
        return "board/modify";
    }
}
