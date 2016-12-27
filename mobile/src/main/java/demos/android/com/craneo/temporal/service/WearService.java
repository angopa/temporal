package demos.android.com.craneo.temporal.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import demos.android.com.craneo.temporal.utils.Constants;
import demos.android.com.craneo.temporal.utils.Utils;

public class WearService extends IntentService {

    private static final String TAG = WearService.class.getSimpleName();

    public WearService() {
        super(TAG);
    }

    public static void startService(Context context){
        Intent intent = new Intent(context, WearService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendMessage();
    }

    /**
     * Transfer the required data over to the wearable
     */
    private void sendDataToWearable() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        // It's OK to use blockingConnect() here as we are running in an
        // IntentService that executes work on a separate (background) thread.
        ConnectionResult connectionResult = googleApiClient.blockingConnect(
                Constants.GOOGLE_API_CLIENT_TIMEOUT_S, TimeUnit.SECONDS);


        if (connectionResult.isSuccess() && googleApiClient.isConnected()) {
            PutDataMapRequest dataMap = PutDataMapRequest.create(Constants.DATA_ITEM_RECEIVE_PATH);
            dataMap.getDataMap().putString(Constants.USE_MICRO_APP, "open");
            dataMap.getDataMap().putLong(Constants.EXTRA_TIMESTAMP, new Date().getTime());
            PutDataRequest request = dataMap.asPutDataRequest();
            request.setUrgent();

            // Send the data over
            DataApi.DataItemResult result =
                    Wearable.DataApi.putDataItem(googleApiClient, request).await();
            if (result.getStatus().isSuccess()) {
                Log.e(TAG, String.format("Send data using DataApi (error code = %d)",
                        result.getStatus().getStatusCode()));
            }else {
                Log.e(TAG, String.format("Error sending data using DataApi (error code = %d)",
                        result.getStatus().getStatusCode()));
            }
        } else {
            Log.e(TAG, String.format(Constants.GOOGLE_API_CLIENT_ERROR_MSG,
                    connectionResult.getErrorCode()));
        }
        googleApiClient.disconnect();
    }

    private void sendMessage(){

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        ConnectionResult connectionResult = googleApiClient.blockingConnect(
                Constants.GOOGLE_API_CLIENT_TIMEOUT_S, TimeUnit.SECONDS);

        if (connectionResult.isSuccess() && googleApiClient.isConnected()) {
            // Loop through all nodes and send a clear notification message
            Iterator<String> itr = Utils.getNodes(googleApiClient).iterator();
            while (itr.hasNext()) {
                Wearable.MessageApi.sendMessage(
                        googleApiClient, itr.next(), Constants.ACTIVITY_PATH, null);
            }
            googleApiClient.disconnect();
        }
    }
}
