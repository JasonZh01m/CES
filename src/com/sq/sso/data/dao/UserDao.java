package com.sq.sso.data.dao;

import com.sq.core.dao.Dao;
import com.sq.sso.model.User;

public abstract interface UserDao extends Dao<User>
{
  public abstract User queryUserByLoginName(String paramString);

  public abstract int deleUserById(User paramUser);

  public abstract int modifyUserInfo(User paramUser);

  public abstract void destoryUserByUserId(String paramString);

  public abstract void updateErrorNumAndCurrentDate(User paramUser);

  public abstract User queryById(int paramInt);
}

