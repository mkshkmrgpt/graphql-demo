package com.example.data;

import com.example.domain.seller.SellerState;

public record ProducerSellerStates(
        String producerId,
        String producerName,
        SellerState sellerState,
        String sellerId) {
}
