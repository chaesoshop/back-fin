package com.carrot.backend.notification.controller;

import com.carrot.backend.notification.NotificationDto.NotificationDto;
import com.carrot.backend.notification.service.NotificationService;
import com.carrot.backend.util.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(
//            @RequestParam("userid") String userid,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
//        System.out.println("userDetail : " + userid);

//        return notificationService.subscribe(userid, lastEventId);
        return null;
    }

    @GetMapping(value = "/notifications")
    public List<NotificationDto> findAllNotifications(@RequestParam("userid") String userid) {
        List<NotificationDto> notifications = notificationService.findAllNotifications(userid);
        return notifications;
    }



    //전체목록 알림 조회에서 해당 목록 클릭 시 읽음처리 ,
    @GetMapping("/notification/read/{notificationId}")
    public void readNotification(@PathVariable Long notificationId){
        notificationService.readNotification(notificationId);

    }
    //알림 조회 - 구독자가 현재 읽지않은 알림 갯수
    @GetMapping("/notifications/count")
    public long countUnReadNotifications(@RequestParam("userid") String userid) {

        return notificationService.countUnReadNotifications(userid);
    }

    //알림 전체 삭제
    @DeleteMapping("/notification/delete")
    public ResponseEntity<Object> deleteNotifications(@RequestParam("userid") String userid){

        notificationService.deleteAllByNotifications(userid);
        return new ResponseEntity<>(new StatusResponseDto("알림 목록 전체삭제 성공",""), HttpStatus.OK);
    }
    //단일 알림 삭제
    @DeleteMapping("/notifications/delete/{notificationId}")
    public List<NotificationDto> deleteNotification(@PathVariable Long notificationId, @RequestBody String userid){

        notificationService.deleteByNotifications(notificationId);
        List<NotificationDto> notifications = notificationService.findAllNotifications(userid);
        return notifications;
    }
}
