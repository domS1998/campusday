package org.server.exceptions;

public class DuplicateChatExeption extends Exception {

    public DuplicateChatExeption(String userName, String targetUserName) {
        super("Chat ('"+userName + ", " + targetUserName +"') already exists in database!\n");
    }
}