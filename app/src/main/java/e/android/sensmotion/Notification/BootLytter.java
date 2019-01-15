package e.android.sensmotion.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class BootLytter extends BroadcastReceiver {

    Context c;
    SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        c = context;
        pref = PreferenceManager.getDefaultSharedPreferences(c);

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {

            Toast.makeText(c, "hændelse boot modtaget af notitest", Toast.LENGTH_LONG).show();
            //TODO: find og sæt tidligere alarmer
            //context.startActivity(new Intent(context, MainActivity.class));
            //Notifikation.bygNotifikation(context,"fra boot","fra boot", "fra boot");
            new AsyncTask() {

                ;
                @Override
                protected Object doInBackground(Object[] params) {

                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    Toast.makeText(c, "Nu er telefonen startet igen", Toast.LENGTH_LONG).show();

                }
            }.execute();



        }
        */
    }
}
