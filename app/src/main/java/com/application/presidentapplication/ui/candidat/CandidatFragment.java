package com.application.presidentapplication.ui.candidat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.application.presidentapplication.Adapters.CandidatAdapter;
import com.application.presidentapplication.Models.Candidat;
import com.application.presidentapplication.R;

import java.util.ArrayList;


public class CandidatFragment extends Fragment {

    ArrayList<Candidat> candidatList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_candidat, container, false);
        candidatList.add(new Candidat("Лукашенко","Александр Григорьевич",R.drawable.lukashenko));
        candidatList.add(new Candidat("Бабарико","Виктор Дмитриевич",R.drawable.babarico));
        candidatList.add(new Candidat("Цепкало","Валерий Вильямович",R.drawable.cepkalo));
        candidatList.add(new Candidat("Тихановская","Светлана Георгиевна",R.drawable.tihanovskaya));
        candidatList.add(new Candidat("Конопацкая","Анна Анатольевна",R.drawable.konopackaya));
        candidatList.add(new Candidat("Дмитриев","Андрей Владимирович",R.drawable.dmitriev));
        candidatList.add(new Candidat("Черечень","Сергей Владимирович",R.drawable.cherechen));

        GridView GList = root.findViewById(R.id.gridview_candidat);
        CandidatAdapter adapter = new CandidatAdapter(root.getContext(), candidatList);
        GList.setAdapter(adapter);

        return root;
    }

}