package com.edstem.repository;

import com.edstem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);
}
