package com.hanul.web;

import member.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

//    Controller -> Parameter 접근해 DTO에 담아야 한다
//    DTO dto = new DTO();
//    dto.setName()
//    .....
//    DB 저장 처리 메소드 호출시 파라미터로 객체의 주소만 보낸다
    @RequestMapping("/member")
    public String member(){
     return "member/join";
    }
    @RequestMapping("/joinRequest")
    public String join(HttpServletRequest req, Model model){
//        화면을 통해 서브밋 된 파라미터를 접근하기
    String name = req.getParameter("name");
    model.addAttribute("name",name);
    model.addAttribute("gender",req.getParameter("gender"));
    model.addAttribute("email",req.getParameter("email"));
    model.addAttribute("method","HttpServletRequest 방식");
//    String -> int -> Integer.parseInt : int
//    String -> int -> Integer.valueOf : Integer
    model.addAttribute("age", Integer.valueOf(req.getParameter("age")) );
        return"member/info";
    }

    @RequestMapping("/joinParam")
    public String join(Model model, int age, @RequestParam("email") String mail, String gender, @RequestParam String name){
        model.addAttribute("method", "@RequestParam 방식");
        model.addAttribute("name",name);
        model.addAttribute("gender", gender);
        model.addAttribute("email", mail);
        model.addAttribute("age", age);
        return "member/info";
    }
    @RequestMapping("/joinData")
    public String join(Model model, MemberVO vo){
        model.addAttribute("method", "데이터 객체 방식");
        model.addAttribute("vo", vo);

        return "member/info";
    }
}

