package dto;

import entity.BuyerInfo;

public class BuyerInfoDto {

    private Long id;

    private String secondName;

    private Integer age;

    private String phone;

    public BuyerInfoDto() {}

    private BuyerInfoDto(BuyerInfo info) {
        this.id = info.getId();
        this.age = info.getAge();
        this.secondName = info.getSecondName();
        this.phone = info.getPhone();
    }

    public BuyerInfo transferDataToEntity(BuyerInfo info) {
        info.setSecondName(this.secondName);
        info.setAge(this.age);
        return info;
    }

    public static BuyerInfoDto converToDto(BuyerInfo entity) {
        return new BuyerInfoDto(entity);
    }

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
