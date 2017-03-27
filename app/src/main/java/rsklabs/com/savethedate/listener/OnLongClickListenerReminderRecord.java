package rsklabs.com.savethedate.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import rsklabs.com.savethedate.activity.SavedEntries;
import rsklabs.com.savethedate.helper.DateReminderHelper;


/**
 * Created by Kumar on 12/26/2015.
 */
public class OnLongClickListenerReminderRecord implements View.OnLongClickListener {


    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Delete" };

        new AlertDialog.Builder(context)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            boolean deleteSuccessful = new DateReminderHelper(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Record deleted.", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(context, "Unable to delete  record.", Toast.LENGTH_SHORT).show();
                            }
                            ((Activity) context).finish();
                            Intent i1 = new Intent (context, SavedEntries.class);
                            context.startActivity(i1);
                        }

                        dialog.dismiss();

                    }
                }).show();
        return false;
    }
}
