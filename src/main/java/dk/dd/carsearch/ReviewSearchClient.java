package dk.dd.carsearch;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@FeignClient("review-catalog")
@RibbonClient(name="review-catalog", configuration = RibbonConfig.class)
public interface ReviewSearchClient {

    @GetMapping("/reviews/username/{username}")
    String readReviewsByUsername(@PathVariable String username);

    @PostMapping("/reviews")
    String writeReview(@RequestBody Review review);


    @GetMapping("/reviews/car-id/{carId}")
    String readReviewsByCarId(@PathVariable String carId);

    @GetMapping("/username/{username}")
    String readUserByUsername(@PathVariable String username);

}
