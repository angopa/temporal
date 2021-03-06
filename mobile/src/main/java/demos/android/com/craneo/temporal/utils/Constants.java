package demos.android.com.craneo.temporal.utils;

public class Constants {

    private Constants() {

    }

    public static final int GOOGLE_API_CLIENT_TIMEOUT_S = 10;
    public static final String ACTIVITY_PATH = "/start-activity";
    public static final String DATA_ITEM_RECEIVE_PATH = "/data-item-received";
    public static final String EXTRA_TIMESTAMP = "extra_timestamp";
    public static final String USE_MICRO_APP = "open";
    public static final String EXTRA_MESSAGE = "extra_message";

    public static final String GOOGLE_API_CLIENT_ERROR_MSG =
            "Failed to connect to GoogleApiClient (error code = %d)";
}
