package demos.android.com.craneo.temporal.receiver;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;


import demos.android.com.craneo.temporal.ui.MyDisplayActivity;
import demos.android.com.craneo.temporal.util.Constants;

public class DataLayerListenerService extends WearableListenerService {
    private static final String TAG = "DataLayer";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged: ");

        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED
                    && event.getDataItem() != null
                    && Constants.DATA_ITEM_RECEIVE_PATH.equals(event.getDataItem().getUri().getPath())) {

                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
            }
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Intent intent = new Intent(this, MyDisplayActivity.class);
        startActivity(intent);
    }
}
