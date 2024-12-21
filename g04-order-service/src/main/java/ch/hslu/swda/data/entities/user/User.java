package ch.hslu.swda.data.entities.user;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@MappedEntity
public final class User {

    @Id
    @GeneratedValue
    private ObjectId id;

    @NonNull
    @NotBlank
    private String firstname;

    @NonNull
    @NotBlank
    private String lastname;

    @NonNull
    @NotBlank
    @Email // TODO-go: Enforce uniqueness / index
    private String email;

    @NonNull
    private UserRole role;

    @NonNull
    @NotBlank
    private String branchNumber;

    public User() { }
    public User(
            final String firstname,
            final String lastname,
            final String email,
            final UserRole role,
            final String branchNumber
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.branchNumber = branchNumber;
    }
}
