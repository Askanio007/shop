package dao;

import java.util.Date;
import java.util.List;

import entity.Buyer;
import entity.Sail;
import utils.DateFilter;
import utils.PaginationFilter;

public interface SailDaoInterface extends GeneralDAO<Sail> {

	int countByReferral(Long referId, DateFilter dateSail);

	int countByBuyer(Long buyerid);

	List<Sail> getByBuyer(PaginationFilter dbFilter, Long buyerid);
	
	List<Sail> getByBuyer(Long buyerid);

	List<Sail> getOverDue(Long time);

	Double profitCompletedByDay(Long buyerId, Date date);

	List<Sail> completedByDay(Long buyerId, Date date);

	List<Sail> completedByDate(Long buyerId, PaginationFilter dbFilter, DateFilter dateSail, String sort);

}