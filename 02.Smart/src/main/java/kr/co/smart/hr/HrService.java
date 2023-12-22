package kr.co.smart.hr;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HrService {
    @Autowired @Qualifier("hr") // db config 구분.
    private SqlSession sql;
//    CRUD
//    신규사원등록
    public int employee_register(EmployeeVO vo){
        return 0;
    }
//    사원목록조회
    public List<EmployeeVO> employee_list(){
        return sql.selectList("hr.list");
    }
//    사원 정보 조회
    public EmployeeVO employee_info(int id){
        return null;
    }
//    사원정보 변경 저장
    public int employee_update(EmployeeVO vo){
        return 0;
    }
//    사원정보 삭제
    public int employee_delete(int id){
        return 0;
    }

}
