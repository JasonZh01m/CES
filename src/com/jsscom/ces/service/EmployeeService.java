package com.jsscom.ces.service;

import com.jsscom.ces.model.Employee;
import com.sq.core.service.Service;
import java.util.List;

public abstract interface EmployeeService extends Service<Employee>
{
  public abstract List<Employee> chackEmployee(Employee paramEmployee);
}

