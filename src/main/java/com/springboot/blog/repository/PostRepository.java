package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * JpaRepository<Post, Long> does not need @Repository and @Transactional because SimpleJpaRepository which implements
 * JpaRepositoryImplementation extends JpaRepository which already has both these annotations.
 *
 * Also, we don't need to write any method declarations in this interface.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

}
