package com.farplace.nonote;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.farplace.nonote.data.MainData;
import com.farplace.nonote.databinding.ActivityMainBinding;
import com.farplace.nonote.util.FileUtil;
import com.farplace.nonote.util.NoteUtil;
import com.farplace.nonote.util.StringUtil;
import com.farplace.nonote.view.FileCreateDialog;
import com.farplace.nonote.view.MenuPopupView;
import com.farplace.nonote.view.VditorWebView;
import com.google.android.material.button.MaterialButton;

import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private VditorWebView vditorWebView;
    private String currentFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        transparentNavBar(getWindow());
        transparentStatusBar(getWindow());
        initView();
        initData();
    }

    private void initData() {
        new MainData().init(this);
        String current_File = getSharedPreferences("DATA", MODE_PRIVATE).getString("currentFile", null);
        if (current_File != null) {
            this.currentFile = current_File;
            getSupportActionBar().setTitle(NoteUtil.getNoteName(new File(currentFile).getName()));
        }
    }

    private void initView() {
        vditorWebView = findViewById(R.id.vditor_view);
        MaterialButton add_bu = findViewById(R.id.add_button);
        add_bu.setOnClickListener(v -> {
            MenuPopupView menuPopupView = new MenuPopupView(this, v);
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            List<File> notes = new NoteUtil().getNotes();
            View.OnClickListener file_click = v12 -> {
                openNote(MainData.workFolder + File.separator + "note" + File.separator + v12.getTag());
            };
            for (File note : notes) {
                String name = note.getName();
                TextView note_name_text = (TextView) layoutInflater.inflate(R.layout.popup_menu_item_view, null, false);
                note_name_text.setTag(note.getName());
                note_name_text.setOnClickListener(file_click);
                note_name_text.setText(NoteUtil.getNoteName(name));
                menuPopupView.addItemView(note_name_text);
            }
            TextView add_text = (TextView) layoutInflater.inflate(R.layout.popup_menu_item_view, null, false);
            add_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.round_add_24, 0, 0, 0);
            menuPopupView.addItemView(add_text);
            add_text.setOnClickListener(v1 -> {
                showFileCreate();
            });
            menuPopupView.showMenuWindow(-100, 0);
        });
        View.OnClickListener helper_click = v -> {
            vditorWebView.insertValue(((String) v.getTag()), true);
        };
        int[] helper_button = new int[]{R.id.tag_insert_bu, R.id.format_insert_bu, R.id.space_insert_bu, R.id.tab_insert_bu};
        for (int bu_id : helper_button) {
            MaterialButton help_bu = findViewById(bu_id);
            help_bu.setOnClickListener(helper_click);
        }
        MaterialButton save_bu = findViewById(R.id.save_bu);
        save_bu.setOnClickListener(v -> {
            if (currentFile == null) {
                showFileCreate();
            } else {
                vditorWebView.getValue(value -> {
                    value = value.substring(1);
                    value = value.substring(0, value.length() - 1);
                    value = StringEscapeUtils.unescapeJava(value);
                    FileUtil.saveTextFile(currentFile, value);
                });

            }
        });
    }

    private void showFileCreate() {

        FileCreateDialog fileCreateDialog = new FileCreateDialog(MainActivity.this, new FileCreateDialog.CreateCallBack() {
            @Override
            public void onCreate(String path) {

            }
        });

        fileCreateDialog.show();
        fileCreateDialog.initView();
    }

    public void transparentStatusBar(@NonNull final Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        int vis = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(option | vis);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.linearStartColor));
    }

    public void transparentNavBar(@NonNull final Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false);
        }
        window.setNavigationBarColor(Color.TRANSPARENT);
        View decorView = window.getDecorView();
        int vis = decorView.getSystemUiVisibility();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(vis | option);
    }

    private void openNote(String noteFile) {
        String content = FileUtil.loadTextFile(noteFile);
        content = StringUtil.escape(content);
        String noteName = NoteUtil.getNoteName(new File(noteFile).getName());
        getSupportActionBar().setTitle(noteName);
        getSharedPreferences("DATA", MODE_PRIVATE).edit().putString("currentFile", noteFile).apply();
        vditorWebView.setValue(content);
    }
}