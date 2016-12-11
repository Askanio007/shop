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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Stream<Referral> referral = referrals.stream();
        return "desc".equals(typeSort) ?
                referral.sorted((refer1, refer2) -> refer2.getProfit().compareTo(refer1.getProfit())).collect(Collectors.toList()) :
                referral.sorted((refer1, refer2) -> refer1.getProfit().compareTo(refer2.getProfit())).collect(Collectors.toList());

        // TODO: Kirill и восьмая джава должна тут подкорректировать что-нибудь ::: переделал со стримами. В классе ReportService немного по-другому выглядит, хз как лучше.
        // TODO: Kirill может сочтешь это более читабельным, я например так считаю.
        // Проверки typeSort на null нет, безопаснее в данном случае сравнить со строкой заведомо, которая заведомо not null
        // return "desc".equals(typeSort) ?
        // TODO: Artyom насчёт сравнивания строки с элементом это да, я обратил на это внимание ещё когда сортировку делал. Тут просто ни разу не падало, вот
        // я и не изменил)) Поправил и в других местах. Насчёт того что, это читабельнее - согласен

        // TODO: Artyom стал кое-где использовать стримы
    }

    public List<Referral> sortByCountSail(List<Referral> referrals, final String typeSort) {

        Stream<Referral> referral = referrals.stream();
        return "desc".equals(typeSort) ?
                referral.sorted((refer1, refer2) -> Long.compare(refer2.getCountSails(), refer1.getCountSails())).collect(Collectors.toList()) :
                referral.sorted((refer1, refer2) -> Long.compare(refer1.getCountSails(), refer2.getCountSails())).collect(Collectors.toList());
    }

    public List<Referral> convertToReferral(List<Buyer> buyers) {
        List<Referral> refers = new ArrayList<>();
        buyers.stream().forEach((buyer) -> refers.add(convertToReferral(buyer)) );
        return refers;
    }

    public Referral convertToReferral(Buyer buyer) {
        return new Referral(buyer, sailService.profitFromSail(buyer.getSails()));
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
        buyers.stream().forEach((buyer) -> sailService.initializeProducts(buyer.getSails()));
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
