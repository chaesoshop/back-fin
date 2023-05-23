package com.carrot.backend.chatting.dao;

import com.carrot.backend.chatting.domain.ChattingRoom;
import com.carrot.backend.chatting.domain.QChattingRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomizedChattingRoomRepositoryImpl implements CustomizedChattingRoomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ChattingRoom getQslChattingRoomByTypeAndIdWithUser(String type, Integer articleId, String myname, String yourname) {
        ChattingRoom chattingRoom = jpaQueryFactory
                .select(QChattingRoom.chattingRoom)
                .from(QChattingRoom.chattingRoom)
                .where(QChattingRoom.chattingRoom.articleId.eq(articleId))
                .where(QChattingRoom.chattingRoom.type.eq(type))
                .where(QChattingRoom.chattingRoom.myName.eq(myname))
                .where(QChattingRoom.chattingRoom.yourName.eq(yourname))
                .fetchOne();
//        ChattingRoomDto chattingRoomDto
//        JobsDto jobsDto = JobsDto.builder()
//                .jobid(jobs.getJobid())
//                .jobCategory(jobs.getJobCategory())
//                .jobCheck(jobs.getJobCheck())
//                .jobContent(jobs.getJobContent())
//                .jobSubject(jobs.getJobSubject())
//                .jobStartTime(jobs.getJobStartTime())
//                .jobEndTime(jobs.getJobEndTime())
//                .jobVolunteer(jobs.getJobVolunteer())
//                .jobLike(jobs.getJobLike())
//                .jobDay(jobs.getJobDay())
//                .jobPrice(jobs.getJobPrice())
//                .jobName(jobs.getJobName())
//                .createDate(jobs.getCreateDate())
//                .jobUserid(jobs.getJobUserid())
//                .jobPlace(jobs.getJobPlace())
//                .profileImage(jobs.getProfileImage())
//                .images(imagePaths)
//                .build();

        return chattingRoom;
    }
}
