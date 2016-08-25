package dao;

import entity.Buyer;
import utils.DateFilter;
import utils.PaginationFilter;

import java.util.Date;
import java.util.List;

public interface ReferalDAO extends GeneralDAO<Buyer> {

    int countReferrals(Buyer buyer, DateFilter date);

    int countReferrals(Buyer buyer, DateFilter date, String tracker);

    int countActiveReferralByDay (Long buyerId, Date date);

    int countActiveReferralByDay (Long buyerId, Date date, String tracker);

    List<Buyer> getReferalsById(Long buyerId);

    List<Buyer> findActiveByDay(Long buyerId, PaginationFilter pagination, Date day);

    List<Buyer> findActiveByDayOrder(Long buyerId, PaginationFilter pagination, Date day, String sort);

    List<Buyer> findActiveByDayWithTracker(Long buyerId, PaginationFilter pagination, Date day, String tracker);

    List<Buyer> findActiveByDayWithTrackerOrder(Long buyerId, PaginationFilter pagination, Date day, String tracker, String sort);

    List<Buyer> findByDateRegistration(Long buyerId, PaginationFilter pagination, DateFilter date);

    List<Buyer> findByDateRegistrationWithTracker(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker);

    List<Buyer> findByDateRegistrationOrder(Long buyerId, PaginationFilter pagination, DateFilter date, String sort);

    List<Buyer> findByDateRegistrationWithTrackerOrder(Long buyerId, PaginationFilter pagination, DateFilter date, String tracker, String sort);







}
