package com.booksystem.controller;

import com.booksystem.dto.requestdto.RestaurantRequestDTO;
import com.booksystem.dto.responsedto.RestaurantResponseDTO;
import com.booksystem.exceptions.ApiException;
import com.booksystem.model.RestaurantEntity;
import com.booksystem.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/add-restaurant")
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantEntity addRestaurant(@RequestBody RestaurantRequestDTO dto, Principal principal) throws ApiException {
        String email = principal.getName();
        return restaurantService.add(dto, email);
    }

    @GetMapping("/get-all")
    public List<RestaurantResponseDTO> getAll(@RequestParam(required = false) String name) throws ApiException {
        return restaurantService.getAll(name);
    }

    @GetMapping("/get-by-address")
    public List<RestaurantResponseDTO> getByAddress(@RequestParam String address) throws ApiException {
        return restaurantService.getByAddress(address);
    }

    @PutMapping("/update")
    public RestaurantEntity update(@RequestBody RestaurantRequestDTO dto) throws ApiException {
        return restaurantService.update(dto);
    }

    @PatchMapping("/reserve")
    public RestaurantResponseDTO reserve(@RequestParam int id, @RequestParam int count) throws ApiException {
        return restaurantService.reserve(id, count);
    }
    @GetMapping("/search")
    public List<RestaurantEntity> search(@RequestParam String name){
     return   restaurantService.search(name);
    }
}
