package utils;

public class StateSail {

    public enum State {
        SENT, COMPLETE, CONFLICT
    }

    // I know about enum.name()
    public static String getState(State state) {
        switch (state) {
            case SENT:
                return "SENT";
            case COMPLETE:
                return "COMPLETE";
            case CONFLICT:
                return "CONFLICT";
            default:
                return null;
        }
    }
}
