package kr.co.smart;


import kr.co.smart.board.BoardCommentVO;
import kr.co.smart.board.BoardService;
import kr.co.smart.board.BoardVO;
import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.FileVO;
import kr.co.smart.common.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService service;
    @Autowired
    private CommonUtility common;

    //    방명록 목록화면 요청
    @RequestMapping("/list")
    public String list(PageVO page, Model model, HttpSession session) {
//         DB에서 방명록 글을 한 페이지 정보를 조회해와 화면에 출력할 수 있도록 Model에 담기
        session.setAttribute("category", "bo");
        model.addAttribute("page", service.board_list(page));
        return "board/list";
    }

    //    방명록 정보 화면 요청
    @RequestMapping("/info")
    public String info(int id, Model model, PageVO page) {
        service.board_read(id);
//        선택한 공지 글 정보를 DB에서 조회해 와 화면에 출력 할 수 있도록 Model 객체에 담기
        model.addAttribute("vo", service.board_info(id));
        model.addAttribute("page", page);
        model.addAttribute("crlf", "\r\n");

        return "board/info";
    }

    //    공지 글 등록 화면 요청
    @RequestMapping("/register")
    public String register() {
        return "board/register";
    }

    @RequestMapping("/insert")
    public String insert(BoardVO vo, MultipartFile[] files, HttpServletRequest request) {
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
    public String delete(int id, PageVO page, HttpServletRequest request, Model model) {
//        해당 방명록 글을 DB에서 삭제하기
//        첨부 파일이 있다면 물리적인 파일을 삭제할 수 있도록 파일정보를 조회해둔다
        List<FileVO> list = service.board_file_list(id);
        if (service.board_delete(id) == 1) {
            for (FileVO vo : list) {
                common.fileDelete(vo.getFilepath(), request);
            }
        }
//       삭제 후 목록 화면으로 연결
        model.addAttribute("page", page);
        model.addAttribute("id", id);
        model.addAttribute("url", "board/list");
        return "include/redirect";
    }

    //     방명록 수정 화면 연결
    @RequestMapping("/modify")
    public String modify(int id, Model model, PageVO page) {

        model.addAttribute("vo", service.board_info(id));
        model.addAttribute("page", page);
        return "board/modify";
    }

    @RequestMapping("/update")
    public String update(BoardVO vo, Model model, PageVO page, String remove, MultipartFile[] files, HttpServletRequest request) {
//        화면에서 입력한 정보로 DB에 변경저장한 후 정보화면으로 연결
//        첨부파일이 있으면 BoardVO의 fileList에 담기
        vo.setFileList(common.multipleFileUpload("board", files, request));
        if (service.board_update(vo) == 1) {
//            삭제된 첨부 파일이 있으면 DB에서 삭제 + 물리적 삭제
            if (!remove.isEmpty()) {
                List<FileVO> list = service.board_file_list(remove);
                if (service.board_file_delete(remove) > 0) {
                    for (FileVO f : list) {
                        common.fileDelete(f.getFilepath(), request);
                    }
                }
            }

        }
        model.addAttribute("url", "board/info");
        model.addAttribute("id", vo.getId());
        model.addAttribute("page", page);

        return "include/redirect";
    }

    //   댓글 등록 저장 처리 요청
    @ResponseBody
    @RequestMapping("/comment/register")
    public boolean comment_register(BoardCommentVO vo) {
        return service.board_comment_register(vo) == 1 ? true : false;

    }

    //댓글 목록 조회
    @RequestMapping("/comment/list/{board_id}")
    public String comment_list(@PathVariable int board_id, Model model) {
        model.addAttribute("list", service.board_comment_list(board_id));
        model.addAttribute("crlf", "\r\n");
        model.addAttribute("lf", "\n");
        return "board/comment/comment_list";
    }

    //댓글 변경 저장 처리
    @ResponseBody
    @RequestMapping("/comment/update")
    public Object comment_update(BoardCommentVO vo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (service.board_comment_update(vo) == 1) {
            map.put("success", true);
            map.put("message", "성공!");
            map.put("content", vo.getContent());
        } else {
            map.put("success", false);
            map.put("message", "실패!");

        }
        return map;
    }

    //    댓글 삭제 처리 요청
    @ResponseBody
    @RequestMapping("/comment/delete")
    public Map<String, Object> comment_delete(int id) {
        Map<String, Object> map = new HashMap<>();
        if (service.board_comment_delete(id) == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);

        }
        return map;
    }

}
