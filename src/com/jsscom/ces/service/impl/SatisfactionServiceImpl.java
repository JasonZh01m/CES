package com.jsscom.ces.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.jsscom.ces.data.dao.SatisfactionDao;
import com.jsscom.ces.model.Satisfaction;
import com.jsscom.ces.service.SatisfactionService;
import com.jsscom.ces.vo.ReportSearchModel;
import com.sq.core.service.impl.ServiceImpl;
import com.sq.core.vo.PaginQueryResult;

@Service
public class SatisfactionServiceImpl extends ServiceImpl<Satisfaction>
		implements SatisfactionService {

	@Resource
	private SatisfactionDao satisfactionDao;

	@Override
	public PaginQueryResult<Satisfaction> queryEvaluatePage(
			ReportSearchModel rsm, Satisfaction e, int index, int size) {
		PaginQueryResult pqr = new PaginQueryResult();
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("obj".intern(), e);
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		int count = this.satisfactionDao.count("countByEmp", map);
		list = this.satisfactionDao.queryPage("queryForPageByEmp", map,
				(index - 1) * size, size);

		if ((count > 0) && (index > 0)) {
			int pageCount = (count - 1) / size + 1;
			pqr.setRows(forList(list));
			pqr.setTotal(pageCount);
			pqr.setPage(index);
			pqr.setRecords(count);
		}
		return pqr;
	}

	private List<Satisfaction> forList(List<Satisfaction> list) {
		DecimalFormat df = new DecimalFormat("0.00%");
		List newList = new ArrayList();
		for (Satisfaction satisfaction : list) {
			satisfaction.setAgvPoint1(avgCalculate(satisfaction.getSumCount(),
					satisfaction.getPoint1()));
			satisfaction.setAgvPoint3(avgCalculate(satisfaction.getSumCount(),
					satisfaction.getPoint3()));
			satisfaction.setAgvPoint5(avgCalculate(satisfaction.getSumCount(),
					satisfaction.getPoint5()));
			satisfaction.setAgvsSatis(df.format(satisfaction.getAgvSatis()));
			newList.add(satisfaction);
		}
		return newList;
	}

	private String avgCalculate(int sumCode, int point) {
		double point_double = point * 1.0D;
		double result = point_double / sumCode;
		DecimalFormat df = new DecimalFormat("0.00%");

		return df.format(result);
	}

	@Override
	public PaginQueryResult<Satisfaction> queryEvaluatePageByOrg(
			ReportSearchModel rsm, Satisfaction e, int index, int size) {
		PaginQueryResult pqr = new PaginQueryResult();
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		int count = this.satisfactionDao.count("countByOrg", map);
		list = this.satisfactionDao.queryPage("queryForPageByOrg", map,
				(index - 1) * size, size);

		if ((count > 0) && (index > 0)) {
			int pageCount = (count - 1) / size + 1;
			pqr.setRows(forList(list));
			pqr.setTotal(pageCount);
			pqr.setPage(index);
			pqr.setRecords(count);
		}
		return pqr;
	}

	@Override
	public PaginQueryResult<Satisfaction> queryEvaluatePageByBusiness(
			ReportSearchModel rsm, Satisfaction e, int index, int size) {
		PaginQueryResult pqr = new PaginQueryResult();
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		int count = this.satisfactionDao.count("countByBusiness", map);
		list = this.satisfactionDao.queryPage("queryForPageByBusiness", map,
				(index - 1) * size, size);

		if ((count > 0) && (index > 0)) {
			int pageCount = (count - 1) / size + 1;
			pqr.setRows(forList(list));
			pqr.setTotal(pageCount);
			pqr.setPage(index);
			pqr.setRecords(count);
		}
		return pqr;
	}

	@Override
	public HSSFWorkbook queryBusinessDescExcel(ReportSearchModel rsm,
			Satisfaction e) {
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		list = this.satisfactionDao.queryPage("queryForBusinessByExcel", map,
				0, 0);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("业务满意度排名");
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment((short) 2);
		HSSFCell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue("业务名称");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellValue("非常满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellValue("一般满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellValue("不满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(4);
		cellHeader.setCellValue("平均满意度");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(5);
		cellHeader.setCellValue("业务量");
		cellHeader.setCellStyle(style);
		for (int i = 0; i < forList(list).size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(
					forList(list).get(i).getServiceName());
			row.createCell(1).setCellValue(forList(list).get(i).getPoint1());
			row.createCell(2).setCellValue(forList(list).get(i).getPoint3());
			row.createCell(3).setCellValue(forList(list).get(i).getPoint5());
			row.createCell(4).setCellValue(forList(list).get(i).getAgvsSatis());
			row.createCell(5).setCellValue(forList(list).get(i).getSumCount());
		}

		return wb;
	}

	@Override
	public HSSFWorkbook queryBusinessExcel(ReportSearchModel rsm, Satisfaction e) {
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		list = this.satisfactionDao.queryPage("queryForBusinessByExcel", map,
				0, 0);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("业务满意度");
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment((short) 2);
		HSSFCell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue("业务名称");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellValue("平均满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellValue("非常满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellValue("一般满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(4);
		cellHeader.setCellValue("不满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(5);
		cellHeader.setCellValue("业务量");
		cellHeader.setCellStyle(style);
		for (int i = 0; i < forList(list).size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(
					forList(list).get(i).getServiceName());
			row.createCell(1).setCellValue(forList(list).get(i).getAgvsSatis());
			row.createCell(2).setCellValue(forList(list).get(i).getAgvPoint1());
			row.createCell(3).setCellValue(forList(list).get(i).getAgvPoint3());
			row.createCell(4).setCellValue(forList(list).get(i).getAgvPoint5());
			row.createCell(5).setCellValue(forList(list).get(i).getSumCount());
		}

		return wb;
	}

	@Override
	public HSSFWorkbook querySatisfactionExcel(ReportSearchModel rsm,
			Satisfaction e) {
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		list = this.satisfactionDao.queryPage("queryForEmpByExcel", map, 0, 0);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("员工满意度");
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment((short) 2);
		HSSFCell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue("机构名称");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellValue("员工姓名");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellValue("平均满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellValue("非常满意占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(4);
		cellHeader.setCellValue("一般满意占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(5);
		cellHeader.setCellValue("不满意占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(6);
		cellHeader.setCellValue("业务量");
		cellHeader.setCellStyle(style);
		for (int i = 0; i < forList(list).size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(forList(list).get(i).getOrgName());
			row.createCell(1).setCellValue(forList(list).get(i).getEmpName());
			row.createCell(2).setCellValue(forList(list).get(i).getAgvsSatis());
			row.createCell(3).setCellValue(forList(list).get(i).getAgvPoint1());
			row.createCell(4).setCellValue(forList(list).get(i).getAgvPoint3());
			row.createCell(5).setCellValue(forList(list).get(i).getAgvPoint5());
			row.createCell(6).setCellValue(forList(list).get(i).getSumCount());
		}
		return wb;
	}

	@Override
	public HSSFWorkbook querySatisfactionDescExcel(ReportSearchModel rsm,
			Satisfaction e) {
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		list = this.satisfactionDao.queryPage("queryForEmpByExcel", map, 0, 0);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("员工满意度排名");
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment((short) 2);
		HSSFCell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue("机构名称");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellValue("员工姓名");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellValue("非常满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellValue("一般满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(4);
		cellHeader.setCellValue("不满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(5);
		cellHeader.setCellValue("平均满意度");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(6);
		cellHeader.setCellValue("业务量");
		cellHeader.setCellStyle(style);
		for (int i = 0; i < forList(list).size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(forList(list).get(i).getOrgName());
			row.createCell(1).setCellValue(forList(list).get(i).getEmpName());
			row.createCell(2).setCellValue(forList(list).get(i).getPoint1());
			row.createCell(3).setCellValue(forList(list).get(i).getPoint3());
			row.createCell(4).setCellValue(forList(list).get(i).getPoint5());
			row.createCell(5).setCellValue(forList(list).get(i).getAgvsSatis());
			row.createCell(6).setCellValue(forList(list).get(i).getSumCount());
		}
		return wb;
	}

	@Override
	public HSSFWorkbook querySatisOrgDescExcel(ReportSearchModel rsm,
			Satisfaction e) {
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		list = this.satisfactionDao.queryPage("queryForOrgByExcel", map, 0, 0);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("机构满意度排名");
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment((short) 2);
		HSSFCell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue("机构名称");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellValue("非常满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellValue("一般满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellValue("不满意次数");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(4);
		cellHeader.setCellValue("平均满意度");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(5);
		cellHeader.setCellValue("业务量");
		cellHeader.setCellStyle(style);
		for (int i = 0; i < forList(list).size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(forList(list).get(i).getOrgName());
			row.createCell(1).setCellValue(forList(list).get(i).getPoint1());
			row.createCell(2).setCellValue(forList(list).get(i).getPoint3());
			row.createCell(3).setCellValue(forList(list).get(i).getPoint5());
			row.createCell(4).setCellValue(forList(list).get(i).getAgvsSatis());
			row.createCell(5).setCellValue(forList(list).get(i).getSumCount());
		}
		return wb;
	}

	@Override
	public HSSFWorkbook querySatisOrgExcel(ReportSearchModel rsm, Satisfaction e) {
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("startTime".intern(), rsm.getStartTime());
		map.put("endTime".intern(), rsm.getEndTime());
		map.put("orgCode".intern(), rsm.getOrgCode());
		map.put("serviceCode".intern(), rsm.getServiceCode());
		map.put("obj".intern(), e);
		map.put("orgId".intern(), rsm.getOrgId());
		list = this.satisfactionDao.queryPage("queryForOrgByExcel", map, 0, 0);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("机构满意度");
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment((short) 2);
		HSSFCell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue("机构名称");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellValue("平均满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellValue("非常满意占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellValue("一般满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(4);
		cellHeader.setCellValue("不满意度占比");
		cellHeader.setCellStyle(style);
		cellHeader = rowHeader.createCell(5);
		cellHeader.setCellValue("业务量");
		cellHeader.setCellStyle(style);
		for (int i = 0; i < forList(list).size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(forList(list).get(i).getOrgName());
			row.createCell(1).setCellValue(forList(list).get(i).getAgvsSatis());
			row.createCell(2).setCellValue(forList(list).get(i).getAgvPoint1());
			row.createCell(3).setCellValue(forList(list).get(i).getAgvPoint3());
			row.createCell(4).setCellValue(forList(list).get(i).getAgvPoint5());
			row.createCell(5).setCellValue(forList(list).get(i).getSumCount());
		}
		return wb;
	}
}
