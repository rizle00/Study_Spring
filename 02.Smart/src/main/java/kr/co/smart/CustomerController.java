package kr.co.smart;

import kr.co.smart.customer.CustomerServiceImp;
import kr.co.smart.customer.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CustomerController {
//    고객 목록 화면 요청
    @Autowired private CustomerServiceImp service;
    @RequestMapping("/list.cu")
    public String list(Model model){
//        DB에서 고객 목록 조회해 와 화면에 출력
        List<CustomerVO> list = service.customer_list();
//        조회한 정보를 화면에 출력할 수 있도록 Model객체에 담기
        model.addAttribute("list",list);
        return "customer/list";
    }
}
