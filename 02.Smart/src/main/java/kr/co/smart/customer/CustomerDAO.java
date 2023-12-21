package kr.co.smart.customer;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAO implements CustomerService{

    @Autowired private SqlSession sql;
    @Override
    public int customer_resiter(CustomerVO vo) {
        return 0;
    }

    @Override
    public List<CustomerVO> customer_list() {
        return sql.selectList("customer.list");
    }

    @Override
    public CustomerVO customer_info(int id) {
        return null;
    }

    @Override
    public int customer_update(CustomerVO vo) {
        return 0;
    }

    @Override
    public int customer_delete(int id) {
        return 0;
    }
}
