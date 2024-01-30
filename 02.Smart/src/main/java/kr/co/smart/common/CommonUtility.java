package kr.co.smart.common;

import kr.co.smart.member.MemberVO;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class CommonUtility {

    @Value("${email_id}")
    private String email_id;

@Value("${email_pw}")
private String email_pw;
    private void connectMailServer(HtmlEmail email){
        email.setDebug(true);
        email.setCharset("utf-8");

        email.setHostName("smtp.naver.com");
        email.setAuthentication(email_id,email_pw);
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

            email.setFrom("kain3558@naver.com", "스마트 IoT 관리자");// 메일 보내는이 정보
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
    
    public void sendWelcome(MemberVO vo, String welcomeFile){
        HtmlEmail email = new HtmlEmail();
        connectMailServer(email);

        try {
            email.setFrom(email_id, "스마트 IoT 관리자");// 메일 보내는이 정보
            email.addTo(vo.getEmail(), vo.getName());     // 메일 수신인 정보
//            메일 제목
            email.setSubject("한울 스마트 IoT 융합 과정 가입 안내");
//            메일 내용
            StringBuffer content = new StringBuffer();
            content.append("<body>");
            content.append("<h3>"+vo.getName()+"님 회원 가입을 축하드립니다.</h3>");
            content.append("<div> 과정 가입을 축하드리며</div>");
            content.append("<div>프로젝트까지 마무리  잘 하시고 취업에 성공하시길 바랍니다</div>");
            content.append("<div>학원 위치 첨부합니다 확인하시고 등교 하시면 됩니다</div>");
            content.append("</body>");
            email.setHtmlMsg(content.toString());

            EmailAttachment file = new EmailAttachment();
            file.setPath(welcomeFile);
            email.attach(file);
            email.send();// 메일 보내기 버튼 클릭
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }

    }

    public String requestAPI(String apiURL){

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
            }
            String inputLine;
            StringBuilder res = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if (responseCode == 200) {
                apiURL = res.toString();
               System.out.println(res.toString());
            }
        } catch (Exception e) {
            // Exception 로깅
        }
        return apiURL;
    }

    public String requestAPI(String apiURL, String property){

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", property);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
            }
            String inputLine;
            StringBuilder res = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if (responseCode == 200) {
                apiURL = res.toString();
                System.out.println(res.toString());
            }
        } catch (Exception e) {
            // Exception 로깅
        }
        return apiURL;
    }

    //    파일 서비스 받을 URL
    public String fileURL(HttpServletRequest request){
        StringBuffer url = new StringBuffer("http://");
        url.append(request.getServerName()).append(":");// http://localhost:, http://127.0.0.1:
        url.append(request.getServerPort());            // http://localhost:80, http://127.0.0.1:8080
        url.append("/file");           // http://localhost:80/smart, http://127.0.0.1:8080/web
        return url.toString();
    }


//    파일 업로드
    public String fileUpload(String category, MultipartFile file, HttpServletRequest request){

        String upload = "d://app/upload/"+category
                + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        File dir = new File(upload);
        if(!dir.exists()) dir.mkdirs();

//        업로드 할 파일 명을 고유 ID 값으로
        String fileName = UUID.randomUUID().toString()+"."
                + StringUtils.getFilenameExtension(file.getOriginalFilename());

        try {
            file.transferTo(new File(upload, fileName));
        } catch (IOException e) {
        }
//        DB에 저장할 형태 : 저장경로 + 파일명
//        물리적 저장 형태              d:app/upload/profile/2024/01/05/abc.png
//        브라우저가 찾을수 있는 형태     http://localhost:8080/file/profile/2024/01/05/abc.png

        return upload.replace("d://app/upload", fileURL(request))+fileName;
    }

//    다중 파일 업로드
    public ArrayList<FileVO> multipleFileUpload(String category, MultipartFile[] files, HttpServletRequest request){
        ArrayList<FileVO> list = null;
        for(MultipartFile file : files){
            if(file.isEmpty()) continue;;
            if(list == null) list = new ArrayList<FileVO>();
            FileVO vo = new FileVO();
            vo.setFilename(file.getOriginalFilename());
            vo.setFilepath(fileUpload(category, file, request));
            list.add(vo);
        }
        return list;
    }


//    파일 다운로드
    public void fileDownload(String filename, String filepath,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
//      filepath >  http://localhost:8080/file/profile/2024/01/05/abc.png
//         d://app/upload/profile/2024/01/05/abc.png
       filepath = filepath.replace(fileURL(request),"d://app/upload");
      File file = new File(filepath);

//      파일 정보
      response.setContentType(request.getSession().getServletContext().getMimeType(filename));
      filename = URLEncoder.encode(filename,"utf-8");
      response.setHeader("content-disposition", "attachment; filename="+filename);
        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    public void fileDelete(String filepath, HttpServletRequest request){
        if(filepath != null){
            filepath = filepath.replace(fileURL(request),"d://app/upload");
            File file = new File(filepath);
            if(file.exists()) file.delete();
        }
    }
}
