package com.carrot.backend.notification.domain;

import com.carrot.backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Embedded
    private NotificationContent content;
    //알림 내용 - 50자 이내

    @Embedded
    private RelatedURL url;
    //관련 링크 - 클릭 시 이동해야할 링크

    @Column(nullable = false)
    private Boolean isRead;
    //읽었는지에 대한 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;
    // 알림 종류 [신청 / 수락 / 거절 등등 ]

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;
    //회원정보

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "sender_id")
    private User sender;
    //알림 신청자

    @Builder
    public Notification(User user, User sender, NotificationType notificationType, String content, String url, Boolean isRead) {
        this.sender=sender;
        this.user = user;
        this.notificationType = notificationType;
        this.content = new NotificationContent(content);
        this.url = new RelatedURL(url);
        this.isRead = isRead;
    }

    public void read() {
        isRead = true;
    }

    public String getContent() {
        return content.getContent();
    }

    public String getUrl() {
        return url.getUrl();
    }

}
