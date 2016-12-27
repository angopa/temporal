package demos.android.com.craneo.temporal.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SimpleNotification {

    public static final String WEARABLE_SHORT_MESSAGE = "wearable_short_message";
    private SharedPreferences settings;
    private static String prefMessage;

    public SimpleNotification(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        prefMessage = settings.getString(WEARABLE_SHORT_MESSAGE, "Not Found");
    }

    public static String getMessage(){
        return prefMessage;
    }
}
