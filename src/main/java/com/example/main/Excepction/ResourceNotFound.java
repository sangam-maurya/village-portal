package com.example.main.Excepction;


public class ResourceNotFound extends RuntimeException {
    public  ResourceNotFound(String msg){
       super(msg);
    }
}
