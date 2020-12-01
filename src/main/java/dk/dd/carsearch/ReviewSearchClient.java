package dk.dd.carsearch;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@FeignClient("review-catalog")
@RibbonClient(name="review-catalog", configuration = RibbonConfig.class)
public interface ReviewSearchClient {

    @GetMapping("/reviews/username/{username}")
    String readReviewsByUsername(@PathVariable String username);
}
