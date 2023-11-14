package com.iot.smart_lighting.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iot.smart_lighting.Model.SmartLampDB;
import com.iot.smart_lighting.R;

import java.util.ArrayList;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerHolder> {

    private Context context;
    private ArrayList time;
    private int selectedIndex;
    SmartLampDB myDB;
    SQLiteDatabase sqlDB;

    public TimerAdapter(Context context, ArrayList time) {
        this.context = context;
        this.time = time;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    @Override
    public TimerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timer_list_item, parent, false);
        return new TimerHolder(view);
    }

    @Override
    public void onBindViewHolder(TimerHolder holder, int position) {
        holder.timeName.setText(String.valueOf(time.get(position)));
        int lampId = selectedIndex + 1;
        String timer = String.valueOf(time.get(holder.getAdapterPosition()));
        myDB = new SmartLampDB(context);

        // Perform a background task using AsyncTask  without blocking the main UI
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... Voids) {
                // Use try-finally to ensure db is close no matter what happen
                try {
                    String query = "SELECT * FROM lampTimer WHERE lamp_id = ? AND time = ?";
                    sqlDB = myDB.getReadableDatabase();
                    Cursor cursor = sqlDB.rawQuery(query, new String[]{String.valueOf(lampId), timer});
                    if (cursor.moveToFirst()) {
                        return cursor.getInt(cursor.getColumnIndexOrThrow("status"));
                    }
                } finally {
                    sqlDB.close();
                }
                // Default status if no data is found
                return 0;
            }

            @Override
            protected void onPostExecute(Integer status) {
                holder.switchTimer.setChecked(status == 1);
                if (status == 1) {
                    holder.timeName.setTextColor(Color.parseColor("#6A0DAD"));
                } else {
                    holder.timeName.setTextColor(Color.parseColor("#D9D9D9"));
                }

                holder.switchTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton switchButton, boolean isChecked) {
                        if (isChecked) {
                            updateSwitchStateAsync(lampId, timer, 1);
                            holder.timeName.setTextColor(Color.parseColor("#6A0DAD"));
                        } else {
                            updateSwitchStateAsync(lampId, timer, 0);
                            holder.timeName.setTextColor(Color.parseColor("#D9D9D9"));
                        }
                    }
                });
            }

            private void updateSwitchStateAsync(int lampId, String timer, int newStatus) {
                // Perform the database update in an AsyncTask
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... Voids) {
                        updateSwitchState(lampId, timer, newStatus);
                        return null;
                    }
                }.execute();
            }
        }.execute();
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
        }
    }

    private void updateSwitchState(int lampId, String timer, int newStatus) {
        // Use try-finally to ensure db is close no matter what happen
        try {
            sqlDB = myDB.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("status", newStatus);
            sqlDB.update("lampTimer", cv, "lamp_id = ? AND time = ?", new String[]{String.valueOf(lampId), timer});
        } finally {
            sqlDB.close();
        }
    }
}
