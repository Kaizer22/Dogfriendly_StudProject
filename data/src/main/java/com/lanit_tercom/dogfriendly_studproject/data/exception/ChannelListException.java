package com.lanit_tercom.dogfriendly_studproject.data.exception;

public class ChannelListException extends Exception {
    public ChannelListException(){
        super();
    }

    public ChannelListException(final String message){
        super(message);
    }

    public ChannelListException(final String message, final Throwable cause){
        super(message, cause);
    }

    public ChannelListException(final Throwable cause){
        super(cause);
    }
}
