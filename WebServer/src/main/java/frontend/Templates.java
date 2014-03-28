package frontend;

/**
 * Created by uzzz on 28.03.14.
 */

public final class Templates {
    private Templates() {
    }

    public enum MessageType {
        ERROR,
        INFO,
    }

    public static final String AUTH_TPL = "auth.tpl";
    public static final String REGISTER_TPL = "register.tpl";
    public static final String MAIN_TPL = "index.tpl";
    public static final String TIMER_TPL = "timer.tpl";
    public static final String USER_TPL = "user.tpl";
}