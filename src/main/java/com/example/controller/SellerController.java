package com.example.controller;

import com.example.data.Page;
import com.example.data.SortBy;
import com.example.domain.seller.SellerFilter;
import com.example.domain.seller.SellerPageableResponse;
import com.example.domain.seller.SellerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @QueryMapping
    public SellerPageableResponse sellers(
            @Argument SellerFilter filter,
            @Argument Page page,
            @Argument SortBy sortBy
    ) {
        return sellerService.sellers(
                filter,
                page.page(),
                page.size(),
                sortBy
        );
    }
}
