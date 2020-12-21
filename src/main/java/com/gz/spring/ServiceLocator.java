package com.gz.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Filename:    ServiceLocator  //文件名
 * Description:   //文件描述
 * Copyright:   //版权说明
 * Company:     zebone//公司
 *
 * @author: gongyy//作者
 * @version: v1.0//版本
 * Create at:   2020/12/21 9:15//创建日期
 * <p>
 * Modification History:  //修改记录
 * Date //修改时间 Author//修改人  Version//版本   Description//修改内容描述
 * ------------------------------------------------------------------
 */
public class ServiceLocator implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private static ServiceLocator servlocator = null;

    public static ServiceLocator getInstance() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            if (servlocator == null) {
                servlocator = (ServiceLocator) applicationContext.getBean(ServiceLocator.class);
            }
        } finally {
            lock.unlock();
        }
        return servlocator;
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> type) {
        return applicationContext.getBean(beanName, type);
    }

    public <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServiceLocator.applicationContext = applicationContext;
    }

    public static void setServiceLocator(ServiceLocator s){
        servlocator = s;
    }
}
