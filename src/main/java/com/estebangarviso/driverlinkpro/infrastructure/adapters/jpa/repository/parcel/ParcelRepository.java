package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.parcel;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.*;

import java.util.Set;

@Repository
public interface ParcelRepository extends CrudRepository<Parcel, Long> {

    /**
     * Create a parcel with its articles
     */
    Parcel save(Parcel parcel, Set<ParcelArticles> articles);

    /**
     * Delete articles from a parcel
     */
    Parcel deleteArticles(Long id, Set<ParcelArticles> articles);

    /**
     * Update a parcel status
     */
    Parcel updateStatus(Long id, ParcelStatus status);
}
