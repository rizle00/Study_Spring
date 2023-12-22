package kr.co.smart.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired private CustomerDAO dao;
    @Override
    public int customer_resiter(CustomerVO vo) {
        return dao.customer_resiter(vo);
    }

    @Override
    public List<CustomerVO> customer_list() {
        return dao.customer_list();
    }

    @Override
    public List<CustomerVO> customer_list(String name) {
        return dao.customer_list(name);
    }

    @Override
    public CustomerVO customer_info(int id) {
        return dao.customer_info(id);
    }

    @Override
    public int customer_update(CustomerVO vo) {
        return dao.customer_update(vo);
    }

    @Override
    public int customer_delete(int id) {
        return dao.customer_delete(id);
    }
}
