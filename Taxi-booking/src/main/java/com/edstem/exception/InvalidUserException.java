package com.edstem.exception;

public class InvalidUserException extends  RuntimeException {
    public InvalidUserException(String login){
        super("Invalid");
    }


}
