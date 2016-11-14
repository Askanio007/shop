package service;

import entity.Buyer;
import entity.Product;
import entity.Sail;
import models.Basket;
import models.ProductBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.EncryptionString;
import utils.PaginationFilter;
import utils.StateSail;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GeneratorStatistic {

    @Autowired
    private BuyerService serviceBuyer;

    @Autowired
    private ProductService serviceProduct;

    @Autowired
    private SailService sailService;

    @Autowired
    private SettingsService settings;

    @Autowired
    private StatisticReferralsService statisticService;

    @Autowired
    private SoldProductService soldProductService;


    @Transactional
    public void generateReferral() {
        String[] trackers = {"site", null, null, "World of Warcraft", "dota 2", null};

        Random r = new Random();
        List<Buyer> buyers = serviceBuyer.list();
        Buyer b = buyers.get(0);
        for (int i = 0; i < 50; i++) {
            String track = trackers[r.nextInt(5)];
            for (int j = 0; j < r.nextInt(10); j++) {
                Calendar c = new GregorianCalendar(2015, r.nextInt(11), r.nextInt(28));
                statisticService.saveClickByLink(b.getRefCode(), c.getTime() , track);
            }

            Calendar dateRegByClick = new GregorianCalendar(2015, 0, r.nextInt(28));
            statisticService.saveClickByLink(b.getRefCode(), dateRegByClick.getTime() , track);
            regGenerateUser("ReferralBuyerClick #" + i, "password", b.getRefCode(), track, dateRegByClick.getTime());

            Calendar dateRegByCode = new GregorianCalendar(2015, 0, r.nextInt(28));
            statisticService.saveEnterCode(b.getRefCode(),dateRegByCode.getTime());
            regGenerateUser("ReferralBuyerCode #" + i, "password", b.getRefCode(), null, dateRegByCode.getTime());
        }
    }


    @Transactional
    public void regGenerateUser(String name, String password, String referCode, String tracker, Date dateReg) {
        Long referId = null;
        if (referCode != null){
            referId = serviceBuyer.getByRefCode(referCode).getId();
            statisticService.saveRegistrationStatistic(serviceBuyer.get(referId), tracker, dateReg);
        }
        String code = serviceBuyer.randomReferCode();// TODO: 16.10.2016 две одинаковые, немного странные :::: вынес в отдельный метод
        // строки, содержащие какой-то логический смысл, и никакого желание вынести метод?
        Buyer buyer = new Buyer
                .Builder(name, EncryptionString.toMD5(password), code, dateReg)
                .percentCashback(settings.getBaseCashback())
                .refId(referId)
                .tracker(tracker)
                .build();
        serviceBuyer.save(buyer);
    }


    // TODO: 16.10.2016 методы для генерации данных нефиг делать в классах реализующих бизнес логику приложения. Весь    :::: вынес всё в отдельный метод
    // этот вспомогательный набор полезностей надо вынести в отдельные сервис. Грубо говоря - интерфейсы твоих сервисов -
    // это API твоего приложения. А функции генерации покупок у него нет
    @Transactional
    public void generateBuyer() {
        regGenerateUser("MainBuyer", "password", null, null, new GregorianCalendar(2013, 2, 12).getTime());
    }

    @Transactional
    public void generateSail() {
        Random r = new Random();
        int countProduct = serviceProduct.countAll();
        int countBuyer = serviceBuyer.countAll();
        List<ProductBasket> list = new ArrayList<>();
        for(int j = 0; j<500;j++)
        {
            int amountProduct = r.nextInt(5);
            Random c = new Random();
            for (int i = 1; i < amountProduct+1; i++) {
                list.add(new ProductBasket(serviceProduct.getByNumber(r.nextInt(countProduct)), c.nextInt(10)+1, (byte) 0));
            }
            Basket basket = new Basket(list);
            PaginationFilter filter = new PaginationFilter(r.nextInt(countBuyer), 1);
            List<Buyer> buyer = serviceBuyer.list(filter);
            Calendar calendar = new GregorianCalendar(2015, c.nextInt(11), c.nextInt(28));
            addGenericUserSail(buyer, basket, calendar.getTime());
            list.clear();
        }
    }

    @Transactional
    public void addGenericUserSail(List<Buyer> buyers, Basket basket, Date date) {
        Sail sail = new Sail(buyers, soldProductService.convertToSoldProduct(basket.getProducts()), basket);
        sail.setState(StateSail.getState(StateSail.State.COMPLETE));
        sail.setDate(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
        sail.setDateChangeState(c.getTime());
        for (Buyer buyer : buyers) {
            if (buyer.getRefId() != null)
                statisticService.saveSailStatistic(serviceBuyer.get(buyer.getRefId()), c.getTime(), buyer.getTracker());
        }
        sailService.save(sail);
    }

    @Transactional
    public void generateProducts() {
        String[] firstWom = {"Прикольная", "Инновационная", "Уникальная", "Единственная в своём роде", "Всепознающая", "Исцеляющая", "Убийственная", "Фееричная", "Безмозглая" };
        String[] secondWom = {"Накидка", "Голова", "Наковальня", "Палица", "Рукоятка", "Кружка", "Мышка", "Регистратура", "Почта России" };
        String[] threeWom = {"Смертоносного гладиатора", "Кенарийской экспедиции", "Илидана, Ярости бурь", "Короля лича", "Псении Кобчак", "Цветка Декабриста", "Больницы №9", "Педро Паскаля", "Исаака Ньютона" };

        String[] firstMan = {"Хвалёный", "Непреклонный", "Очень не очень", "Чуть-чуть отвратитльный очень сильно", "Волшебный", "Православный", "Переключающий", "Литературный", "Всепожирающий" };
        String[] secondMan = {"Меч", "Мяч", "Скотч", "Мангал", "Балкон", "Замок", "Джим Карр", "Яндекс", "Ультиматум" };
        String[] threeMan = {"Никого", "Винтерфела", "Харви Спектера", "Короля Бангладеша", "Борна", "Турецкого", "Оленя", "Петербурга", "'Кефирчик'" };

        Random r = new Random();
        for (int i = 0; i < 3000; i++) {
            String name;
            if (i % 2 == 0 )
                name = firstWom[r.nextInt(3)] + " " + secondWom[r.nextInt(8)] + " " + threeWom[r.nextInt(8)];
            else
                name = firstMan[r.nextInt(3)] + " " + secondMan[r.nextInt(8)] + " " + threeMan[r.nextInt(8)];
            double cost = ThreadLocalRandom.current().nextDouble(0, 30000);
            serviceProduct.save(new Product(name, cost));
        }
    }
}
