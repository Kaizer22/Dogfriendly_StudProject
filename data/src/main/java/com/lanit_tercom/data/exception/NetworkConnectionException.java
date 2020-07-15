package com.lanit_tercom.data.exception;

public class NetworkConnectionException extends Exception {

    public NetworkConnectionException(){
        super();
    }

    public NetworkConnectionException(String message){
        super(message);
    }

    public NetworkConnectionException(String message, final Throwable cause){
        super(message, cause);
    }

    public NetworkConnectionException(final Throwable cause){
        super(cause);
    }
}
