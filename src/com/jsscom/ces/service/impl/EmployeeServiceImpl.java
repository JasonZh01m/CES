 package com.jsscom.ces.service.impl;
 
 import com.jsscom.ces.data.dao.EmployeeDao;
 import com.jsscom.ces.model.Employee;
 import com.jsscom.ces.service.EmployeeService;
 import com.sq.core.service.impl.ServiceImpl;
 import java.util.List;
 import javax.annotation.Resource;
 import org.springframework.stereotype.Service;
 
 @Service
 public class EmployeeServiceImpl extends ServiceImpl<Employee>
   implements EmployeeService
 {
 
   @Resource
   private EmployeeDao employeeDao;
 
   public List<Employee> chackEmployee(Employee employee)
   {
     List empList = this.employeeDao.query("chackEmployee", employee);
     return empList;
   }
 }

