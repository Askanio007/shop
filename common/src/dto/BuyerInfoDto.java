package dto;

import entity.BuyerInfo;

public class BuyerInfoDto {

    private Long id;

    private String secondName;

    private Integer age;

    private String phone;

    public BuyerInfoDto() {}

    public Long getId() {
        return id;
    }

    public String getSecondName() {
        return secondName;
    }

    public Integer getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
