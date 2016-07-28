 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.EvaluateLevel;
 import com.jsscom.ces.service.EvaluateLevelService;
 import com.sq.core.vo.ResultModel;
 import com.sq.core.web.Action;
 import java.io.PrintStream;
 import javax.annotation.Resource;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/evaluateLevel"})
 public class EvaluateLevelController extends Action<EvaluateLevel>
 {
   protected final Log log = LogFactory.getLog(EvaluateLevelController.class);
 
   @Resource
   private EvaluateLevelService evaluateLevelService;
 
   @RequestMapping({"/deleEvaluateLevelByLevelJson.do"})
   @ResponseBody
   public ResultModel deleEvaluateLevel(String ids) { System.out.println("ids : " + ids);
     int rs = 0;
     ResultModel rm = new ResultModel();
     String[] id = ids.split(",");
     for (int i = 0; i < id.length; i++) {
       rs = this.evaluateLevelService.delete(id[i]);
     }
     if (rs > 0) {
       rm.setSuccess(0);
       this.log.info("删除成功！");
     } else {
       rm.setSuccess(1);
       this.log.info("删除失败！");
     }
     return rm; }
 
   @RequestMapping({"/validatorLevelJson.do"})
   @ResponseBody
   public ResultModel validatorLevel(int level) {
     ResultModel rm = new ResultModel();
     System.out.println("level : " + level);
     EvaluateLevel evaluateLevel = (EvaluateLevel)this.evaluateLevelService.findById(level);
     if (evaluateLevel == null) {
       rm.setSuccess(1);
       this.log.info("该终端已存在... ");
     } else {
       rm.setSuccess(0);
       this.log.info("该终端不存在... ");
     }
     return rm;
   }
 }

