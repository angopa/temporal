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
import java.util.concurrent.TimeUnit;

import demos.android.com.craneo.temporal.util.Constants;

/**
 * Created by crane on 10/26/2016.
 */

public class UtilityService extends IntentService {
    private static final String TAG = "UtilityService";
    private static Integer option = 0;
    private static Context mContext;
    private static final String ACTION_START_DEVICE_ACTIVITY = "start_device_activity";

    public UtilityService() {
        super(TAG);
    }

    public UtilityService(Context context, Integer option){
        this();
        this.mContext = context;
        this.option = option;
    }

    /**
     * Trigger a message that asks the master device to start an activity.
     */
    public static void startDeviceActivity() {
        Intent intent = new Intent(mContext, UtilityService.class);
        intent.setAction(ACTION_START_DEVICE_ACTIVITY);
        mContext.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (ACTION_START_DEVICE_ACTIVITY.equals(action)) {
            sendDataToHandler();
        }
    }

    private void sendDataToHandler() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        // It's OK to use blockingConnect() here as we are running in an
        // IntentService that executes work on a separate (background) thread.
        ConnectionResult connectionResult = googleApiClient.blockingConnect(
                Constants.GOOGLE_API_CLIENT_TIMEOUT_S, TimeUnit.SECONDS);


        if (connectionResult.isSuccess() && googleApiClient.isConnected()) {
            PutDataMapRequest dataMap = PutDataMapRequest.create(
                    Constants.ACTIVITY_PATH);
            dataMap.getDataMap().putInt(Constants.CHOICE_OPTION, option);
            dataMap.getDataMap().putLong(Constants.EXTRA_TIMESTAMP, new Date().getTime());
            PutDataRequest request = dataMap.asPutDataRequest();
            request.setUrgent();

            // Send the data over
            DataApi.DataItemResult result =
                    Wearable.DataApi.putDataItem(googleApiClient, request).await();
            if (result.getStatus().isSuccess()) {
                Log.d(TAG, String.format("Send data using DataApi (code = %d)",
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
}
