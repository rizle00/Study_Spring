package kr.co.smart;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.mail.iap.Response;
import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.PageVO;
import kr.co.smart.member.MemberService;
import kr.co.smart.member.MemberVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

@Controller
@RequestMapping("/member")
//@PropertySource("classpath:login.properties")
public class MemberController {

    @Autowired
    private CommonUtility common;
    @Autowired
    private MemberService service;
    @Autowired
    private BCryptPasswordEncoder pwEncoder;

    private final String ENC = "text/html; charset = utf-8";

    private String redirectURL(HttpSession session, Model model){
        if(session.getAttribute("redirect") == null){
            return "redirect:/";
        }else {
            HashMap<String, Object> map = (HashMap<String, Object>) session.getAttribute("redirect");
            model.addAttribute("url", map.get("url"));
            model.addAttribute("id", map.get("id"));
            model.addAttribute("page", map.get("page"));
            session.removeAttribute("redirect");
            return "include/redirect";
        }
    }

    //     로그인 화면 요청
    @RequestMapping("/login")
    public String login(HttpSession session, String url, PageVO page, String id) {
//        방명록 정보화면에서 서브밋 된 경우
        if(url != null){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("url",url);
            map.put("page",page);
            map.put("id",id);
            session.setAttribute("redirect", map);
        }

        session.setAttribute("category", "login");
        return "default/member/login";// 타일즈 적용
    }

//    @ResponseBody
    @RequestMapping(value = "/smartLogin", produces = "text/html; charset=utf-8")
    public String login(HttpServletRequest request, Model model, String user_id, String user_pw, HttpSession session, RedirectAttributes redirect) {
//        화면에서 입력한 아이디/비번이 일치하는 회원 정보를 조회

        MemberVO vo = service.member_info(user_id);

       // StringBuffer msg = new StringBuffer("<script>");
        boolean match = false;

        if (vo != null)
            match = pwEncoder.matches(user_pw, vo.getUser_pw());

        if (match) {
            session.setAttribute("loginInfo", vo);// 세션에 로그인 정보 담기
            //        msg.append("location='http://localhost/smart'");
           // msg.append("location='").append(common.appURL(request)).append("'");// location=''
//            return "redirect:/";
            return redirectURL(session,model);
        } else {
            //        로그인 되지 않는 경우
            //msg.append("alert('아이디나 비밀번호가 일치하지 않습니다'); history.go(-1)");
            redirect.addFlashAttribute("fail",true);
            return "redirect:login";
        }


       // msg.append("</script>");

       // return msg.toString();

    }

    //    로그아웃 처리 요청
    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
//        세션에 있는 로그인 정보 삭제 -> 웰컴 페이지로 연결
//
//        ${YOUR_REST_API_KEY}
//        ${YOUR_LOGOUT_REDIRECT_URI}"

       MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
        session.removeAttribute("loginInfo");
//        if(vo.getSocial()!= null && vo.getSocial().equals("K")){
        if("K".equals(vo.getSocial())){
            StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/logout?");
            url.append("client_id=").append(KAKAO_CLIENT_ID)
                    .append("&logout_redirect_uri=").append(common.appURL(request));
            return "redirect:"+url.toString();

       }else return "redirect:/";

    }

    //    비밀번호 찾기 화면 요청
    @RequestMapping("/findPassword")
    public String findPassword(HttpSession session) {
        session.setAttribute("category", "find");
        return "default/member/find";
    }

    //    임시비밀번호 발급 처리 요청, 이메일
    @ResponseBody
    @RequestMapping(value = "/resetPassword", produces = "text/html; charset=utf-8")
    public String resetPassword(MemberVO vo) {

        vo = service.member_userid_email(vo);

        StringBuffer msg = new StringBuffer("<script>");


        if (vo == null) {
            msg.append("alert('아이디나 이메일이 맞지 않습니다. \\n확인하세요!'); ");
            msg.append("location='findPassword'");

        } else {
            //        화면에서 입력한 아이디와 이메일이 일치하는 회원에게
//        임시 비밀번호를 발급해 이메일로 보내기
            String pw = UUID.randomUUID().toString();
            pw = pw.substring(pw.lastIndexOf("-") + 1, pw.lastIndexOf("-") + 10);
            vo.setUser_pw(pwEncoder.encode(pw));
//            service.member_resetPassword(vo);

            if (service.member_resetPassword(vo) == 1 && common.sendPassword(vo, pw)) {
                msg.append("alert('임시 비밀번호가 발급되었습니다. \\n이메일을 확인하세요.')");
                msg.append("location='login'");
            } else {
                msg.append("alert('임시 비밀번호 발급 실패...');");
                msg.append("history.go(-1)");
            }
        }

        msg.append("</script>");
        return msg.toString();
    }

    //비밀 번호 변경 화면 요청

    @RequestMapping("/changePassword")
    public String changePassword() {
        return "member/password";
    }

    //    현재 비밀번호 확인 처리 요청
    @RequestMapping("/confirmPassword")
    @ResponseBody
    public int confirmPassword(String user_pw, HttpSession session) {
//        service
//        세션 로그인 정보 꺼내기
        MemberVO vo = (MemberVO) session.getAttribute("loginInfo");
        if (vo == null) {
//            로그인이 안 되어 있다면 로그인 화면으로 연결되도록
            return -1;

        } else {
//            입력한 현재 비번이 DB
            return pwEncoder.matches(user_pw, vo.getUser_pw()) ? 0 : 1;
        }
    }

    //  새 비밀번호로 변경 저장 처리 요청
    @RequestMapping("/updatePassword")
    @ResponseBody
    public boolean updatePassword(String user_pw, HttpSession session) {
        //        세션 로그인 정보 꺼내기
        MemberVO vo = (MemberVO) session.getAttribute("loginInfo");
//        입력 새 비멀번호를 암호화하기
        vo.setUser_pw(pwEncoder.encode(user_pw));
        return service.member_resetPassword(vo) == 1 ? true : false;
    }
