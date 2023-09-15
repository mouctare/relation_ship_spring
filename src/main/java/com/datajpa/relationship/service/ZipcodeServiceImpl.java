package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.ZipCodeRequestDto;
import com.datajpa.relationship.model.City;
import com.datajpa.relationship.model.ZipCode;
import com.datajpa.relationship.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ZipcodeServiceImpl implements ZipcodeService{

    private final ZipCodeRepository zipCodeRepository;
    private final CityService cityService;

    @Autowired
    public ZipcodeServiceImpl(ZipCodeRepository zipCodeRepository, CityService cityService) {
        this.zipCodeRepository = zipCodeRepository;
        this.cityService = cityService;
    }

    @Transactional
    @Override
    public ZipCode addZipcode(ZipCodeRequestDto zipCodeRequestDto) {
        ZipCode zipCode = new ZipCode();
        zipCode.setName(zipCodeRequestDto.getName());
        if(zipCodeRequestDto.getCityId() == null){
            return zipCodeRepository.save(zipCode);
        }
        //On ajoute le code postal à la ville(entity)
        City city = cityService.getCity(zipCodeRequestDto.getCityId());
        zipCode.setCity(city);
        return zipCodeRepository.save(zipCode);
    }

    @Override
    public List<ZipCode> getZipcodes() {

        return StreamSupport
                .stream(zipCodeRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());

    }

    @Override
    public ZipCode getZipcode(Long zipcodeId) {
        return zipCodeRepository.findById(zipcodeId).orElseThrow(() ->
                new IllegalArgumentException("zipcode with id: " + zipcodeId + "could not be found"));
    }

    @Override
    public ZipCode deleteZipcode(Long zipcodeId) {
        ZipCode zipCode = getZipcode(zipcodeId);
         zipCodeRepository.delete(zipCode);
        return zipCode;
    }

    @Transactional
    @Override
    public ZipCode editZipcode(Long zipcodeId, ZipCodeRequestDto zipCodeRequestDto) {
        ZipCode zipcodeToEdit = getZipcode(zipcodeId);
        zipcodeToEdit.setName(zipCodeRequestDto.getName());
        if(zipCodeRequestDto.getCityId() != null){
            return zipcodeToEdit;
        }
        City city  = cityService.getCity(zipCodeRequestDto.getCityId());
        zipcodeToEdit.setCity(city);
        return zipcodeToEdit;
    }

    @Override
    public ZipCode addCityToZipCODE(Long zipcodeId, Long cityId) {
        ZipCode zipCode = getZipcode(zipcodeId);
        City city = cityService.getCity(cityId);
        if(Objects.nonNull(zipCode.getCity())) {
            throw new IllegalArgumentException("zipcode alredy has a city");
        }
        zipCode.setCity(city);
        return zipCode;
    }

    @Transactional
    @Override
    public ZipCode removeCityFromZipcode(Long zipcodeId) {
       ZipCode zipCode = getZipcode(zipcodeId);
       if(!Objects.nonNull(zipCode.getCity())) {
            throw new IllegalArgumentException("zipcode does not have  a city");
        }
        zipCode.setCity(null);
        return zipCode;
    }
}
