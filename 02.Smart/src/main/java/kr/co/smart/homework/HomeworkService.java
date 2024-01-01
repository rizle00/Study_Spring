package kr.co.smart.homework;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {

    @Autowired @Qualifier("hanul")
    private SqlSession sql;
// sql 셀렉트에서 파라미터는 오브젝트 하나로 넘김
    public List<HomeworkVO> customer_list(int customer_id){
        return sql.selectList("home.list", customer_id);
    }

    public List<HomeworkVO> customer_info(int customer_id){
        return sql.selectOne("home.info", customer_id);
    }
}
