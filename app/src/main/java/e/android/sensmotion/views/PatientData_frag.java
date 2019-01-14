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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.common.util.Strings;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
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
    private String jsonString, dateChosen, id;
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

        if(getArguments() != null){
            id = getArguments().getString("id");
        }

        dc = ControllerRegistry.getDataController();
        uc = ControllerRegistry.getUserController();
        loading = new ProgressDialog(view.getContext());

        updateSensorData(id);

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

        dateChosen = sdf.format(calendar.getTime());
        periode.setText(dateChosen);
        updateSensorData(id);
        updateBarChart();
    }

    public void updateBarChart(){

        if(ControllerRegistry.getUserController().getPatient(id) != null) {
            Values values = ControllerRegistry.getUserController().getPatient(id).getSensor("s1").getCurrentPeriod().getValuesList().get(0);

            //Initializes lists of BarEntry.
            List<BarEntry> entriesStand = new ArrayList<BarEntry>();
            List<BarEntry> entriesWalk = new ArrayList<BarEntry>();
            List<BarEntry> entriesCycling = new ArrayList<BarEntry>();
            List<BarEntry> entriesExercise = new ArrayList<BarEntry>();
            List<BarEntry> entriesRest = new ArrayList<BarEntry>();
            List<BarEntry> entriesOther = new ArrayList<BarEntry>();

            //Creates the entries for the different values.
            entriesStand.add(new BarEntry(0f, Float.valueOf(values.getStand())));
            entriesWalk.add(new BarEntry(1f, Float.valueOf(values.getWalk())));
            entriesCycling.add(new BarEntry(2f, Float.valueOf(values.getCycling())));
            entriesExercise.add(new BarEntry(3f, Float.valueOf(values.getExercise())));
            entriesRest.add(new BarEntry(4f, Float.valueOf(values.getRest())));
            entriesOther.add(new BarEntry(5f, Float.valueOf(values.getOther())));

            //Creates the various datasets and sets their color.
            BarDataSet dataSetStand = new BarDataSet(entriesStand, "Stående");
            dataSetStand.setColor(getResources().getColor(R.color.colorBlack));

            BarDataSet dataSetWalk = new BarDataSet(entriesWalk, "Gang");
            dataSetWalk.setColor(getResources().getColor(R.color.colorBlue));

            BarDataSet dataSetCycling = new BarDataSet(entriesCycling, "Cykling");
            dataSetCycling.setColor(getResources().getColor(R.color.colorGreen));

            BarDataSet dataSetExercise = new BarDataSet(entriesExercise, "Træning");
            dataSetExercise.setColor(getResources().getColor(R.color.colorYellow));

            BarDataSet dataSetRest = new BarDataSet(entriesRest, "Hvile");
            dataSetRest.setColor(getResources().getColor(R.color.colorRed));

            BarDataSet dataSetOther = new BarDataSet(entriesOther, "Andet");
            dataSetOther.setColor(getResources().getColor(R.color.colorGray));

            //Creates data for the chart based on the different datasets.
            BarData data = new BarData(dataSetStand, dataSetWalk, dataSetExercise, dataSetCycling, dataSetRest, dataSetOther);
            data.setValueFormatter(new ValueFormatter());

            //Formats the chart.
            data.setBarWidth(0.9f);
            barChart.setData(data);
            barChart.setFitBars(true);
            barChart.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            barChart.setDrawBorders(true);
            barChart.setTouchEnabled(false);

            //Sets chart description
            Description disc = new Description();
            disc.setText("Sensordata");
            barChart.setDescription(disc);

            //Updates the chart.
            barChart.invalidate();
        }



    }

    public void updateSensorData(String id){
        try {
            String hentDataResult = new HentDataAsyncTask().execute().get();

            if(uc.getPatient(id) != null) {
                dc.saveData(hentDataResult, uc.getPatient(id).getSensor("s1"));
            }

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

            if(Strings.isEmptyOrWhitespace(dateChosen)){
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateChosen = sdf.format(calendar.getTime());
                System.out.println(dateChosen);
            }
            else{
                System.out.println("dateChosen er ikke empty");
            }

            jsonString = dc.getDataString(uc.getPatient("p1"), dateChosen);
            System.out.println("jsonString: " + jsonString);

            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
        }
    }

    class ValueFormatter implements IValueFormatter{

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            int hours = (int) value / 60;
            int minutes = Math.round(value % 60);

            return hours + " t, " + minutes + " min";
        }
    }

    }

