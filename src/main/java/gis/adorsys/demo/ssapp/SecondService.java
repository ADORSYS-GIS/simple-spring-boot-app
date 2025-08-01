package gis.adorsys.demo.ssapp;

import org.springframework.stereotype.Service;

@Service
public class SecondService implements HelloWorldServiceInterface {

    @Override
    public String getHelloWorldMessage() {
        return "Hello from the second service!";
    }
}