package utils;

public class SortParameterParser {
	
    public static String getColumnName(String sort) {
        if (sort == null)
            return null;
        String[] str = sort.trim().split("_");
        return str[0];
    }

    public static String getTypeOrder(String sort) {
        if (sort == null)
            return null;
        String[] str = sort.trim().split("_");
        return str[1].equals("up") ? "desc" : "asc";
    }
}
