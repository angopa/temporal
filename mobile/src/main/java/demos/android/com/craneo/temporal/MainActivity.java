package demos.android.com.craneo.temporal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

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

    private static final String TAG = "MainActivity";
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
            createData();
            kids = dataSource.findAll();
        }

        kid = kids.get(1);
        ArrayAdapter<Kid> adapter = new ArrayAdapter<Kid>(this,
                R.layout.item_layout, kids);
        //setListAdapter(adapter);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_message_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }

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

    private void createData(){
        Kid kid = new Kid();
        kid.setName("Gisel");
        kid.setLastName("Beatriz");
        kid.setImage("student_1");
        kid = dataSource.create(kid);
        Log.i(TAG, "Kid created with id "+kid.getId());

        kid = new Kid();
        kid.setName("Ariatna");
        kid.setLastName("Montes");
        kid.setImage("student_2");
        kid = dataSource.create(kid);
        Log.i(TAG, "Kid created with id "+kid.getId());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: ");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
    }


}