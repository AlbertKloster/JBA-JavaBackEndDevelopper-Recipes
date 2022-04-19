package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String email;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String category;

    @UpdateTimestamp
    private LocalDateTime date;

    @NotBlank
    @NotNull
    private String description;

    @ElementCollection
    @NotNull
    @Size(min = 1)
    private List<String> ingredients;

    @ElementCollection
    @NotNull
    @Size(min = 1)
    private List<String> directions;

}
