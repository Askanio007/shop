package utils;
    public enum StateSail {
        SENT, COMPLETE, CONFLICT
    }

    // I know about enum.name()
    // TODO: 16.10.2016 тогда объясни смысл этой невероятной задумки приносящей лишь дополнительный гемморой ::: Если изменится название статусов в базе, то надо будет только тут поменять
    // TODO: Kirill тогда для справки посмотри какие есть варианты работы с енумами для работы с базой через гибернейт
    // TODO: Artyom разобрался с энумами узнал что в базе можно их задавать как строками, так и числами. Я сделал строками




/* public static State getState(State state) {
        switch (state) {
            case SENT:
                return State.SENT;
            case COMPLETE:
                return "COMPLETE";
            case CONFLICT:
                return "CONFLICT";
            default:
                return null;
        }
    }*/

