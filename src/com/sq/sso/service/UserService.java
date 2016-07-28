package com.sq.sso.service;

import com.sq.core.service.Service;
import com.sq.sso.model.User;
import com.sq.sso.vo.SSOSession;
import java.util.List;

public abstract interface UserService extends Service<User>
{
  public abstract List<User> queryUser(User paramUser);

  public abstract boolean queryUserByLoginName(String paramString);

  public abstract int registerUser(User paramUser);

  public abstract int deleUserById(User paramUser);

  public abstract int modifyUserInfo(User paramUser);

  public abstract void destroyOnlineUser(SSOSession paramSSOSession);

  public abstract void updateErrorNumAndCurrentDate(User paramUser);

  public abstract User queryById(int paramInt);
}

