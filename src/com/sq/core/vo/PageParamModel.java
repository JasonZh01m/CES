 package com.sq.core.vo;
 
 public class PageParamModel<T>
 {
   private int page = 1;
 
   private int rows = 10;
   private String sort;
 
   public int getPage()
   {
     return this.page;
   }
 
   public void setPage(int page) {
     this.page = page;
   }
 
   public int getRows() {
     return this.rows;
   }
 
   public void setRows(int rows) {
     this.rows = rows;
   }
 
   public String getSort() {
     return this.sort;
   }
 
   public void setSort(String sort) {
     this.sort = sort;
   }
 
   public String toString()
   {
     return String.format("[ page:%s, rows:%d, sort:%s ]", new Object[] { Integer.valueOf(this.page), Integer.valueOf(this.rows), this.sort });
   }
 }

