package utils;

import entity.Buyer;

import javax.servlet.http.HttpServletRequest;

public class LinkBuilder {

    public static String buildReferralLink(Buyer buyer, String tracker, String ancor, HttpServletRequest request) {
        String link = buildRootDomain(request) + "/reg/" + buyer.getRefCode() ;
        if (tracker != "") link = link + "&tracker=" + tracker;
        if (ancor != "") link = "&lt;a href=\""+link+"\"&gt;"+ancor+"&lt;/a&gt;";
        return link;
    }

    private static String buildRootDomain(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
