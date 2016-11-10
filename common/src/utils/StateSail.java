package utils;

public class StateSail {

    public enum State {
        SENT, COMPLETE, CONFLICT
    }

    // I know about enum.name()
    // TODO: 16.10.2016 тогда объясни смысл этой невероятной задумки приносящей лишь дополнительный гемморой ::: Если изменится название статусов в базе, то надо будет только тут поменять
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
