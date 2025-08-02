package gis.adorsys.demo.ssapp;

import org.springframework.stereotype.Service;

@Service
public class SecondService implements HelloWorldServiceInterface {

    @Override
    public String getWorldMes() {
        return "Hello from the second service!";
    }
}