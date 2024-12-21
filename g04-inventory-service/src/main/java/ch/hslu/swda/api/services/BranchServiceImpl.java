package ch.hslu.swda.api.services;

import ch.hslu.swda.api.dto.BranchDto;
import ch.hslu.swda.api.dto.StockDto;
import ch.hslu.swda.api.entities.Branch;
import ch.hslu.swda.api.mappers.BranchMapper;
import ch.hslu.swda.api.mappers.StockMapper;
import ch.hslu.swda.api.repositories.BranchRepository;
import ch.hslu.swda.api.repositories.InventoryRepository;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public final class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final InventoryRepository inventoryRepository;

    public BranchServiceImpl(
            final BranchRepository branchRepository,
            final InventoryRepository inventoryRepository
    ) {
        this.branchRepository = branchRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<BranchDto> getAll() {
        return branchRepository.findAll().stream()
                .map(BranchMapper::toDto)
                .toList();
    }

    @Override
    public Optional<BranchDto> findByNumber(@NonNull final String branchNumber) {
        return branchRepository.findByBranchNumber(branchNumber).map(BranchMapper::toDto);
    }

    @Override
    public List<StockDto> getArticlesOfBranch(@NonNull final String branchNumber) {
        return inventoryRepository.findByBranchNumber(branchNumber).stream()
                .map(StockMapper::toDto)
                .toList();
    }

    @Override
    public Optional<BranchDto> create(@NonNull final BranchDto branchDto) {
        Branch branch = BranchMapper.toEntity(branchDto);
        Branch saveResult = branchRepository.save(branch);

        return Optional.ofNullable(BranchMapper.toDto(saveResult));
    }
}