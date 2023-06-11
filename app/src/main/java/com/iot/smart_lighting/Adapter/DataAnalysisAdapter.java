package com.iot.smart_lighting.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iot.smart_lighting.Model.LampColourModel;
import com.iot.smart_lighting.Model.LampModel;
import com.iot.smart_lighting.R;

import java.util.List;

public class DataAnalysisAdapter extends RecyclerView.Adapter<DataAnalysisAdapter.DataAnalysisHolder> {

    private List<LampModel> lampData;
    private List<LampColourModel> lampColourData;
    private Context context;

    @Override
    public DataAnalysisHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_analysis_list_item, parent, false);
        return new DataAnalysisHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAnalysisHolder holder, int position) {
        LampModel lamp = lampData.get(position);
        LampColourModel lampColour = lampColourData.get(position);

        // Fetch data and Set it
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DataAnalysisHolder extends RecyclerView.ViewHolder {

        TextView lampNo, ssidTitle, ssidName, statusTitle, status, intensityTitle, intensity, colourTitle, colour;

        public DataAnalysisHolder(View itemView) {
            super(itemView);

            lampNo = itemView.findViewById(R.id.lampName);
            ssidTitle = itemView.findViewById(R.id.ssidNameTitle);
            ssidName = itemView.findViewById(R.id.ssidAnswer);
            statusTitle = itemView.findViewById(R.id.statusTitle);
            status = itemView.findViewById(R.id.statusAnswer);
            intensityTitle = itemView.findViewById(R.id.intensityTitle);
            intensity = itemView.findViewById(R.id.intensityAnswer);
            colourTitle = itemView.findViewById(R.id.colourTitle);
            colour = itemView.findViewById(R.id.colourAnswer);

            //Set all title to default text
            ssidTitle.setText("SSID Name:");
            statusTitle.setText("Status:");
            intensityTitle.setText("Intensity:");
            colourTitle.setText("Colour:");
        }
    }
}
