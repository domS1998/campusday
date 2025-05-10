package org.server.orm;

import org.server.exceptions.*;

public interface DAO {

    abstract public void insert () throws ChatWithUserException, DuplicateChatExeption, SaveObjectException, NoSuchUserException;
    abstract public void load   (Object primaryKey);
    abstract public void update () throws NoSuchChatException;
    abstract public void delete () throws NoSuchChatException;
}

