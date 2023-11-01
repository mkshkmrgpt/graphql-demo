package com.example.domain.seller;

import com.example.data.PageMeta;
import com.example.data.Seller;

import java.util.List;

public record SellerPageableResponse(PageMeta meta, List<Seller> data) {
}
