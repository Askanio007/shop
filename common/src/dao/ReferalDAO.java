package dao;

import entity.Buyer;
import utils.DateFilter;
import utils.PaginationFilter;

import java.util.Date;
import java.util.List;

public interface ReferalDAO extends GeneralDAO<Buyer> {

    int count(Buyer buyer, DateFilter date, String tracker);

    int countActiveByDay (Long buyerId, Date date, String tracker);

    List<Buyer> getById(Long buyerId);

    List<Buyer> getActiveByBuyer(Buyer buyer, Date date);

    List<Buyer> findActiveByDay(Long buyerId, PaginationFilter pagination, Date day, String tracker, String sort);

    List<Buyer> findByDateRegistration(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker, String sort);

}
