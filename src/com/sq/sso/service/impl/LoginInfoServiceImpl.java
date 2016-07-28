package com.sq.sso.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sq.core.cache.GeneralCache;
import com.sq.core.utils.PasswordUtils;
import com.sq.sso.data.dao.UserDao;
import com.sq.sso.model.LoginInfo;
import com.sq.sso.model.LoginResult;
import com.sq.sso.model.Role;
import com.sq.sso.model.User;
import com.sq.sso.service.LoginInfoService;
import com.sq.sso.service.PermissionService;
import com.sq.sso.service.RoleService;
import com.sq.sso.service.UserService;
import com.sq.sso.vo.SSOSession;

public class LoginInfoServiceImpl implements LoginInfoService {

	@Resource
	private UserDao userDao;
	private int sesionTimeout;
	private GeneralCache gc;
	private int errorNum;
	private final Log log = LogFactory.getLog(getClass());

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService;

	@Resource
	private PermissionService permissionServiceImpl;
	private final String dot_str = ",";

	private final String VALID_STATUS = "1";
	private final String adminStr = "admin";
	private final String adminRole = "super_administrators";

	@Override
	public int updateUserPwd(int userId, String oldPwd, String newPwd) {
		int rs = 0;
		User user = this.userDao.queryById(userId);
		if (user != null) {
			String saltCode = user.getSaltCode();
			String securePassword = PasswordUtils.getShaSecurePassword(oldPwd,
					saltCode);

			if (user.getPassWord().equals(securePassword)) {
				securePassword = PasswordUtils.getShaSecurePassword(newPwd,
						saltCode);
				user.setPassWord(securePassword);
				this.userDao.modifyUserInfo(user);
			} else {
				rs = 1;
			}
		}

		return rs;
	}

	@Override
	public void logout(String userId, String tokenKey) {
	}

