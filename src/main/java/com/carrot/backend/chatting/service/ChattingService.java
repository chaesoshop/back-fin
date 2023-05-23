package com.carrot.backend.chatting.service;

import com.carrot.backend.chatting.dao.ChattingRepository;
import com.carrot.backend.chatting.dao.ChattingRoomRepository;
import com.carrot.backend.chatting.dao.CustomizedChattingRoomRepositoryImpl;
import com.carrot.backend.chatting.domain.Chatting;
import com.carrot.backend.chatting.domain.ChattingRoom;
import com.carrot.backend.chatting.dto.ChattingRoomDto;
import com.carrot.backend.user.dao.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChattingService {
    private final ObjectMapper objectMapper;
    private Map<String, ChattingRoom> chattingRoom;

    private final ChattingRepository chattingRepository;
    private final ChattingRoomRepository chattingRoomRepository;

    private final CustomizedChattingRoomRepositoryImpl customizedChattingRoomRepository;

    private final UserRepository userRepository;
    @PostConstruct
    private void init(){
        chattingRoom = new LinkedHashMap<>();
    }

    //채팅방 최근 생성순으로 반환
    public List<ChattingRoom> findAllRoom(){
List<ChattingRoom> result = new ArrayList<>(chattingRoom.values());
Collections.reverse(result);
        return result;
    }

    //채팅방 하나 반환
    public ChattingRoom findById(String roomId){

        try {
            ChattingRoom room = chattingRoomRepository.findById(roomId).orElseThrow();
            if(room!=null){
                return room;
            }
            return null;
        }catch(Exception e){
            return null;
        }

    }

    //채팅방 생성
    public ChattingRoom createRoom(String roomId,String myname,String yourName,String myURL, String yourURL, String type, Integer articleId){


        ChattingRoom room = ChattingRoom.builder()
                .roomId(roomId)
                .myName(myname)
                .yourName(yourName)
                .myURL(myURL)
                .yourURL(yourURL)
                .type(type)
                .articleId(articleId)
                .build();
        chattingRoom.put(roomId, room);

        chattingRoomRepository.save(room);

        return room;
    }

    public Chatting saveChat(Chatting chatting){
        Chatting chat = new Chatting();
        chat.setMessage(chatting.getMessage());
        chat.setRoomId(chatting.getRoomId());
        chat.setType(chatting.getType());
        chat.setSender(chatting.getSender());
        LocalDateTime date = LocalDateTime.now();
        chat.setCreateDate(date);
        chattingRepository.save(chat);
        ChattingRoom room = findById(chatting.getRoomId());
        room.setLastMessage(chatting.getMessage());
        chattingRoomRepository.save(room);

        return chat;
    }

    public List<ChattingRoom> findByUser(String myName, String yourName) {
        List<ChattingRoom> room = chattingRoomRepository.findByMyNameAndYourName(myName,yourName);

        return room;
    }

    public List<Chatting> getMessage(String roomId) {
       return chattingRepository.findByRoomId(roomId);
    }

    public List<ChattingRoom> findAllRoomByUser(String userid) {
        List<ChattingRoom> rooms = chattingRoomRepository.findByMyNameOrYourNameContaining(userid,userid);

        return  rooms;
    }

    public ChattingRoom findRoomsByTypeAndId(ChattingRoomDto chattingRoomDto) {
        ChattingRoom rooms = customizedChattingRoomRepository.getQslChattingRoomByTypeAndIdWithUser(chattingRoomDto.getType(), chattingRoomDto.getArticleId(), chattingRoomDto.getMyName(),chattingRoomDto.getYourName());

        return rooms;
    }

    public List<ChattingRoom> findByArticleId(Integer productId) {
        List<ChattingRoom> room = chattingRoomRepository.findByArticleIdAndType(productId,"product");
        return room;
    }

    public List<ChattingRoom> findByRealtyId(Integer realtyId) {
        List<ChattingRoom> room = chattingRoomRepository.findByArticleIdAndType(realtyId,"realty");
        return room;
    }
}
