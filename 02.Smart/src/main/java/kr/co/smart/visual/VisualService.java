package kr.co.smart.visual;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service
public class VisualService {
   @Autowired @Qualifier("hr")
   private SqlSession sql;
//    부서별 사원 수 조회
    public List<HashMap<String,Object>> department(){
        return sql.selectList("visual.list");
    }


    //채용인원수(년도별/월별) 조회 - 년도 범위 지정하기
    public List<HashMap<String, Object>> hirement_year(HashMap<String, Object> map) {
        return sql.selectList("visual.hirementYear", map);
    }
    public List<HashMap<String, Object>> hirement_month() {
        return sql.selectList("visual.hirementMonth");
    }
    //상위부서3개의 채용인원수(년도별/월별) 조회
    public List<HashMap<String, Object>> hirement_top3_year(HashMap<String, Object> map) {
        return sql.selectList("visual.hirementTop3Year", map);
    }
    public List<HashMap<String, Object>> hirement_top3_month() {
        return sql.selectList("visual.hirementTop3Month");
    }

}
