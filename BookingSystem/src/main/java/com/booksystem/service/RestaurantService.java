package com.booksystem.service;

import com.booksystem.dto.requestdto.RestaurantRequestDTO;
import com.booksystem.dto.responsedto.RestaurantResponseDTO;
import com.booksystem.exceptions.ApiException;
import com.booksystem.model.RestaurantEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RestaurantService {
    RestaurantEntity add(RestaurantRequestDTO dto, String email) throws ApiException;
    List<RestaurantResponseDTO> getAll(String name) throws ApiException;
    List<RestaurantResponseDTO>getByAddress(String address) throws ApiException;
    RestaurantEntity update(RestaurantRequestDTO dto) throws ApiException;
    RestaurantResponseDTO reserve(int id, int count) throws ApiException;
    List<RestaurantEntity> search(String name);
}
