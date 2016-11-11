package utils;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class ReferralParametersParser {

    public static String[] trimParameters(String params){
        return params.trim().split("&");
    }

    public static String[] trimParameter(String param){
        return param.trim().split("=");
    }

    public static String getParentCode(String params){
        String [] param = params.trim().split("&");
        return param[0];
    }

    public static String getTracker(String params) {
        for(String param : trimParameters(params)) {
            if (trimParameter(param)[0].equals("tracker")){
               return trimParameter(param)[1];
            }
        }
        return null;
    }

}
