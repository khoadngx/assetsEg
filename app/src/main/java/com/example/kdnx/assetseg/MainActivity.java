package com.example.kdnx.assetseg;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView txtFont;
    ListView lvFont;
    ArrayList<String>dsFont;
    ArrayAdapter<String>fontAdapter;

    String archiveName="FontStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents(){
        lvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                xuLyDoiFontChu(position);
            }
        });
    }

    private void xuLyDoiFontChu(int position) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "font/"+dsFont.get(position));
        txtFont.setTypeface(typeface);

        SharedPreferences preferences=getSharedPreferences(archiveName, MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("FONTCHU", "font/"+dsFont.get(position));
        editor.commit();
    }

    private void addControls(){
        txtFont = findViewById(R.id.txtFont);
        lvFont = findViewById(R.id.lvFont);
        dsFont = new ArrayList<>();
        fontAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsFont);
        lvFont.setAdapter(fontAdapter);

        try {
            AssetManager assetManager = getAssets();
            String[] arrFontName = assetManager.list("font");
            dsFont.addAll(Arrays.asList(arrFontName));
            fontAdapter.notifyDataSetChanged();

            SharedPreferences preferences=getSharedPreferences(archiveName, MODE_PRIVATE);
            String font=preferences.getString("FONTCHU","");
            if(font.length()>0){
                Typeface typeface=Typeface.createFromAsset(getAssets(), font);
                txtFont.setTypeface(typeface);
            }
        } catch (Exception ex){
            Log.e("Font error", ex.toString());
        }
    }

}
