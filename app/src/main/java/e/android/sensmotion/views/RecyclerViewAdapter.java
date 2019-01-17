package e.android.sensmotion.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import e.android.sensmotion.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> days, ArrayList<Integer> images) {
        this.context = context;
        this.days = days;
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(days.get(position));
        holder.iv.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv;
        public ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.previousTitle);
            iv = itemView.findViewById(R.id.previousSmiley);
            tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("nice makker B)");
            Toast.makeText(context, "yeet", Toast.LENGTH_LONG).show();
        }
    }
}
