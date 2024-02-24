package com.example.pharmacy.pharmacy.service;

import com.example.pharmacy.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.pharmacy.pharmacy.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
약국을 조회할 때 DB 뿐만 아니라 redis 에서도 사용하기 위해
의존성을 분리하여 유연한 서비스 제공하고자 서비스 로직 추가
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {
    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    /*
    ⭐️ Redis 에서 조회 시 에러가 발생하면 DB에서 조회하도록!
     */
    public List<PharmacyDto> searchPharmacyDtoList() {
        // redis
        List<PharmacyDto> pharmacyDtoList = pharmacyRedisTemplateService.findAll();
        if(!CollectionUtils.isEmpty(pharmacyDtoList)) return pharmacyDtoList;

        // db
        return pharmacyRepositoryService.findAll()
                .stream()
                .map(PharmacyDto::from)
                .collect(Collectors.toList());
    }
}
