package com.sq.core.service;

import com.sq.core.vo.PaginQueryResult;

public abstract interface Service<T>
{
  public abstract int delete(String paramString);

  public abstract int create(T paramT);

  public abstract int update(T paramT);

  public abstract int delete(int paramInt);

  public abstract T findById(int paramInt);

  public abstract PaginQueryResult<T> paginQuery(T paramT, int paramInt1, int paramInt2);
}

