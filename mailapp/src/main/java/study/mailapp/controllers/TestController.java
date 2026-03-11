package study.mailapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @GetMapping
    public String test() {
        return String.format("It's OK. app instance is: %s", instanceId);
    }
}
