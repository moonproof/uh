package com.github.moonproof.uh.repository;

import com.github.moonproof.uh.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    List<User> findUsersByUrlPictureIsNotNull();
}
