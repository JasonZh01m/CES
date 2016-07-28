 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.OrganChat;
 import com.jsscom.ces.service.OrganChatService;
 import com.jsscom.ces.vo.ChatParam;
 import com.jsscom.ces.vo.MonthLineChartBean;
 import com.jsscom.ces.vo.ReportSearchModel;
 import com.sq.core.vo.PaginQueryResult;
 import com.sq.core.web.Action;
 import com.sq.sso.service.SsoService;
 import com.sq.sso.vo.SSOSession;
 import java.io.PrintStream;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/organChat"})
 public class OrganChatController extends Action<OrganChat>
 {
   protected final Log log = LogFactory.getLog(OrganChatController.class);
 
   @Resource
   private OrganChatService organChatService;
 
   @Resource
   private SsoService ssoService;
 
   @RequestMapping({"/getOrganChatJson.do"})
   @ResponseBody
   public PaginQueryResult<OrganChat> getOrganChat() { PaginQueryResult pqr = new PaginQueryResult();
     ReportSearchModel rsm = new ReportSearchModel();
     pqr.setRows(this.organChatService.queryPage(rsm));
     return pqr; } 
   @RequestMapping({"/getOrganChat.do"})
   @ResponseBody
   public MonthLineChartBean getOrganChat(ChatParam chatParam, HttpServletRequest request) {
     MonthLineChartBean mc = new MonthLineChartBean();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
 
     System.out.println("chatParam:" + chatParam);
     String finaOrg = chatParam.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return mc;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         chatParam.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         chatParam.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       chatParam.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         chatParam.setOrgCode(userOrg);
       else {
         chatParam.setOrgCode(finaOrg);
       }
     }
 
     return this.organChatService.queryForOrgChat(chatParam);
   }
 }

