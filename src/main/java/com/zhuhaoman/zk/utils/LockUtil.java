package com.zhuhaoman.zk.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LockUtil {
    @Autowired
    CuratorFramework curatorFramework;

    public static final String NODE_PATH = "/lock-space/%s";

    public InterProcessMutex tryLock(String key, int expireTime, TimeUnit timeUnit) {
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, String.format(NODE_PATH, key));
        try {
            boolean locked = mutex.acquire(expireTime, timeUnit);
            if (locked) {
                log.info("申请锁（" + key + " )成功");
                return mutex;
            }
        } catch (Exception e) {
            log.error("申请锁（"+ key +"）失败，错误：{}",e);
        }
        log.warn("申请锁（"+key+"）失败");
        return null;
    }

    /**
     * 释放锁
     *
     * @param key          分布式锁 key
     * @param lockInstance InterProcessMutex 实例
     */
    public void unLock(String key, InterProcessMutex lockInstance) {
        try {
            lockInstance.release();
            log.info("解锁(" + key + ")成功");
        } catch (Exception e) {
            log.error("解锁(" + key + ")失败！");
        }
    }
}
