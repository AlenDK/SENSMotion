package e.android.sensmotion.views;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.entities.sensor.Values;

public class PatientData_frag extends Fragment implements View.OnClickListener {

    private Button periode, graf;
    private ImageButton ImgBtn;
    private TextView patientinformation;
    private HorizontalBarChart barChart;
    private IDataController dc;
    private IUserController uc;
    private String jsonString;
    private ProgressDialog loading;

    final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_data_frag, container, false);

        dc = ControllerRegistry.getDataController();
        uc = ControllerRegistry.getUserController();
        loading = new ProgressDialog(view.getContext());

        updateSensorData();

        barChart = view.findViewById(R.id.chart);
        updateBarChart();

        periode = view.findViewById(R.id.dato_knap);
        periode.setOnClickListener(this);

        graf = view.findViewById(R.id.graf_knap);
        graf.setOnClickListener(this);

        ImgBtn = view.findViewById(R.id.knap_profil);
        ImgBtn.setOnClickListener(this);

        patientinformation = view.findViewById(R.id.textView2);

        return view;
    }


    @Override
    public void onClick(View view) {

        if (view == periode) {

            new DatePickerDialog(view.getContext(), date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();


        } else if (view == graf) {

            //en masse kode.


        } else if (view == ImgBtn) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new Terapuet_setting())
                        .addToBackStack(null)
                        .commit();

        }


        }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        patientinformation.setText(sdf.format(calendar.getTime()));
    }

    public void updateBarChart(){
        List<BarEntry> entries = new ArrayList<BarEntry>();

        Values values = ControllerRegistry.getUserController().getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0);

        entries.add(new BarEntry(0f, Float.valueOf(values.getStand())));
        entries.add(new BarEntry(1f, Float.valueOf(values.getWalk())));
        entries.add(new BarEntry(2f, Float.valueOf(values.getRest())));
        entries.add(new BarEntry(3f, Float.valueOf(values.getOther())));

        BarDataSet dataSet = new BarDataSet(entries, "Værdier");
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();
    }

    public void updateSensorData(){
        try {
            String hentDataResult = new HentDataAsyncTask().execute().get();
            dc.saveData(hentDataResult, uc.getPatient("p1").getSensor("s1"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class HentDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setMessage("\t Henter data...");
            loading.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            jsonString = dc.getDataString(uc.getPatient("p1"));

            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
        }
    }

    }

