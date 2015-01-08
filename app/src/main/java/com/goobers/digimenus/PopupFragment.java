package com.goobers.digimenus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by tyler on 12/20/2014.
 */
public class PopupFragment extends DialogFragment
{
    public interface EditDialogListener{
        void onFinishEditDialog(int input);
    }
    public PopupFragment()
    {}
    EditDialogListener activity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        String title = args.getString("title", "");
        String message = args.getString("message", "");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setView(input)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Editable value = input.getText();
                        if (!input.getText().toString().matches(""))
                        {
                            int intval = Integer.parseInt(input.getText().toString());
                            activity = (EditDialogListener) getActivity();
                            activity.onFinishEditDialog(intval);
                            getDialog().dismiss();
                        }
                        else
                            getDialog().dismiss();
                    }
                })

                .create();
    }
}
