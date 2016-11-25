package dto;

import entity.BuyerInfo;

public class BuyerInfoDTO {

    private Long id;

    private String secondName;

    private String ava;

    private Integer age;

    private String phone;

    private BuyerDTO buyer;

    private BuyerInfoDTO(BuyerInfo info) {


    }
}
