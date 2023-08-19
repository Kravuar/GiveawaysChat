package net.kravuar.fluxsocket.application.repo;

import net.kravuar.fluxsocket.domain.model.Giveaway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface GiveawayRepository extends PagingAndSortingRepository<Giveaway, String>, CrudRepository<Giveaway, String> {
    List<Giveaway> findAllByExpirationTimestampBefore(ZonedDateTime before, Sort sort);
    List<Giveaway> findAllByAmountBetween(Long min, Long max);
    List<Giveaway> findAllByCountGreaterThan(Long min);
    Page<Giveaway> findAllByIsPrivateIsFalseOrOwnerIdIsIn(Collection<String> ids, Pageable pageable);
}