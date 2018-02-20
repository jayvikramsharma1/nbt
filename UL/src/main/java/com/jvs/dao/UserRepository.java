package com.jvs.dao;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jvs.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	@Query("select u from User u where u.username=:username")
	public User findUserByUserName(@Param("username") String username);
	
	@Query("select u from User u where u.username=:username and u.password=:password")
	public User findUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
	
	@Query("select count(u) from User u")
	public long getUserCount();
	
	@Modifying
	@Transactional
	@Query("UPDATE User SET fullname=:fullname, password=:password where id=:id")
	public void updateUser(@Param("id") Long id, @Param("fullname") String fullname, @Param("password") String password);
}
