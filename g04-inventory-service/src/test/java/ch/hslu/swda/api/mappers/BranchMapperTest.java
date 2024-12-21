package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.BranchDto;
import ch.hslu.swda.api.entities.Branch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BranchMapperTest {

    @Test
    void toEntity() {
        BranchDto dto = new BranchDto("rk", "Rotkreuz");
        var Branch = BranchMapper.toEntity(dto);

        assertEquals(dto.branchName(), Branch.getBranchName());
        assertEquals(dto.branchNumber(), Branch.getBranchNumber());
    }

    @Test
    void toDto() {
        Branch branch = new Branch("rk", "Rotkreuz");
        var dto = BranchMapper.toDto(branch);

        assertEquals(branch.getBranchName(),dto.branchName());
        assertEquals(branch.getBranchNumber(),dto.branchNumber());
    }

    @Test
    void ifNull() {
        assertNull(BranchMapper.toEntity(null));
        assertNull(BranchMapper.toDto(null));
    }
}