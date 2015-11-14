package com.example.mateuspalhares.projetodeengenhariadesoftware;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * Created by mateuspalhares on 13/11/15.
 */
public class NomeFragment extends DialogFragment {

    nameDialogListener nameListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_name, null))
                // Add action buttons
                .setPositiveButton(R.string.enviar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        nameListener.onEnviarPressed();
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NomeFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            nameListener = (nameDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"Tem que implementar nameDialogListener");
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        Toast.makeText(getActivity(),"Direcionando para o inicio do jogo",Toast.LENGTH_SHORT).show();
    }

    public interface nameDialogListener{
        public void onEnviarPressed();
        public void onCancelarPressed();
    }
}