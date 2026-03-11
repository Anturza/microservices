package fallback;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public String fallback() {
        return "Fallback response: Service is currently unavailable";
    }
}
