package dao;

import entity.ClickByLink;
import models.ReportByDay;
import utils.DateFilter;
import utils.PaginationFilter;

import java.util.Date;
import java.util.List;

public interface ClickStatisticDAO extends GeneralDAO<ClickByLink> {

    int countClickStatistic (Long buyerId, DateFilter date);

    int countClickStatistic (Long buyerId, DateFilter date, String tracker);

    ClickByLink clickStatisticByDay(Long buyerId, Date date, String tracker);

    List<ReportByDay> listClickStatisticByDate(Long buyerId, PaginationFilter dbPagination, DateFilter date);

    List<ReportByDay> listClickStatisticByDateWithTracker(Long buyerId, PaginationFilter dbPagination, DateFilter date, String tracker);

    List<ReportByDay> listClickStatisticByDateOrder(Long buyerId, PaginationFilter dbPagination, DateFilter date, String sort);

    List<ReportByDay> listClickStatisticByDateWithTrackerOrder(Long buyerId, PaginationFilter dbPagination, DateFilter date, String tracker, String sort);

}
