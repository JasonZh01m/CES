 package com.jsscom.ces.service.impl;
 
 import com.jsscom.ces.data.dao.EvaluateDao;
 import com.jsscom.ces.model.Evaluate;
 import com.jsscom.ces.service.EvaluateService;
 import com.jsscom.ces.vo.ReportSearchModel;
 import com.sq.core.service.impl.ServiceImpl;
 import com.sq.core.vo.PaginQueryResult;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.springframework.stereotype.Service;
 
 @Service
 public class EvaluateServiceImpl extends ServiceImpl<Evaluate>
   implements EvaluateService
 {
 
   @Resource
   private EvaluateDao evaluateDao;
 
   public PaginQueryResult<Evaluate> queryPage(ReportSearchModel rsm, Evaluate e, int index, int size)
   {
     PaginQueryResult pqr = new PaginQueryResult();
     Map map = new HashMap();
     map.put("startTime".intern(), rsm.getStartTime());
     map.put("endTime".intern(), rsm.getEndTime());
     map.put("obj".intern(), e);
     map.put("orgCode".intern(), rsm.getOrgCode());
     int count = this.evaluateDao.count(map);
     if ((count > 0) && (index > 0)) {
       int pageCount = (count - 1) / size + 1;
       pqr.setRows(this.evaluateDao.queryPage(map, (index - 1) * size, size));
       pqr.setTotal(pageCount);
       pqr.setPage(index);
       pqr.setRecords(count);
     }
     return pqr;
   }
 }

