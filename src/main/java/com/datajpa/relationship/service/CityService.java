package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.CityRequestDto;
import com.datajpa.relationship.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
     City addCity(CityRequestDto cityRequestDto);
     List<City> getCities();
     City getCity(Long cityId);
     City deleteCity(Long cityId);
     City editCity(Long cityId, CityRequestDto cityRequestDto);
}
