package com.in28minutes.springbootrest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User details repository to use for JPA operations.
 */
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

  List<UserDetails> findByRole(String role);

}
