package demos.android.com.craneo.temporal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import demos.android.com.craneo.temporal.notifications.AlertNotification;
import demos.android.com.craneo.temporal.beans.Kid;
import demos.android.com.craneo.temporal.dao.KidsDataSource;
import demos.android.com.craneo.temporal.notifications.MultipleChoiceNotification;
import demos.android.com.craneo.temporal.R;
import demos.android.com.craneo.temporal.utils.SendNotification;
import demos.android.com.craneo.temporal.notifications.SimpleNotification;
import demos.android.com.craneo.temporal.service.WearService;

/**
 * This activity is used to config the wearable message, it has three different flavors:
 * Normal Message: The student arrives or lefts the school, and just inform about it.
 * Multiple chooses: The system ask why the student don't arrive jet at school.
 * Alert Message: The system trigger an alert that alert the parent about some unusual situation.
 *
 */
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private Button bSendNotf;
    private Button bMultChoice;
    private Button bAlert;
    private Button bWearableApp;
    private Button bSettings;

    private Kid kid;
    private SimpleNotification simpleNotification;
    private AlertNotification alertNotification;
    private MultipleChoiceNotification multipleChoiceNotification;

    KidsDataSource dataSource;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        setupActionBar();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        simpleNotification = new SimpleNotification(this);
        alertNotification = new AlertNotification(this);
        multipleChoiceNotification = new MultipleChoiceNotification(this);

        dataSource = new KidsDataSource(this);
        dataSource.open();

        List<Kid> kids = dataSource.findAll();
        if (kids.size() == 0){
            kids = dataSource.findAll();
        }

        kid = kids.get(1);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initializeComponents() {
        bSendNotf = (Button) findViewById(R.id.sentSimpleNotification);
        bMultChoice = (Button) findViewById(R.id.sentChoice);
        bAlert = (Button) findViewById(R.id.sentAlert);
        bWearableApp = (Button) findViewById(R.id.launchWearableApp);
        bSettings = (Button) findViewById(R.id.settings);

        bSendNotf.setOnClickListener(this);
        bMultChoice.setOnClickListener(this);
        bAlert.setOnClickListener(this);
        bWearableApp.setOnClickListener(this);
        bSettings.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataSource.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataSource.open();
        simpleNotification = new SimpleNotification(this);
        alertNotification = new AlertNotification(this);
        multipleChoiceNotification = new MultipleChoiceNotification(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.sentSimpleNotification:
                new SendNotification(this, view, kid,
                        simpleNotification.getMessage(), true);
                break;
            case R.id.sentChoice:
                new SendNotification(this, view, kid,
                        multipleChoiceNotification.getMessage(),
                        multipleChoiceNotification.getMessages());
                break;
            case R.id.sentAlert:
                new SendNotification(this, view, kid,
                        alertNotification.getMessage(), false);
                break;
            case R.id.launchWearableApp:
                WearService.startService(this);
                break;
            case R.id.settings:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //This method was left blank intentionally
    }

    @Override
    public void onConnectionSuspended(int i) {
        //This method was left blank intentionally
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //This method was left blank intentionally
    }
}