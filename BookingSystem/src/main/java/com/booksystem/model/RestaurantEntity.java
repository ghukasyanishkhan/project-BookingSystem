package com.booksystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
@Data
public class RestaurantEntity {
    @Id
    @Column(name = "restaurant_id")
    private Integer id;
    private String name;
    private String address;
    private String phone;
    @Column(name = "count_of_tables")
    private Integer tables;
    @Column(name = "count_of_reserved_tables")
    private Integer reservedTables;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
}
