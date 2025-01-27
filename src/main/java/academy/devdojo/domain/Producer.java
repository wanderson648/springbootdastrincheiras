package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producer {
    private Long id;
    @JsonProperty("name")
    private String name;
    private LocalDateTime createdAt;
}
