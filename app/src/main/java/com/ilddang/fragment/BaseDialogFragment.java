package com.ilddang.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ilddang.R;

public class BaseDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button mFirstButton;
    private Button mSecondButton;
    private String mFirstText;
    private String mSecondText;

    public BaseDialogFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_dialog_fragment, container);
        mFirstButton = view.findViewById(R.id.dialog_first_button);
        mFirstButton.setText(mFirstText);
        mSecondButton = view.findViewById(R.id.dialog_second_button);
        mSecondButton.setText(mSecondText);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static BaseDialogFragment getInstance() {
        BaseDialogFragment dialogFragment = new BaseDialogFragment();

        return dialogFragment;
    }


    public void setFirstButtonText(String text) {
        mFirstText = text;
    }

    public void setSecondButtonText(String text) {
        mSecondText = text;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_first_button) {
        } else if (view.getId() == R.id.dialog_second_button) {
            
        }
    }
}
