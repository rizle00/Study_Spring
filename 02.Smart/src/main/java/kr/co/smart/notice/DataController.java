package kr.co.smart.notice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.co.smart.common.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller @RequestMapping("/data")
public class DataController {

    @Autowired private CommonUtility common;

    @Value("${GeoData_Key}")
    private String key;

//    공공데이터 목록 화면 요청
    @RequestMapping("/list")
    public String list(HttpSession session){
        session.setAttribute("category", "da");
        return "data/list";
    }

//    약국 정보 조회 요청
    @RequestMapping("pharmacy") @ResponseBody
    public Object pharmacy_list(int pageNo, int numOfRows){
        //공공데이터 포털에서 약국 정보를 조회해 오기

        StringBuffer url = new StringBuffer("https://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?");
        url.append("ServiceKey=").append(key);
        url.append("&_type=json");
        url.append("&pageNo=").append(pageNo);
        url.append("&numOfRows=").append(numOfRows);

       return responseAPI(url);



    }
    private String animalURl = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/";
    //유기 동물 목록 조회 요청
    @RequestMapping("animal/list")
    public String animal_list(int pageNo, int numOfRows, Model model){
        //공공데이터 포털에서 약국 정보를 조회해 오기

        StringBuffer url = new StringBuffer(animalURl);
        url.append("abandonmentPublic?ServiceKey=").append(key);
        url.append("&_type=json");
        url.append("&pageNo=").append(pageNo);
        url.append("&numOfRows=").append(numOfRows);

//        return responseAPI(url);
        model.addAttribute("list",responseAPI(url));
        return "data/animal/animal_list";

    }

    //유기동물 시도 조회 요청
    @RequestMapping("animal/sido")
    public String animal_sido(Model model){


        StringBuffer url = new StringBuffer(animalURl);
        url.append("sido?ServiceKey=").append(key);
        url.append("&_type=json");
        url.append("&numOfRows=50");
        model.addAttribute("list",responseAPI(url));
        return "data/animal/sido";

    }


    private HashMap<String ,Object> responseAPI(StringBuffer url){
        return new Gson().fromJson(common.requestAPI(url.toString()), new TypeToken<HashMap<String, Object>>(){}.getType()) ;
    }
}
