package com.jsscom.ces.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jsscom.ces.model.DevInfo;
import com.jsscom.ces.model.Employee;
import com.jsscom.ces.model.EmployeePhoto;
import com.jsscom.ces.model.EmployeePhotoModel;
import com.jsscom.ces.model.Evaluate;
import com.jsscom.ces.model.EvaluateLevel;
import com.jsscom.ces.model.Media;
import com.jsscom.ces.model.Organization;
import com.jsscom.ces.model.UpdateFile;
import com.jsscom.ces.model.interceptor.DevInfoClient;
import com.jsscom.ces.model.interceptor.MediaClient;
import com.jsscom.ces.service.DevInfoService;
import com.jsscom.ces.service.EmployeePhotoService;
import com.jsscom.ces.service.EmployeeService;
import com.jsscom.ces.service.EvaluateLevelService;
import com.jsscom.ces.service.EvaluateService;
import com.jsscom.ces.service.MediaService;
import com.jsscom.ces.service.OrganizationService;
import com.jsscom.ces.service.UpdateFileService;
import com.jsscom.ces.vo.ArgsModel;
import com.jsscom.ces.vo.DevAuthModel;
import com.jsscom.ces.vo.EmployeeModel;
import com.jsscom.ces.vo.RequestModel;
import com.jsscom.ces.vo.ResponseModel;
import com.jsscom.ces.vo.ServerTimeModel;
import com.sq.core.cache.JsonSerializable;
import com.sq.core.utils.DateUtil;
import com.sq.core.utils.HttpUtil;
import com.sq.core.web.Action;

@Controller
@RequestMapping({ "/" })
public class DataUploadController extends Action<DevInfo> {
	protected final Log log = LogFactory.getLog(DataUploadController.class);

	@Resource
	private DevInfoService devInfoService;

	@Resource
	private EvaluateLevelService evaluateLevelService;

	@Resource
	private OrganizationService organizationService;

	@Resource
	private EvaluateService evaluateService;

	@Resource
	private EmployeePhotoService employeePhotoService;

	@Resource
	private EmployeeService employeeService;

	@Resource
	private MediaService mediaService;

	@Resource
	private UpdateFileService updateFileService;

