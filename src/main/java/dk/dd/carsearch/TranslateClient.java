package dk.dd.carsearch;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@FeignClient("translator")
@RibbonClient(name="translator", configuration = RibbonConfig.class)
public interface TranslateClient {

    @PostMapping("/translate/")
    String translate(@RequestBody String body, @RequestHeader("Accept") String accept);
}
