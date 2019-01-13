package e.android.sensmotion.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import e.android.sensmotion.R;

public class Task_complete_frag extends Fragment implements View.OnClickListener {

    Button ok;
    ImageButton del;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.task_complete, container, false);

        ok = view.findViewById(R.id.okKnap);
        del = view.findViewById(R.id.delKnap);
        ok.setOnClickListener(this);
        del.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.okKnap){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Patient_start_frag())
                    .commit();
        }

        if(view.getId() == R.id.delKnap){
            del();
        }

    }

    private void del(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Test";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
