package service;

import dao.ReferalDAO;
import entity.Buyer;
import entity.Sail;
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
    private StatisticReferralsService statisticService;

    @Autowired
    @Qualifier("referalDao")
    private ReferalDAO referalDao;


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

    public void setSails(List<Buyer> buyers, DateFilter date) {
        for (Buyer b : buyers) {
            b.setSails(sailService.listCompletedByDate(b.getId(), date));
        }
    }

    @Transactional
    public Referral findBySailDate(Long referId, PaginationFilter pagination, DateFilter date, String sort) {
        Referral referral = new Referral(serviceBuyer.get(referId));
        referral.setSails(sailService.listCompletedByDate(referId, pagination, date, sort));
        return referral;
    }

    @Transactional
    public List<Referral> findDailyActive(Long buyerId, PaginationFilter pagination, Date date, String tracker, String sort) {
        if (sort != null) {
            if (SortParameterParser.getColumnName(sort).equals("profit"))
                return sortByProfit(getDailyActive(buyerId, pagination, date, tracker, null), SortParameterParser.getTypeOrder(sort));
        }
        return getDailyActive(buyerId, pagination, date, tracker, sort);
    }

    @Transactional
    private List<Referral> getDailyActive(Long buyerId, PaginationFilter pagination, Date date, String tracker, String sort) {
        List<Buyer> buyers = referalDao.findActiveByDay(buyerId, pagination, date, tracker, sort);
        for (Buyer buyer : buyers) {
            List<Sail> sails = sailService.listCompletedByDay(buyer.getId(), date);
            sailService.initialize(sails);
            buyer.setSails(sails);
        }
        return convertToReferral(buyers);
    }

    @Transactional
    public int countActiveByDay(Long buyerId, Date date, String tracker) {
        return referalDao.countActiveByDay(buyerId, date, tracker);
    }

    @Transactional
    public int count(String buyerName, DateFilter dateRegistrationFilter, String tracker) {
        Buyer buyer = serviceBuyer.get(buyerName);
        return referalDao.count(buyer, dateRegistrationFilter, tracker);
    }


    @Transactional
    public List<Referral> find(String buyerName, PaginationFilter pagination, DateFilter dateRegistrationFilter, DateFilter dateStatisticFilter, String tracker, String sort) {
        if (sort != null) {
            switch (SortParameterParser.getColumnName(sort)) {
                case "profit":
                    return sortByProfit(findReferrals(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker, null), SortParameterParser.getTypeOrder(sort));
                case "countSail":
                    return sortByCountSail(findReferrals(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker, null), SortParameterParser.getTypeOrder(sort));
                default:
                    break;
            }
        }
        return findReferrals(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker, sort);
    }

    @Transactional
    private List<Referral> findReferrals(String buyerName, PaginationFilter pagination, DateFilter dateRegistrationFilter, DateFilter dateStatisticFilter, String tracker, String sort) {
        Buyer buyer = serviceBuyer.get(buyerName);
        List<Buyer> list = referalDao.findByDateRegistration(buyer.getId(), pagination, dateRegistrationFilter, tracker, sort);
        setSails(list, dateStatisticFilter);
        return convertToReferral(list);
    }

    @Transactional
    public Referral find(Long referralId) {
        return convertToReferral(referalDao.find(referralId));
    }
}
