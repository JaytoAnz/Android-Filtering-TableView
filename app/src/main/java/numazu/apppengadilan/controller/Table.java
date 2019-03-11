package numazu.apppengadilan.controller;

import android.content.Context;

import java.util.ArrayList;

import numazu.apppengadilan.model.MDatanya;

public class Table {

    Context c;
    private String[] header = {"ID", "NAMA", "ALAMAT", "TGL LAHIR", "NO. HP", "USIA"};
    private String[][] row;

    public Table(Context c){
        this.c = c;
    }

    public String[] getHeader() {
        return header;
    }

    public String[][] returnRowArray(ArrayList<MDatanya> mDatanyas){
        row = new String[mDatanyas.size()][6];
        MDatanya m;

        for (int i=0; i<mDatanyas.size(); i++){
            m = mDatanyas.get(i);

            row[i][0] = m.getId();
            row[i][1] = m.getNama();
            row[i][2] = m.getAlamat();
            row[i][3] = m.getTgl_lahir();
            row[i][4] = m.getNo_hp();
            row[i][5] = m.getUsia();
        }
        return row;
    }
}
