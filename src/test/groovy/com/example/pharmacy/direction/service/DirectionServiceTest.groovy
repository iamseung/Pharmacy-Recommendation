package com.example.pharmacy.direction.service

import com.example.pharmacy.api.dto.DocumentDto
import com.example.pharmacy.pharmacy.dto.PharmacyDto
import com.example.pharmacy.pharmacy.repository.PharmacySearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {
    // @Mock
    private PharmacySearchService pharmacySearchService = Mock();

    // @InjectMocks, 테스트 하려는 주체 & 주입 대상
    private DirectionService directionService = new DirectionService(pharmacySearchService)

    private List<PharmacyDto> pharmacyList

    def setup() {
        pharmacyList = new ArrayList<>();

        pharmacyList.addAll(
                PharmacyDto.builder()
                        .id(1L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                PharmacyDto.builder()
                        .id(2L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build()
        )
    }

    def "buildDirectionList - 결과 값이 거리 순으로 정렬 되는지 확인"() {
        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
            .addressName(addressName)
            .latitude(inputLatitude)
            .longitude(inputLongitude)
            .build()

        when:
        // stubbing : 왼쪽에 있는 메서드 호출에 대한 반환 값을 오른쪽에 지정된 값으로 대체하라는 의미
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList

        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).targetPharmacyName == "호수온누리약국"
        result.get(1).targetPharmacyName == "돌곶이온누리약국"
    }

    def "buildDirectionList -  정해진 반경 10km 내에 검색이 되는지 확인"() {
        given:
        pharmacyList.add(
                PharmacyDto.builder()
                        .id(3L)
                        .pharmacyName("경기약국")
                        .pharmacyAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())

        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList

        def results = directionService.buildDirectionList(documentDto)
        then:

        results.size() == 2
        results.get(0).targetPharmacyName == "호수온누리약국"
        results.get(1).targetPharmacyName == "돌곶이온누리약국"
    }
}