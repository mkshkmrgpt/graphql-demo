package com.example.controller;

import com.example.domain.seller.SellerPageableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SellerControllerTest {
    private HttpGraphQlTester graphQlTester;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl(String.format("http://localhost:%s/graphql", port))
                .build();

        graphQlTester = HttpGraphQlTester.create(client);
    }

    @Test
    void contextLoads() {
        assertNotNull(graphQlTester);
    }

    @Test
    void shouldReturnAllSellers() {
        var document = """
                query {
                  sellers(
                    page: {page: 0, size: 10}
                    filter: { producerIds: [], marketplaceIds: []}
                    sortBy: MARKETPLACE_ID_DESC
                  ) {
                    data {
                      sellerName
                      marketplaceId
                      externalId
                      producerSellerStates {
                        producerId
                        producerName
                        sellerState
                        sellerId
                      }
                    }
                    meta {
                      currentPage
                      totalPages
                    }
                  }
                }
                """;
        var sellers = graphQlTester.document(document)
                .execute()
                .path("sellers")
                .entity(SellerPageableResponse.class);

        assertThat(sellers)
                .isNotNull()
                .extracting(sellerPageableResponseEntity -> sellerPageableResponseEntity.get().data())
                .asList()
                .hasSizeBetween(0, 10);
    }
}