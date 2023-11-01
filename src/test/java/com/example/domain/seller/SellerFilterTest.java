package com.example.domain.seller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
public class SellerFilterTest {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProducerRepo producerRepo;

    @Autowired
    SellerInfoRepo sellerInfo;

    @Autowired
    MarketPlaceRepo marketPlaceRepo;

    @Test
    void should_filter_sellers_by_name() {
        createSellers("seller name");

        var sellers = sellerRepository.findAll(
                sellerFilter("seller name", List.of(), List.of())
        );
        assertThat(sellers)
                .hasSize(1)
                .extracting(s -> tuple(s.getSellerInfoId().getName(), s.getState()))
                .isEqualTo(List.of(tuple("seller name", SellerState.REGULAR)));

        assertThat(sellerRepository.findAll(sellerFilter("seller1", List.of(), List.of())))
                .hasSize(0);
    }

    @Test
    void should_filter_by_producer_id() {
        createSellers("seller");
        var producerId = producerId();

        var sellers = sellerRepository.findAll(
                sellerFilter(null, List.of(producerId), List.of())
        );
        assertThat(sellers)
                .hasSize(1)
                .extracting(s -> s.getSellerInfoId().getName())
                .isEqualTo(List.of("seller"));

        assertThat(sellerRepository.findAll(sellerFilter(null, List.of(UUID.randomUUID()), List.of())))
                .hasSize(0);
    }

    @Test
    void should_filter_by_marketplace_id() {
        createSellers("seller");
        var marketplaceId = marketplaceId();
        var sellersList = sellerRepository.findAll(sellerFilter(null, List.of(), List.of(marketplaceId)));
        assertThat(sellersList)
                .hasSize(1)
                .extracting(s -> s.getSellerInfoId().getName())
                .isEqualTo(List.of("seller"));

        var sellers = sellerRepository.findAll(sellerFilter(null, List.of(), List.of(UUID.randomUUID().toString())));
        assertThat(sellers)
                .hasSize(0);
    }

    @Test
    void should_filter_by_name_producerId_marketplaceId() {
        createSellers("seller");
        var marketplaceId = marketplaceId();
        var producerId = producerId();
        var sellers = sellerRepository.findAll(
                sellerFilter("seller", List.of(producerId), List.of(marketplaceId))
        );
        assertThat(sellers)
                .hasSize(1)
                .extracting(s -> tuple(s.getSellerInfoId().getName(), s.getProducerId().getId(), s.getSellerInfoId().getMarketplace().getId()))
                .isEqualTo(List.of(tuple("seller", producerId, marketplaceId)));
    }

    private static Specification<Sellers> sellerFilter(String searchByName, List<UUID> producers, List<String> marketplaces) {
        return new SellerFilter(
                searchByName,
                producers,
                marketplaces
        ).sellersSpecification();
    }

    private String marketplaceId() {
        return marketPlaceRepo.findAll().iterator().next().getId();
    }

    private UUID producerId() {
        return producerRepo.findAll().iterator().next().getId();
    }


    private void createSellers(String sellerName) {
        var producer = new Producers("producer", Instant.now());
        Marketplace marketplace = new Marketplace("marketplace");
        var seller = new SellerInfo(marketplace, sellerName, "url", "IND", "externalId");
        producerRepo.save(producer);
        marketPlaceRepo.save(marketplace);
        sellerInfo.save(seller);
        Sellers sellers = new Sellers(
                producer,
                seller,
                SellerState.REGULAR
        );
        sellerRepository.save(sellers);
    }
}
