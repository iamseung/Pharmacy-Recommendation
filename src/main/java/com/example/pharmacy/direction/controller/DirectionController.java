package com.example.pharmacy.direction.controller;

import com.example.pharmacy.direction.entity.Direction;
import com.example.pharmacy.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    // 길안내 URL
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId) {
        // encodedId 를 decode 해서 PK 값으로 엔티티 조회
        Direction resultDirection = directionService.findById(encodedId);

        String params = String.join(",",
                resultDirection.getTargetPharmacyName(),
                String.valueOf(resultDirection.getTargetLatitude()),
                String.valueOf(resultDirection.getTargetLongitude())
        );

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params)
                .toUriString();

        log.info("direction params : {}, url: {}", params, result);

        return "redirect:"+result;
    }
}
