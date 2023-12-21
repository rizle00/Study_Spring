package com.hanul.web;

import member.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
//        화면을 통해 서브밋 된 파라미터를 HttpServletRequest 로 접근하기
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

    //화면을 통해 서브밋된 파라미터를 @RequestParam 로 접근하기
    @RequestMapping("/joinParam")
    public String join(Model model, int age, @RequestParam("email") String mail, String gender, @RequestParam String name){
        model.addAttribute("method", "@RequestParam 방식");
        model.addAttribute("name",name);
        model.addAttribute("gender", gender);
        model.addAttribute("email", mail);
        model.addAttribute("age", age);
        return "member/info";
    }

    //Controller -> Parameter 접근해 DTO에 담아야 한다
    // DTO dto = new DTO();
    // dto.setName( name )
    // .....
    // DB저장처리메소드 호출시 파라미터로 객체의 주소만 보낸다
    @RequestMapping("/joinData")
    public String join(Model model, MemberVO vo){
        model.addAttribute("method", "데이터 객체 방식");
        model.addAttribute("vo", vo);

        return "member/info";
    }

    @RequestMapping("/joinPath/{name}/{gender}/{e}/{age}")
    public String join(Model model, @PathVariable String name,
                       @PathVariable String gender, @PathVariable("e") String email, @PathVariable int age){
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("gender", gender);
        model.addAttribute("age", age);
        model.addAttribute("method", "@PathVariable 방식");

        return "member/info";
    };

    @RequestMapping("/login")
        public String login(){
            return "member/login";
        }
    @RequestMapping("/login_result")
    public String login(String userid, String userpw){
//        아이디 , 비번 일치해서 로그인 성공하면 home화면으로
//        일치하지 않아 로그인 실패하면 로그인 화면으로 연결
//        아이디 : hong 비번 : 0000이면 로그인 성공
        if(userid.equals("hong")&& userpw.equals("0000")){
//            return "home"; // forward 방식
            return "redirect:/"; //redirect 방식

        } else {
            return "redirect:login"; //redirect 방식
        }
    }
}

