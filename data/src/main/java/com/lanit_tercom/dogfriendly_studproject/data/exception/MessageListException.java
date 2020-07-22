package com.lanit_tercom.dogfriendly_studproject.data.exception;

public class MessageListException extends Exception {
    public MessageListException(){
        super();
    }

    public MessageListException(final String message){
        super(message);
    }

    public MessageListException(final String message, final Throwable cause){
        super(message, cause);
    }

    public MessageListException(final Throwable cause){
        super(cause);
    }
}
