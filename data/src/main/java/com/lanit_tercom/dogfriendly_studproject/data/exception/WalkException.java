package com.lanit_tercom.dogfriendly_studproject.data.exception;

public class WalkException extends Exception {

    public WalkException(){
        super();
    }

    public WalkException(String message){super(message);}

    public WalkException(String message, final Throwable cause){super(message, cause);}

    public WalkException(final Throwable cause){super(cause);}
}
