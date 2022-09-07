package com.demo.pochi.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 重写存取sessionId的方法
 */
@Component
public class SessionDaoConfig extends EnterpriseCacheSessionDAO {

    @Resource
    private RedisTemplate<Serializable, Session> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        //获取sessionId
        Serializable sessionId = this.generateSessionId(session);
        // session要和sessionId绑定
        SimpleSession simpleSession = (SimpleSession) session;
        simpleSession.setId(sessionId);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 从redis中读取sessionId
        return redisTemplate.boundValueOps(sessionId).get();
    }

    /**
     * 更新sessionId的方法
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {

        if (session instanceof ValidatingSession) {
            ValidatingSession validatingSession = (ValidatingSession) session;
            if (validatingSession.isValid()) {
                redisTemplate.boundValueOps(session.getId()).set(session);
            } else {
                // 校验失败，说明未登录或者登录失效
                redisTemplate.delete(session.getId());
            }
        } else {
            redisTemplate.boundValueOps(session.getId()).set(session);
        }
    }

    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(session.getId());
    }
}
