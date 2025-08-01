package gis.adorsys.demo.ssapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HelloWorldServiceTest {

    @Test
    void testGetHelloWorldMessage() {
        HelloWorldServiceInterface service = new HelloWorldService();
        String expected = "hello world";
        String actual = service.getHelloWorldMessage();
        assertEquals(expected, actual, "The hello world message should be 'hello world'");
    }
}