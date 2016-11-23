package utils;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class ReferralParametersParser {

    private ReferralParametersParser() {}

    // TODO: Kirill trim - Returns a string whose value is this string, with any leading and trailing whitespace removed ::: исправил на split
    public static String[] splitParameters(String params){
        return params.trim().split("&");
    }

    public static String[] splitParameter(String param){
        return param.trim().split("=");
    }

    public static String getParentCode(String params){
        String [] param = params.trim().split("&");
        return param[0];
    }

    public static String getTracker(String params) {
        for(String param : splitParameters(params)) {
            if (splitParameter(param)[0].equals("tracker")){
               return splitParameter(param)[1];
            }
        }
        return null;
    }

}
