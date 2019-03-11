package numazu.apppengadilan.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import numazu.apppengadilan.R;

public class FilterActivity extends AppCompatActivity {

    private static final String[] Filter = {
            "Nama",
            "NPM",
            "Tanggal Lahir",
            "Usia"
    };

    ImageView date1, date2;
    LinearLayout line1, line2;
    Spinner spfilter;
    EditText tgl1, tgl2, usia, npm, nama;
    TextView sampai;
    Button btn_oke;
    SharedPreferences shared;

    static final int Date_Dialog_ID=1;
    static final int Date_Dialog_ID1=2;
    private DatePickerDialog.OnDateSetListener from_dateListener, to_dateListener;
    private int day,month,year;
    int f_year, f_month, f_day, t_year, t_month, t_day;
    int cur = 0;
    private long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

//        spfilter = (Spinner) findViewById(R.id.spinner);
        tgl1 = (EditText) findViewById(R.id.tanggaldari);
        tgl2 = (EditText) findViewById(R.id.tanggalke);
        usia = (EditText) findViewById(R.id.usia);
        nama = (EditText) findViewById(R.id.nama);
        npm = (EditText) findViewById(R.id.npm);
        sampai = (TextView) findViewById(R.id.sampai);
        line1 = (LinearLayout) findViewById(R.id.line1);
        line2 = (LinearLayout) findViewById(R.id.line2);
        date1 = (ImageView) findViewById(R.id.date1);
        date2 = (ImageView) findViewById(R.id.date2);
        btn_oke = (Button) findViewById(R.id.btn_ok);

        shared = getSharedPreferences("DataPrivate", Context.MODE_PRIVATE);

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_Dialog_ID);
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_Dialog_ID1);
            }
        });

        btn_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actionnya();
            }
        });

        from_dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear,
                                  int dayOfMonth) {

                year=year1;
                day=dayOfMonth;
                month=monthOfYear;
                tgl1.setText(new StringBuilder().append(year)
                        .append("-").append(month + 1).append("-").append(day));

            }
        };

        to_dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear,
                                  int dayOfMonth) {

                year=year1;
                day=dayOfMonth;
                month=monthOfYear;
                tgl2.setText(new StringBuilder().append(year)
                        .append("-").append(month + 1).append("-").append(day));

            }
        };
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

        case Date_Dialog_ID:
            System.out.println("onCreateDialog  : " + id);
            cur = Date_Dialog_ID;
            return new DatePickerDialog(this, from_dateListener, f_year, f_month,
                    f_day);

        case Date_Dialog_ID1:
            cur = Date_Dialog_ID1;
            System.out.println("onCreateDialog2  : " + id);
            return new DatePickerDialog(this, to_dateListener, t_year, t_month,
                    t_day);
        }
        return null;
    }

    private void Actionnya(){
        String namanya = nama.getText().toString();
        String npmnya = npm.getText().toString();
        String tgldarinya = tgl1.getText().toString();
        String tglkenya = tgl2.getText().toString();
        String usianya = usia.getText().toString();

        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.putString("namaKey", namanya);
        editor.putString("npmKey", npmnya);
        editor.putString("tgldariKey", tgldarinya);
        editor.putString("tglkeKey", tglkenya);
        editor.putString("usiaKey", usianya);
        editor.commit();

        nama.setText("");
        npm.setText("");
        tgl1.setText("");
        tgl2.setText("");
        usia.setText("");

        Intent intent = new Intent(FilterActivity.this, DataActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

}
