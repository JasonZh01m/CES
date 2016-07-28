 package com.jsscom.ces.testCase;
 
 import com.sq.sso.data.dao.PermissionDao;
 import java.util.concurrent.CountDownLatch;
 
 public class TestPRunnable
   implements Runnable
 {
   private PermissionDao permissionDao;
   private CountDownLatch latch;
 
   public void run()
   {
     try
     {
       Thread.sleep(24L);
     }
     catch (InterruptedException e) {
       e.printStackTrace();
     }
     this.permissionDao.iterator("iterator", null);
     this.latch.countDown();
   }
 
   public PermissionDao getPermissionDao()
   {
     return this.permissionDao;
   }
 
   public void setPermissionDao(PermissionDao permissionDao) {
     this.permissionDao = permissionDao;
   }
 
   public CountDownLatch getLatch() {
     return this.latch;
   }
 
   public void setLatch(CountDownLatch latch) {
     this.latch = latch;
   }
 }

