package kr.co.smart.hr;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class EmployeeVO {

    private int employee_id, department_id, salary, manager_id;
    private String last_name, first_name, name, department_name
            , job_id, job_title, phone_number, email, manager_name;
    private Date hire_date;
    private Double commission_pct;
}
