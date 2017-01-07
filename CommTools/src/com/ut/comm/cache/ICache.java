package com.ut.comm.cache;

public interface ICache {
    public Object get(Object objKey);

    public void put(Object objKey, Object objValue);

    public boolean remove(Object objKey);

    public void removeAll();
    
    public String toString();
    
    public boolean isEnabled();
}
