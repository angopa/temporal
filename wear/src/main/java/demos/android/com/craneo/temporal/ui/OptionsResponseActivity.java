package demos.android.com.craneo.temporal.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import demos.android.com.craneo.temporal.R;
import demos.android.com.craneo.temporal.service.UtilityService;

public class OptionsResponseActivity extends Activity implements
        WearableListView.ClickListener{

    private static final String TAG = "OptionsResponseActivity";
    //Sample dataset for the list
    String[] elements = {"Family Problems", "No one to get him/her to school", "Sickness", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_response);

        //Get the list component from the layout of the activity
        WearableListView listView =
                (WearableListView) findViewById(R.id.wearable_list);

        //Assign an adapter to the list
        listView.setAdapter(new Adapter(this, elements));

        //set the click listener
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder view) {
        Integer tag = (Integer) view.itemView.getTag();
        String s = elements[tag];
        UtilityService utilityService = new UtilityService(this, tag);
        utilityService.startDeviceActivity();
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                s);
        startActivity(intent);
    }

    @Override
    public void onTopEmptyRegionClick() {
        Log.d(TAG, "onTopEmptyRegionClick");
    }
}
