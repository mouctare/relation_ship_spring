package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.ZipCodeRequestDto;
import com.datajpa.relationship.model.ZipCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZipcodeService {
     ZipCode addZipcode(ZipCodeRequestDto zipCodeRequestDto);
     List<ZipCode> getZipcodes();
     ZipCode getZipcode(Long zipcodeId);
    ZipCode deleteZipcode(Long zipcodeId);
    ZipCode editZipcode(Long zipcodeId, ZipCodeRequestDto zipCodeRequestDto);
   ZipCode addCityToZipCODE(Long zipcodeId, Long cityId);
    ZipCode removeCityFromZipcode(Long zipcodeId);

}
