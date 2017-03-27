package rsklabs.com.savethedate.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import rsklabs.com.savethedate.R;
import rsklabs.com.savethedate.bean.DateReminderBean;
import rsklabs.com.savethedate.helper.DateReminderHelper;


public class MyActivity extends AppCompatActivity {

    float x1,x2;
    float y1, y2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EditText editTextFocus = (EditText) findViewById(R.id.editText);
        editTextFocus.setOnFocusChangeListener(focusListener);

        Button button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                final Context context = view.getContext();
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                EditText editText = (EditText) findViewById(R.id.editText);

                DateReminderBean dateReminderBean = new DateReminderBean();
                dateReminderBean.date = "" + day + "/" + month + "/" + "" + year + "";
                dateReminderBean.reason = editText.getText().toString();
                if (!editText.getText().toString().trim().equals("")) {
                    boolean createSuccessful = new DateReminderHelper(context).create(dateReminderBean);

                    if (createSuccessful) {

                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                        long lnsTime = 0;

                        try {
                            String dob_var = ""+day+"/"+month+"/"+year+" 00:00";
                            Date newDate = formatter.parse(dob_var);
                            lnsTime = newDate.getTime();
                            Log.e(null, Long.toString(lnsTime));
                        }

                        catch (java.text.ParseException e) {
                            // TODO Auto-generated catch block
                            Log.i("E11111111111", e.toString());
                        }


                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra("beginTime", lnsTime);
                        intent.putExtra("allDay", true);
                        intent.putExtra("rrule", "FREQ=YEARLY");
                        intent.putExtra("title", dateReminderBean.reason);
                        startActivity(intent);

                        Toast.makeText(context, "Press back to Auto Sync with Calendar", Toast.LENGTH_SHORT).show();
                        Intent mi = new Intent(MyActivity.this, SavedEntries.class);
                        startActivity(mi);
                    } else {
                        Toast.makeText(context, "Unable to save  date.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please enter reason.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AdView adView = (AdView)findViewById(R.id.adView1);
        AdRequest adRequest =new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        AdView adView1 = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest1 =new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);
    }


    protected OnFocusChangeListener focusListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            AdView adView = (AdView) findViewById(R.id.adView1);
            AdView adView1 = (AdView) findViewById(R.id.adView2);
            if (adView.getVisibility() == View.VISIBLE && adView1.getVisibility() == View.VISIBLE) {
                adView1.setVisibility(View.GONE);
                adView.setVisibility(View.GONE);
            } else {
                adView1.setVisibility(View.VISIBLE);
                adView.setVisibility(View.VISIBLE);
            }
        }
       };


    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                if (x1 > x2)
                {
                    finish();
                    Intent mi = new Intent(this , SavedEntries.class);
                    startActivity(mi);
                }
                break;
            }
        }
        return false;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        moveTaskToBack(true);
        return super.onKeyDown(keyCode, event);

    }
}
