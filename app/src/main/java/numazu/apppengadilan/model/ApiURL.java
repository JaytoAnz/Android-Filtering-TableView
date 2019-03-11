package numazu.apppengadilan.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import numazu.apppengadilan.R;
import numazu.apppengadilan.controller.Table;

public class ApiURL {

    private static final String URL = "http://192.168.1.7/AppPengadilan/readData.php";
    private final Context c;
    private SimpleTableDataAdapter adapter;
    private SharedPreferences shared;
    private SimpleTableHeaderAdapter simpleTableHeaderAdapter;


    public ApiURL(Context c) {
        this.c = c;
    }

    public void retrieve(final TableView tb) {
        final ArrayList<MDatanya> mDatanyas = new ArrayList<>();
        shared = c.getSharedPreferences("DataPrivate", Context.MODE_PRIVATE);
        String nama = shared.getString("namaKey", "");
        String alamat = shared.getString("alamatKey", "");
        String tglAwal = shared.getString("tgldariKey", "");
        String tglakhir = shared.getString("tglkeKey", "");
        String usia = shared.getString("usiaKey", "");

        AndroidNetworking.get(URL + "?nama=" + nama + "&alamat=" + alamat + "&tgldari=" + tglAwal + "&tglke=" + tglakhir + "&usia=" + usia)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        MDatanya mDatanya;
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean responsestatus = jsonObject.getBoolean("success");
                            Log.d("sasa", "dada " + responsestatus);
                            if (responsestatus){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("no_id");
                                    String nama = object.getString("nama");
                                    String alamat = object.getString("alamat");
                                    String tgl_lahir = object.getString("tgl_lahir");
                                    String no_hp = object.getString("no_hp");
                                    String usia = object.getString("usia");

                                    mDatanya = new MDatanya();
                                    mDatanya.setId(id);
                                    mDatanya.setNama(nama);
                                    mDatanya.setAlamat(alamat);
                                    mDatanya.setTgl_lahir(tgl_lahir);
                                    mDatanya.setNo_hp(no_hp);
                                    mDatanya.setUsia(usia);

                                    mDatanyas.add(mDatanya);
                                }
                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(c, "" + message, Toast.LENGTH_SHORT).show();
                            }

                            //SET_TABLE
                            adapter = new SimpleTableDataAdapter(c, new Table(c).returnRowArray(mDatanyas));
                            adapter.setTextSize(15);
                            adapter.setTextColor(Color.WHITE);
                            adapter.notifyDataSetChanged();
                            tb.setDataAdapter(adapter);

                        }catch (JSONException e){
                            Toast.makeText(c, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Toast.makeText(c, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
