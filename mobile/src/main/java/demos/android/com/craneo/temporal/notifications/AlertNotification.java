package demos.android.com.craneo.temporal.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AlertNotification {
    public static final String WEARABLE_SHORT_ALERT = "wearable_short_alert";
    private SharedPreferences settings;
    private static String prefMessage;

    public AlertNotification(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        prefMessage = settings.getString(WEARABLE_SHORT_ALERT, "Not Found");
    }

    public static String getMessage(){
        return prefMessage;
    }
}
