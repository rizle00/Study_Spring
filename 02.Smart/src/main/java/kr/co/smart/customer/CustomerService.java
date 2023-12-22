package kr.co.smart.customer;

import java.util.List;

public interface CustomerService {
//    CRUD(Create, Read, Update, Delete
//    고객 정보 신규 저장
    int customer_resiter(CustomerVO vo);
//    고객 목록 조회
    List<CustomerVO> customer_list();

//    고객명으로 목록 조회
    List<CustomerVO> customer_list(String name);
//    고객 정보 조회
    CustomerVO customer_info(int id);
//    고객 정보 변경 저장
    int customer_update(CustomerVO vo);
//    고객 정보 삭제
    int customer_delete(int id);


}
