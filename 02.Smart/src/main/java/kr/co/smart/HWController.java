package kr.co.smart;


import com.google.gson.Gson;
import kr.co.smart.homework.HomeworkService;
import kr.co.smart.homework.HomeworkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/homework") @Controller
public class HWController {

    @Autowired private HomeworkService service;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "-1") int customer_id, HttpSession session){
        session.setAttribute("category", "home");
        List<HomeworkVO> vo = service.customer_list(customer_id);
        String jsonData = new Gson().toJson(vo);
        System.out.println(jsonData);
        model.addAttribute("list",vo);
        model.addAttribute("customer_id", customer_id);


        return "homework/list";
    }

    @RequestMapping("/info")
    public String info(Model model ,int customer_id){
        model.addAttribute("vo",service.customer_info(customer_id));


        return "homework/info";
    }

}
