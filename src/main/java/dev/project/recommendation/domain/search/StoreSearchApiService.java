package dev.project.recommendation.domain.search;

public interface StoreSearchApiService<T>{

    <P> T requestSearch(P inputDataDto);
}
