package dev.project.recommendation.domain.search;

public interface AddressSearchService<T> {

    T requestAddressSearch(String address);
}
