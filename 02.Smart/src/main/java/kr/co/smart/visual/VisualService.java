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
//    채용 인원 수 조회(년/월)
    public List<HashMap<String,Object>> hirement(){
        return null;
    }

}
