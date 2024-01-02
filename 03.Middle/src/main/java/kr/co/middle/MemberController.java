package kr.co.middle;

import kr.co.middle.member.MemberService;
import kr.co.middle.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/member", produces = "text/plain; charset=utf-8")

public class MemberController {
    @Autowired private MemberService service;

    @RequestMapping("/login")
    public void login(String user_id, String user_pw){

        MemberVO vo = service.member_info(user_id);
        if(vo != null){

        }

    }
}
