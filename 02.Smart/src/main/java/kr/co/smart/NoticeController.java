package kr.co.smart;

import kr.co.smart.common.CommonUtility;
import kr.co.smart.notice.NoticeService;
import kr.co.smart.notice.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller @RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService service;
   @Autowired private CommonUtility common;

//    공지글 목록 화면 요청
    @RequestMapping("/list")
    public String list(Model model, HttpSession session){
        session.setAttribute("category","no");
//        DB에서 조회한 정보를 화면에 출력할 수 있도록 Model 객체에 담기
        model.addAttribute("list", service.notice_list());
        return "notice/list";
    }

//    공지 글 정보 화면 요청
    @RequestMapping("/info")
    public String info(int id, Model model){
        service.notice_read(id);
//        선택한 공지 글 정보를 DB에서 조회해 와 화면에 출력 할 수 있도록 Model 객체에 담기
        model.addAttribute("vo", service.notice_info(id));
        model.addAttribute("crlf", "\r\n");

        return "notice/info";
    }

//    공지 글 등록 화면 요청
    @RequestMapping("/register")
    public String register(){
        return "notice/register";
    }

//    공지 글 등록 처리 요청
    @RequestMapping("/insert")
    public String insert(NoticeVO vo, MultipartFile file, HttpServletRequest request){
//        파일을 첨부한 경우
        if(!file.isEmpty()){
            vo.setFilename(file.getOriginalFilename());
           vo.setFilepath(common.fileUpload("notice", file, request));

        }
//        화면에서 입력한 정보로 DB에 신규 저장처리 후 목록화면으로 연결\
        service.notice_register(vo);
        return "redirect:list";
    }

//    공지 글 첨부 파일 다운로드 처리 요청
    @RequestMapping("/download")
    public void download(int id, HttpServletRequest request, HttpServletResponse response){
//        해당 글의 정보를 DB에서 조회해 와
//        서버의 물리적 영역에 있는 파일을 클라이언트 컴에 다운로드 처리
      NoticeVO vo = service.notice_info(id);
        try {
            common.fileDownload(vo.getFilename(), vo.getFilepath(), request, response);
        } catch (Exception e) {
        }
    }

    @RequestMapping("/delete")
    public String delete(int id, HttpServletRequest request){
//        해당 글을 DB에서 삭제한 후 목록 화면으로 연결
//        첨부 파일의 물리적인 경로를 찾아 삭제 할 수 있도록 미리 조회해 둔다
        NoticeVO vo = service.notice_info(id);

       if(service.notice_delete(id) == 1 ) {
            common.fileDelete(vo.getFilepath(), request);
       }
        return "redirect:list";
    }
}
