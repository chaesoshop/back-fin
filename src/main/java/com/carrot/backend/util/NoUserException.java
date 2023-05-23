package com.carrot.backend.util;

public class NoUserException extends Exception{
    public NoUserException(){
        super("해당 유저가 존재하지 않습니다.");
    }
}
