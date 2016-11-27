package demos.android.com.craneo.temporal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyDisplayActivity extends Activity implements View.OnClickListener{

    private TextView mTextView;
    private ImageButton bResponseYes;
    private ImageButton bResponseNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mTextView = (TextView) findViewById(R.id.message);
        mTextView.setText(R.string.will_arrive);
        initializeComponents();
    }

    private void initializeComponents() {
        bResponseNo = (ImageButton) findViewById(R.id.responseNo);
        bResponseYes = (ImageButton) findViewById(R.id.responseYes);

        bResponseNo.setOnClickListener(this);
        bResponseYes.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        int id = view.getId();
        switch (view.getId()){
            case R.id.responseYes:
                intent = new Intent(this, ConfirmationActivity.class);
                intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.SUCCESS_ANIMATION);
                intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                        getString(R.string.msg_sent));
                startActivity(intent);
                break;
            case R.id.responseNo:
                intent = new Intent(this, OptionsResponseActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
