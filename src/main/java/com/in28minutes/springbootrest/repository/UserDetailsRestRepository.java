package com.in28minutes.springbootrest.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Rest Repository of user details.
 */
public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails, Long> {

  List<UserDetails> findByRole(String role);
}
