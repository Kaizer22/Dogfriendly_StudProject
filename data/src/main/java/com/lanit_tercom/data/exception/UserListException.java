package com.lanit_tercom.data.exception;

public class UserListException extends Exception {

    public UserListException(){
        super();
    }

    public UserListException(final String message){
        super(message);
    }

    public UserListException(final String message, final Throwable cause){
        super(message, cause);
    }

    public UserListException(final Throwable cause){
        super(cause);
    }
}
