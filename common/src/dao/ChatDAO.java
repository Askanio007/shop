package dao;

import java.util.List;

import entity.Buyer;
import entity.Chat;
import utils.PaginationFilter;

public interface ChatDAO extends GeneralDAO<Chat> {
	
	List<Chat> getChat(PaginationFilter filter, Buyer buyer);
	
	int count(Buyer buyer);
}
