package com.andy.smartparking.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.andy.smartparking.R;
import com.andy.smartparking.Webservices.DarajaApiClient;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HomeFragment extends Fragment {


    private PieChart pieChart;
    private Button payButton;
    private DarajaApiClient mApiClient;

    String partyA = "254708374149";
    int partyB = 174379;
    String phoneNumber = "254791499932";


    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_home, container, false);
        pieChart = view1.findViewById(R.id.activity_main_piechart);
        payButton = view1.findViewById(R.id.payment);

        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "ButtonClicked" , Toast.LENGTH_SHORT).show();

//                public static Thread login = new Thread(new Runnable() {

                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("BusinessShortCode", 174379);
                    jsonObject.put("Password", "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjEwODMwMjI1NDE1");
                    jsonObject.put("Timestamp", "20210830225415");
                    jsonObject.put("TransactionType", "CustomerPayBillOnline");
                    jsonObject.put("Amount", 1);
                    jsonObject.put("PartyA", partyA);
                    jsonObject.put("PartyB", 174379);
                    jsonObject.put("PhoneNumber", phoneNumber);
                    jsonObject.put("CallBackURL", "https://parking-finder-web-app.herokuapp.com/login.php");
                    jsonObject.put("AccountReference", "CompanyXLTD");
                    jsonObject.put("TransactionDesc", "Payment of X");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonObject.toString());

                Request request = new Request.Builder()
                        .url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer AoA5jMHpdtg5MSnFG9lO04YLZBAb")
                        .build();
//                Response response = client.newCall(request).execute();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response1) throws IOException {
                        if(response1.isSuccessful()){
                            Response response = client.newCall(request).execute();
                            return;

                        }

                    }
                });


            }
        });
        setupPieChart();
        loadPieChartData();

        return view1;
    }

//    public void getAccessToken() {
//        mApiClient.setGetAccessToken(true);
//        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
//
//                if (response.isSuccessful()) {
//                    mApiClient.setAuthToken(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
//
//            }
//        });
//    }


    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Total Parking Spaces");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Occupied Parking"));
        entries.add(new PieEntry(0.15f, "Booked parking spaces"));
        entries.add(new PieEntry(0.10f, "Out of service Parking Spaces"));
        entries.add(new PieEntry(0.25f, "Available parking spaces"));
        entries.add(new PieEntry(0.3f, "Reserved Parking"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Parking Spaces");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}