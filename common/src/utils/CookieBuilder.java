package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CookieBuilder {

    private static Cookie parentCode(String params) {
        return setMaxAge(new Cookie("partnerCode", ReferralParametersParser.getParentCode(params)));
    }

    private static List<Cookie> setMaxAge(List<Cookie> cookies){
        for (Cookie cookie : cookies){
            setMaxAge(cookie);
        }
        return cookies;
    }

    private static Cookie setMaxAge(Cookie cookie){
        cookie.setMaxAge(86400);
        return cookie;
    }

    private static List<Cookie> referralParams(String params){
        String[] parameters = ReferralParametersParser.trimParameters(params);
        List<Cookie> cookies = new ArrayList<>();
        for (int i = 1; i<parameters.length; i++) {
            String[] nameAndValue = ReferralParametersParser.trimParameter(parameters[i]);
            cookies.add(new Cookie(nameAndValue[0], nameAndValue[1]));
        }
        return setMaxAge(cookies);
    }

    public static void addCookie(HttpServletResponse response, String param) {
        List<Cookie> cookies = CookieBuilder.referralParams(param);
        cookies.add(parentCode(param));
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
    }

}
