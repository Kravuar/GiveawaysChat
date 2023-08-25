package net.kravuar.giveaways.application.repo;

import net.kravuar.giveaways.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>, CrudRepository<User, String>, JpaRepository<User, String> {
    User findByUsername(String username);
}