package com.in28minutes.springbootrest.repository;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Command line runner for jpa operations with user details.
 */
@Slf4j
@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

  private final UserDetailsRepository userDetailsRepository;

  @Autowired
  public UserDetailsCommandLineRunner(UserDetailsRepository userDetailsRepository) {
    this.userDetailsRepository = userDetailsRepository;
  }

  @Override
  public void run(String... args) {
    userDetailsRepository.save(new UserDetails("admin1", "admin"));
    userDetailsRepository.save(new UserDetails("admin2", "admin"));
    userDetailsRepository.save(new UserDetails("tester", "tester"));

    List<UserDetails> userDetailsList = userDetailsRepository.findByRole("admin");
    userDetailsList.forEach(userDetails ->
        log.info("USER: " + userDetails.toString()));
  }
}
