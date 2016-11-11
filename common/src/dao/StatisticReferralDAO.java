package dao;

import entity.Buyer;
import entity.StatisticReferral;
import models.ReportByDay;
import utils.DateFilter;
import utils.PaginationFilter;

import java.util.Date;
import java.util.List;

public interface StatisticReferralDAO extends GeneralDAO<StatisticReferral> {

    int count (Buyer buyer, DateFilter date, String tracker);

    StatisticReferral byDay(Buyer buyer, Date date, String tracker);

    List<ReportByDay> listByDate(Buyer buyer, PaginationFilter dbPagination, DateFilter date, String tracker, String sort);

}
