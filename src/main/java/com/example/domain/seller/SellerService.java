package com.example.domain.seller;

import com.example.data.PageMeta;
import com.example.data.ProducerSellerStates;
import com.example.data.Seller;
import com.example.data.SortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;

    SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public SellerPageableResponse sellers(SellerFilter sellerFilter, int page, int size, SortBy sortBy) {
        return getSellerPageableResponse(sellerRepository.findAll(
                sellerFilter.sellersSpecification(),
                PageRequest.of(page, size, sortBy(sortBy))
        ));
    }

    private SellerPageableResponse getSellerPageableResponse(Page<Sellers> sellers) {
        return new SellerPageableResponse(
                new PageMeta(sellers.getNumber(), sellers.getTotalPages()),
                mapSellers(sellers)
        );
    }

    private List<Seller> mapSellers(Page<Sellers> sellerPage) {
        var sellers = sellerPage.stream().collect(
                Collectors.groupingBy(Sellers::getSellerInfoId, Collectors.toSet())
        );
        return sellers.entrySet().stream()
                .map(seller -> {
                    var sellerInfo = seller.getKey();
                    return new Seller(
                            sellerInfo.getName(),
                            sellerInfo.getExternalId(),
                            producerSellerStates(seller),
                            sellerInfo.getMarketplace().getId(),
                            sellerInfo.getId().toString()
                    );
                }).toList();
    }

    private static List<ProducerSellerStates> producerSellerStates(Map.Entry<SellerInfo, Set<Sellers>> sellers) {
        return sellers.getValue().stream().map(seller -> new ProducerSellerStates(
                seller.getProducerId().getId().toString(),
                seller.getProducerId().getName(),
                seller.getState(),
                seller.getId().toString()
        )).toList();
    }

    private Sort sortBy(SortBy sortBy) {
        var orderBy = Sort.by(sortBy.value());
        return sortBy.toString().endsWith("DESC")
                ? orderBy.descending()
                : orderBy;
    }
}
