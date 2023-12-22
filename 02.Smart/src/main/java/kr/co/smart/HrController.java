package kr.co.smart;


import kr.co.smart.hr.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller @RequestMapping("/hr")
public class HrController {
 @Autowired private HrService service;
//    사원 목록 화면 요청
    @RequestMapping("/list")
    public String list(HttpSession session, Model model){
        session.setAttribute("category","hr");

//        DB에서 사원 목록을 조회해 와 목록 화면에 출력 -> Model 객체에 담기
        model.addAttribute("list", service.employee_list());
        return "hr/list";
    }
}
