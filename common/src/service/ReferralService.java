package service;

import dao.ReferralDAO;
import entity.Buyer;
import models.Referral;
import utils.DateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.PaginationFilter;
import utils.SortParameterParser;

import java.util.*;

@Service
public class ReferralService {

    @Autowired
    private BuyerService serviceBuyer;

    @Autowired
    private SailService sailService;

    @Autowired
    @Qualifier("referralDao")
    private ReferralDAO referralDao;


    public List<Referral> sortByProfit(List<Referral> referrals, final String typeSort) {
        Collections.sort(referrals, new Comparator<Referral>() {
            @Override
            public int compare(Referral ref1, Referral ref2) {
                if (typeSort.equals("desc")) return ref2.getProfit().compareTo(ref1.getProfit());
                return ref1.getProfit().compareTo(ref2.getProfit());
            }
        });
        return referrals;
    }

    public List<Referral> sortByCountSail(List<Referral> referrals, final String typeSort) {
        Collections.sort(referrals, new Comparator<Referral>() {
            @Override
            public int compare(Referral ref1, Referral ref2) {
                if (ref2.getCountSails() == ref1.getCountSails()) return 0;
                if (typeSort.equals("desc")) return ref2.getCountSails() > ref1.getCountSails() ? 1 : -1;
                return ref1.getCountSails() > ref2.getCountSails() ? 1 : -1;
            }

        });
        return referrals;
    }

    public List<Referral> convertToReferral(List<Buyer> buyers) {
        List<Referral> refers = new ArrayList<>();
        for (Buyer b : buyers) {
            refers.add(convertToReferral(b));
        }
        return refers;
    }

    public Referral convertToReferral(Buyer buyer) {
        return new Referral(buyer, sailService.getProfit(buyer.getSails()));
    }

    @Transactional
    public List<Referral> findDailyActive(Long buyerId, PaginationFilter pagination, Date date, String tracker, String sort) {
        if ("profit".equals(SortParameterParser.getColumnName(sort)))
                return sortByProfit(getDailyActive(buyerId, pagination, date, tracker, null), SortParameterParser.getTypeOrder(sort));
        return getDailyActive(buyerId, pagination, date, tracker, sort);
    }

    @Transactional
    private List<Referral> getDailyActive(Long buyerId, PaginationFilter pagination, Date date, String tracker, String sort) {
        List<Buyer> buyers = referralDao.findActiveByDay(buyerId, pagination, date, tracker, sort);
        return convertToReferral(buyers);
    }

    @Transactional
    public int countActiveByDay(Long buyerId, Date date, String tracker) {
        return referralDao.countActiveByDay(buyerId, date, tracker);
    }

    @Transactional
    public int count(String buyerName, DateFilter dateRegistrationFilter, String tracker) {
        return referralDao.count(serviceBuyer.get(buyerName), dateRegistrationFilter, tracker);
    }

    //TODO Artyom: Может быть создать объект, который будет включать в себя все эти параметры для поиска и в дальнейшем передавать этот объект а не кучу параметров?
    //Да не, бред какой-то.
    @Transactional
    public List<Referral> find(String buyerName, PaginationFilter pagination, DateFilter dateRegistrationFilter, DateFilter dateStatisticFilter, String tracker, String sort) {
        if ("profit".equals(SortParameterParser.getColumnName(sort)))
            return sortByProfit(findReferrals(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker, null), SortParameterParser.getTypeOrder(sort));
        if ("countSail".equals(SortParameterParser.getColumnName(sort)))
            return sortByCountSail(findReferrals(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker, null), SortParameterParser.getTypeOrder(sort));
        return findReferrals(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker, sort);
    }

    @Transactional
    private List<Referral> findReferrals(String buyerName, PaginationFilter pagination, DateFilter dateRegistrationFilter, DateFilter dateStatisticFilter, String tracker, String sort) {
        List<Buyer> list = referralDao.findByDateRegistration(serviceBuyer.get(buyerName), pagination, dateRegistrationFilter, dateStatisticFilter, tracker, sort);
        return convertToReferral(list);
    }

    @Transactional
    public Referral find(Long referralId) {
        return convertToReferral(referralDao.find(referralId));
    }

    @Transactional
    public Referral findBySailDate(Long referId, PaginationFilter pagination, DateFilter sailDate, String sort) {
        return convertToReferral(referralDao.find(referId, pagination, sailDate, sort));
    }
}
