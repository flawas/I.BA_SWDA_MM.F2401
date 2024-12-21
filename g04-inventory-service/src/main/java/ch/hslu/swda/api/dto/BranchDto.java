package ch.hslu.swda.api.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Serdeable
public record BranchDto(
        @NotNull
        @NotBlank
        String branchNumber,

        @NotNull
        @NotBlank
        String branchName
) {}
