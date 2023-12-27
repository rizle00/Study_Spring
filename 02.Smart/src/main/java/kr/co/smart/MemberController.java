package kr.co.smart;


import kr.co.smart.common.CommonUtility;
import kr.co.smart.member.MemberService;
import kr.co.smart.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller @RequestMapping("/member")
public class MemberController {

    @Autowired private CommonUtility common;
   @Autowired private MemberService service;
   @Autowired private BCryptPasswordEncoder pwEncoder;


//     로그인 화면 요청
    @RequestMapping("/login")
    public String login(HttpSession session){
        session.setAttribute("category","login");
        return "default/member/login";
    }

    @ResponseBody
    @RequestMapping(value = "/smartLogin", produces = "text/html; charset=utf-8")
    public String login(HttpServletRequest request, String user_id, String user_pw, HttpSession session){
//        화면에서 입력한 아이디/비번이 일치하는 회원 정보를 조회

        MemberVO vo = service.member_info(user_id);

        StringBuffer msg = new StringBuffer("<script>");
        boolean match = false;

        if(vo != null){
            match = pwEncoder.matches(user_pw, vo.getUser_pw());
            if(match){
                session.setAttribute("loginInfo", vo);// 세션에 로그인 정보 담기
                //        msg.append("location='http://localhost/smart'");
                msg.append("location='").append(common.appURL(request)).append("'");// location=''
            } else{
                //        로그인 되지 않는 경우
                msg.append("alert('아이디나 비밀번호가 일치하지 않습니다'); history.go(-1)");
            }
        }


        msg.append("</script>");

        return msg.toString();
        
    }
//    로그아웃 처리 요청
    @RequestMapping("/logout")
    public String logout(HttpSession session){
//        세션에 있는 로그인 정보 삭제 -> 웰컴 페이지로 연결
        session.removeAttribute("loginInfo");
        return "redirect:/";
    }

//    비밀번호 찾기 화면 요청
    @RequestMapping("/findPassword")
    public String findPassword(HttpSession session){
        session.setAttribute("category", "find");
        return "default/member/find";
    }
    
//    임시비밀번호 발급 처리 요청
    @ResponseBody @RequestMapping(value = "/resetPassword",produces = "text/html; charset=utf-8" )
    public String resetPassword(MemberVO vo){

        vo = service.member_userid_email(vo);

        StringBuffer msg = new StringBuffer("<script>");


        if(vo == null){
            msg.append("alert('아이디나 이메일이 맞지 않습니다. \\n확인하세요!'); ");
            msg.append("location='findPassword'");

        } else {
            //        화면에서 입력한 아이디와 이메일이 일치하는 회원에게
//        임시 비밀번호를 발급해 이메일로 보내기
            String pw = UUID.randomUUID().toString();
            pw = pw.substring(pw.lastIndexOf("-")+1, pw.lastIndexOf("-")+10);
            vo.setUser_pw(pwEncoder.encode(pw));
//            service.member_resetPassword(vo);

            if(service.member_resetPassword(vo)==1 && common.sendPassword(vo,pw)){
                msg.append("alert('임시 비밀번호가 발급되었습니다. \\n이메일을 확인하세요.')");
                msg.append("location='login'");
            }else{
                msg.append("alert('임시 비밀번호 발급 실패...');");
                msg.append("history.go(-1)");
            }
        }

        msg.append("</script>");
        return msg.toString();
    }

}
