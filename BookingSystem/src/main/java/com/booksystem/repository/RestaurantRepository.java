package com.booksystem.repository;

import com.booksystem.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Integer > {
List<RestaurantEntity>getAllBy();
List<RestaurantEntity>getByName(String name);
List<RestaurantEntity>getByAddress(String address);
RestaurantEntity getById(Integer id);
@Query(nativeQuery = true,value = "select * from restaurant where"+
        " if(?1 is not null, lower(name)like concat ('%',lower(?1),'%'), true)")
List<RestaurantEntity> search(String name);

}
