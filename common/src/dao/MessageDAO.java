package dao;

import java.util.List;

import entity.Buyer;
import entity.Message;
import utils.PaginationFilter;

public interface MessageDAO extends GeneralDAO<Message> {
	
	List<Message> getChat(PaginationFilter filter, Buyer buyer);
	
	int count(Buyer buyer);
}
