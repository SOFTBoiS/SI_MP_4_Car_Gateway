package dk.dd.carsearch;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class OrderSearchController {

    private OrderSearchClient orderClient = null;
    private TranslateClient translateClient = null;
    private final Gson GSON = new Gson();
    Logger logger = Logger.getLogger(OrderSearchController.class.getName());

    public OrderSearchController(OrderSearchClient orderClient, TranslateClient translateClient) {
        this.orderClient = orderClient;
        this.translateClient = translateClient;
    }

    @GetMapping("/orders/{id}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public Order getOrderById(@PathVariable String id) {
        return orderClient.getOrderById(id);
    }

    @GetMapping("/orders/username/{username}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Order> getOrdersByUsername(@PathVariable String username, @RequestHeader("Accept") String accept)
    {
        String rsOrder = orderClient.getOrdersByUsername(username);
        List<Order> orders = GSON.fromJson(rsOrder, new TypeToken<List<Order>>(){}.getType());
        return orders;
    }

    @GetMapping("/orders/car-id/{carId}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public List<Order> getOrdersByCarId(@PathVariable String carId, @RequestHeader("Accept") String accept)
    {
        String rsOrder = orderClient.getOrdersByCarId(carId);
        List<Order> orders = GSON.fromJson(rsOrder, new TypeToken<List<Order>>(){}.getType());
        return orders;
    }

    @PostMapping("/orders")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    @HystrixCommand(fallbackMethod = "fallbackPost")
    public ResponseEntity postOrder(@RequestBody Order order, @RequestHeader("Accept") String accept){
        try {
            String res = orderClient.makeOrder(order);
            res = "{ order : " + res + "}";
            res = convertToXML(accept, res);

            return new ResponseEntity(res, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.warning("A post failed in OrderSearchController with msg: " + e.getMessage());
            return  new ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/orders/{id}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    @HystrixCommand(fallbackMethod = "fallbackDelete")
    public ResponseEntity deleteOrder(@PathVariable String id) {
        String res = orderClient.deleteOrder(id);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    private ResponseEntity fallbackPost(Order order, String accept){
        return new ResponseEntity("Something went wrong with your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity fallbackDelete(String id){
        return new ResponseEntity("Something went wrong with your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String convertToXML(String returnType, String json) {
        if (!returnType.contains("application/json") && !returnType.contains("*/*") && returnType.contains("application/xml")) {
            return translateClient.translate(json, "application/xml");
        }
        return json;
    }


}
