package sheduler;


import entity.Sail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.SailService;
import service.SettingsService;

import java.util.List;

@Component
public class AutoCompletionDelivery implements Runnable {

    @Autowired
    private SettingsService settings;
    @Autowired
    private SailService sailService;

    @Override
    public void run() {
        List<Sail> sails = sailService.getOverDueSails(settings.deliveredCompleteTime());
        sails.stream().forEach((sail) ->sailService.sailComplete(sail.getId()) );
    }
}
