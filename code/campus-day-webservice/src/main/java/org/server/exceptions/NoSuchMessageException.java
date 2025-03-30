package org.server.exceptions;

public class NoSuchMessageException extends Exception{

    public NoSuchMessageException(String id) {
        super("No such message with id " + id +" found in db!") ;
    }
}
