package com.sq.core.dao;

import java.util.List;

public abstract interface Dao<T>
{
  public abstract int count(String paramString, T paramT);

  public abstract List<T> queryPageList(String paramString, T paramT, int paramInt1, int paramInt2);

  public abstract T queryById(String paramString, int paramInt);

  public abstract T queryByCode(String paramString1, String paramString2);

  public abstract int insert(String paramString, T paramT);

  public abstract int delete(String paramString, T paramT);

  public abstract void batchInsert(String paramString, List<T> paramList);

  public abstract int deleteById(String paramString, int paramInt);

  public abstract int update(String paramString, T paramT);

  public abstract int batchUpdate(String paramString, List<T> paramList);

  public abstract List<T> queryList(String paramString);

  public abstract List<T> query(String paramString, T paramT);

  public abstract List<T> queryList(String paramString, T paramT);

  public abstract List<T> iterator(String paramString, T paramT);
}

