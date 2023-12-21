package kr.co.smart.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired private CustomerDAO dao;
    @Override
    public int customer_resiter(CustomerVO vo) {
        return 0;
    }

    @Override
    public List<CustomerVO> customer_list() {
        return dao.customer_list();
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
