package demos.android.com.craneo.temporal.receiver;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import demos.android.com.craneo.temporal.utils.Constants;

public class DataLayerListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (Constants.ACTIVITY_PATH.equals(messageEvent.getPath())) {
            // Request for this device open the attraction detail screen
            // to a specific tourist attraction
            String attractionName = new String(messageEvent.getData());

        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED
                    && event.getDataItem() != null
                    && event.getDataItem().getUri().getPath().equals(Constants.ACTIVITY_PATH)) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());

//                showNotification(dataMapItem.getUri(), attractionsData);
            }
        }
    }
}
