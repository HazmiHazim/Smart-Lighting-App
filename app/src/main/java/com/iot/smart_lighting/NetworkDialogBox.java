package com.iot.smart_lighting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NetworkDialogBox extends AppCompatDialogFragment {

    EditText editNetworkName, editNetworkKey;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.network_dialog_box, null);

        builder.setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Hande Save Button
            }
        });

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setTitle("Edit Network");
        builder.setCancelable(true);

        editNetworkName = view.findViewById(R.id.networkSsidName);
        editNetworkKey = view.findViewById(R.id.networkSsidKey);

        return builder.create();
    }
}
