package com.iot.smart_lighting.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iot.smart_lighting.Model.SmartLampDB;
import com.iot.smart_lighting.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DataAnalysisAdapter extends RecyclerView.Adapter<DataAnalysisAdapter.DataAnalysisHolder> {

    private Context context;
    private ArrayList id, ssid_name, status, intensity, colour;

    public DataAnalysisAdapter(Context context, ArrayList id, ArrayList ssid_name, ArrayList status, ArrayList intensity, ArrayList colour) {
        this.context = context;
        this.id = id;
        this.ssid_name = ssid_name;
        this.status = status;
        this.intensity = intensity;
        this.colour = colour;
    }

    @Override
    public DataAnalysisHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_analysis_list_item, parent, false);
        return new DataAnalysisHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAnalysisHolder holder, int position) {
        holder.lampNo.setText("LAMP " + String.valueOf(id.get(position)));
        holder.ssidName.setText(String.valueOf(ssid_name.get(position)));
        holder.statusName.setText(String.valueOf(status.get(position)));
        holder.intensity.setText(String.valueOf(intensity.get(position)) + "%");
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class DataAnalysisHolder extends RecyclerView.ViewHolder {

        TextView lampNo, ssidTitle, ssidName, statusTitle, statusName, intensityTitle, intensity, colourTitle, colourName;

        public DataAnalysisHolder(View itemView) {
            super(itemView);

            lampNo = itemView.findViewById(R.id.lampName);
            ssidTitle = itemView.findViewById(R.id.ssidNameTitle);
            ssidName = itemView.findViewById(R.id.ssidAnswer);
            statusTitle = itemView.findViewById(R.id.statusTitle);
            statusName = itemView.findViewById(R.id.statusAnswer);
            intensityTitle = itemView.findViewById(R.id.intensityTitle);
            intensity = itemView.findViewById(R.id.intensityAnswer);
            colourTitle = itemView.findViewById(R.id.colourTitle);
            colourName = itemView.findViewById(R.id.colourAnswer);

            //Set all title to default text
            ssidTitle.setText("SSID Name:");
            statusTitle.setText("Status:");
            intensityTitle.setText("Intensity:");
            colourTitle.setText("Colour:");
        }
    }
}
