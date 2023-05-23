package com.carrot.backend.chatting.dao;

import com.carrot.backend.chatting.domain.ChattingRoom;

public interface CustomizedChattingRoomRepository {

    ChattingRoom getQslChattingRoomByTypeAndIdWithUser(String type, Integer articleId, String myname, String yourname);
}
