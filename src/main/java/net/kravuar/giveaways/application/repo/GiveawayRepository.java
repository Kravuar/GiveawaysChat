package net.kravuar.giveaways.application.repo;

import net.kravuar.giveaways.domain.model.giveaways.Giveaway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface GiveawayRepository extends PagingAndSortingRepository<Giveaway, String>, CrudRepository<Giveaway, String>, JpaRepository<Giveaway, String> {
    List<Giveaway> findAllByExpirationTimestampBefore(ZonedDateTime before, Sort sort);
    List<Giveaway> findAllByAmountBetween(Long min, Long max);
    List<Giveaway> findAllByCountGreaterThan(Long min);
    Page<Giveaway> findAllByIsPrivateIsFalseOrOwnerIdIsIn(Collection<String> ids, Pageable pageable);
}