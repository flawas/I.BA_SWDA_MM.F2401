package ch.hslu.swda.api.services;

import ch.hslu.swda.api.dto.BranchDto;
import ch.hslu.swda.api.dto.StockDto;
import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public interface BranchService {
    List<BranchDto> getAll();
    Optional<BranchDto> findByNumber(@NonNull final String branchNumber);
    List<StockDto> getArticlesOfBranch(@NonNull final String branchNumber);
    Optional<BranchDto> create(@NonNull final BranchDto branchDto);
}
