package com.towne.framework.spring.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.towne.framework.springmvc.model.User;

/**
 * User Spring Data Jpa
 * @author towne
 *
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	
	/**
	 * is query com.towne.framework.springmvc.model.User  @NamedQuery(name="findUserById",query=" from User a where a.id=?1 ")
	 * @see com.towne.framework.springmvc.model.User<br/>@NamedQuery(name="findUserById",query=" from User a where a.id=?1 ")
	 * @param id
	 * @return
	 */
	User findUserById(int id);
	

	/**
	 * is set username by id = ?
	 * @param id
	 * @return
	 */	
	@Modifying
	@Query(value="update User u set u.username=?1 where u.id=?2 ")
	int modifyById(String username,int id);
	

	List<User> findByUsernameLike(String username);
	
	/**
	 * is query id < ?
	 * @param id
	 * @return
	 */
	List<User> findByIdLessThan(int id);
	
	
	/**
	 * is query by username && id desc
	 * @param username
	 * @return
	 */
	List<User> findByUsernameLikeOrderByIdDesc(String username);
	
	
	
	/**
	 * is query by id  between and
	 * @param i
	 * @param j
	 * @return
	 */
	List<User> findByIdBetween(int i,int j);
	
	
	
	/**
	 * user && dept related queries
	 * @param deptid
	 * @return
	 */
	@Query(value=" from User user where user.dept.id=:deptid")
	List<User> findUserByDeptId(@Param("deptid")int deptid);
	
}
