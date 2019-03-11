package numazu.apppengadilan.views;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;
import numazu.apppengadilan.R;
import numazu.apppengadilan.controller.Table;
import numazu.apppengadilan.model.ApiURL;

public class DataActivity extends AppCompatActivity {

    TableView<String[]> tb;
    Table table;
    private long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        table = new Table(this);
        tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(6);
        tb.setHeaderBackgroundColor(Color.WHITE);
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, table.getHeader()));
        int colorEvenRows = getResources().getColor(R.color.green);
        int colorOddRows = getResources().getColor(R.color.teal);
        tb.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));

        TableColumnWeightModel columnModel = new TableColumnWeightModel(6);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 2);
        tb.setColumnModel(columnModel);

        fething();
    }

    private void fething(){
        if(CheckNetwork()){
            new ApiURL(DataActivity.this).retrieve(tb);
        }else if (!CheckNetwork()) {
            Toast.makeText(this, "Network Disconnected", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean CheckNetwork(){
        boolean WIFI = false;
        boolean DATA_MOBILE = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos){
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    WIFI = true;

            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    DATA_MOBILE = true;
        }

        return WIFI||DATA_MOBILE;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
