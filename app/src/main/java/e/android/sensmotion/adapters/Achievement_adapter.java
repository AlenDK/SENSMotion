package e.android.sensmotion.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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

public class Achievement_adapter extends BaseAdapter {

    Activity context;
    List<Achievement> achievements = new ArrayList<>();
    ImageView icons;
    LayoutInflater inflater;
    ColorMatrix matrix = new ColorMatrix();


    public Achievement_adapter(Activity context, ArrayList<Achievement> achievements) {
        this.context = context;
        this.achievements = achievements;
        inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return achievements.size();
    }

    @Override
    public Object getItem(int position) {
        return achievements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(R.layout.achievement_adapter, null, true);

        icons = view.findViewById(R.id.icon);

        icons.setImageResource(achievements.get(position).getImage());


        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);


        if (achievements.get(position).getComplete() == false) {
            icons.setColorFilter(filter);
        }


        return view;
    }
}
