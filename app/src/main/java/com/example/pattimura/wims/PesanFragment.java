package com.example.pattimura.wims;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pattimura.wims.Adapter.AdapterListChat;
import com.example.pattimura.wims.Model.ListChat;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PesanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PesanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PesanFragment extends Fragment {
    ArrayList<ListChat> daftarChat;
    AdapterListChat adapter;
    ListView lv;

    public PesanFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ListChat a = new ListChat("Graham","user","","",0);
        ListChat b = new ListChat("SMA 1 Bandung","grup","","",0);
        daftarChat = new ArrayList<>();
        View v = inflater.inflate(R.layout.fragment_pesan, container, false);
        adapter = new AdapterListChat(daftarChat, PesanFragment.this.getContext());
        lv = (ListView) v.findViewById(R.id.listPesan);
        lv.setAdapter(adapter);
        daftarChat.add(a);
        daftarChat.add(b);
        adapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != daftarChat.size()) {
                    ListChat b = daftarChat.get(position);
                    //Toast.makeText(BandListFragment.this.getContext(), b.getDesc(), Toast.LENGTH_SHORT).show();
                    /*Intent i = new Intent(PesanFragment.this.getContext(), DetailBand.class);
                    i.putExtra("namaband", b.getName());
                    i.putExtra("descband", b.getDesc());
                    i.putExtra("imgurl", b.getImg_url());
                    startActivity(i);*/
                }
            }
        });
        return v;
    }


}
