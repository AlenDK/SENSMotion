package e.android.sensmotion.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private onClickRecycle cr;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();


    public RecyclerViewAdapter(Context context, RecyclerView recyclerView, ArrayList<String> days, ArrayList<Integer> images, onClickRecycle cr) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.days = days;
        this.images = images;
        this.cr = cr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_list_item, parent, false);
        holder = new ViewHolder(view, cr);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        recyclerView = view.findViewById(R.id.previousList);
        linearLayout = view.findViewById(R.id.linearLayout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(days.get(position));
        holder.iv.setImageResource(images.get(position));
        holder.ll.setSelected(selectedItems.get(position, false));

    }


    @Override
    public int getItemCount() {
        return days.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv;
        public ImageView iv;
        public ConstraintLayout cl;
        public onClickRecycle cr;
        public LinearLayout ll;

        public ViewHolder(View itemView, onClickRecycle cr) {
            super(itemView);
            this.cr = cr;
            tv = itemView.findViewById(R.id.previousTitle);
            iv = itemView.findViewById(R.id.previousSmiley);
            cl = itemView.findViewById(R.id.constraintLayout);
            ll = itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                linearLayout.setSelected(false);
            } else {
                selectedItems.put(getAdapterPosition(), true);
                linearLayout.setSelected(true);
            }
            cr.clickItem(getAdapterPosition());
        }
    }

    public interface onClickRecycle {
        void clickItem(int position);
    }
}
