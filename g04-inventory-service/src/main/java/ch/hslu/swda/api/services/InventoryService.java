package ch.hslu.swda.api.services;

import ch.hslu.swda.api.dto.ValidateOrderPositionDto;
import ch.hslu.swda.api.dto.StockDto;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    List<StockDto> getInventory();
    Optional<StockDto> findStockByArticleNumber(final String articleNumber);
    Optional<StockDto> updateStockByArticleNumber(final String articleNumber, final StockDto stockDto);
    void validateOrderPosition(final ValidateOrderPositionDto validateOrderPositionDto);
    void createStock(final StockDto stockDto);
}
