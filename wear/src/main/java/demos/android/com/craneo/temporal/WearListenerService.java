package demos.android.com.craneo.temporal;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by crane on 11/27/2016.
 */

public class WearListenerService extends WearableListenerService {
    private static final String TAG = "WearListenerService";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v(TAG, "onMessageReceived: " + messageEvent);
    }
}
