package engine.util;

public class Log {

    public enum TextColor {
        RESET("\033[0m"),

        BLACK("\033[0;30m"), //
        GREEN("\u001B[32m"), //
        RED("\u001B[31m"), //
        YELLOW("\u001B[33m"), //
        BLUE("\u001B[34m"), //
        MAGENTA("\033[0;35m"), //
        CYAN("\033[0;36m"), //
        WHITE("\033[0;37m"),

        BLACK_UNDERLINED("\033[4;30m"), //
        RED_UNDERLINED("\033[4;31m"), //
        GREEN_UNDERLINED("\033[4;32m"), //
        YELLOW_UNDERLINED("\033[4;33m"), //
        BLUE_UNDERLINED("\033[4;34m"), //
        MAGENTA_UNDERLINED("\033[4;35m"), //
        CYAN_UNDERLINED("\033[4;36m"), //
        WHITE_UNDERLINED("\033[4;37m"),

        BLACK_BACKGROUND("\033[40m"), //
        RED_BACKGROUND("\033[41m"), //
        GREEN_BACKGROUND("\033[42m"), //
        YELLOW_BACKGROUND("\033[43m"), //
        BLUE_BACKGROUND("\033[44m"), //
        MAGENTA_BACKGROUND("\033[45m"), //
        CYAN_BACKGROUND("\033[46m"), //
        WHITE_BACKGROUND("\033[47m");

        private final String color;

        TextColor(String color) {
            this.color = color;
        }

        String get() {
            return this.color;
        }
    }

    private static String logHeader = new String();

    private static void print(TextColor color, String logHeader, Object object) {
        if (!logHeader.equals(Log.logHeader)) {
            Log.logHeader = logHeader;
            System.out.println();
        }

        System.out.println(color.get() + "<" + logHeader + "> " + object + TextColor.MAGENTA.get());
    }

    public static void print(TextColor color, String logHeader, Exception e) {
        StringBuilder builder = new StringBuilder();

        builder.append(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
            builder.append(ste.toString() + "\n");
        }

        print(color, logHeader, builder.toString());
    }

    public static void error(Object object) {
        print(TextColor.RED, "ERROR", object);
    }

    public static void error(Exception e) {
        print(TextColor.RED, "ERROR", e);
    }

    public static void warning(Object object) {
        print(TextColor.YELLOW, "WARNING", object);
    }

    public static void system(Object object) {
        print(TextColor.BLUE, "SYSTEM", object);
    }

    public static void info(String logHeader, Object object) {
        print(TextColor.GREEN, logHeader, object);
    }

    public static void info(Object object) {
        print(TextColor.GREEN, "INFO", object);
    }

    public static void print(String logHeader, Object object) {
        print(TextColor.RESET, logHeader, object);
    }

    public static void debug( Object object) {
        print(TextColor.MAGENTA, "DEBUG", object);
    }
}
