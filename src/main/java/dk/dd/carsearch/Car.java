package dk.dd.carsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@XmlRootElement
public class Car
{
//        @Id
        //@GeneratedValue(strategy = GenerationType.AUTO)
        @JsonProperty
        private Long id;
        @NonNull private String brand;
        @NonNull private int year;
        @NonNull private long km;
        @NonNull private long price;
}
