package net.kravuar.fluxsocket.application.repo;

import net.kravuar.fluxsocket.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>, CrudRepository<User, String> {
    User findByUsername(String username);
}