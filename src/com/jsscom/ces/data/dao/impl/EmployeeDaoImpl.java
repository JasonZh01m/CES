package com.jsscom.ces.data.dao.impl;

import com.jsscom.ces.data.dao.EmployeeDao;
import com.jsscom.ces.model.Employee;
import com.sq.core.dao.impl.DaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl extends DaoImpl<Employee>
  implements EmployeeDao
{
}

