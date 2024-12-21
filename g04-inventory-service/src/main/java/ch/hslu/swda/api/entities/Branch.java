package ch.hslu.swda.api.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;


@Data
@MappedEntity
public final class Branch {
    @Id
    @GeneratedValue
    private ObjectId id;

    @NonNull
    @NotBlank
    private final String branchNumber;

    @NonNull
    @NotBlank
    private final String branchName;

}
