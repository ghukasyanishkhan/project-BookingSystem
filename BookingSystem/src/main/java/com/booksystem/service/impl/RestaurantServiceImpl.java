package com.booksystem.service.impl;

import com.booksystem.dto.requestdto.RestaurantRequestDTO;
import com.booksystem.dto.responsedto.RestaurantResponseDTO;
import com.booksystem.enums.Role;
import com.booksystem.exceptions.ApiException;
import com.booksystem.exceptions.restaurantexceptions.RestaurantNotFoundException;
import com.booksystem.exceptions.restaurantexceptions.RestaurantValidationException;
import com.booksystem.exceptions.userexceptions.UserValidationException;
import com.booksystem.model.RestaurantEntity;
import com.booksystem.model.UserEntity;
import com.booksystem.repository.RestaurantRepository;
import com.booksystem.repository.UserRepository;
import com.booksystem.service.RestaurantService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public RestaurantEntity add(RestaurantRequestDTO dto, String email) throws ApiException {
        List<UserEntity> userEntityList = userRepository.getByEmail(email);
        UserEntity userEntity = userEntityList.get(0);
        if (!userEntity.getRole().equals(Role.CUSTOMER)) {
            throw new UserValidationException("You have no permission to add restaurant");
        }
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(0);
        restaurantEntity.setName(dto.getName());
        restaurantEntity.setAddress(dto.getAddress());
        restaurantEntity.setPhone(dto.getPhone());
        restaurantEntity.setTables(dto.getTables());
        restaurantEntity.setUserEntity(userEntity);
        try {
            restaurantRepository.save(restaurantEntity);
        } catch (Exception e) {
            throw new ApiException("problem during adding restaurant");
        }
        return restaurantEntity;
    }

    @Override
    public List<RestaurantResponseDTO> getAll(String name) throws ApiException {
        List<RestaurantEntity> entityList = null;
        try {
            if (name == null) {
                entityList = restaurantRepository.getAllBy();
            } else {
                entityList = restaurantRepository.getByName(name);
            }
        } catch (Exception e) {
            throw new ApiException("problem during getting restaurant");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<RestaurantResponseDTO> restaurantResponseDTOS = objectMapper
                .convertValue(entityList, new TypeReference<List<RestaurantResponseDTO>>() {

                });
        return restaurantResponseDTOS;
    }

    @Override
    public List<RestaurantResponseDTO> getByAddress(String address) throws ApiException {
        List<RestaurantEntity> restaurantEntityList = null;
        try {
            restaurantEntityList = restaurantRepository.getByAddress(address);
        } catch (Exception e) {
            throw new ApiException("problem during getting restaurant");
        }
        if (restaurantEntityList.isEmpty()) {
            throw new RestaurantNotFoundException("Wrong address");
        }
        return new ObjectMapper()
                .convertValue(restaurantEntityList, new TypeReference<List<RestaurantResponseDTO>>() {
                });
    }

    @Override
    public RestaurantEntity update(RestaurantRequestDTO dto) throws ApiException {
        RestaurantEntity restaurantEntity = null;
        try {
            restaurantEntity = restaurantRepository.getById(dto.getId());
        } catch (Exception e) {
            throw new ApiException("problem during updating restaurant");
        }
        restaurantEntity.setName(dto.getName() == null ? restaurantEntity.getName() : dto.getName());
        restaurantEntity.setAddress(dto.getAddress() == null ? restaurantEntity.getAddress() : dto.getAddress());
        restaurantEntity.setPhone(dto.getPhone() == null ? restaurantEntity.getPhone():dto.getPhone());
        restaurantEntity.setTables(dto.getTables()==null? restaurantEntity.getTables(): dto.getTables());
       try {
           restaurantRepository.save(restaurantEntity);
       }catch (Exception e){
           throw new ApiException("problem during updating restaurant");
       }
       return restaurantEntity;
    }

    @Override
    public RestaurantResponseDTO reserve(int id, int count) throws ApiException {
        RestaurantEntity restaurantEntity = null;
        try {
            restaurantEntity = restaurantRepository.getById(id);
            restaurantEntity.setReservedTables(restaurantEntity.getReservedTables() == null ? 0 : restaurantEntity.getReservedTables());
            if (restaurantEntity.getTables() - restaurantEntity.getReservedTables() < count) {
                throw new RestaurantValidationException("not free tables");
            }
            restaurantEntity.setReservedTables(restaurantEntity.getReservedTables() + count);
            restaurantRepository.save(restaurantEntity);
        } catch (RestaurantValidationException e) {
            throw new ApiException("not free tables");
        } catch (Exception e) {
            throw new ApiException("problem during reserving table");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(restaurantEntity, RestaurantResponseDTO.class);
    }

    @Override
    public List<RestaurantEntity> search(String name) {
       return restaurantRepository.search(name);
    }
}
