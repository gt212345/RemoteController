package hrw.remotecontrol.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import hrw.remotecontrol.IntroActivity;
import hrw.remotecontrol.R;

/**
 * Created by heiruwu on 9/9/15.
 */
public class DialogFragment extends android.app.DialogFragment implements TextWatcher {
    String edit;

    public interface EditListener {
        void onEditFinished(String edit);
    }

    public static DialogFragment newInstance() {
        DialogFragment dialogFragment = new DialogFragment();
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        EditText editText = (EditText) view.findViewById(R.id.editIp);
        editText.addTextChangedListener(this);
        builder.setView(view).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditListener listener = (IntroActivity) getActivity();
                listener.onEditFinished(edit);
            }
        });
        return builder.create();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        edit = charSequence.toString();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
