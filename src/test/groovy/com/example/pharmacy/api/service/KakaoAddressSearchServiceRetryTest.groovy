package com.example.pharmacy.api.service

import com.example.pharmacy.AbstractIntegrationContainerBaseTest
import com.example.pharmacy.api.dto.KakaoApiResponseDto
import com.example.pharmacy.api.dto.MetaDto
import com.example.pharmacy.api.dto.DocumentDto
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.HttpHeaders
import spock.lang.Specification

class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    /*
    ⭐️ jUnit 은 @MockBean, Spring Container 내에 있는 빈을 모킹
    이는 kakaoUriBuilderService 내에 URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);
    이 부분이 실제 카카오 api 를 호출하기 위한 url 이다.
    그렇기에 KakaoUriBuilderService 의 buildUriByAddressSearch 가 반환하는 uri 를 목 서버로 return 하기 위해 사용.
     */
    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper mapper = new ObjectMapper()

    private String inputAddress = "서울 성북구 종암로 10길"

    // 카카오 api 를 통신하지 않고 MockWebServer 를 통한 테스트 설정
    def setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()

        // Debug
        println mockWebServer.port
        println mockWebServer.url("/")

    }

    def cleanup() {
        mockWebServer.shutdown()
    }

    def "requestAddressSearch retry success"() {
        given:
        def metaDto = new MetaDto(1)
        def documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build()
        def expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto))
        def uri = mockWebServer.url("/").uri()

        when:
        // 응답 값 조작
        // 1. 비정상 호출
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        // 2. 정상 호출
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(expectedResponse)))

        def kakaoApiResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)
        def takeRequest = mockWebServer.takeRequest()

        then:
        // 두번의 호출 결과
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        takeRequest.getMethod() == "GET"
        kakaoApiResult.getDocumentList().size() == 1
        kakaoApiResult.getMetaDto().totalCount == 1
        kakaoApiResult.getDocumentList().get(0).getAddressName() == inputAddress
    }


    def "requestAddressSearch retry fail "() {
        given:
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        result == null
    }
}
