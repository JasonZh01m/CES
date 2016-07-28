 package com.sq.core.cache.sql;
 
 import java.util.Map;
 import java.util.concurrent.locks.Lock;
 import java.util.concurrent.locks.ReentrantLock;
 import java.util.concurrent.locks.ReentrantReadWriteLock;
 import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
 import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
 import org.apache.commons.collections.map.LRUMap;
 
 public class KeyLock
 {
   private final Map<String, Lock> map;
   private final ReentrantReadWriteLock rwLock;
 
   public KeyLock()
   {
     this.map = new LRUMap(64);
     this.rwLock = new ReentrantReadWriteLock();
   }
 
   public Lock getLock(String key)
   {
     Lock lock = null;
     this.rwLock.readLock().lock();
     try {
       lock = (Lock)this.map.get(key);
       if (lock == null) {
         this.rwLock.readLock().unlock();
         this.rwLock.writeLock().lock();
         try {
           lock = (Lock)this.map.get(key);
           if (lock == null) {
             lock = new ReentrantLock();
             this.map.put(key, lock);
           }
         } finally {
           this.rwLock.writeLock().unlock();
         }
         this.rwLock.readLock().lock();
       }
     } finally {
       this.rwLock.readLock().unlock();
     }
     return lock;
   }
 }

