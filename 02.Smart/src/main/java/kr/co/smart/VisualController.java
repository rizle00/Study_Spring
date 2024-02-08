package kr.co.smart;


import kr.co.smart.visual.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController @RequestMapping("/visual")
public class VisualController {
    @Autowired private VisualService service;

//    //    시각화 화면 요청 홈으로 이동
//    @RequestMapping("/list")
//    public String list(HttpSession session){
//        session.setAttribute("category", "vi");
//        return "visual/list";
//    }

//    부서원 수 조회 요청
    @RequestMapping("/department") //@ResponseBody
    public List<HashMap<String,Object>> departmnet(){
        return service.department();
    }
}
