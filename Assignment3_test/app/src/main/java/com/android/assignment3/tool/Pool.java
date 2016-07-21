package com.android.assignment3.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory contain a certain of specific class instances,using to reduce consumption of system memory
 *
 * Created by Administrator on 2016/4/20.
 */
public class Pool<T> {
    /**
     * This interface will be implemented with specific object type T
     *
     * @param <T> specific object type
     */
    public interface PoolObject<T>{
        public T createObjectInstance();
    }

    private final int maxSize;
    public final List<T> instanceList;
    private final PoolObject<T> poolObject;

    public Pool(PoolObject poolObject,int maxSize){
        this.maxSize = maxSize;
        this.poolObject = poolObject;
        instanceList = new ArrayList<T>(maxSize);
    }

    /**
     * When instance list is empty, it create an instance and return
     * When instance list is not empty, it takes the last instance from the list and return
     *
     * @return : Instance of specific object
     */
    public T getInstance(){
        if (instanceList.size() == 0){
            return poolObject.createObjectInstance();
        }else if (instanceList.size() > 0)
            return instanceList.remove(instanceList.size()-1);
        else
            return null; // Error
    }

    /**
     * When instance list's length is not maximal, adding the instance to the list for future usage
     *
     * @param instance : The instance which is no longer use
     */
    public void freeInstance(T instance){
        if (instanceList.size() < maxSize)
            instanceList.add(instance);
    }
}
