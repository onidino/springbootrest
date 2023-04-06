package com.in28minutes.springbootrest.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Rest Repository of user details.
 */
@Repository
public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails, Long> {

  List<UserDetails> findByRole(String role);
}
