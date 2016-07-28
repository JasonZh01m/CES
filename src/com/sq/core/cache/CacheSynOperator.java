package com.sq.core.cache;

public abstract interface CacheSynOperator
{
  public abstract void publish(String paramString1, String paramString2, SynMesssage paramSynMesssage);

  public abstract void messageOpt(SynMesssage paramSynMesssage, String paramString);

  public abstract void subscribe(String paramString1, String paramString2);

  public abstract void synCache(SynMesssage paramSynMesssage);

  public abstract void getAllMessage(String paramString);
}

