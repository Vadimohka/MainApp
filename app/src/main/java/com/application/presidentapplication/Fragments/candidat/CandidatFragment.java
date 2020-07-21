package com.application.presidentapplication.Fragments.candidat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.application.presidentapplication.Activities.CherechenActivity;
import com.application.presidentapplication.Activities.DmitrievActivity;
import com.application.presidentapplication.Activities.KonopackayaActivity;
import com.application.presidentapplication.Activities.LukashenkoActivity;
import com.application.presidentapplication.Activities.TihanovskayaActivity;
import com.application.presidentapplication.Adapters.CandidatAdapter;
import com.application.presidentapplication.Models.Candidat;
import com.application.presidentapplication.R;

import java.util.ArrayList;


public class CandidatFragment extends Fragment {

    ArrayList<Candidat> candidatList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_candidat, container, false);
        candidatList.add(new Candidat("Лукашенко","Александр Григорьевич",R.drawable.lukashenko));
        //candidatList.add(new Candidat("Бабарико","Виктор Дмитриевич",R.drawable.babarico));
        //candidatList.add(new Candidat("Цепкало","Валерий Вильямович",R.drawable.cepkalo));
        candidatList.add(new Candidat("Тихановская","Светлана Георгиевна",R.drawable.tihanovskaya));
        candidatList.add(new Candidat("Дмитриев","Андрей Владимирович",R.drawable.dmitriev));
        candidatList.add(new Candidat("Черечень","Сергей Владимирович",R.drawable.cherechen));
        candidatList.add(new Candidat("Конопацкая","Анна Анатольевна",R.drawable.konopackaya));

        GridView GList = root.findViewById(R.id.gridview_candidat);
        CandidatAdapter adapter = new CandidatAdapter(root.getContext(), candidatList);
        GList.setAdapter(adapter);

        GList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((int)id)
                {
                    case 0:
                        Intent lukashenko = new Intent(root.getContext(), LukashenkoActivity.class);
                        startActivity(lukashenko);
                        break;
                    case 1:
                        Intent tihanovskaya = new Intent(root.getContext(), TihanovskayaActivity.class);
                        startActivity(tihanovskaya);
                        break;
                    case 2:
                        Intent dmitriev = new Intent(root.getContext(), DmitrievActivity.class);
                        startActivity(dmitriev);
                        break;
                    case 3:
                        Intent cherechen = new Intent(root.getContext(), CherechenActivity.class);
                        startActivity(cherechen);
                        break;
                    case 4:
                        Intent konopackaya = new Intent(root.getContext(), KonopackayaActivity.class);
                        startActivity(konopackaya);
                        break;
                    default:
                        break;
                }

            }
        });

        return root;
    }

}