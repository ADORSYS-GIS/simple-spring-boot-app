package gis.adorsys.demo.ssapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    @Qualifier("helloWorldService")
    private HelloWorldServiceInterface helloWorldService;

    @GetMapping("/hello")
    public String helloWorld() {
        return helloWorldService.getWorldMes();
    }
}