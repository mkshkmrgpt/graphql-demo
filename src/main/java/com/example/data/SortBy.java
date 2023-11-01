package com.example.data;

public enum SortBy {
    SELLER_INFO_EXTERNAL_ID_ASC ("sellerInfoId.externalId"),
    SELLER_INFO_EXTERNAL_ID_DESC("sellerInfoId.externalId"),
    NAME_ASC("sellerInfoId.name"),
    NAME_DESC("sellerInfoId.name"),
    MARKETPLACE_ID_ASC("sellerInfoId.marketplace.id"),
    MARKETPLACE_ID_DESC("sellerInfoId.marketplace.id");

    private final String sortBy;

    public String value() {
        return sortBy;
    }

    SortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
