package demos.android.com.craneo.temporal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by crane on 10/19/2016.
 */

public class SettingActivity extends AppCompatPreferencesActivity {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener
            = new Preference.OnPreferenceChangeListener(){

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference){
                //For list preferences, look up the correct display value in
                //the preference's 'entries' list
                ListPreference listPreference = (ListPreference)preference;
                int index = listPreference.findIndexOfValue(stringValue);

                //Set the Summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ?listPreference.getEntries()[index]
                                :null);
            }else if(preference instanceof RingtonePreference){
                //For ringtone preferences, look up the correct display value using RingtoneManager
                if(TextUtils.isEmpty(stringValue)){
                    //Empty values correspond to silent
                    preference.setSummary("Silent");
                }else{
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null){
                        //Clear the summary if there was a looking error.
                        preference.setSummary(null);
                    }else{
                        //Set the Summary to reflec the new ringtone display name
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }
            }else{
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        //Set the listener to watch for value changes
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        //Trigger the listener immediately with the preference's current value
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || SettingActivity.SimpleNotificationFragment.class.getName().equals(fragmentName)
                || SettingActivity.QuestionNotificationFragment.class.getName().equals(fragmentName)
                || SettingActivity.AlertNotificationFragment.class.getName().equals(fragmentName);
    }


    /**
     * This fragment shows the notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    public static class SimpleNotificationFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_simple_notif);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference("wearable_short_message"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if(id == android.R.id.home){
                startActivity(new Intent(getActivity(), SettingActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows the notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    public static class QuestionNotificationFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_question_notif);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference("wearable_choice_message"));
            bindPreferenceSummaryToValue(findPreference("wearable_option_a"));
            bindPreferenceSummaryToValue(findPreference("wearable_option_b"));
            bindPreferenceSummaryToValue(findPreference("wearable_option_c"));

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if(id == android.R.id.home){
                startActivity(new Intent(getActivity(), SettingActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows the notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    public static class AlertNotificationFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_alert_notif);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference("wearable_short_alert"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if(id == android.R.id.home){
                startActivity(new Intent(getActivity(), SettingActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }



}
