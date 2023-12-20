package com.hanul.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class TestController {

//    첫번째 테스트
    @RequestMapping("/first")
    public String first(Model model){
//        웹 브라우저의 first 라는 요청이 들어오면
//        응답화면 연결 : index.jsp
//    	오늘 날짜를 응답화면에 출력할 수 있도록 Model객체에 attribute로 담는다

        String today = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
        model.addAttribute("today",today);
        model.addAttribute("type", "Model");
        return "index";
    }

//    두번째 테스트
     @RequestMapping("/second")
    public ModelAndView second(){
         ModelAndView model = new ModelAndView();
         String now  = new SimpleDateFormat("hh시 mm분 ss초").format(new Date());
         model.addObject("now",now);
         model.addObject("type", "ModelAndView");
         model.setViewName("index");
         return model;
    }
}
