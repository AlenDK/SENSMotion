package e.android.sensmotion.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import e.android.sensmotion.R;
import e.android.sensmotion.views.ProgressBars.ProgBar;

public class ProgressBar_adapter extends ArrayAdapter<ProgBar> {

    private Activity context;
    private List<ProgBar> progressBarList;

    public ProgressBar_adapter(Activity context, List<ProgBar> progressBarList) {
        super(context,R.layout.progress_bar, progressBarList);
        this.context = context;
        this.progressBarList = progressBarList;
    }

   public View getView(int position, @Nullable View convertView, ViewGroup viewGroup){
        LayoutInflater inflater = context.getLayoutInflater();

        View view = inflater.inflate(R.layout.progress_bar, null, true);

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView progText = view.findViewById(R.id.progressText);
        ImageView progImage = view.findViewById(R.id.barImage);

        progImage.setImageResource(R.drawable.walking_stickman);
        progressBar.setProgress(progressBarList.get(position).getProgress());
        progText.setText(progressBarList.get(position).getProgress() +"/"+ progressBarList.get(position).getGoal());


        return view;
    }
}
