package demos.android.com.craneo.temporal;

import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by crane on 10/26/2016.
 */

public class DataLayerListenerService extends WearableListenerService {
    private static final String TAG = "DataLayer";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.v(TAG, "onMessageReceived: " + messageEvent);


        if (Constants.ACTIVITY_PATH.equals(messageEvent.getPath())) {
            // Request for this device open the attraction detail screen
            // to a specific tourist attraction
            String attractionName = new String(messageEvent.getData());

        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.v(TAG, "onDataChanged: " + dataEvents);
        for (DataEvent event : dataEvents) {
            Log.v(TAG, "onDataChanged: " + event);
            if (event.getType() == DataEvent.TYPE_CHANGED
                    && event.getDataItem() != null
                    && event.getDataItem().getUri().getPath().equals(Constants.ACTIVITY_PATH)) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());

                Log.d(TAG, dataMapItem.getDataMap().getString(Constants.EXTRA_MESSAGE));
//                showNotification(dataMapItem.getUri(), attractionsData);
            }
        }
    }
}
