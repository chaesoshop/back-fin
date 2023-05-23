package com.carrot.backend.notification.controller;

import com.carrot.backend.notification.NotificationDto.NotificationDto;
import com.carrot.backend.notification.NotificationDto.NotificationRequestDto;
import com.carrot.backend.notification.domain.Notification;
import com.carrot.backend.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequiredArgsConstructor
public class SseController {


    private final NotificationService notificationService;

    ExecutorService taskExecutor = Executors.newSingleThreadExecutor();

    @GetMapping(value = "/sse/{userid}")
    public ResponseEntity<SseEmitter> publish(@PathVariable String userid,
                                              @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws Exception {
        SseEmitter emitter = new SseEmitter();

        notificationService.subscribe(userid, lastEventId);

        List<NotificationDto> dto = notificationService.findAllNotifications(userid);
        emitter.send(SseEmitter.event().name("get").data(dto)
//                .reconnectTime(0)
        );

        emitter.complete();


        return ResponseEntity.ok(emitter);


    }

    @PostMapping("/addChatNotification")
    public ResponseEntity<SseEmitter> addChat(@RequestBody NotificationRequestDto notificationRequestDto, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws Exception {
        SseEmitter emitter = new SseEmitter();
        notificationService.subscribe(notificationRequestDto.getUserid(), lastEventId);

        notificationService._addChat(notificationRequestDto);
        Notification notification = notificationService.getNewOne(notificationRequestDto.getUserid(), notificationRequestDto.getSender());

//        emitter.send(SseEmitter.event().name("new").data(notification).reconnectTime(0));
//        emitter.complete();


        return ResponseEntity.ok(emitter);
    }

    @PostMapping("/addApplyNotification")
    public ResponseEntity<SseEmitter> addApply(@RequestBody NotificationRequestDto notificationRequestDto, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws Exception {
        SseEmitter emitter = new SseEmitter();
        notificationService.subscribe(notificationRequestDto.getUserid(), lastEventId);


        notificationService._addApply(notificationRequestDto);
        Notification notification = notificationService.getNewOne(notificationRequestDto.getUserid(), notificationRequestDto.getSender());

//        emitter.send(SseEmitter.event().name("new").data(notification).reconnectTime(0));
//        emitter.complete();

        return ResponseEntity.ok(emitter);
    }

   @PostMapping("/addReviewNotification")
    public ResponseEntity<SseEmitter> addReview(@RequestBody NotificationRequestDto notificationRequestDto, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws Exception {
        SseEmitter emitter = new SseEmitter();
        notificationService.subscribe(notificationRequestDto.getUserid(), lastEventId);

        notificationService._addReview(notificationRequestDto);
        return ResponseEntity.ok(emitter);
    }

    @PostMapping("/addReplyNotification")
    public ResponseEntity<SseEmitter> addReply(@RequestBody NotificationRequestDto notificationRequestDto, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws Exception {
        SseEmitter emitter = new SseEmitter();
        notificationService.subscribe(notificationRequestDto.getUserid(), lastEventId);
        notificationService._addReply(notificationRequestDto);

        return ResponseEntity.ok(emitter);
    }


}

