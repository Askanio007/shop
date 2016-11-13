package utils;

import entity.Buyer;

public class LinkBuilder {

    public static String buildReferralLink(Buyer buyer, String tracker, String ancor, String path) {
        String link = path + "reg/" + buyer.getRefCode() ;
        if (tracker != "") link = link + "&tracker=" + tracker;
        if (ancor != "") link = "&lt;a href=\""+link+"\"&gt;"+ancor+"&lt;/a&gt;";
        return link;
    }
}
