package e.android.sensmotion.adapters;

import android.app.Activity;
import android.content.res.ColorStateList;
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

    ProgressBar progressBar;
    TextView progText;
    ImageView progImage;

    public ProgressBar_adapter(Activity context, List<ProgBar> progressBarList) {
        super(context,R.layout.progress_bar, progressBarList);
        this.context = context;
        this.progressBarList = progressBarList;
    }

   public View getView(int position, @Nullable View convertView, ViewGroup viewGroup){
        LayoutInflater inflater = context.getLayoutInflater();

        View view = inflater.inflate(R.layout.progress_bar, null, true);

        progressBar = view.findViewById(R.id.progressBar);
        progText = view.findViewById(R.id.progressText);
        progImage = view.findViewById(R.id.barImage);

        setProgressImage(position);
        setProgessbarColor(position);
        progressBar.setProgress(progressBarList.get(position).getProgress());
        progText.setText(progressBarList.get(position).getProgress() +"/"+ progressBarList.get(position).getGoal());


        return view;
    }

    private void setProgressImage(int position){
            if(progressBarList.get(position).getName().equals("walk"))
                progImage.setImageResource(R.drawable.stickman_walking);

            else if(progressBarList.get(position).getName().equals("stand"))
                progImage.setImageResource(R.drawable.stickman_standing2);

            else if(progressBarList.get(position).getName().equals("cycle"))
                progImage.setImageResource(R.drawable.stickman_biking);

            else if(progressBarList.get(position).getName().equals("exercise"))
                progImage.setImageResource(R.drawable.stickman_exercise);

            else if(progressBarList.get(position).getName().equals("other"))
                progImage.setImageResource(R.drawable.stickman_other);
        }

    private void setProgessbarColor(int position){
        int progress = progressBarList.get(position).getProgress();
        if(progress < 20)
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.progress20)));

        else if(progress < 40)
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.progress40)));

        else if(progress < 50)
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.progress50)));

        else if(progress < 60)
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.progress60)));

        else if(progress < 80)
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.progress80)));

        else if(progress < 100)
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.progress99)));

        else if(progress >= 100){
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.progress100)));
        }
    }
}
