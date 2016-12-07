package dto;

import entity.Buyer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuyerDto {

    private Long id;

    private String name;

    private String secondName;

    private String password;

    private boolean enable;

    private Date dateReg;

    private Long refId;

    private String refCode;

    private String tracker;

    private BigDecimal balance;

    private Integer percentCashback;

    private Integer countSails;

    private String phone;

    private String ava;

    private Integer age;

    private BuyerDto() {}

    public BuyerDto(Buyer buyer) {
        this.id = buyer.getId();
        this.name = buyer.getName();
        this.password = buyer.getPassword();
        this.enable = buyer.getEnable();
        this.dateReg = buyer.getDateReg();
        this.refId = buyer.getRefId();
        this.balance = buyer.getBalance();
        this.refCode = buyer.getRefCode();
        this.tracker = buyer.getTracker();
        this.percentCashback = buyer.getPercentCashback();
        this.countSails = buyer.getSails().size();
        this.secondName = buyer.getInfo().getSecondName();
        this.phone = buyer.getInfo().getPhone();
        this.age = buyer.getInfo().getAge();
        this.ava = buyer.getInfo().getAva();
    }

    public static BuyerDto convertToDTO(Buyer buyer) {
        return new BuyerDto(buyer);
    }

    public static List<BuyerDto> convertToDTO(List<Buyer> buyers) {
        List<BuyerDto> dtos = new ArrayList<>();
        for (Buyer buyer : buyers) {
            dtos.add(convertToDTO(buyer));
        }
        return dtos;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean getEnable() {
        return enable;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public Long getRefId() {
        return refId;
    }

    public String getRefCode() {
        return refCode;
    }

    public String getTracker() {
        return tracker;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getPercentCashback() {
        return percentCashback;
    }

    public String getSecondName() {
        return secondName;
    }

    public Integer getCountSails() {
        return countSails;
    }

    public String getPhone() {
        return phone;
    }

    public String getAva() {
        return ava;
    }

    public Integer getAge() {
        return age;
    }

    public Buyer convertToEntity() {
        return new Buyer.Builder(this.name, this.password, this.refCode, this.dateReg)
                .balance(this.balance)
                .enable(this.enable)
                .percentCashback(this.percentCashback)
                .tracker(this.tracker)
                .info(this.secondName, this.phone, this.age)
                .refId(this.refId).build();
    }

    public Buyer transferDataToEntity(Buyer buyer) {
        buyer.getInfo().setAge(this.age);
        buyer.getInfo().setPhone(this.phone);
        buyer.getInfo().setSecondName(this.secondName);
        buyer.setPercentCashback(this.percentCashback);
        buyer.setBalance(this.balance);
        return buyer;
    }
}
