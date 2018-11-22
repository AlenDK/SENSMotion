package e.android.sensmotion.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.app.AlertDialog;

public class Popup extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        android.support.v7.app.AlertDialog.Builder popup = new android.support.v7.app.AlertDialog.Builder(getActivity());
        popup.setTitle("Godt klaret!");
        popup.setMessage("Du har nået en af dine daglige mål for i dag!");
        popup.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return  popup.create();
    }
}
