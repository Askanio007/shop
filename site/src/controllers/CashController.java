package controllers;

import entity.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.BuyerService;
import utils.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class CashController {

    @Autowired
    private BuyerService serviceBuyer;

    @RequestMapping(value = "/user/deposit", method = RequestMethod.GET)
    public String deposit(Model model, HttpServletRequest request) {
        BuyController.setCountProductBasketInModel(request, model);
        return "user/deposit";
    }

    @RequestMapping(value = "/user/deposit", method = RequestMethod.POST)
    public String deposit(@RequestParam("sum") Integer sum) {
        Buyer b = serviceBuyer.get(CurrentUser.getName());
        b.setBalance(b.getBalance().add(BigDecimal.valueOf(sum.doubleValue())));
        serviceBuyer.edit(b);
        return "redirect:/user/profile";
    }
}
