package kr.co.smart.common;

import kr.co.smart.member.MemberVO;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CommonUtility {

    private final String EMAIL = "kain3558@naver.com" ;

    private void connectMailServer(HtmlEmail email){
        email.setDebug(true);
        email.setCharset("utf-8");

        email.setHostName("smtp.naver.com");
        email.setAuthentication(EMAIL,"비밀번호");
        email.setSSLOnConnect(true); //로그인 버튼 클릭
    }

//     웹브라우저 요청의 루트 반환
    public String appURL(HttpServletRequest request){
        StringBuffer url = new StringBuffer("http://");
        url.append(request.getServerName()).append(":");// http://localhost:, http://127.0.0.1:
        url.append(request.getServerPort());            // http://localhost:80, http://127.0.0.1:8080
        url.append(request.getContextPath());           // http://localhost:80/smart, http://127.0.0.1:8080/web
        return url.toString();
    }

//    임시 비밀번호를 이메일로 보내기
    public boolean sendPassword(MemberVO vo, String pw)  {

        boolean send = true;
        HtmlEmail email = new HtmlEmail();
        connectMailServer(email);// 메일서버에 연결하기(메일 아이디/ 비번 입력 후 로그인)
        try {

            email.setFrom(EMAIL, "스마트 IoT 관리자");// 메일 보내는이 정보
            email.addTo(vo.getEmail(), vo.getName());     // 메일 수신인 정보
//            메일 제목
            email.setSubject("비밀 번호 재발급 안내");
//            메일 내용
            StringBuffer content = new StringBuffer();
            content.append("<h3>"+vo.getName()+"님 임시 비밀번호가 발급되었습니다.</h3>");
            content.append("<div> 아이디 :  "+vo.getUser_id()+"</div>");
            content.append("<div>임시 비밀번호 : <strong> ").append(pw).append("</div>");
            content.append("<div>발급된 임시 비밀번호로 로그인 후 비밀번호를 변경하세요</div>");
            email.setHtmlMsg(content.toString());
            email.send();// 메일 보내기 버튼 클릭
        } catch (EmailException e){
            System.out.println(e.getMessage());
            send = false;
        }
        return send;
    }
}
