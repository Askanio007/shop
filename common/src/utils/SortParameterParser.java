package utils;

public class SortParameterParser {
	
    public static String getColumnName(String sort) {
        String[] str = sort.trim().split("_");
        return str[0];
    }

    public static String getTypeOrder(String sort) {
        String[] str = sort.trim().split("_");
        if (str[1].equals("up"))
           return "desc";
        return "asc";
    }
}
