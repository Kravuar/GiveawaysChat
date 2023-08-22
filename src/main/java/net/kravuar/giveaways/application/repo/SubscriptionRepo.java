package net.kravuar.giveaways.application.repo;

import net.kravuar.giveaways.domain.model.Subscription;
import net.kravuar.giveaways.domain.model.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends CrudRepository<Subscription, SubscriptionId>, JpaRepository<Subscription, SubscriptionId> {
}
