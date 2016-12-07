package dao;

import java.util.List;

import dto.BuyerDto;
import entity.Buyer;
import entity.Message;
import utils.PaginationFilter;

public interface MessageDAO extends GeneralDAO<Message> {
	
	List<Message> getChat(PaginationFilter filter, BuyerDto buyer);
	
	int count(BuyerDto buyer);
}
