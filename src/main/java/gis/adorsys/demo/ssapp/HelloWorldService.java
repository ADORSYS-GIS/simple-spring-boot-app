package gis.adorsys.demo.ssapp;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService implements HelloWorldServiceInterface {

    @Override
    public String getWorldMes() {
        return "hello world";
    }
}