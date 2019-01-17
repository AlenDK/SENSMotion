package e.android.sensmotion.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.views.Achievement.Achievement;

public class Achievement_adapter extends ArrayAdapter<Achievement> {

    Activity context;
    LayoutInflater inflater;
    ArrayList<Achievement> achievements = new ArrayList<>();


    public Achievement_adapter(Activity context, ArrayList<Achievement> achievements) {
        super(context,R.layout.achievement_adapter ,achievements);
        this.context = context;
        this.achievements = achievements;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        inflater = context.getLayoutInflater();

        View view = inflater.inflate(R.layout.achievement_adapter, null, true);

        ImageView icon = (ImageView) view.findViewById(R.id.icon);

        if(achievements.get(position).getComplete()==true && achievements.get(position).getName() == "Marathon"){
            achievements.get(position).setImage(R.drawable.sensmotionblack);
        }

        icon.setImageResource(achievements.get(position).getImage());


        return view;
    }
}
