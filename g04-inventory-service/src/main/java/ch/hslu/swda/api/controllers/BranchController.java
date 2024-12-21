package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.dto.BranchDto;
import ch.hslu.swda.api.dto.StockDto;
import ch.hslu.swda.api.services.BranchService;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Controller("/branches")
@ExecuteOn(TaskExecutors.IO)
@Slf4j
public final class BranchController {

    private final BranchService branchService;

    public BranchController(
            final BranchService branchService) {
        this.branchService = branchService;
    }

    @Get()
    @Version("1")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BranchDto> getAllBranches() {
        final List<BranchDto> branches = branchService.getAll();
        log.info("API: Returning all {} branches", branches.size());
        return branches;
    }

    @Get("/{number}")
    @Version("1")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<BranchDto> getBranch(@PathVariable("number") final String number) {
        final Optional<BranchDto> branch = branchService.findByNumber(number);
        if (branch.isEmpty()) {
            log.warn("API: No branch found for {} branch number", number);
        } else {
            log.info("API: Returning branch for {} branch number", number);
        }

        return branch;
    }

    @Get("/{number}/articles")
    @Version("1")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StockDto> getAllArticlesOfBranch(@PathVariable("number") final String number) {
        final List<StockDto> articles = branchService.getArticlesOfBranch(number);
        log.info("API: Returning all {} articles of branch {}", articles.size(), number);

        return articles;
    }

    @Post()
    @Version("1")
    public Optional<BranchDto> createBranch(@Body final BranchDto branch) {
        return branchService.create(branch);
    }
}
