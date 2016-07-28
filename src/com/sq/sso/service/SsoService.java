package com.sq.sso.service;

import com.sq.sso.model.UriInfo;
import com.sq.sso.vo.SSOSession;
import javax.servlet.http.HttpServletRequest;

public abstract interface SsoService
{
  public abstract String getSsoServer();

  public abstract int getRootPermissionId();

  public abstract void setSsoServer(String paramString);

  public abstract String getPidByUri(String paramString);

  public abstract void setOvertime(int paramInt);

  public abstract int getOvertime();

  public abstract void intiExclude(String paramString);

  public abstract UriInfo getUriInfo(String paramString1, String paramString2);

  public abstract boolean isPermit(String paramString1, String paramString2);

  public abstract boolean isExclude(String paramString);

  public abstract SSOSession getSsoSession(HttpServletRequest paramHttpServletRequest);

  public abstract boolean isEmpty(String paramString);

  public abstract boolean isLogin(SSOSession paramSSOSession, String paramString);

  public abstract void initUriBySysId(String paramString);
}

