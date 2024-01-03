package kr.co.middle.common;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class CommonService {

//    프로필, 공지사항, 방명록...
    public String fileUpload(String category, MultipartFile file, HttpServletRequest request){
//        물리적 저장 : d://app/upload/profile/2024/01/03/abc.jpg
//      http://localhost:80/file/profile/2024/01/03/abc.jpg
//      http://localhost:80/file/board/2024/01/03/abc.jpg
        String upload = "d://app/upload/"+category+new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
//        폴더가 있는지 확인 후 없으면 폴더 생성
        File folder = new File(upload);
        if(!folder.exists()) folder.mkdirs();

//        업로드하는 파일 명을 고유한 아이디로 저장하기
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
       String fileName = UUID.randomUUID().toString() + "."+ext;

        try {
//            파일을 해당 폴더에 저장하기
            file.transferTo(new File(upload, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //         upload : d://app/upload/profile/2024/01/03/
//      DB에 저장될 파일명 http://localhost:80/file/profile/2024/01/03/abc.jpg
//      http://localhost:80/file/board/2024/01/03/abc.jpg
        return upload.replace("d://app/upload", fileURL(request))+ fileName;
    }



    public String fileURL(HttpServletRequest request){
        StringBuffer url = new StringBuffer("http://");
        url.append(request.getServerName()).append(":")          //http://localhost:
                .append(request.getServerPort()).append("/file");//http://localhost:80/file

        return url.toString();
    }
}
