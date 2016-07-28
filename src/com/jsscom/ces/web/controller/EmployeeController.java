package com.jsscom.ces.web.controller;

import com.jsscom.ces.model.Employee;
import com.jsscom.ces.service.EmployeeService;
import com.sq.core.web.Action;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/employee"})
public class EmployeeController extends Action<Employee>
{

  @Resource
  private EmployeeService employeeService;
}

