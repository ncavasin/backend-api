package com.sip.api.repositories;

import com.sip.api.domains.resource.Resource;
import lombok.NonNull;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends PagingAndSortingRepository<Resource, String> {
    @NonNull List<Resource> findAll();

    Optional<Resource> findByName(String name);

    boolean existsByName(String name);
}
