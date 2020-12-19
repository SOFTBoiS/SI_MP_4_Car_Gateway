package dk.dd.carsearch;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@XmlRootElement
public class Order {
    String id;
    @NonNull String username;
    @NonNull long carId;
    @NonNull String startDate;
    @NonNull String endDate;
    @NonNull long price;
}
