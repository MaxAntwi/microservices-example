package org.example.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.dto.InventoryResponse;
import org.example.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> inStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inv ->
                InventoryResponse
                        .builder()
                        .skuCode(inv.getSkuCode())
                        .isInStock(inv.getQuantity() > 0)
                        .build()
        ).toList();
    }
}
