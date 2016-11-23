package dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class SailDTO {

    private Long id;

    private Date date;

    private Date dateChangeState;

    private Integer amount;

    private BigDecimal totalsum;

    private String state;

    private Integer cashbackPercent;

    private Collection<SoldProductDTO> products;

    private BuyerDTO buyer;

    private SailDTO() {}

}
