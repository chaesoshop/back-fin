package com.carrot.backend.chatting.controller;

import com.carrot.backend.chatting.domain.Chatting;
import com.carrot.backend.chatting.domain.ChattingRoom;
import com.carrot.backend.chatting.dto.ChattingRoomDto;
import com.carrot.backend.chatting.service.ChattingService;
import com.carrot.backend.product.Service.ProductService;
import com.carrot.backend.realty.service.RealtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChattingService chattingService;

    private final ProductService productService;

    private final RealtyService realtyService;

    private static final Set<String> SESSION_IDS = new HashSet<>();


    @MessageMapping("/chat")
    public Chatting sendMessage(Chatting chatting, SimpMessageHeaderAccessor accessor){
        String roomNum = chatting.getRoomId();
        Chatting chats = chattingService.saveChat(chatting);
        simpMessagingTemplate.convertAndSend("/sub/chat/" +roomNum, chats);
        return chats;
    }

    //채팅방 생성
    @PostMapping("/chat")
    public ChattingRoom createRoom(@RequestBody ChattingRoom chattingRoom){

        String myname = chattingRoom.getMyName();
        String yourName = chattingRoom.getYourName();
        String roomId = chattingRoom.getRoomId();
        String myURL = chattingRoom.getMyURL();
        String yourURL = chattingRoom.getYourURL();
        String type = chattingRoom.getType();
        Integer articleId = chattingRoom.getArticleId();
        if(type.equals("product")) {
            productService.addChatNumProduct(articleId);
        }else if(type.equals("realty")){
            realtyService.addChatNumRealty(articleId);
        }
        return chattingService.createRoom(roomId,myname,yourName,myURL,yourURL,type, articleId);

    }

    //모든 채팅방 목록 반환
    @GetMapping("/chat")
    public List<ChattingRoom> findAllRoom(){
        return chattingService.findAllRoom();
    }

//    @EventListener(SessionConnectEvent.class)
//    public void onConnect(SessionConnectEvent event){
//        String sessionId = event.getMessage().getHeaders().get("simSessionId").toString();
//        SESSION_IDS.add(sessionId);
//        System.out.println("[connect] connections : {}" + SESSION_IDS.size());
//    }
//
//    @EventListener(SessionConnectEvent.class)
//    public void onDisConnect(SessionConnectEvent event){
//        String sessionId = event.getMessage().getHeaders().get("simSessionId").toString();
//        SESSION_IDS.remove(sessionId);
//        System.out.println("[disconnect] connections : {}" + SESSION_IDS.size());
//    }
    //특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    public ChattingRoom roomInfo(@PathVariable String roomId){
        return chattingService.findById(roomId);
    }

    @GetMapping("/getRoomByProductId/{productId}")
    public List<ChattingRoom> getRoomByProductId(@PathVariable Integer productId){
        return chattingService.findByArticleId(productId);
    }
    @GetMapping("/getRoomByRealtyId/{realtyId}")
    public List<ChattingRoom> getRoomByRealtyId(@PathVariable Integer realtyId){
        return chattingService.findByRealtyId(realtyId);
    }
    @PostMapping("/getRoomByType/{roomId}")
    public ChattingRoom getRoomByTypes (@PathVariable String roomId,@RequestBody ChattingRoomDto chattingRoomDto ){
        return chattingService.findRoomsByTypeAndId(chattingRoomDto);
    }

    @GetMapping("/getChattingRoom")
    public List<ChattingRoom> findRoom(@RequestParam String myName, @RequestParam String yourName){
        List<ChattingRoom> room = chattingService.findByUser(myName,yourName);

        return room;
    }

//    @GetMapping("/getRooms")
//    public List<ChattingRoom> getRooms(@RequestBody ChattingRoomDto chattingRoomDto){
//        List<ChattingRoom> rooms = chattingService.findRoomsByTypeAndId(chattingRoomDto);
//        return rooms;
//    }

    @GetMapping("/getMessage")
    public List<Chatting> getMessages(@RequestParam String roomId){
        List<Chatting> messages = chattingService.getMessage(roomId);
        return messages;
    }

    @GetMapping("/getChatList")
    public List<ChattingRoom> getChatList(@RequestParam String userid){
        List<ChattingRoom> rooms = chattingService.findAllRoomByUser(userid);
        return rooms;
    }



}
