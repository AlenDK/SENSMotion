package e.android.sensmotion.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import e.android.sensmotion.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context;
    ViewHolder holder;
    LinearLayout linearLayout;
    ConstraintLayout constraintLayout;

    int positionHolder = 0;


    public RecyclerViewAdapter(Context context, RecyclerView recyclerView, ArrayList<String> days, ArrayList<Integer> images) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.days = days;
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_list_item, parent, false);
        holder = new ViewHolder(view);
        linearLayout = view.findViewById(R.id.linearLayout);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        recyclerView = view.findViewById(R.id.previousList);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(days.get(position));
        holder.iv.setImageResource(images.get(position));


        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cl.setBackgroundResource(R.color.colorGreen);
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tv;
        public ImageView iv;
        public ConstraintLayout cl;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.previousTitle);
            iv = itemView.findViewById(R.id.previousSmiley);
            cl = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
