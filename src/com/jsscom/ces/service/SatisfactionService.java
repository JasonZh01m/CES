package com.jsscom.ces.service;

import com.jsscom.ces.model.Satisfaction;
import com.jsscom.ces.vo.ReportSearchModel;
import com.sq.core.service.Service;
import com.sq.core.vo.PaginQueryResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract interface SatisfactionService extends Service<Satisfaction>
{
  public abstract PaginQueryResult<Satisfaction> queryEvaluatePage(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction, int paramInt1, int paramInt2);

  public abstract PaginQueryResult<Satisfaction> queryEvaluatePageByOrg(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction, int paramInt1, int paramInt2);

  public abstract PaginQueryResult<Satisfaction> queryEvaluatePageByBusiness(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction, int paramInt1, int paramInt2);

  public abstract HSSFWorkbook queryBusinessDescExcel(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction);

  public abstract HSSFWorkbook queryBusinessExcel(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction);

  public abstract HSSFWorkbook querySatisfactionExcel(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction);

  public abstract HSSFWorkbook querySatisfactionDescExcel(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction);

  public abstract HSSFWorkbook querySatisOrgDescExcel(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction);

  public abstract HSSFWorkbook querySatisOrgExcel(ReportSearchModel paramReportSearchModel, Satisfaction paramSatisfaction);
}

