package com.example.data;

import java.util.List;

public record Seller(
        String sellerName,
        String externalId,
        List<ProducerSellerStates> producerSellerStates,
        String marketplaceId,
        String sellerId
) {
}
