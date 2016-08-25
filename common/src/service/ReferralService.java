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
    private ClickStatisticService clickService;

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
            b.setSails(sailService.listCompleteSailByDate(b.getId(), date));
        }
    }

    @Transactional
    public Double calculateProfitByDay(Buyer buyer, Date date) {
        List<Buyer> referals = referalDao.getReferalsById(buyer.getId());
        for (Buyer ref : referals) {
            ref.setSails(sailService.listCompleteSailByDay(ref.getId(), date));
        }
        Double profit = 0.0;
        for (Buyer ref : referals) {
            profit += sailService.getProfit(ref.getSails());
        }
        return profit;
    }

    @Transactional
    public Referral findBySailDate(Long referId, PaginationFilter pagination, DateFilter date) {
        Referral referral = new Referral(serviceBuyer.getBuyer(referId));
        referral.setSails(sailService.listCompleteSailByDate(referId, pagination, date));
        return referral;
    }


    @Transactional
    public Referral findBySailDate(Long referId, PaginationFilter pagination, DateFilter date, String sort) {
        Referral referral = new Referral(serviceBuyer.getBuyer(referId));
        referral.setSails(sailService.listCompleteSailByDateOrder(referId, pagination, date, sort));
        return referral;
    }

    @Transactional
    public List<Referral> findDailyActive(Long buyerId, PaginationFilter pagination, Date date, String tracker, String sort) {
        if ("".equals(sort) || sort == null)
            return findDailyActive(buyerId, pagination, date, tracker);

        if (SortParameterParser.getColumnName(sort).equals("profit"))
            return sortByProfit(findDailyActive(buyerId, pagination, date, tracker), SortParameterParser.getTypeOrder(sort));

        return findDailyActiveWithOrder(buyerId, pagination, date, tracker, sort);
    }

    @Transactional
    public List<Referral> findDailyActive(Long buyerId, PaginationFilter pagination, Date date, String tracker) {
        List<Buyer> buyers;
        if ("".equals(tracker) || tracker == null)
            buyers = referalDao.findActiveByDay(buyerId, pagination, date);
        else
            buyers = referalDao.findActiveByDayWithTracker(buyerId, pagination, date, tracker);
        for (Buyer buyer : buyers) {
            List<Sail> sails = sailService.listCompleteSailByDay(buyer.getId(), date);
            sailService.initialize(sails);
            buyer.setSails(sails);
        }
        return convertToReferral(buyers);
    }

    @Transactional
    public int countActiveReferralByDay(Long buyerId, Date date, String tracker) {
        if ("".equals(tracker) || tracker == null)
            return referalDao.countActiveReferralByDay(buyerId, date);
        return referalDao.countActiveReferralByDay(buyerId, date, tracker);
    }

    public List<Referral> findDailyActiveWithOrder(Long buyerId, PaginationFilter pagination, Date date, String tracker, String sort) {
        List<Buyer> buyers;
        if ("".equals(tracker) || tracker == null)
            buyers = referalDao.findActiveByDayOrder(buyerId, pagination, date, sort);
        else
            buyers = referalDao.findActiveByDayWithTrackerOrder(buyerId, pagination, date, tracker, sort);
        for (Buyer buyer : buyers) {
            List<Sail> sails = sailService.listCompleteSailByDay(buyer.getId(), date);
            sailService.initialize(sails);
            buyer.setSails(sails);
        }
        return convertToReferral(buyers);
    }

    @Transactional
    public int countReferrals(String buyerName, DateFilter dateRegistrationFilter, String tracker) {
        Buyer buyer = serviceBuyer.getBuyer(buyerName);
        if ("".equals(tracker) || tracker == null)
            return referalDao.countReferrals(buyer, dateRegistrationFilter);
        return referalDao.countReferrals(buyer, dateRegistrationFilter, tracker);
    }


    @Transactional
    public List<Referral> find(String buyerName, PaginationFilter pagination, DateFilter dateRegistrationFilter, DateFilter dateStatisticFilter, String tracker, String sort) {
        if ("".equals(sort) || sort == null)
            return find(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker);

        if (SortParameterParser.getColumnName(sort).equals("profit")) {
            return sortByProfit(find(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker), SortParameterParser.getTypeOrder(sort));
        }

        if (SortParameterParser.getColumnName(sort).equals("countSail")) {
            return sortByCountSail(find(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker), SortParameterParser.getTypeOrder(sort));
        }

        return findWithOrder(buyerName, pagination, dateRegistrationFilter, dateStatisticFilter, tracker, sort);
    }

    @Transactional
    public List<Referral> find(String buyerName, PaginationFilter pagination, DateFilter dateRegistrationFilter, DateFilter dateStatisticFilter, String tracker) {
        Buyer buyer = serviceBuyer.getBuyer(buyerName);
        List<Buyer> list;
        if ("".equals(tracker) || tracker == null)
            list = referalDao.findByDateRegistration(buyer.getId(), pagination, dateRegistrationFilter);
        else
            list = referalDao.findByDateRegistrationWithTracker(buyer.getId(), pagination, dateRegistrationFilter, tracker);
        setSails(list, dateStatisticFilter);
        return convertToReferral(list);
    }

    @Transactional
    public List<Referral> findWithOrder(String buyerName, PaginationFilter pagination, DateFilter dateRegistrationFilter, DateFilter dateStatisticFilter, String tracker, String sort) {
        Buyer buyer = serviceBuyer.getBuyer(buyerName);
        List<Buyer> list;
        if ("".equals(tracker) || tracker == null)
            list = referalDao.findByDateRegistrationOrder(buyer.getId(), pagination, dateRegistrationFilter, sort);
        else
            list = referalDao.findByDateRegistrationWithTrackerOrder(buyer.getId(), pagination, dateRegistrationFilter, tracker, sort);
        setSails(list, dateStatisticFilter);
        return convertToReferral(list);

    }

    @Transactional
    public Referral find(Long referralId) {
        return convertToReferral(referalDao.find(referralId));
    }


    @Transactional
    public void generateReferral() {
        String[] trackers = {"site", null, null, "World of Warcraft", "dota 2", null};

        Random r = new Random();
        List<Buyer> buyers = serviceBuyer.listBuyer();
        Buyer b = buyers.get(0);
        for (int i = 0; i < 50; i++) {
            String track = trackers[r.nextInt(5)];
            for (int j = 0; j < r.nextInt(10); j++) {
                Calendar c = new GregorianCalendar(2015, r.nextInt(11), r.nextInt(28));
                clickService.setClickStatistic(b.getRefCode(), c.getTime() , track);
            }

            Calendar dateRegByClick = new GregorianCalendar(2015, 0, r.nextInt(28));
            clickService.setClickStatistic(b.getRefCode(), dateRegByClick.getTime() , track);
            serviceBuyer.regGenerateUser("ReferralBuyerClick #" + i, "password", b.getRefCode(), track, dateRegByClick.getTime());

            Calendar dateRegByCode = new GregorianCalendar(2015, 0, r.nextInt(28));
            clickService.setEnterCode(b.getRefCode(),dateRegByCode.getTime());
            serviceBuyer.regGenerateUser("ReferralBuyerCode #" + i, "password", b.getRefCode(), null, dateRegByCode.getTime());
        }
    }
}
