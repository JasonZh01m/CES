 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.DevInfo;
 import com.jsscom.ces.model.Evaluate;
 import com.jsscom.ces.model.Satisfaction;
 import com.jsscom.ces.service.EvaluateService;
 import com.jsscom.ces.service.OrganizationService;
 import com.jsscom.ces.service.SatisfactionService;
 import com.jsscom.ces.service.UserOrganizationRelationService;
 import com.jsscom.ces.vo.LineChatParam;
 import com.jsscom.ces.vo.MonthLineChartBean;
 import com.jsscom.ces.vo.ReportSearchModel;
 import com.sq.core.vo.PaginQueryResult;
 import com.sq.core.web.Action;
 import com.sq.sso.service.SsoService;
 import com.sq.sso.vo.SSOSession;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.net.URLEncoder;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/report"})
 public class ReportController extends Action<DevInfo>
 {
   protected final Log log = LogFactory.getLog(ReportController.class);
 
   @Resource
   private EvaluateService evaluateService;
 
   @Resource
   private SatisfactionService satisfactionService;
 
   @Resource
   private SsoService ssoService;
 
   @Resource
   private UserOrganizationRelationService userOrganizationRelationService;
 
   @Resource
   private OrganizationService organizationService;
   private String orgId;
 
   @RequestMapping({"/organChatData.do"})
   @ResponseBody
   public MonthLineChartBean registerDevInfo(LineChatParam lcp) { MonthLineChartBean mlcb = new MonthLineChartBean();
     mlcb.setOrganName("中央局");
     mlcb.setMonth1("80.11");
     mlcb.setMonth2("78");
     mlcb.setMonth3("68");
     mlcb.setMonth4("82");
     mlcb.setMonth5("91");
     mlcb.setMonth6("98");
     mlcb.setMonth7("87");
     mlcb.setMonth8("71");
     mlcb.setMonth9("66");
     mlcb.setMonth10("71");
     mlcb.setMonth11("82");
     mlcb.setMonth12("89");
     return mlcb; }
 
   @RequestMapping({"/evaluateDetail.do"})
   @ResponseBody
   public PaginQueryResult<Evaluate> evaluateDetail(@ModelAttribute("rsm") ReportSearchModel rsm, @ModelAttribute("e") Evaluate e, int page, int rows, HttpServletRequest request)
   {
     PaginQueryResult pqr = new PaginQueryResult();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     rsm.setUserId(sSOSession.getUserId());
     System.out.println("rsm:" + rsm);
     System.out.println("e:" + e);
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return pqr;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     e.setOrgCode(rsm.getOrgCode());
     return this.evaluateService.queryPage(rsm, e, page, rows);
   }
   @RequestMapping({"/satisfactionJson.do"})
   @ResponseBody
   public PaginQueryResult<Satisfaction> satisfactionDetail(@ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, int page, int rows, HttpServletRequest request) {
     PaginQueryResult pqr = new PaginQueryResult();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     rsm.setUserId(sSOSession.getUserId());
     System.out.println("rsm:" + rsm);
     System.out.println("e:" + e);
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return pqr;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
 
     }
 
     return this.satisfactionService.queryEvaluatePage(rsm, e, page, rows);
   }
   @RequestMapping({"/satisfactionByOrgJson.do"})
   @ResponseBody
   public PaginQueryResult<Satisfaction> satisfactionByOrg(@ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, int page, int rows, HttpServletRequest request) {
     PaginQueryResult pqr = new PaginQueryResult();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return pqr;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     return this.satisfactionService.queryEvaluatePageByOrg(rsm, e, page, rows);
   }
   @RequestMapping({"/satisfactionByBusinessJson.do"})
   @ResponseBody
   public PaginQueryResult<Satisfaction> satisfactionByBusiness(@ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, int page, int rows, HttpServletRequest request) {
     PaginQueryResult pqr = new PaginQueryResult();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return pqr;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     return this.satisfactionService.queryEvaluatePageByBusiness(rsm, e, page, rows);
   }
 
   @RequestMapping({"/businessDescByExcelJson.do"})
   public ResponseEntity<byte[]> exportBusinessDescExcel(@RequestParam(value="colNames", defaultValue="") String colNames, @RequestParam(value="colIndex", defaultValue="") String colIndex, @RequestParam(value="fileName", defaultValue="") String fileName, @ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     this.log.debug("导出业务满意度排名报表----->");
     ResponseEntity respinseEntity = null;
     HttpHeaders headers = new HttpHeaders();
     if (!isff(request)) {
       fileName = URLEncoder.encode("业务满意度排名报表.xls".intern(), "UTF-8".intern());
       this.log.debug("非火狐浏览器----- > ");
     } else {
       fileName = new String("业务满意度排名报表.xls".intern().getBytes("UTF-8".intern()), 
         "ISO-8859-1".intern());
       this.log.debug("火狐浏览器----- > ");
     }
 
     headers.setContentDispositionFormData("attachment", fileName);
 
     response.setContentType("application/octet-stream;charset=UTF-8");
     ByteArrayOutputStream bout = new ByteArrayOutputStream();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return respinseEntity;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     HSSFWorkbook wb = this.satisfactionService.queryBusinessDescExcel(rsm, e);
     wb.write(bout);
     bout.close();
     respinseEntity = new ResponseEntity(bout.toByteArray(), headers, HttpStatus.OK);
     return respinseEntity;
   }
 
   @RequestMapping({"/businessByExcelJson.do"})
   public ResponseEntity<byte[]> exportBusinessExcel(@RequestParam(value="colNames", defaultValue="") String colNames, @RequestParam(value="colIndex", defaultValue="") String colIndex, @RequestParam(value="fileName", defaultValue="") String fileName, @ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     this.log.debug("导出业务满意度报表----->");
     ResponseEntity respinseEntity = null;
     HttpHeaders headers = new HttpHeaders();
     if (!isff(request)) {
       fileName = URLEncoder.encode("业务满意度报表.xls".intern(), "UTF-8".intern());
       this.log.debug("非火狐浏览器----- > ");
     } else {
       fileName = new String("业务满意度报表.xls".intern().getBytes("UTF-8".intern()), 
         "ISO-8859-1".intern());
       this.log.debug("火狐浏览器----- > ");
     }
 
     headers.setContentDispositionFormData("attachment", fileName);
     response.setContentType("application/octet-stream;charset=UTF-8");
     ByteArrayOutputStream bout = new ByteArrayOutputStream();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return respinseEntity;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     HSSFWorkbook wb = this.satisfactionService.queryBusinessExcel(rsm, e);
     wb.write(bout);
     bout.close();
     respinseEntity = new ResponseEntity(bout.toByteArray(), headers, HttpStatus.OK);
     return respinseEntity;
   }
 
   @RequestMapping({"/satisfactionByExcelJson.do"})
   public ResponseEntity<byte[]> exportSatisfactionExcel(@RequestParam(value="colNames", defaultValue="") String colNames, @RequestParam(value="colIndex", defaultValue="") String colIndex, @RequestParam(value="fileName", defaultValue="") String fileName, @ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     this.log.debug("导出员工满意度报表----->");
     ResponseEntity respinseEntity = null;
     HttpHeaders headers = new HttpHeaders();
     if (isff(request)) {
       fileName = new String("员工满意度报表.xls".intern().getBytes("UTF-8".intern()), 
         "ISO-8859-1".intern());
       this.log.debug("火狐浏览器----- > ");
     } else {
       fileName = URLEncoder.encode("员工满意度报表.xls".intern(), "UTF-8".intern());
       this.log.debug("非火狐浏览器----- > ");
     }
     headers.setContentDispositionFormData("attachment", fileName);
     response.setContentType("application/octet-stream;charset=UTF-8");
     ByteArrayOutputStream bout = new ByteArrayOutputStream();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return respinseEntity;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     HSSFWorkbook wb = this.satisfactionService.querySatisfactionExcel(rsm, e);
     wb.write(bout);
     bout.close();
     respinseEntity = new ResponseEntity(bout.toByteArray(), headers, HttpStatus.OK);
     return respinseEntity;
   }
 
   @RequestMapping({"/satisfactionDescByExcelJson.do"})
   public ResponseEntity<byte[]> exportSatisfactionDescExcel(@RequestParam(value="colNames", defaultValue="") String colNames, @RequestParam(value="colIndex", defaultValue="") String colIndex, @RequestParam(value="fileName", defaultValue="") String fileName, @ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     this.log.debug("导出员工满意度排名报表----->");
     ResponseEntity respinseEntity = null;
     HttpHeaders headers = new HttpHeaders();
     if (!isff(request)) {
       fileName = URLEncoder.encode("员工满意度排名报表.xls".intern(), "UTF-8".intern());
       this.log.debug("非火狐浏览器----- > ");
     } else {
       fileName = new String("员工满意度排名报表.xls".intern().getBytes("UTF-8".intern()), 
         "ISO-8859-1".intern());
       this.log.debug("火狐浏览器----- > ");
     }
 
     headers.setContentDispositionFormData("attachment", fileName);
     response.setContentType("application/octet-stream;charset=UTF-8");
     ByteArrayOutputStream bout = new ByteArrayOutputStream();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return respinseEntity;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     HSSFWorkbook wb = this.satisfactionService.querySatisfactionDescExcel(rsm, e);
     wb.write(bout);
     bout.close();
     respinseEntity = new ResponseEntity(bout.toByteArray(), headers, HttpStatus.OK);
     return respinseEntity;
   }
 
   @RequestMapping({"/satisOrgByExcelJson.do"})
   public ResponseEntity<byte[]> exportSatisOrgExcel(@RequestParam(value="colNames", defaultValue="") String colNames, @RequestParam(value="colIndex", defaultValue="") String colIndex, @RequestParam(value="fileName", defaultValue="") String fileName, @ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     this.log.debug("导出机构满意度报表----->");
     ResponseEntity respinseEntity = null;
     HttpHeaders headers = new HttpHeaders();
     if (!isff(request)) {
       fileName = URLEncoder.encode("机构满意度报表.xls".intern(), "UTF-8".intern());
       this.log.debug("非火狐浏览器----- > ");
     } else {
       fileName = new String("机构满意度报表.xls".intern().getBytes("UTF-8".intern()), 
         "ISO-8859-1".intern());
       this.log.debug("火狐浏览器----- > ");
     }
 
     headers.setContentDispositionFormData("attachment", fileName);
     response.setContentType("application/octet-stream;charset=UTF-8");
     ByteArrayOutputStream bout = new ByteArrayOutputStream();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return respinseEntity;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     HSSFWorkbook wb = this.satisfactionService.querySatisOrgExcel(rsm, e);
     wb.write(bout);
     bout.close();
     respinseEntity = new ResponseEntity(bout.toByteArray(), headers, HttpStatus.OK);
     return respinseEntity;
   }
 
   @RequestMapping({"/satisOrgDescByExcelJson.do"})
   public ResponseEntity<byte[]> exportSatisOrgDescExcel(@RequestParam(value="colNames", defaultValue="") String colNames, @RequestParam(value="colIndex", defaultValue="") String colIndex, @RequestParam(value="fileName", defaultValue="") String fileName, @ModelAttribute("rsm") ReportSearchModel rsm, Satisfaction e, HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     this.log.debug("导出机构满意度排名报表----->");
     ResponseEntity respinseEntity = null;
     HttpHeaders headers = new HttpHeaders();
     if (!isff(request)) {
       fileName = URLEncoder.encode("机构满意度排名报表.xls".intern(), "UTF-8".intern());
       this.log.debug("非火狐浏览器----- > ");
     } else {
       fileName = new String("机构满意度排名报表.xls".intern().getBytes("UTF-8".intern()), 
         "ISO-8859-1".intern());
       this.log.debug("火狐浏览器----- > ");
     }
     headers.setContentDispositionFormData("attachment", fileName);
     response.setContentType("application/octet-stream;charset=UTF-8");
     ByteArrayOutputStream bout = new ByteArrayOutputStream();
     SSOSession sSOSession = this.ssoService.getSsoSession(request);
     System.out.println("rsm : " + rsm.toString());
     System.out.println("e : " + e.toString());
     this.orgId = "";
     String finaOrg = rsm.getOrgCode();
     String userOrg = sSOSession.getOrgCode();
     if ((userOrg == null) || (userOrg.equals(""))) {
       this.log.debug("未分配机构！");
       return respinseEntity;
     }
     if (finaOrg == null) {
       if (sSOSession.getUserId().equals("1")) {
         this.log.debug("超级管理员：");
         rsm.setOrgCode("0");
       } else {
         this.log.debug("普通用户：");
         rsm.setOrgCode(userOrg);
       }
     }
     else if (sSOSession.getUserId().equals("1")) {
       this.log.debug("超级管理员：");
       rsm.setOrgCode(finaOrg);
     } else {
       this.log.debug("普通用户：");
       if (finaOrg.length() < userOrg.length())
         rsm.setOrgCode(userOrg);
       else {
         rsm.setOrgCode(finaOrg);
       }
     }
 
     this.log.debug("机构编号:" + rsm.getOrgCode());
     HSSFWorkbook wb = this.satisfactionService.querySatisOrgDescExcel(rsm, e);
     wb.write(bout);
     bout.close();
     respinseEntity = new ResponseEntity(bout.toByteArray(), headers, HttpStatus.OK);
     return respinseEntity;
   }
 
   private boolean isff(HttpServletRequest request)
   {
     return request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox".intern()) > 0;
   }
 }

