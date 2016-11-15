package utils;

import entity.Buyer;

import javax.servlet.http.HttpServletRequest;

public class LinkBuilder {

    public static String buildReferralLink(Buyer buyer, String tracker, String ancor, HttpServletRequest request) {
        StringBuffer str = new StringBuffer(20);
        str.append(buildRootDomain(request));
        str.append("/site/reg/");
        str.append(buyer.getRefCode());
        if (tracker != "") str.append("&tracker=").append(tracker);
        if (ancor != "") {
            String link = "&lt;a href=\"" + str.toString() + "\"&gt;" + ancor + "&lt;/a&gt;";
            return link;
        }
        return str.toString();
    }

    private static String buildRootDomain(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
