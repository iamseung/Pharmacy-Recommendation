package com.example.pharmacy.pharmacy.dto;


import com.example.pharmacy.pharmacy.entity.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyDto {
    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    // Entity to Dto
    public static PharmacyDto from(Pharmacy pharmacy) {
        return new PharmacyDto(
                pharmacy.getId(),
                pharmacy.getPharmacyName(),
                pharmacy.getPharmacyAddress(),
                pharmacy.getLatitude(),
                pharmacy.getLongitude()
        );
    }
}
