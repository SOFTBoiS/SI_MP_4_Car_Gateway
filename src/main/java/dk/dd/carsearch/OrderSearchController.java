package dk.dd.carsearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderSearchController {

    private  OrderSearchClient orderClient = null;
    private final Gson GSON = new Gson();

    public OrderSearchController(OrderSearchClient orderClient) {
        this.orderClient = orderClient;
    }

    @GetMapping("/orders/username/{username}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Order> getOrdersByUsername(@PathVariable String username)
    {
        String rsOrder = orderClient.getOrdersByUsername(username);
        List<Order> orders = GSON.fromJson(rsOrder, new TypeToken<List<Order>>(){}.getType());
        return orders;
    }

    @GetMapping("/orders/car-id/{carId}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Order> getOrdersByCarId(@PathVariable String carId)
    {
        String rsOrder = orderClient.getOrdersByCarId(carId);
        List<Order> orders = GSON.fromJson(rsOrder, new TypeToken<List<Order>>(){}.getType());
        return orders;
    }

    @PostMapping("/orders")
    @HystrixCommand(fallbackMethod = "fallbackPost")
    public ResponseEntity postOrder(@RequestBody Order order){

        String res = orderClient.makeOrder(order);
        Order resOrder = GSON.fromJson(res, Order.class);
        return new ResponseEntity(resOrder, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    @HystrixCommand(fallbackMethod = "fallbackDelete")
    public ResponseEntity deleteOrder(@PathVariable String id) {
        String res = orderClient.deleteOrder(id);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    private ResponseEntity fallbackPost(Order order){
        return new ResponseEntity("Something went wrong with your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity fallbackDelete(String id){
        return new ResponseEntity("Something went wrong with your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
