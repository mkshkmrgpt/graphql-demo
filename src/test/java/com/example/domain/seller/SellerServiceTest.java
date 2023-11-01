package com.example.domain.seller;

import com.example.data.SortBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SellerServiceTest {

    @Mock
    SellerRepository sellerRepository;
    @InjectMocks
    SellerService sellerService;

    @Test
    void should_group_sellers_by_seller_Info() {
        SellerInfo sellerInfo = new SellerInfo(new Marketplace("test marketPalce"), "seller", "url", "India", "externalid");
        sellerInfo.setId(UUID.randomUUID());
        Producers producer = new Producers("producer", Instant.now());
        producer.setId(UUID.randomUUID());
        var seller1 = new Sellers(
                producer,
                sellerInfo,
                SellerState.BLACKLISTED
        );
        var seller2 = new Sellers(
                producer,
                sellerInfo,
                SellerState.BLACKLISTED
        );
        seller1.setId(UUID.randomUUID());
        seller2.setId(UUID.randomUUID());

        var sellers = new PageImpl<>(List.of(seller1, seller2));
        when(sellerRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(sellers);

        var sellerPageableResponse = sellerService.sellers(
                new SellerFilter("test", List.of(), List.of()),
                0,
                10,
                SortBy.NAME_ASC
        );

        assertThat(sellerPageableResponse)
                .extracting(SellerPageableResponse::data)
                .asList()
                .isNotNull()
                .hasSize(1);
    }
}