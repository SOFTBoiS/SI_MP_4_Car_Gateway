package dk.dd.carsearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewSearchController {

    private ReviewSearchClient reviewClient = null;
    private CarSearchClient carClient = null;
    private final Gson GSON = new Gson();

    public ReviewSearchController(ReviewSearchClient reviewClient, CarSearchClient carClient)
    {
        this.reviewClient = reviewClient;
        this.carClient = carClient;
    }

    @GetMapping("/reviews/username/{username}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Review> getReviewsByUsername(@PathVariable String username)
    {
        String rsReview = reviewClient.readReviewsByUsername(username);
        List<Review> reviews = GSON.fromJson(rsReview, new TypeToken<List<Review>>(){}.getType());
        return reviews;
    }

    @GetMapping("/reviews/car-id/{carId}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Review> reviewsByCarId(@PathVariable String carId)
    {
        String rsReview = reviewClient.readReviewsByCarId(carId);
        List<Review> reviews = GSON.fromJson(rsReview, new TypeToken<List<Review>>(){}.getType());
        return reviews;
    }

    @PostMapping("/reviews")
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity postReview(@RequestBody Review review){
        String car = carClient.readCarById(review.carId);

        if(car == null){
            return new ResponseEntity("Car not found", HttpStatus.NOT_FOUND);
        }

        String res = reviewClient.writeReview(review);
        Review resReview = GSON.fromJson(res, Review.class);
        return new ResponseEntity(resReview, HttpStatus.OK);
    }


    private ResponseEntity fallback(Review review){
        return new ResponseEntity("Something went wrong with your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