//    Properties properties = new Properties(); //프로퍼티 등록

    @Value
            ("${KAKAO_CLIENT_ID}")
    private String KAKAO_CLIENT_ID;
    @Value
            ("${NAVER_CLIENT_ID}")
    private String NAVER_CLIENT_ID;
    @Value
            ("${NAVER_SECRET}")
    private String NAVER_SECRET;

    //네이버 로그인 요청
    @RequestMapping("/naverLogin")
    public String naverlogin(HttpSession session, HttpServletRequest request) {
//       String naver_id = properties.getProperty("NAVER_CLIENT_ID");
        // 네이버 로그인 연동 URL 생성하기
//        https://nid.naver.com/oauth2.0/authorize?response_type=code
//        &client_id=CLIENT_ID
//        &state=STATE_STRING
//        &redirect_uri=CALLBACK_URL
        String state = UUID.randomUUID().toString();
        session.setAttribute("state", state);
        StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/authorize?response_type=code");
        url.append("&client_id=").append(NAVER_CLIENT_ID)
                .append("&state=").append(state)
                .append("&redirect_uri=").append(common.appURL(request))
                .append("/member/naverCallback"); //http://localhost:80/smart/member/naverCallback
        return "redirect:" + url.toString();

    }

    @RequestMapping("/naverCallback")
    public String naverCallback(String state, String code, HttpSession session, Model model) {
        if (code == null) return "redirect:/";
//        if(code != null){
//
//        }
//        https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
//        &client_id=jyvqXeaVOVmV
//        & client_secret=527300A0_COq1_XV33cf
//        &code=EIc5bFrl4RibFls1
//        &state=9kgsGTfH4j7IyAkg
//
        StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
        url.append("&client_id=").append(NAVER_CLIENT_ID)
                .append("&client_secret=").append(NAVER_SECRET)
                .append("&code=").append(code)
                .append("&state=").append(state);
        String response = common.requestAPI(url.toString());

        HashMap<String, String> map =
                new Gson().fromJson(response, new TypeToken<HashMap<String, String>>() {
                }.getType());
        String token = map.get("access_token");
        String type = map.get("token_type");

//        {
//            "access_token": "AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg",
//                "token_type": "bearer",
//        }

//        접근 토큰을 이용하여 프로필 api 호출하기
        response = common.requestAPI("https://openapi.naver.com/v1/nid/me", type + " " + token);
//        HashMap<String,Object> me =
//                new Gson().fromJson(response, new TypeToken<HashMap<String , Object>>(){}.getType());
        JSONObject json = new JSONObject(response);
        if (json.getString("resultcode").equals("00")) {
            json = json.getJSONObject("response");

            String email = json.getString("email");
            String gender = json.getString("gender").equals("F") ? "여" : "남"; // F/M -< 여/ 남
            String id = json.getString("id");
            String name = json.getString("name");
            String profile = json.getString("profile_image");

            MemberVO member = new MemberVO();
            member.setSocial("N");// N : 네이버 , K : 카카오
            member.setUser_id(id);
            member.setName(name);
            member.setEmail(email);
            member.setGender(gender);
            member.setProfile(profile);
            member.setPhone(json.getString("mobile"));

//            네이버 로그인이 처음인 경우 : insert, 아니면 update
            if (service.member_info(id) == null) {
                service.member_join(member);
            } else {
                service.member_update(member);
            }
            session.setAttribute("loginInfo", member);
        }
//        if(me.get("resultcode").equals("00")){
//            HashMap<String,Object> json = (HashMap<String,Object>) me.get("response");

//        }

//        {
//            "resultcode": "00",
//                "message": "success",
//                "response": {
//            "email": "openapi@naver.com",
//                    "nickname": "OpenAPI",
//                    "profile_image": "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
//                    "age": "40-49",
//                    "gender": "F",
//                    "id": "32742776",
//                    "name": "오픈 API",
//                    "birthday": "10-01"
//        }
//        }

//        return "redirect:/";
        return redirectURL(session,model);
    }

    @RequestMapping("/kakaoLogin")
    public String kakaologin(HttpServletRequest request) {
        StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/authorize?response_type=code");
        url.append("&client_id=").append(KAKAO_CLIENT_ID)
                .append("&redirect_uri=").append(common.appURL(request))
                .append("/member/kakaoCallback"); //http://localhost:80/smart/member/naverCallback
        return "redirect:" + url.toString();
    }

    @RequestMapping("/kakaoCallback")
    private String kakaocallback(String code, HttpSession session, Model model) {
        if(code==null) return "redirect:/";
        StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
        url.append("&client_id=").append(KAKAO_CLIENT_ID)
                .append("&code=").append(code);
//url 대로 요청을 함. -> requestAPI
        String response = common.requestAPI(url.toString());
//        JSONObject json = new JSONObject(response);
//        String token_type = json.getString("token_type");
//        String access_token = json.getString("access_token");
        HashMap<String, String> map = new Gson().fromJson(response, new TypeToken<HashMap<String, String>>() {
        }.getType());
        String token = map.get("access_token");

        response = common.requestAPI("https://kapi.kakao.com/v2/user/me", "Bearer " + token);
        JSONObject json = new JSONObject(response);
        if (!json.isEmpty()) {
            String id = json.getLong("id") + "";
//            String.valueOf()
            json = json.getJSONObject("kakao_account");
//            String name = json.has("name")? json.getString("name") : "";
            String name = hashKey(json, "name");
            String email = hashKey(json, "email");
            String gender = hashKey(json, "gender", "남");
            String phone_number = hashKey(json, "phone_number");
            json = json.getJSONObject("profile");
            String profile =hashKey(json, "profile_image_url");
            if(name.isEmpty()){
               name = hashKey(json, "nickname",  "아무개");
            }
            MemberVO vo = new MemberVO();
            vo.setSocial("K");// N : 네이버 , K : 카카오
            vo.setUser_id(id);
            vo.setName(name);
            vo.setEmail(email);
            vo.setGender(gender);
            vo.setPhone(phone_number);
            vo.setProfile(profile);

//            카카오 로그인이 처음이면 insert 아니면 update
            if (service.member_info(id) == null) {
                service.member_join(vo);
            } else {
                service.member_update(vo);
            }
            session.setAttribute("loginInfo", vo);
//            json = json.getJSONObject("properties");
////            String name = json.getString("nickname");
////            String profile = json.getString("profile_image");

//            System.out.println(new Gson().toJson(member));

        }
//        return "redirect:/";
        return redirectURL(session,model);
    }
    private String hashKey(JSONObject json, String key){
      return json.has(key)? json.getString(key) : "";
    }

    private String hashKey(JSONObject json, String key, String defaultValue){
        return json.has(key)? json.getString(key) : defaultValue;
    }

