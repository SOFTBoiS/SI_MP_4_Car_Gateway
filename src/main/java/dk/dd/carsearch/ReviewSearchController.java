package dk.dd.carsearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewSearchController {

    private ReviewSearchClient reviewClient = null;
    private final Gson GSON = new Gson();

    public ReviewSearchController(ReviewSearchClient reviewClient)
    {
        this.reviewClient = reviewClient;
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

  

}
