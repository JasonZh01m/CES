package com.sq.core.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sq.core.exception.ExtendException;
import com.sq.core.service.Service;
import com.sq.core.utils.AppContextUtil;
import com.sq.core.vo.PageParamModel;
import com.sq.core.vo.PaginQueryResult;
import com.sq.core.vo.RegistrarModel;
import com.sq.core.vo.ResultModel;
import com.sq.sso.vo.SSOSession;

public abstract class Action<T> {
	protected Service<T> service;
	private final Log Log = LogFactory.getLog(getClass());

	private final String br = "\r\n";

	public Action() {
		Class c = getClass();
		Type t = c.getGenericSuperclass();

		if ((t instanceof ParameterizedType)) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			Class nowClass = (Class) p[0];
			String className = nowClass.getSimpleName();
			// Modified By JasonZh 0728
			// String firstChar = className.charAt(0).toLowerCase();
			String firstChar = className.toLowerCase().charAt(0) + "";
			String nowChar = className.substring(1, className.length());
			className = firstChar + nowChar;
			this.service = ((Service) AppContextUtil.getBean(className
					+ "ServiceImpl"));
		} else {
			throw new ExtendException(
					"Extends the core controller class is not valid");
		}
	}

	@RequestMapping({ "/PageJson.do" })
	@ResponseBody
	public PaginQueryResult<T> getPage(
			@ModelAttribute("ppm") PageParamModel<T> ppm,
			@ModelAttribute("t") T t) {
		PaginQueryResult pqr = null;
		System.out.println("ppm: " + ppm);
		System.out.println("T: " + t);
		pqr = this.service.paginQuery(t, ppm.getPage(), ppm.getRows());
		return pqr;
	}

	@RequestMapping({ "/getMoJson.do" })
	@ResponseBody
	public T getModel(@RequestParam(value = "id", defaultValue = "0") int id) {
		Object model = null;
		model = this.service.findById(id);
		// Modified By JasonZh 0728
		// return model;
		return null;
	}

	@RequestMapping({ "/addJson.do" })
	@ResponseBody
	public ResultModel add(@ModelAttribute("t") T t, HttpServletRequest request) {
		ResultModel rm = new ResultModel();
		try {
			initRegistrarModel(t, request);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		int rs = this.service.create(t);
		if (rs > 0)
			rm.setSuccess(0);
		else {
			rm.setSuccess(1);
		}
		return rm;
	}

	private void initRegistrarModel(T t, HttpServletRequest request)
			throws IllegalArgumentException, IllegalAccessException {
		Class clz = t.getClass();
		if (clz.getSuperclass().equals(RegistrarModel.class)) {
			SSOSession session = (SSOSession) request.getSession()
					.getAttribute("$session".intern());
			System.out.println("session:" + session);
			if (session != null) {
				Field[] fields = clz.getSuperclass().getDeclaredFields();
				int len = fields.length;
				for (int i = 0; i < len; i++) {
					Field f = fields[i];
					if ("createUserName".intern().equals(f.getName())) {
						f.setAccessible(true);
						f.set(t, session.getUserName());
					} else if ("createUserId".intern().equals(f.getName())) {
						f.setAccessible(true);
						f.set(t, Integer.valueOf(session.getUserId()));
					}
				}
			}
		}
	}

	@RequestMapping({ "/delJson.do" })
	@ResponseBody
	public ResultModel remove(@RequestParam("id") String id) {
		ResultModel rm = new ResultModel();
		int rs = this.service.delete(id);
		if (rs > 0)
			rm.setSuccess(0);
		else {
			rm.setSuccess(1);
		}
		return rm;
	}

	@RequestMapping({ "/modifyJson.do" })
	@ResponseBody
	public ResultModel modify(@ModelAttribute("t") T t) {
		ResultModel rm = new ResultModel();
		int rs = this.service.update(t);
		if (rs > 0)
			rm.setSuccess(0);
		else {
			rm.setSuccess(1);
		}
		return rm;
	}

	@ExceptionHandler
	@ResponseBody
	public ResultModel resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex) {
		ResultModel result = new ResultModel();
		String url = request.getRequestURI();
		result.setSuccess(2);
		StringBuilder sb = new StringBuilder();
		sb.append(this.br);
		sb.append(ex);
		for (StackTraceElement elem : ex.getStackTrace()) {
			sb.append(this.br);
			sb.append(elem);
		}
		this.Log.error(sb);
		result.setMsg("URI:" + url + " exception:" + this.br + "["
				+ sb.toString() + "]");

		return result;
	}

	@RequestMapping({ "exportExcel.do" })
	public ResponseEntity<byte[]> exportExcel(
			@RequestParam(value = "colNames", defaultValue = "") String colNames,
			@RequestParam(value = "colIndex", defaultValue = "") String colIndex,
			@RequestParam(value = "fileName", defaultValue = "") String fileName)
			throws IOException {
		ResponseEntity respinseEntity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		bout.close();
		respinseEntity = new ResponseEntity(bout.toByteArray(), headers,
				HttpStatus.CREATED);
		return respinseEntity;
	}
}
