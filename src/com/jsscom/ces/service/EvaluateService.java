package com.jsscom.ces.service;

import com.jsscom.ces.model.Evaluate;
import com.jsscom.ces.vo.ReportSearchModel;
import com.sq.core.service.Service;
import com.sq.core.vo.PaginQueryResult;

public abstract interface EvaluateService extends Service<Evaluate>
{
  public abstract PaginQueryResult<Evaluate> queryPage(ReportSearchModel paramReportSearchModel, Evaluate paramEvaluate, int paramInt1, int paramInt2);
}

