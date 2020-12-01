package dk.dd.carsearch;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class User {
    String id;
    @NonNull
    String username;
    List<Integer> cars;
}