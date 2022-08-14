package com.cognizant.microservices.authservice.repository;

import com.cognizant.microservices.authservice.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<LoginUser,Long> {

    public Optional<LoginUser> findByUserName(String username);
}
