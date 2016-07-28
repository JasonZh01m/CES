 package com.sq.sso.controller;
 
 import com.sq.core.vo.ResultModel;
 import com.sq.core.vo.ZtreeBean;
 import com.sq.core.web.Action;
 import com.sq.sso.model.Permission;
 import com.sq.sso.model.Role;
 import com.sq.sso.service.PermissionService;
 import java.io.IOException;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/permission"})
 public class PermissionController extends Action<Permission>
 {
 
   @Resource
   private PermissionService permissionServiceImpl;
 
   @RequestMapping({"/getPermissionTree.do"})
   @ResponseBody
   public List<ZtreeBean> getPermissionTree()
     throws IOException
   {
     List results = this.permissionServiceImpl.queryAllPermissionZtreeBean();
     return results;
   }
   @RequestMapping({"/getParentPermissionTree.do"})
   @ResponseBody
   public List<ZtreeBean> getParentPermissionTree(HttpServletRequest request, HttpServletResponse response, String pId) throws IOException {
     List results = this.permissionServiceImpl
       .queryAllPermissionZtreeBean();
 
     return results;
   }
   @RequestMapping({"/permissionTree.do"})
   public String permissionTree() {
     return "/permission/permissionTree";
   }
   @RequestMapping({"/permissionManage.do"})
   public String permissionManage() {
     return "/permission/permissionInfo";
   }
   @RequestMapping({"/validateResourceString.do"})
   @ResponseBody
   public ResultModel validateResourceString(String resourceString, String permissionId) {
     Integer rs = this.permissionServiceImpl
       .validateResourceString(resourceString);
     ResultModel result = new ResultModel();
     if (rs == null)
       result.setSuccess(0);
     else {
       result.setSuccess(1);
     }
     return result;
   }
 
   @RequestMapping({"/getAllPermissionTree.do"})
   @ResponseBody
   public List<ZtreeBean> getAllPermissionTree(HttpServletRequest request, HttpServletResponse response, Role role) throws IOException {
     List results = null;
     if ((role != null) && (role.getId() > 0))
       results = this.permissionServiceImpl.queryAllPermissionByRole(role
         .getId());
     else {
       results = this.permissionServiceImpl.queryAllPermissionZtreeBean();
     }
     return results;
   }
   @RequestMapping({"/delPermissionJson.do"})
   @ResponseBody
   public ResultModel delPermissionJson(int permissionId) { ResultModel result = new ResultModel();
     int rs = this.permissionServiceImpl.deletePermissionId(permissionId);
     if (rs > 0)
       result.setSuccess(0);
     else {
       result.setSuccess(1);
     }
     return result; }
 
   @RequestMapping({"/updatePermission.do"})
   @ResponseBody
   public ResultModel updatePermission(Permission permission) {
     ResultModel result = new ResultModel();
     int rs = this.permissionServiceImpl.updatePermission(permission);
     if (rs > 0)
       result.setSuccess(0);
     else {
       result.setSuccess(1);
     }
     return result;
   }
   @RequestMapping({"/addPermission.do"})
   @ResponseBody
   public ResultModel addPermission(Permission permission) {
     ResultModel result = new ResultModel();
     int rs = this.permissionServiceImpl.addPermission(permission);
     if (rs > 0)
       result.setSuccess(0);
     else {
       result.setSuccess(1);
     }
     return result;
   }
 }

