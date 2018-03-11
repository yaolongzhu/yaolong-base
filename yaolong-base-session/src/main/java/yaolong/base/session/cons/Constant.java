package yaolong.base.session.cons;

/**
 * @author
 */
public class Constant {

    //	public static String headerName = "x-auth-token";

    public static String headerName = "hid";

    public static String tokenName = "tid";

    public static String maxAge = "3600000";

    public static int maxAgeSec = 3600;

    public static boolean repeatOpen = true;

    public static String origin = "*";

    public static String getMaxAge() {
        return maxAge;
    }

    public static void setMaxAge(int maxAge) {
        maxAgeSec = maxAge;
        Constant.maxAge = (maxAge * 1000) + "";
    }

    public static String getHeaderName() {
        return headerName;
    }

    public static void setHeaderName(String headerName) {
        Constant.headerName = headerName;
    }

    public static String getOrigin() {
        return origin;
    }

    public static void setOrigin(String origin) {
        Constant.origin = origin;
    }

    public static int getMaxAgeSec() {
        return maxAgeSec;
    }

    public static void setMaxAgeSec(int maxAgeSec) {
        Constant.maxAgeSec = maxAgeSec;
    }

    public static void setRepeatOpen(boolean repeatOpen) {
        Constant.repeatOpen = repeatOpen;
    }

}
