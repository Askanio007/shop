package dao;

import entity.Buyer;
import utils.DateFilter;
import utils.PaginationFilter;

import java.util.Date;
import java.util.List;

public interface ReferralDAO extends GeneralDAO<Buyer> {

    int count(Buyer buyer, DateFilter date, String tracker);

    Buyer find(Long referId, PaginationFilter pagination, DateFilter date, String sort);

    int countActiveByDay (Long buyerId, Date date, String tracker);

    List<Buyer> findActiveByDay(Long buyerId, PaginationFilter pagination, Date day, String tracker, String sort);

    List<Buyer> findByDateRegistration(Buyer buyer, PaginationFilter pagination, DateFilter date, DateFilter sailDate, String tracker, String sort);

}
