package utils;

import entity.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import service.BuyerService;

public class CurrentUser {
    public static String getName() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
