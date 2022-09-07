package com.demo.pochi.context;

import com.demo.pochi.pojo.SysLog;
import lombok.Data;

/**
 *本地线程上下文
 * 用来存储在同一个线程中可能会用到的全局变量
 */
@Data
public class SystemContext {

    /**
     * 日志实体
     */
    private SysLog sysLog;

    /**
     * 是否记录日志
     */
//    private boolean isLog=false;

    /**
     * 饿汉式
     *本地线程上下文，存储线程工作内存中的变量
     */
    private static ThreadLocal<SystemContext> threadLocal=new ThreadLocal<>();

    /**
     * 获取当前线程上下文
     * @return
     */
    public static SystemContext get(){
        if (threadLocal.get() == null){
            SystemContext threadLocalContext = new SystemContext();
            threadLocalContext.setSysLog(new SysLog());
            threadLocal.set(threadLocalContext);

        }
        return threadLocal.get();
    }

    public void remove(){
        threadLocal.remove();
    }
}
