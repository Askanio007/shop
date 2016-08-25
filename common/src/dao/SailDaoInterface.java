package dao;

import java.util.Date;
import java.util.List;

import entity.Buyer;
import entity.Sail;
import utils.DateFilter;
import utils.PaginationFilter;

public interface SailDaoInterface extends GeneralDAO<Sail> {

	int countSailsByReferral(Long referId, DateFilter dateSail);

	int countSailsByBuyer(Long buyerid);

	List<Sail> getAllSailByBuyer(PaginationFilter dbFilter, Long buyerid);
	
	List<Sail> getAllSailByBuyer(Long buyerid);

	List<Sail> getOverDueSail(Long time);

	List<Sail> completeSailByDay(Long buyerId, Date date);

	List<Sail> completeSailByDate(Long buyerId, DateFilter dateSail);

	List<Sail> completeSailByDate(Long buyerId, PaginationFilter dbFilter, DateFilter dateSail);
	
	List<Sail> completeSailByDateOrder(Long buyerId, PaginationFilter dbFilter, DateFilter dateSail, String sort);

}