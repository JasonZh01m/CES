package com.sq.sso.service;

import com.sq.sso.model.LoginInfo;
import com.sq.sso.model.LoginResult;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;

public abstract interface LoginInfoService
{
  public abstract int updateUserPwd(int paramInt, String paramString1, String paramString2);

  public abstract void logout(String paramString1, String paramString2);

  public abstract LoginResult login(LoginInfo paramLoginInfo, boolean paramBoolean, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, ParseException;

  public abstract int getSesionTimeout();

  public abstract void setSesionTimeout(int paramInt);

  public abstract int getErrorNum();

  public abstract void setErrorNum(int paramInt);
}

