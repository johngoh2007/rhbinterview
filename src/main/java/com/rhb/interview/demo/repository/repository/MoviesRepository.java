package com.rhb.interview.demo.repository.repository;

import com.rhb.interview.demo.repository.entity.MoviesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends CrudRepository<MoviesEntity,Long> {
}
