package recipes;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Data
@Entity
public class User {

    @Id
    @NotNull
    @NotBlank
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;

}
