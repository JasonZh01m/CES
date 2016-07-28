 package com.jsscom.ces.testCase;
 
 import com.alibaba.fastjson.JSON;
 import com.jsscom.ces.data.dao.CarInfoTempDao;
 import com.jsscom.ces.data.dao.DevInfoDao;
 import com.jsscom.ces.data.dao.EvaluateDao;
 import com.jsscom.ces.data.dao.OrganChatDao;
 import com.jsscom.ces.data.dao.SatisfactionDao;
 import com.jsscom.ces.service.DataUploadService;
 import com.jsscom.ces.service.EmployeePhotoService;
 import com.jsscom.ces.service.EvaluateService;
 import com.jsscom.ces.service.OrganChatService;
 import com.jsscom.ces.service.SatisfactionService;
 import com.sq.sso.data.dao.PermissionDao;
 import com.sq.sso.data.dao.PermissionRoleRelationDao;
 import java.io.PrintStream;
 import javax.annotation.Resource;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.test.context.ContextConfiguration;
 import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
 @RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations={"classpath:config/applicationContext.xml"})
 public class TestClass
 {
 
   @Resource
   private DevInfoDao devInfoDao;
 
   @Resource
   private OrganChatDao organChatDao;
 
   @Resource
   private PermissionDao permissionDao;
 
   @Resource
   private DataUploadService dataUploadService;
 
   @Resource
   private PermissionRoleRelationDao permissionRoleRelationDao;
 
   @Resource
   private CarInfoTempDao carInfoTempDao;
 
   @Resource
   private EvaluateDao evaluateDao;
 
   @Resource
   private EvaluateService evaluateService;
 
   @Resource
   private SatisfactionDao satisfactionDao;
 
   @Resource
   private SatisfactionService satisfactionService;
 
   @Resource
   private OrganChatService organChatService;
 
   @Resource
   private EmployeePhotoService employeePhotoService;
 
   @Test
   public void Testquery()
   {
     System.out.println(JSON.toJSONString(this.employeePhotoService.getEmployee("111")));
   }
 
   public static void main(String[] args)
   {
   }
 
   public static int getRandom(int start, int end)
   {
     if ((start > end) || (start < 0) || (end < 0)) {
       return -1;
     }
     return (int)(Math.random() * (end - start + 1)) + start;
   }
 }

