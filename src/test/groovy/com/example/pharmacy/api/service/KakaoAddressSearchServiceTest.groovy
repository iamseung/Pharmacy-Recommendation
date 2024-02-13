package com.example.pharmacy.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoAddressSearchServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService;

    // 메서드 시작 전, 실행 메서드
    def setup() {
        // 인스턴스화
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    def "buildUriByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩이 되는지 확인한다."() {
        given:
        String address = "서울 마포구";
        def charset =StandardCharsets.UTF_8;
        when:
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)
        def decodeResult = URLDecoder.decode(uri.toString(), charset)

        then:
        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 마포구"
//        println(uri);
    }
}
