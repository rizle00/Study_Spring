package kr.co.smart;

import kr.co.smart.customer.CustomerServiceImp;
import kr.co.smart.customer.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CustomerController {
//    고객 목록 화면 요청
    @Autowired private CustomerServiceImp service;
//    필요한 정보가 있으면 파라미터로 선언한다
    @RequestMapping("/list.cu")
    public String list(Model model, HttpSession session, @RequestParam(defaultValue = "") String name){
        session.setAttribute("category", "cu");
//        DB에서 고객 목록 조회해 와 화면에 출력
        List<CustomerVO> list = service.customer_list(name);
//        List<CustomerVO> list = name.isEmpty() ? service.customer_list() : service.customer_list(name);
//        조회한 정보를 화면에 출력할 수 있도록 Model객체에 담기
        model.addAttribute("list",list);
        model.addAttribute("name", name);
        return "customer/list";
    }

    public String list(Model model, String name, HttpSession session ){
        session.setAttribute("category", "cu");
//        DB에서 고객 목록 조회해 와 화면에 출력
        List<CustomerVO> list = name == null? service.customer_list() : service.customer_list(name);
//        List<CustomerVO> list = name.isEmpty() ? service.customer_list() : service.customer_list(name);
//        조회한 정보를 화면에 출력할 수 있도록 Model객체에 담기
//        vlf
        model.addAttribute("list",list);
        return "customer/list";
    }

//    고객 정보 화면 요청
    @RequestMapping("/info.cu")
    public String info(Model model, int id){
//        화면에서 선택한 고객정보를 DB에서 조회해와 정보화면에 출력
//        조회해온 정보를 화면에 출력할 수 있도록 Model 객체에 담기
//        파라미터.. id
        CustomerVO vo = service.customer_info(id);
        model.addAttribute("vo",vo);

        return "customer/info";
    }

//    고객 정보 수정 화면 요청
    @RequestMapping("/modify.cu")
    public String modify(Model model, int id){
//        화면에서 선택한 고객정보를 DB에서 조회해 수정화면에 출력 -> Model 객체에 담기
        model.addAttribute("vo", service.customer_info(id));
        return "customer/modify";
    }

//    고객 정보 수정 저장처리 요청
    @RequestMapping("/update.cu")
    public String update(CustomerVO vo ){
//        화면에서 변경 입력한 정보로 DB에 변경 저장한다.
        service.customer_update(vo);

//        화면 연결 - 고객 정보 화면 응답
        return "redirect:info.cu?id="+vo.getCustomer_id(); //서블릿 요청
    }
//    DML처리를 한 후 화면 연결은 redirect
//    forward 는 그 화면에 남아있어서 새로고침하면 한번 더 기능함

//    고객 정보 삭제 처리 요청
    @RequestMapping("/delete.cu")
    public String  delete(int id){
//        선택한 고객정보를 DB에서 삭제 -> 목록화면으로 연결
        service.customer_delete(id);
        return "redirect:list.cu";
    }

//    신규 고객 정보 입력화면 요청
    @RequestMapping("/resister.cu")
    public String register(){

        return  "customer/resister";
    }

//    신규 고객 정보 저장 처리 요청

    @RequestMapping("/insert.cu")
    public String insert(CustomerVO vo){
//        화면에서 입력한 정보로 DB에 신규 저장하기 -> 목록화면으로 연결
        service.customer_resiter(vo);
        return "redirect:list.cu";
    }
}
