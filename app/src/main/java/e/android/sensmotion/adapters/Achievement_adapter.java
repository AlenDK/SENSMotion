package e.android.sensmotion.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.entities.user.Patient;

public class Achievement_adapter extends BaseAdapter {


    int icons[];
    Activity context;
    LayoutInflater inflater;

    public Achievement_adapter(Activity context, int icons[]) {
        this.context = context;
        this.icons = icons;
    }


    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        inflater = context.getLayoutInflater();

        View view = inflater.inflate(R.layout.achievement_adapter, null, true);


        ImageView icon = (ImageView) view.findViewById(R.id.icon);

        icon.setImageResource(icons[position]);

        return view;
    }
}
