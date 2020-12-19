package dk.dd.carsearch;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Resources;

import java.util.List;


@CrossOrigin
@FeignClient("rent-a-car")
@RibbonClient(name="rent-a-car", configuration = RibbonConfig.class)
public interface OrderSearchClient {

    @GetMapping("/orders/username/{username}")
    String getOrdersByUsername(@PathVariable String username);

    @PostMapping("/orders")
    String makeOrder(@RequestBody Order order);

    @GetMapping("/orders/car-id/{carId}")
    String getOrdersByCarId(@PathVariable String carId);

    @DeleteMapping("/orders/{id}")
    String deleteOrder(@PathVariable String id);
}
