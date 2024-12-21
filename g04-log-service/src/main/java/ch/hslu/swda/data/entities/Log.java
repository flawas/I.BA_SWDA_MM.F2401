package ch.hslu.swda.data.entities;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import static ch.hslu.swda.util.StringUtil.generateRandomText;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@MappedEntity
public final class Log {

    @Id
    @GeneratedValue
    private ObjectId id;

    @NonNull
    @NotBlank
    private UUID logNumber;

    @NonNull
    private Instant triggeredAt;

    private ObjectId triggeredBy;

    @NonNull
    private Service service;

    private String orderNumber;

    @NonNull
    @NotBlank
    private String message;

    public Log() { }

//    public Log(Date triggeredAt,
//               ObjectId triggeredBy,
//               Service service,
//               ObjectId order,
//               String message) {
//        this(
//                "FBS24_" + generateRandomText(5),
//                triggeredAt,
//                triggeredBy,
//                service,
//                order,
//                message
//        );
//    }



    @Creator
    public Log(final Instant triggeredAt,
               final ObjectId triggeredBy,
               final Service service,
               final String orderNumber,
               final String message) {
        this.logNumber = UUID.randomUUID();
        this.triggeredAt = triggeredAt;
        this.triggeredBy = triggeredBy;
        this.service = service;
        this.orderNumber = orderNumber;
        this.message = message;
    }
}
