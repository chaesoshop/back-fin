package com.carrot.backend.notification.service;

import com.carrot.backend.chatting.dao.ChattingRoomRepository;
import com.carrot.backend.notification.NotificationDto.NotificationDto;
import com.carrot.backend.notification.NotificationDto.NotificationRequestDto;
import com.carrot.backend.notification.dao.EmitterRepository;
import com.carrot.backend.notification.dao.NotificationRepository;
import com.carrot.backend.notification.domain.Notification;
import com.carrot.backend.notification.domain.NotificationType;
import com.carrot.backend.notification.domain.RelatedURL;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterRepository emitterRepository;

    private final ChattingRoomRepository chattingRoomRepository;

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(String userId
            , String lastEventId
    ) {
        //emitter 하나하나 에 고유의 값을 주기 위해
        String emitterId = makeTimeIncludeId(userId);
        Long timeout = 60L * 1000L * 60L; // 1시간
        // 생성된 emitterId를 기반으로 emitter를 저장
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));
        //emitter의 시간이 만료된 후 레포에서 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(userId);


        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + userId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }

        return emitter;
    }

    // SseEmitter를 구분 -> 구분자로 시간을 사용함 ,
    // 시간을 붙혀주는 이유 -> 브라우저에서 여러개의 구독을 진행 시
    //탭 마다 SssEmitter 구분을 위해 시간을 붙여 구분하기 위해 아래와 같이 진행
    private String makeTimeIncludeId(String userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    // 유효시간이 다 지난다면 503 에러가 발생하기 때문에 더미데이터를 발행
    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
//        emitter.send(SseEmitter.event().name("get").data(dto).reconnectTime(0));
        try {
            emitter.send(SseEmitter.event()
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    // Last - event - id 가 존재한다는 것은 받지 못한 데이터가 있다는 것이다.
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    // 받지못한 데이터가 있다면 last - event - id를 기준으로 그 뒤의 데이터를 추출해 알림을 보내주면 된다.
    private void sendLostData(String lastEventId, String userId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    @Async
    public void send(User user, NotificationType notificationType, String content, String url) {
        Notification notification = notificationRepository.save(createNotification(user, notificationType, content, url));

        String receiverId = String.valueOf(user.getUserid());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationDto.create(notification));
                }
        );
    }

    private Notification createNotification(User user, NotificationType notificationType, String content, String url) {
        return Notification.builder()
                .user(user)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }

    @Transactional
    public List<NotificationDto> findAllNotifications(String userId) {

        List<Notification> notifications = notificationRepository.findAllByUser(userId);


        return notifications.stream()
                .map(NotificationDto::create)
                .collect(Collectors.toList());
    }
    public long countUnReadNotifications(String userId) {
        //유저의 알람리스트에서 ->isRead(false)인 갯수를 측정 ,
        Long count = notificationRepository.countUnReadNotifications(userId);


        return count;
    }

    public Notification getNewOne(String userid,String sender){
        User user = userRepository.findByUserid(userid).get();
        User sendUser = userRepository.findByUserid(sender).get();
        List<Notification> notifications = notificationRepository.findByUserAndSender(user,sendUser);
        int size = notifications.size();
        return notifications.get(size-1);
    }


    public void readNotification(Long notificationId) {
        //알림을 받은 사람의 id 와 알림의 id 를 받아와서 해당 알림을 찾는다.
        Notification notification = notificationRepository.findById(notificationId).get();
        notification.read(); // 읽음처리
        notificationRepository.save(notification);

    }

    @Transactional
    public void deleteAllByNotifications(String userid) {
        User user = userRepository.findByUserid(userid).get();

        notificationRepository.deleteAllByUser(user);

    }
    @Transactional
    public void deleteByNotifications(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Transactional
    public void _addChat(NotificationRequestDto notificationRequestDto) throws Exception {
        String url = notificationRequestDto.getUrl();
        User user = userRepository.findByUserid(notificationRequestDto.getUserid()).get();
        User sender = userRepository.findByUserid(notificationRequestDto.getSender()).get();
        RelatedURL relatedURL1 = new RelatedURL(url);

        Notification isExistNotification = notificationRepository.findByUrl(relatedURL1).orElse(new Notification());

        Notification notification;

        if(isExistNotification.getUser() == null){
             notification = Notification.builder()
                    .user(user)
                    .sender(sender)
                    .notificationType(notificationRequestDto.getNotificationType())
                    .content(notificationRequestDto.getContent())
                    .url(notificationRequestDto.getUrl())
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);
        }else{
        notificationRepository.delete(isExistNotification);
        notification = Notification.builder()
                .user(user)
                .sender(sender)
                .notificationType(notificationRequestDto.getNotificationType())
                .content(notificationRequestDto.getContent())
                .url(notificationRequestDto.getUrl())
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

}

    public void _addApply (NotificationRequestDto notificationRequestDto) throws Exception {
        User user = userRepository.findByUserid(notificationRequestDto.getUserid()).get();
        User sender = userRepository.findByUserid(notificationRequestDto.getSender()).get();

        Notification notification = Notification.builder()
                .user(user)
                .sender(sender)
                .notificationType(notificationRequestDto.getNotificationType())
                .content(notificationRequestDto.getContent())
                .url(notificationRequestDto.getUrl())
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    public void _addReview(NotificationRequestDto notificationRequestDto)  throws Exception{
        User user = userRepository.findByUserid(notificationRequestDto.getUserid()).get();
        User sender = userRepository.findByUserid(notificationRequestDto.getSender()).get();


        Notification notification = Notification.builder()
                .user(user)
                .sender(sender)
                .notificationType(notificationRequestDto.getNotificationType())
                .content(notificationRequestDto.getContent())
                .url(notificationRequestDto.getUrl())
                .isRead(false)
                .build();
        notificationRepository.save(notification);

    }

    public void _addReply(NotificationRequestDto notificationRequestDto) {
        User user = userRepository.findByUserid(notificationRequestDto.getUserid()).get();
        User sender = userRepository.findByUserid(notificationRequestDto.getSender()).get();


        Notification notification = Notification.builder()
                .user(user)
                .sender(sender)
                .notificationType(notificationRequestDto.getNotificationType())
                .content(notificationRequestDto.getContent())
                .url(notificationRequestDto.getUrl())
                .isRead(false)
                .build();
        notificationRepository.save(notification);

    }
}