	@Override
	public LoginResult login(LoginInfo loginInfo, boolean verifyLogin,
			String tokenCode) throws NoSuchAlgorithmException,
			NoSuchProviderException, ParseException {
		LoginResult lr = new LoginResult();
		User user = new User();
		user.setLoginName(loginInfo.getLoginName());

		List users = this.userService.queryUser(user);
		if ((users != null) && (users.size() > 0)) {
			user = (User) users.get(0);

			String saltCode = user.getSaltCode();
			String securePassword = PasswordUtils.getShaSecurePassword(
					loginInfo.getPassWord(), saltCode);

			if (user.getPassWord().equals(securePassword)) {
				boolean isBol = false;

				if (!this.adminStr.equals(user.getLoginName())) {
					List roleList = this.roleService.queryAllRoleByUser(user
							.getId());
					for (int i = 0; i < roleList.size(); i++) {
						if (this.adminRole.equals(((Role) roleList.get(i))
								.getCode())) {
							isBol = true;
						}
					}

					if (!isBol) {
						SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
						String cDate = null;
						String nowDate = s.format(new Date());
						if (StringUtils.isNotEmpty(user.getCurrentDate())) {
							cDate = s.format(s.parse(user.getCurrentDate()));
						}
						if ((cDate != null) && (nowDate.equals(cDate))
								&& (user.getErrorNum() >= this.errorNum)) {
							lr.setResult(false);
							lr.setMessage("该用户已锁,今日内无法登录,请联系系统管理员!");
							this.log.info("该用户已锁,今日内无法登录,请联系系统管理员!");
							return lr;
						}

						if ((StringUtils.isNotEmpty(user.getValidStatus()))
								&& (this.VALID_STATUS.equals(user
										.getValidStatus()))) {
							lr.setResult(false);
							lr.setMessage("用户已注销!");
							this.log.info("用户已注销!");
							return lr;
						}

						if (StringUtils.isNotEmpty(user.getExpireDate())) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date expireDate = sdf.parse(user.getExpireDate());
							Date date = new Date();

							if (date.compareTo(expireDate) > 0) {
								lr.setResult(false);
								lr.setMessage("用户已过有效期!");
								this.log.info("用户已过有效期!");
								return lr;
							}
						}
					}
				}

				this.log.info("用户验证成功!");

				lr = saveSessionToCache(user, loginInfo, tokenCode, verifyLogin);
			} else {
				String msg = errorNumberRecording(user);
				lr.setResult(false);
				lr.setMessage(msg);
			}
		} else {
			lr.setResult(false);
			lr.setMessage("不存在该用户!");
		}
		return lr;
	}

	private LoginResult saveSessionToCache(User user, LoginInfo loginInfo,
			String tokenCode, boolean verifyLogin) {
		String tokenKey = "";
		List permission = null;
		LoginResult lr = new LoginResult();
		try {
			List roles = this.roleService.queryAllRoleByUser(user.getId());
			this.log.info("用户角色获取成功!   " + roles.toString());
			StringBuilder rids = new StringBuilder();
			StringBuilder roleName = new StringBuilder();
			if ((roles != null) && (roles.size() > 0)) {
				rids.append(((Role) roles.get(0)).getId());
				roleName.append(((Role) roles.get(0)).getName());

				for (int i = 1; i < roles.size(); i++) {
					rids.append(this.dot_str);
					rids.append(((Role) roles.get(i)).getId());
					roleName.append(this.dot_str);
					roleName.append(((Role) roles.get(i)).getName());
				}
			}
			if (verifyLogin)
				tokenKey = tokenCode;
			else {
				tokenKey = UUID.randomUUID().toString().replace("-", "");
			}

			SSOSession session = new SSOSession();
			long nowTime = System.currentTimeMillis();
			session.setIp(loginInfo.getClientIp());
			session.setLoginTime(nowTime);

			session.setMac(null);
			session.setRids(rids.toString());
			session.setRoleName(roleName.toString());
			session.setLoginName(user.getLoginName());
			session.setUserName(user.getUserName());
			session.setTokenCode(tokenKey);
			session.setUserId(user.getId() + "");
			session.setOrgCode(user.getOrgCode());

			lr.setSession(session);
			for (int i = 0; i < roles.size(); i++) {
				permission = this.permissionServiceImpl
						.queryAllPermissionByRoleId(((Role) roles.get(i))
								.getId());
				this.log.info("根据角色id获取权限成功!  "
						+ ((Role) roles.get(i)).getName() + ": "
						+ permission.toString());
			}

			lr.setResult(true);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return lr;
	}

	private String errorNumberRecording(User user) throws ParseException {
		String msg = "";
		String cDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String nowDate = sdf.format(new Date());
		if (StringUtils.isNotEmpty(user.getCurrentDate())) {
			cDate = sdf.format(sdf.parse(user.getCurrentDate()));
		}

		int id = user.getId();
		int num = user.getErrorNum();
		user = new User();
		user.setId(id);

		if ((cDate != null) && (nowDate.equals(cDate))) {
			user.setErrorNum(num + 1);
			this.userService.updateErrorNumAndCurrentDate(user);
			if (user.getErrorNum() >= this.errorNum) {
				user = new User();
				user.setId(id);
				user.setValidStatus("1");
				this.userService.modifyUserInfo(user);
				msg = "该用户已锁,今日内无法登录,请联系系统管理员。";
			} else {
				msg = "密码错误,今日还有" + (this.errorNum - user.getErrorNum())
						+ "次输入机会!";
			}
		} else {
			user.setErrorNum(1);
			user.setCurrentDate(nowDate);

			this.userService.updateErrorNumAndCurrentDate(user);
			msg = "密码错误,今日还有" + (this.errorNum - user.getErrorNum()) + "次输入机会!";
		}
		return msg;
	}

	public GeneralCache getGc() {
		return this.gc;
	}

	public void setGc(GeneralCache gc) {
		this.gc = gc;
	}

	@Override
	public int getSesionTimeout() {
		return this.sesionTimeout;
	}

	@Override
	public void setSesionTimeout(int sesionTimeout) {
		this.sesionTimeout = sesionTimeout;
	}

	@Override
	public int getErrorNum() {
		return this.errorNum;
	}

	@Override
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
}
