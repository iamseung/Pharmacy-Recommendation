package com.example.pharmacy.direction.entity;

import com.example.pharmacy.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "direction") // 클래스의 모든 필드를 매개변수로 받는 전체 생성자(Full Constructor)를 자동으로 생성
@AllArgsConstructor // 매개변수가 없는 기본 생성자(Default Constructor)를 자동으로 생성
@NoArgsConstructor
@Builder
@Getter
public class Direction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    // 약국
    private String targetPharmacyName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 고객과 약국간의 거리
    private double distance;
}