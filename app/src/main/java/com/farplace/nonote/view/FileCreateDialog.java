package com.farplace.nonote.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.farplace.nonote.R;
import com.farplace.nonote.data.MainData;
import com.farplace.nonote.util.FileUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

public class FileCreateDialog extends MaterialAlertDialogBuilder {

    View view;
    public CreateCallBack createCallBack;
    AlertDialog myDialog;

    public interface CreateCallBack {
        void onCreate(String path);
    }

    public FileCreateDialog(@NonNull Context context, CreateCallBack createCallBack) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.file_create_dialog_layout, null, false);
        setView(view);
        this.createCallBack = createCallBack;
    }

    public void initView() {
        TextInputEditText fileName = view.findViewById(R.id.file_name_input);
        MaterialButton add = view.findViewById(R.id.add_button);
        add.setOnClickListener(v -> {
            String name = fileName.getText().toString();
            if (name.length() == 0) {
                fileName.setError(getContext().getString(R.string.empty_error));
            } else {
                String filePath = MainData.workFolder + File.separator + "note" + File.separator + name + ".md";
                if (new File(filePath).exists()) {
                    Toast.makeText(getContext(), R.string.file_exist, Toast.LENGTH_SHORT).show();
                    createCallBack.onCreate(null);
                } else {
                    FileUtil.saveTextFile(filePath, "test");
                    createCallBack.onCreate(filePath);
                }
                myDialog.dismiss();
            }
        });
    }

    @NonNull
    @Override
    public AlertDialog create() {
        myDialog = super.create();
        return myDialog;
    }
}
