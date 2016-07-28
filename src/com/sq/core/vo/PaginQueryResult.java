 package com.sq.core.vo;
 
 import java.util.List;
 
 public class PaginQueryResult<T>
 {
   private int records;
   private int total;
   private int page;
   private List<T> rows;
 
   public int getRecords()
   {
     return this.records;
   }
 
   public void setRecords(int records) {
     this.records = records;
   }
 
   public int getPage() {
     return this.page;
   }
 
   public void setPage(int page) {
     this.page = page;
   }
 
   public int getTotal() {
     return this.total;
   }
 
   public void setTotal(int total) {
     this.total = total;
   }
 
   public List<T> getRows() {
     return this.rows;
   }
 
   public void setRows(List<T> rows) {
     this.rows = rows;
   }
 }

