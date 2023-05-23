package com.carrot.backend.notification.dao;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    //Emitter저장
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    //이벤트 저장
    void saveEventCache(String emitterId, Object event);
    //해당 회원과 관련된 모든 Emitter 조회
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);
    // 해당 회원과 관련된 모든 Event 조회
    Map<String, Object> findAllEventCacheStartWithByUserId(String userId);
    // id로 Emitter 지움
    void deleteById(String id);
    // 해당 회원과 관련된 모든 Emitter 지움
    void deleteAllEmitterStartWithId(String userId);
    // 해당 회원과 관련된 모든 Event 지움
    void deleteAllEventCacheStartWithId(String userId);

}
