package com.example.pharmacy.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetaDto {
    // Json 으로 응답을 받을 때 현재 매핑된 필드와 매핑
    @JsonProperty("total_count")
    private Integer totalCount;
}
