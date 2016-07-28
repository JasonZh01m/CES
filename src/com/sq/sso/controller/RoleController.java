 package com.sq.sso.controller;
 
 import com.sq.core.vo.PaginQueryResult;
 import com.sq.core.web.Action;
 import com.sq.sso.model.Role;
 import com.sq.sso.service.RoleService;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/role"})
 public class RoleController extends Action<Role>
 {
 
   @Resource
   private RoleService roleServiceImpl;
 
   @RequestMapping({"/roleList.do"})
   public String roleList()
   {
     return "/role/roleList";
   }
   @RequestMapping({"/rolePage.do"})
   @ResponseBody
   public PaginQueryResult<Role> rolePage(Role role, int rows, int page) { System.out.println(String.format("role:%s,rows:%s,page:%s", new Object[] { role.toString(), Integer.valueOf(rows), Integer.valueOf(page) }));
     PaginQueryResult roles = null;
     try {
       roles = this.roleServiceImpl.paginQuery(role, rows * (page - 1) + 1, rows);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return roles;
   }
 
   @RequestMapping({"/updateRole.do"})
   @ResponseBody
   public Map<String, Integer> updateRole(Role role, String permissionIds)
   {
     Map result = new HashMap();
     try {
       this.roleServiceImpl.updateRole(role, permissionIds);
       result.put("success", Integer.valueOf(0));
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return result;
   }
   @RequestMapping({"/insertRole.do"})
   @ResponseBody
   public Map<String, Integer> insertRole(Role role, String permissionIds) {
     Map result = new HashMap();
     try {
       System.out.println(role.toString() + "===" + permissionIds);
       this.roleServiceImpl.insertRole(role, permissionIds);
       result.put("success", Integer.valueOf(0));
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return result;
   }
 
   @RequestMapping({"/checkRoleCode.do"})
   @ResponseBody
   public Map<String, Integer> checkRoleCode(Role role) {
     Map result = new HashMap();
     try {
       boolean isRepeat = this.roleServiceImpl.checkRoleCode(role);
       if (isRepeat)
         result.put("success", Integer.valueOf(1));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return result;
   }
   @RequestMapping({"/deleteRoleById.do"})
   @ResponseBody
   public Map<String, Integer> deleteRoleById(String id) { Map result = new HashMap();
     try {
       this.roleServiceImpl.delete(id);
       result.put("success", Integer.valueOf(0));
     }
     catch (Exception e) {
       e.printStackTrace();
       result.put("success", Integer.valueOf(1));
     }
     return result;
   }
 }

