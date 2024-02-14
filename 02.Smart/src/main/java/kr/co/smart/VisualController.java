package kr.co.smart;


import kr.co.smart.visual.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController @RequestMapping("/visual")
public class VisualController {
    @Autowired private VisualService service;

//    //    시각화 화면 요청 홈으로 이동
//    @RequestMapping("/list")
//    public String list(HttpSession session){
//        session.setAttribute("category", "vi");
//        return "visual/list";
//    }

//    부서원 수 조회 요청
    @RequestMapping("/department") //@ResponseBody
    public List<HashMap<String,Object>> departmnet(){
        return service.department();
    }





    //채용인원수 년도별 조회 요청
    @RequestMapping("/hirement/year")
    public Object hirement_year(@RequestBody HashMap<String, Object> map) {
        return service.hirement_year(map);
    }

    //채용인원수 월별 조회 요청
    @RequestMapping("/hirement/month")
    public Object hirement_month() {
        return service.hirement_month();
    }

    private HashMap<String, Object> yearRange(HashMap<String, Object> map) {
        int begin =  Integer.parseInt( map.get("begin").toString() );
        int end =  Integer.parseInt( map.get("end").toString() );
        String range = "";
        for(int year = begin; year <= end; year++ ) {
            range +=   (range.isEmpty()? "" : ", " ) + year + "\"" + year + "년\"" ;
            //2012 "2012년", 2013 "2013년", 2014 "2014년"
        }
        map.put("range",range);
        return map;
    }


    //상위3개부서의 채용인원수 년도별 조회 요청
    @RequestMapping("/hirement/top3/year")
    public Object hirement_top3_year(@RequestBody HashMap<String, Object> map) {
        List<HashMap<String, Object>> list = service.hirement_top3_year(yearRange(map));
        //단위 추출

        Object[] keys = list.get(0).keySet().toArray();
        System.out.println(keys.length);
        Arrays.sort(keys);
        System.out.println(keys.toString());
        keys = Arrays.copyOfRange(keys, 0, keys.length-1); //department_name 제외 : 0~10
        System.out.println(keys.toString());
        System.out.println(list);

        map.put("list", list);
        map.put("unit", keys);

        return map;
    }

    //상위3개부서의 채용인원수 월별 조회 요청
    @RequestMapping("/hirement/top3/month")
    public Object hirement_top3_month() {
        List<HashMap<String, Object>> list = service.hirement_top3_month();
        //단위 추출
        Object[] keys = list.get(0).keySet().toArray();
        Arrays.sort(keys);
        keys = Arrays.copyOfRange(keys, 0, keys.length-1); //department_name 제외 : 0~10

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("unit", keys);

        return map;
    }
}
