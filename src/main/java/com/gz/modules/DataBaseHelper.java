package com.gz.modules;

import com.gz.spring.ServiceLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Filename:    DataBaseHelper  //文件名
 * Description:   数据处理类//文件描述
 * Copyright:   //版权说明
 * Company:     //公司
 *
 * @author: gongyy//作者
 * @version: v1.0//版本
 * Create at:   2020/12/19 11:05//创建日期
 * <p>
 * Modification History:  //修改记录
 * Date //修改时间 Author//修改人  Version//版本   Description//修改内容描述
 * ------------------------------------------------------------------
 */
public class DataBaseHelper {
    private static Logger logger =  LogManager.getLogger(DataBaseHelper.class);

    private static JdbcTemplate jdbcTemplate = null;

    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;

    public static JdbcTemplate getJdbcTemplate() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            if (jdbcTemplate == null) {
                    jdbcTemplate = ServiceLocator.getInstance().getBean(JdbcTemplate.class);
            }
        } finally {
            lock.unlock();
        }
        return jdbcTemplate;
    }
}
