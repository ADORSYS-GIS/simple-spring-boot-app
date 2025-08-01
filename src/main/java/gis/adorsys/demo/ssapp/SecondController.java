package gis.adorsys.demo.ssapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {

    @Autowired
    @Qualifier("secondService")
    private HelloWorldServiceInterface helloWorldService;

    @GetMapping("/second")
    public String secondEndpoint() {
        return helloWorldService.getHelloWorldMessage();
    }
}