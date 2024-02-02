package kr.co.smart;

import kr.co.smart.member.MemberService;
import kr.co.smart.member.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired private MemberService member;
	@Autowired private BCryptPasswordEncoder pwEncoder;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
//		테스트 하는 동안 사용할 수 있도록 임시 로그인 처리-----
		String user_id = "test", user_pw = "asdfG1";
		MemberVO vo = member.member_info(user_id);
		vo.setRole("admin");
		if(pwEncoder.matches(user_pw, vo.getUser_pw())){
			session.setAttribute("loginInfo",vo);
//			session 에 세팅

		}
//---------------------------------------------------------
		session.removeAttribute("category");
//		session.setAttribute("category", "");
		return "home";
//		return "layout";
	}
	
}
