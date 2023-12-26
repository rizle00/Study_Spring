package kr.co.smart;


import kr.co.smart.hr.EmployeeVO;
import kr.co.smart.hr.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller @RequestMapping("/hr")
public class HrController {
 @Autowired private HrService service;
//    사원 목록 화면 요청
    @RequestMapping("/list")
    public String list(HttpSession session, Model model){
        session.setAttribute("category","hr");

//        DB에서 사원 목록을 조회해 와 목록 화면에 출력 -> Model 객체에 담기
        model.addAttribute("list", service.employee_list());
        return "hr/list";
    }

//    사원 정보 조회 하면 요청
    @RequestMapping("/info")
    public String info(int id, Model model){
//        선택한 사원정보를  DB에서 조회 -> 정보화면에 출력할 수 있도록 Model객체에 담기
        model.addAttribute("vo",service.employee_info(id));
        return "hr/info";
    }

//    사원 정보 삭제 처리 요청
    @RequestMapping("/delete")
    public String delete(int id){
//        해당 사눵 정보를 DB에서 삭제하기 -> 목록화면으로 연결
    service.employee_delete(id);

        return "redirect:list";
    }

//    사원 정보 수정 화면 요청
    @RequestMapping("/modify")
    public String modify(Model model, int id){
//        해당 사원 정보를 DB에서 조회 -> 수정화면에 출력할 수 있도록 Model객체에 담기
        model.addAttribute("vo",service.employee_info(id));

//         부서, 업무 변경 할 수 있도록 부서/업무 목록 조회해 화면에 출력할 수 있도록 Model에 담기
        model.addAttribute("departments", service.hr_department_list());
        model.addAttribute("jobs", service.hr_job_list());
        return "hr/modify";
    }

//    사원 정보 변경 저장 처리 요청
    @RequestMapping("/update")
    public String update(EmployeeVO vo){
//        화면에서 변경입력한 정보로 DB에 변경 저장 -> 정보화면으로 연결
        service.employee_update(vo);
        return "redirect:info?id="+vo.getEmployee_id();

    }
//    신규 사원 등록 화면 요청
    @RequestMapping("/register")
    public String register(Model model){
//        부서, 업무 선택할 수 있도록 DB에서 조회해 와 화면에 출력 -> Model 객체에 담기
        model.addAttribute("departments", service.hr_department_list());
        model.addAttribute("jobs",service.hr_job_list());
        model.addAttribute("managers", service.hr_manager_list());

        return "hr/register";
    }

//    신규 사원 등록 저장 처리
    @RequestMapping("/insert")
    public String insert(EmployeeVO vo){
        service.employee_register(vo);
        return "redirect:list";
    }
}
