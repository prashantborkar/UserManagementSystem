package com.assignment.telia.repository;

import com.assignment.telia.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserInformation, Long> {
	List<UserInformation> findByPersonalNumber(Long personalNumber);

}