//    회원 가입 화면 요청
    @RequestMapping("/join")
    public String join(HttpSession session){
        session.setAttribute("category","join");
        return "member/join";
    }

//    아이디 중복 확인 요청
    @RequestMapping("/idCheck") @ResponseBody
    public boolean idCheck(String user_id){
//        true 사용가능, false 사용불가
       return service.member_info(user_id)==null ? true : false;
    }

//    회원 가입 처리 요청
    @RequestMapping(value = "/register", produces = ENC) @ResponseBody
    public String register(MemberVO vo, HttpServletRequest request, MultipartFile file, HttpSession session){
//        화면에서 입력한 정보로 DB에 신규회원정보를 저장 -> 웰컴 페이지로 연결

        if(!file.isEmpty()){
            vo.setProfile(common.fileUpload("profile", file, request));
        }
        vo.setUser_pw(pwEncoder.encode(vo.getUser_pw()));

        StringBuffer msg = new StringBuffer("<script>");
        if(service.member_join(vo) ==1){
//            회원 가입 축하 메일 보내기
           String welcome =  session.getServletContext().getRealPath("resources/file/학원 안내서.txt");
            common.sendWelcome(vo, welcome);

            msg.append("alert('회원 가입을 축하합니다 '); location ='")
                    .append(request.getContextPath())
                    .append("'");
            System.out.println(request.getContextPath());

        } else {
            msg.append("alert('회원 가입 실패 '); history.go(-1)");
        }


        msg.append("</script>");
        return msg.toString();
    }
}
