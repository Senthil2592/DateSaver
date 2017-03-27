package rsklabs.com.savethedate.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rsklabs.com.savethedate.R;
import rsklabs.com.savethedate.bean.DateReminderBean;
import rsklabs.com.savethedate.helper.DateReminderHelper;
import rsklabs.com.savethedate.listener.OnLongClickListenerReminderRecord;

/**
 * Created by Kumar on 9/10/2015.
 */
public class SavedEntries extends AppCompatActivity {

    float x1,x2;
    float y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedentries);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<DateReminderBean> reminderList = new DateReminderHelper(this).read();

        Collections.sort(reminderList, new Comparator<DateReminderBean>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            public int compare(DateReminderBean o1, DateReminderBean o2) {
                int i=0;
                if (o1.date == null || o2.date == null)
                    return 0;
                try {
                   i= f.parse(o1.date).compareTo(f.parse(o2.date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return i;
            }
        });

        if (reminderList.size() > 0) {

            for (DateReminderBean obj : reminderList) {

                int id = obj.id;
                String date = obj.date;
                String reason = obj.reason;

                String textViewContents = date + " - " + reason;

                TextView textViewItem= new TextView(this);
                textViewItem.setPadding(0, 10, 0, 10);
                textViewItem.setText(textViewContents);
                textViewItem.setTag(Integer.toString(id));
                textViewItem.setOnLongClickListener(new OnLongClickListenerReminderRecord());
                textViewItem.setTextSize(18);
                textViewItem.setTypeface(Typeface.DEFAULT_BOLD);
                linearLayoutRecords.addView(textViewItem);
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setTextSize(18);
            locationItem.setTypeface(Typeface.DEFAULT_BOLD);
            locationItem.setText("No records found");

            linearLayoutRecords.addView(locationItem);
        }

        Button addEntries = (Button)findViewById(R.id.addEntries);
        addEntries.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                Intent mi;
                mi = new Intent(SavedEntries.this, MyActivity.class);
                startActivity(mi);
            }
        });
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest =new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        finish();
        return super.onKeyDown(keyCode, event);

    }
}












