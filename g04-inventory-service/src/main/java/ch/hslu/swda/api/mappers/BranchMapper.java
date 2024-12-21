package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.BranchDto;
import ch.hslu.swda.api.entities.Branch;

public final class BranchMapper {

    public static Branch toEntity(final BranchDto branch) {

        if (branch == null)
            return null;

        return new Branch(
                branch.branchNumber(),
                branch.branchName()
        );
    }

    public static BranchDto toDto(final Branch branch) {
        if (branch == null)
            return null;

        return new BranchDto(
                branch.getBranchNumber(),
                branch.getBranchName()
        );
    }
}