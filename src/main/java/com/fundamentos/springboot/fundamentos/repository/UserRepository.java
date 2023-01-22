package com.fundamentos.springboot.fundamentos.repository;

import com.fundamentos.springboot.fundamentos.dto.UserDto;
import com.fundamentos.springboot.fundamentos.entity.User;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u from User u WHERE u.email=?1")

	Optional<User> findByUserEmail(String email);
	@Query("SELECT u from User u WHERE u.name like ?1%")
	List<User> findAndSort(String name, Sort sort);
	List<User> findByName(String name);
	Optional<User> findByEmailAndName(String email, String name);

	List<User> findByNameLike(String name);
	List<User> findByNameOrEmail(String name, String email);

	List<User> findByBirthdateBetween(LocalDate begin, LocalDate end);

	//List<User> findByNameLikeOrderByIdDesc(String name);
	List<User> findByNameContainingOrderByIdDesc(String name);

	@Query("SELECT new com.fundamentos.springboot.fundamentos.dto.UserDto(u.id, u.name, u.birthdate)" +
	" from User u " +
			" WHERE u.birthdate=:parametroFecha " +
			" and u.email=:parametroEmail ")
	Optional<UserDto> getAllByBirthdateAndEmail(@Param("parametroFecha") LocalDate date,
												@Param("parametroEmail") String email);


}

