package dk.dd.carsearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/username/{username}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Review> getReviewsByUsername(@PathVariable String username)
    {
        String rsReview = reviewClient.readReviewsByUsername(username);
        List<Review> reviews = GSON.fromJson(rsReview, new TypeToken<List<Review>>(){}.getType());
        return reviews;
    }

    @GetMapping("/car-id/{carId}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Review> reviewsByCarId(@PathVariable String carId)
    {
        String rsReview = reviewClient.readReviewsByCarId(carId);
        List<Review> reviews = GSON.fromJson(rsReview, new TypeToken<List<Review>>(){}.getType());
        return reviews;
    }

    @PostMapping("/review")
    public Review postReview(@RequestBody Review review){
        Car car; // =

        if(car != null){
            String res = reviewClient.writeReview(review);
            Review resReview = GSON.fromJson(res, Review.class);
        }

    }


}
