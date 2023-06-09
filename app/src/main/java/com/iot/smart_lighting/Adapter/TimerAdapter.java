package com.iot.smart_lighting.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.iot.smart_lighting.Model.SmartLampDB;
import com.iot.smart_lighting.R;

import java.util.ArrayList;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerHolder> {

    private Context context;
    private ArrayList time;

    public TimerAdapter(Context context, ArrayList time) {
        this.context = context;
        this.time = time;
    }

    @Override
    public TimerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timer_list_item, parent, false);
        return new TimerHolder(view);
    }

    @Override
    public void onBindViewHolder(TimerHolder holder, int position) {
        holder.timeName.setText(String.valueOf(time.get(position)));
    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class TimerHolder extends RecyclerView.ViewHolder {

        TextView timeName;
        Switch switchTimer;

        public TimerHolder(View itemView) {
            super(itemView);

            timeName = itemView.findViewById(R.id.lampName);
            switchTimer = itemView.findViewById(R.id.timerSwitchBtn);

            if (switchTimer.isChecked()) {
                Toast.makeText(context, "Your timer is turn on!!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Your timer is turn off!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
