package demos.android.com.craneo.temporal.receiver;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by crane on 11/25/2016.
 */

public class UpdateService extends WearableListenerService {

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
//                if(SyncStateContract.Constants.RUN_UPDATE_NOTIFICATION.equalsIgnoreCase(dataEvent.getDataItem().getUri().getPath())){
//                    buildRunUpdateNotification(dataMap);
//                }
            }
        }
    }
}