	@RequestMapping({ "/dateUpload.do" })
	@ResponseBody
	public ResponseModel dateUpload(HttpServletRequest request,
			HttpServletResponse response, RequestModel paramModel) {
		ResponseModel rm = null;
		String argsJson = paramModel.getArgs();
		String cmd = paramModel.getCmd();

		this.log.debug("cmd : " + cmd);
		if ("regTerminal".intern().equals(cmd)) {
			String ip = HttpUtil.getClientIp(request);
			rm = regTerminal(ip, argsJson);
		} else if ("getOrglist".intern().equals(cmd)) {
			rm = getOrglist(argsJson);
		} else if ("getServerTime".intern().equals(cmd)) {
			rm = getServerTime(argsJson);
		} else if ("terminalAuth".intern().equals(cmd)) {
			rm = terminalAuth(argsJson);
		} else if ("loginSystem".intern().equals(cmd)) {
			rm = loginSystem(argsJson);
		} else if ("saveEvaluate".intern().equals(cmd)) {
			rm = saveEvaluate(argsJson);
		} else if ("getMedialist".intern().equals(cmd)) {
			rm = getMedialist(argsJson);
		} else if ("getUpdatefiles".intern().equals(cmd)) {
			rm = getUpdatefiles(argsJson);
		} else if ("getUserImage".intern().equals(cmd)) {
			rm = getUserImage(argsJson);
		} else {
			rm = new ResponseModel();
			rm.setReturncode("1001".intern());
			rm.setReturnmsg("The cmd " + cmd + " is invalid!");
		}
		this.log.debug("ResponseModel:" + rm);
		try {
			response.setHeader("Content-Length".intern(), rm.toString()
					.getBytes("UTF-8".intern()).length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rm;
	}

	private ResponseModel getUserImage(String argsJson) {
		ResponseModel rm = new ResponseModel();
		if (StringUtils.isNotEmpty(argsJson)) {
			Gson gson = new Gson();
			ArgsModel args = (ArgsModel) gson.fromJson(argsJson,
					new TypeToken() {
					}.getType());
			this.log.debug(args);
			if (args != null) {
				initResponseModel(rm, args);
				EmployeePhotoModel employeePhotoModelArgs = (EmployeePhotoModel) args
						.getParam();
				List eps = this.employeePhotoService
						.getEmployee(employeePhotoModelArgs.getUsercode());
				if ((eps != null) && (eps.size() > 0)) {
					List data = new ArrayList(eps.size());
					for (int i = 0; i < eps.size(); i++) {
						EmployeePhoto ep = (EmployeePhoto) eps.get(i);
						EmployeePhotoModel employeePhotoModel = new EmployeePhotoModel();
						employeePhotoModel.setUsercode(ep.getEmployeeNumber());
						employeePhotoModel.setImage1(ep.getHpImgUrl());
						employeePhotoModel.setImage2(ep.getCertificateUrl());
						employeePhotoModel.setImg1md5(ep.getHpImgMd5());
						employeePhotoModel.setImg2md5(ep.getCertificateMd5());
						data.add(employeePhotoModel);
					}
					rm.setData(data);
				}
			}
			rm.setReturncode("0".intern());
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private ResponseModel saveEvaluate(String argsJson) {
		ResponseModel rm = new ResponseModel();
		if (StringUtils.isNotEmpty(argsJson)) {
			Gson gson = new Gson();
			ArgsModel args = (ArgsModel) gson.fromJson(argsJson,
					new TypeToken() {
					}.getType());
			this.log.debug(args);
			if (args != null) {
				initResponseModel(rm, args);
				Evaluate evaluate = (Evaluate) args.getParam();
				evaluate.setDevCode(args.getTerminalid());
				evaluate.setUpdateTime(DateUtil.getDateTime());
				evaluate.setReqId(args.getReqid());
				String point = evaluate.getEvaluatePoint();
				EvaluateLevel el = this.evaluateLevelService.findById(Integer
						.valueOf(point).intValue());
				if (el != null) {
					evaluate.setEvaluateName(el.getName());
					evaluate.setEvaluateValue(el.getValue());
				}
				int rs = -1;
				try {
					rs = this.evaluateService.create(evaluate);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					if ("DuplicateKeyException".intern().equals(e.getMessage())) {
						rm.setReturncode("0".intern());
					} else {
						rm.setReturncode("9000".intern());
						rm.setReturnmsg(e.getMessage());
					}
				}
				if (rs > 0)
					rm.setReturncode("0".intern());
			} else {
				rm.setReturncode("1001".intern());
			}
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private ResponseModel<DevInfoClient> terminalAuth(String argsJson) {
		ResponseModel rm = new ResponseModel();
		List listClient = new ArrayList();
		DevInfoClient devInfoClient = null;
		if (StringUtils.isNotEmpty(argsJson)) {
			Gson gson = new Gson();

			ArgsModel args = (ArgsModel) gson.fromJson(argsJson,
					new TypeToken() {
					}.getType());
			this.log.debug(args);
			DevAuthModel dam = (DevAuthModel) args.getParam();
			if ((args != null) && (StringUtils.isNotEmpty(dam.getAuthtype()))
					&& (StringUtils.isNotEmpty(dam.getAuthdata()))) {
				DevInfo devInfo = new DevInfo();
				if ("1".intern().equals(dam.getAuthtype()))
					devInfo.setSerialNo(dam.getAuthdata());
				else if ("2".intern().equals(dam.getAuthtype()))
					devInfo.setMac(dam.getAuthdata());
				else if ("3".intern().equals(dam.getAuthtype()))
					devInfo.setIp(dam.getAuthdata());
				else if ("4".intern().equals(dam.getAuthtype())) {
					devInfo.setCode(dam.getAuthdata());
				}
				List<DevInfo> list = this.devInfoService.queryByClient(devInfo);
				for (DevInfo info : list) {
					devInfoClient = new DevInfoClient();
					devInfoClient.setTerminalno(info.getCode());
					devInfoClient.setDevip(info.getIp());
					devInfoClient.setDevmac(info.getMac());
					devInfoClient.setOrgcode(info.getOrgCode());
					devInfoClient.setOrgname(info.getOrgName());
					devInfoClient.setWindowname(info.getWindowName());
					devInfoClient.setDevstatus(info.getAuditStatus());
					listClient.add(devInfoClient);
				}
				if ((listClient != null) && (listClient.size() > 0)) {
					rm.setReturncode("0".intern());
					rm.setData(listClient);
				} else {
					rm.setReturnmsg("查无此序列号的终端!");
				}
			} else {
				rm.setReturncode("1001".intern());
			}
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private ResponseModel<ServerTimeModel> getServerTime(String argsJson) {
		ResponseModel rm = new ResponseModel();
		if (StringUtils.isNotEmpty(argsJson)) {
			ArgsModel args = (ArgsModel) JsonSerializable.parseObject(
					argsJson.getBytes(), ArgsModel.class);
			this.log.debug(args);
			if (args != null) {
				initResponseModel(rm, args);
				ServerTimeModel stm = new ServerTimeModel(
						DateUtil.getDateTime());
				List list = new ArrayList(1);
				list.add(stm);
				rm.setReturncode("0".intern());
				rm.setData(list);
			}
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private ResponseModel<Organization> getOrglist(String argsJson) {
		this.log.debug("req=" + argsJson);
		ResponseModel rm = new ResponseModel();
		this.log.debug("req=" + StringUtils.isNotEmpty(argsJson));
		if (StringUtils.isNotEmpty(argsJson)) {
			ArgsModel args = (ArgsModel) JsonSerializable.parseObject(
					argsJson.getBytes(), ArgsModel.class);
			this.log.debug(args);
			if (args != null) {
				initResponseModel(rm, args);
				List list = this.organizationService.queryByClient();

				if ((list != null) && (list.size() > 0)) {
					rm.setReturncode("0".intern());
					rm.setData(list);
				} else {
					rm.setReturnmsg("暂无组织机构信息!");
				}
			} else {
				rm.setReturncode("1001".intern());
			}
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private ResponseModel<DevInfo> regTerminal(String ip, String argsJson) {
		ResponseModel rm = new ResponseModel();
		if (StringUtils.isNotEmpty(argsJson)) {
			Gson gson = new Gson();
			ArgsModel args = (ArgsModel) gson.fromJson(argsJson,
					new TypeToken() {
					}.getType());
			this.log.debug(args);
			if (args != null) {
				initResponseModel(rm, args);
				DevInfo devInfo = (DevInfo) args.getParam();
				devInfo.setCode(args.getTerminalid());
				devInfo.setIp(ip);

				devInfo.setCreateTime(DateUtil.getDateTime());
				int rs = -1;
				try {
					devInfo.setAuditStatus(1);
					this.log.debug("divCode------> " + devInfo.getCode());
					int rcode = this.devInfoService.queryDevCode(devInfo
							.getCode());
					this.log.debug("rcode : " + rcode);
					if (rcode > 0) {
						this.log.debug("终端已注册");
						rm.setReturnmsg("终端已注册，终端号为: " + devInfo.getCode());
					} else {
						rs = this.devInfoService.create(devInfo);
						this.log.debug("终端成功！");
					}

				} catch (Exception e) {
					rm.setReturnmsg(e.getMessage());
				}
				if (rs > 0)
					rm.setReturncode("0".intern());
			} else {
				rm.setReturncode("1001".intern());
			}
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private ResponseModel loginSystem(String argsJson) {
		ResponseModel rm = new ResponseModel();
		int rs = 0;
		Employee emp = new Employee();

		System.out.println("argsJson: " + argsJson);
		if (StringUtils.isNotEmpty(argsJson)) {
			Gson gson = new Gson();
			ArgsModel argsEmp = (ArgsModel) gson.fromJson(argsJson,
					new TypeToken() {
					}.getType());
			System.out.println("argsEmp : " + argsEmp);
			this.log.debug(argsEmp);
			if (argsEmp != null) {
				initResponseModel(rm, argsEmp);
				EmployeeModel empModel = (EmployeeModel) argsEmp.getParam();
				emp.setCode(empModel.getUsercode());
				emp.setName(empModel.getUsername());
				emp.setOrgCode(empModel.getOrgcode());
				List<Employee> empList = this.employeeService
						.chackEmployee(emp);
				rs = empList.size();
				if (rs == 1) {
					for (Employee e : empList) {
						updateEmployee(argsEmp, e.getId());
					}
					rm.setReturncode("0".intern());
				} else {
					createEmployee(argsEmp);
					rm.setReturncode("0".intern());
				}
			}

		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private int createEmployee(ArgsModel<EmployeeModel> argsEmp) {
		this.log.debug("argsEmp: " + argsEmp.toString());
		EmployeeModel empModel = argsEmp.getParam();
		Employee emp = new Employee();
		emp.setCode(empModel.getUsercode());
		emp.setName(empModel.getUsername());
		emp.setOrgCode(empModel.getOrgcode());
		emp.setLastTime(DateUtil.getDateTime());
		return this.employeeService.create(emp);
	}

	private int updateEmployee(ArgsModel<EmployeeModel> argsEmp, int id) {
		this.log.debug("argsEmp: " + argsEmp.toString());
		EmployeeModel empModel = argsEmp.getParam();
		Employee emp = new Employee();
		emp.setId(id);
		emp.setCode(empModel.getUsercode());
		emp.setName(empModel.getUsername());
		emp.setOrgCode(empModel.getOrgcode());
		emp.setLastTime(DateUtil.getDateTime());
		return this.employeeService.update(emp);
	}

	private ResponseModel getMedialist(String argsJson) {
		ResponseModel rm = new ResponseModel();
		List listClient = new ArrayList();
		MediaClient mediaClient = null;
		if (StringUtils.isNotEmpty(argsJson)) {
			ArgsModel args = (ArgsModel) JsonSerializable.parseObject(
					argsJson.getBytes(), ArgsModel.class);
			this.log.debug(args);
			if (args != null) {
				initResponseModel(rm, args);
				List<Media> list = this.mediaService.queryByClient();
				for (Media media : list) {
					mediaClient = new MediaClient();
					mediaClient.setFilename(media.getName());
					mediaClient.setFiletype(media.getType());
					mediaClient.setFilemd5(media.getMd5Code());
					mediaClient.setFilepath(media.getDownloadPath());
					listClient.add(mediaClient);
				}
				if ((listClient != null) && (listClient.size() > 0)) {
					rm.setReturncode("0".intern());
					rm.setData(listClient);
				} else {
					rm.setReturnmsg("暂无媒体信息！");
				}
			} else {
				rm.setReturncode("1001".intern());
			}
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private ResponseModel getUpdatefiles(String argsJson) {
		ResponseModel rm = new ResponseModel();
		if (StringUtils.isNotEmpty(argsJson)) {
			ArgsModel args = (ArgsModel) JsonSerializable.parseObject(
					argsJson.getBytes(), ArgsModel.class);
			this.log.debug(args);
			if (args != null) {
				List list = this.updateFileService.queryByClient();
				if ((list != null) && (list.size() > 0)) {
					for (int i = 0; i < list.size(); i++) {
						UpdateFile uf = (UpdateFile) list.get(i);
						uf.setSavePath(uf.getDownloadPath());
					}
					rm.setReturncode("0".intern());
					rm.setData(list);
				} else {
					rm.setReturnmsg("暂无升级文件信息！");
				}
			} else {
				rm.setReturncode("1001".intern());
			}
		} else {
			rm.setReturncode("1001".intern());
		}
		return rm;
	}

	private void initResponseModel(ResponseModel rm, ArgsModel args) {
		rm.setReqid(args.getReqid());
		rm.setTerminalno(args.getTerminalid());
	}

	public DevInfoService getDevInfoService() {
		return this.devInfoService;
	}

	public void setDevInfoService(DevInfoService devInfoService) {
		this.devInfoService = devInfoService;
	}

	public OrganizationService getOrganizationService() {
		return this.organizationService;
	}

	public EvaluateService getEvaluateService() {
		return this.evaluateService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public void setEvaluateService(EvaluateService evaluateService) {
		this.evaluateService = evaluateService;
	}
}
