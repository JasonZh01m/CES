 package com.sq.core.service.impl;
 
 import com.sq.core.dao.Dao;
 import com.sq.core.exception.ExtendException;
 import com.sq.core.service.Service;
 import com.sq.core.utils.AppContextUtil;
 import com.sq.core.vo.PaginQueryResult;
 import java.lang.reflect.ParameterizedType;
 import java.lang.reflect.Type;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public abstract class ServiceImpl<T>
   implements Service<T>
 {
   private static final String PREFIX_SAVE = "save";
   private static final String PREFIX_UPDATE = "updateById";
   private static final String PREFIX_DELETE = "deleteById";
   private static final String PREFIX_GET = "queryById";
   private static final String PREFIX_QUERY_PAGE = "queryForPage";
   private static final String PREFIX_QUERY_COUNT = "count";
 
   private String dotStr = ",";
 
   private Log Log = LogFactory.getLog(getClass());
   protected Dao<T> dao;
 
   public ServiceImpl()
   {
     Class c = getClass();
     Type t = c.getGenericSuperclass();
 
     if ((t instanceof ParameterizedType)) {
       Type[] p = ((ParameterizedType)t).getActualTypeArguments();
       Class nowClass = (Class)p[0];
       String className = nowClass.getSimpleName();
       String firstChar = className.charAt(0).toLowerCase();
       String nowChar = className.substring(1, className.length());
       className = firstChar + nowChar;
       this.dao = ((Dao)AppContextUtil.getBean(className + "DaoImpl"));
     } else {
       throw new ExtendException(
         "Extends the core servie class is not valid");
     }
   }
 
   public int delete(String ids)
   {
     int rs = 0;
     String[] arr = ids.split(this.dotStr);
     for (int i = 0; i < arr.length; i++) {
       int id = Integer.parseInt(arr[i]);
       rs += this.dao.deleteById(PREFIX_DELETE, id);
     }
     return rs;
   }
 
   public int create(T t)
   {
     return this.dao.insert(PREFIX_SAVE, t);
   }
 
   public int update(T t)
   {
     return this.dao.update(PREFIX_UPDATE, t);
   }
 
   public int delete(int id)
   {
     return this.dao.deleteById(PREFIX_DELETE, id);
   }
 
   public T findById(int id)
   {
     return this.dao.queryById(PREFIX_GET, id);
   }
 
   public PaginQueryResult<T> paginQuery(T t, int index, int size)
   {
     PaginQueryResult pqr = new PaginQueryResult();
     int count = this.dao.count(PREFIX_QUERY_COUNT, t);
     if ((count > 0) && (index > 0)) {
       int pageCount = (count - 1) / size + 1;
       pqr.setRows(this.dao.queryPageList(PREFIX_QUERY_PAGE, t, index, size));
       pqr.setTotal(pageCount);
       pqr.setPage(index);
       pqr.setRecords(count);
     }
     return pqr;
   }
 
   public Dao<T> getDao() {
     return this.dao;
   }
 
   public void setDao(Dao<T> dao) {
     this.dao = dao;
   }
 }

