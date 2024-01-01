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
        return sql.insert("hr.insert",vo);
    }
//    사원목록조회
    public List<EmployeeVO> employee_list(){
        return sql.selectList("hr.list");
    }
    public List<EmployeeVO> employee_list(int department_id){

        return sql.selectList("hr.list",  department_id);
    }
//    사원 정보 조회
    public EmployeeVO employee_info(int id){
        return sql.selectOne("hr.info",id);
    }
//    사원정보 변경 저장
    public int employee_update(EmployeeVO vo){
        return sql.update("hr.update",vo);
    }
//    사원정보 삭제
    public int employee_delete(int id){
        return sql.delete("hr.delete",id);
    }

//    회사 전체 부서 목록 조회
    public List<DepartmentVO> hr_department_list(){
        return sql.selectList("hr.departmentList");
    }
//    회사 전체 업무 목록 조회
    public List<JobVO> hr_job_list(){
        return sql.selectList("hr.jobList");
    }

//    회사 매니저로 적용할 모든 사원 목록 조회(이름 순 정렬)
    public List<EmployeeVO> hr_manager_list(){
        return sql.selectList("hr.managerList");
    }

//    사원들이 속해 있는 부서 목록 조회
    public List<DepartmentVO> employee_department_list(){
        return sql.selectList("hr.employeeDepartment");

    }

}
