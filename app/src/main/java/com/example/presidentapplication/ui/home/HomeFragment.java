package com.example.presidentapplication.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.presidentapplication.SpotActivity;
import com.example.presidentapplication.MainActivity;
import com.example.presidentapplication.R;
import com.example.presidentapplication.SpinnerAdapter1;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    List<String> lstSource1 = new ArrayList<>();
    List<String> lstSource2 = new ArrayList<>();
    List<String> lstSource3 = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // create spinner Area
        startData();
        Spinner spinner1 = root.findViewById(R.id.spinner1);
        SpinnerAdapter adapter1 = new SpinnerAdapter1(lstSource1, this.getActivity());
        spinner1.setAdapter(adapter1);


        //create input region
        insertDataBrest();
        insertDataGomel();
        insertDataGrodno();
        insertDataMinsk();
        insertDataMogilev();
        insertDataVitebsk();
        final AutoCompleteTextView autoCompleteTextViewRegion = root.findViewById(R.id.autocompleteRegion);
        ArrayAdapter<String> adapterRegion =
                new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, lstSource2);
        autoCompleteTextViewRegion.setAdapter(adapterRegion);


        // autocomlpete city
        ArrayList<String> cities= MainActivity.citylist;
        final AutoCompleteTextView autoCompleteTextViewCity = root.findViewById(R.id.autocompleteCity);
        ArrayAdapter<String> adapterCity =
                new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, cities);
        autoCompleteTextViewCity.setAdapter(adapterCity);

        // autocomplete street
        ArrayList<String> streets= MainActivity.streetlist;
        final AutoCompleteTextView autoCompleteTextViewStreet = root.findViewById(R.id.autocompleteStreet);
        ArrayAdapter<String> adapterStreet =
                new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, streets);
        autoCompleteTextViewStreet.setAdapter(adapterStreet);

        // visible off
        autoCompleteTextViewCity.setVisibility(View.GONE);
        autoCompleteTextViewStreet.setVisibility(View.GONE);

        lstSource3.add("1");
        lstSource3.add("2");
        lstSource3.add("4");
        lstSource3.add("4Б");
        final AutoCompleteTextView autoCompleteTextViewHouse = root.findViewById(R.id.autocompleteHouse);
        ArrayAdapter<String> adapterHouse =
                new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, lstSource3);
        autoCompleteTextViewHouse.setAdapter(adapterHouse);

        Button find = root.findViewById(R.id.button_find);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpotActivity.class);
                intent.putExtra("SpotId", MainActivity.spotId);
                startActivity(intent);
            }
        });


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch ((int)id)
                {
                    case 0:
                        //lstSource2.clear();
                        //spinner2.setVisibility(View.GONE);
                        autoCompleteTextViewRegion.setVisibility(View.GONE);
                        autoCompleteTextViewCity.setVisibility(View.GONE);
                        autoCompleteTextViewStreet.setVisibility(View.VISIBLE);
                        autoCompleteTextViewHouse.setVisibility(View.VISIBLE);
                        break;

//                    case 1:
//                        lstSource2.clear();
//                        autoCompleteTextViewCity.setVisibility(View.GONE);
//                        autoCompleteTextViewStreet.setVisibility(View.GONE);
//                        //insertDataMinsk();
//                        //spinner2.setVisibility(View.VISIBLE);
//                        break;
//
//                    case 2:
//                        lstSource2.clear();
//                        autoCompleteTextViewCity.setVisibility(View.GONE);
//                        autoCompleteTextViewStreet.setVisibility(View.GONE);
//                        //insertDataGomel();
//                        //spinner2.setVisibility(View.VISIBLE);
//                        break;
//
//                    case 3:
//                        autoCompleteTextViewCity.setVisibility(View.GONE);
//                        autoCompleteTextViewStreet.setVisibility(View.GONE);
//                        //insertDataVitebsk();
//                        //spinner2.setVisibility(View.VISIBLE);
//                        break;
//
//                    case 4:
//                        lstSource2.clear();
//                        autoCompleteTextViewCity.setVisibility(View.GONE);
//                        autoCompleteTextViewStreet.setVisibility(View.GONE);
//                        //insertDataMogilev();
//                        //spinner2.setVisibility(View.VISIBLE);
//                        break;
//
//                    case 5:
//                        lstSource2.clear();
//                        autoCompleteTextViewCity.setVisibility(View.GONE);
//                        autoCompleteTextViewStreet.setVisibility(View.GONE);
//                        //insertDataGrodno();
//                        //spinner2.setVisibility(View.VISIBLE);
//                        break;
//                    case 6:
//                        autoCompleteTextViewCity.setVisibility(View.GONE);
//                        autoCompleteTextViewStreet.setVisibility(View.GONE);
//                        //insertDataBrest();
//                        //spinner2.setVisibility(View.VISIBLE);
//                        break;
                    default:
                        autoCompleteTextViewRegion.setVisibility(View.VISIBLE);
                        autoCompleteTextViewCity.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        return root;
    }


    private void insertDataBrest()
    {
        //lstSource2.clear();
        lstSource2.add("Барановичский");
        lstSource2.add("Берёзовский");
        lstSource2.add("Бресткий");
        lstSource2.add("Ганцевичский");
        lstSource2.add("Дрогичинский");
        lstSource2.add("Жабинковсикй");
        lstSource2.add("Ивановский");
        lstSource2.add("Ивацевичский");
        lstSource2.add("Каменецкий");
        lstSource2.add("Кобринский");
        lstSource2.add("Лунинецкий");
        lstSource2.add("Ляховичский");
        lstSource2.add("Малоритсикй");
        lstSource2.add("Пинский");
        lstSource2.add("Пружанский");
        lstSource2.add("Столинский");
    }

    private void insertDataVitebsk()
    {
        //lstSource2.clear();
        lstSource2.add("Бешенковичский");
        lstSource2.add("Браславский");
        lstSource2.add("Верхнедвинский");
        lstSource2.add("Витебский");
        lstSource2.add("Глубокский");
        lstSource2.add("Городокский");
        lstSource2.add("Докшицкий");
        lstSource2.add("Дубровенский");
        lstSource2.add("Лепельский");
        lstSource2.add("Лиозненский");
        lstSource2.add("Миорский");
        lstSource2.add("Оршанский");
        lstSource2.add("Полоцкий");
        lstSource2.add("Поставский");
        lstSource2.add("Россонский");
        lstSource2.add("Сененский");
        lstSource2.add("Толочинский");
        lstSource2.add("Ушачский");
        lstSource2.add("Чашникский");
        lstSource2.add("Шарковщинский");
        lstSource2.add("Шумилинский");

    }

    private void insertDataMogilev()
    {
        //lstSource2.clear();
        lstSource2.add("Белыничский");
        lstSource2.add("Бобруйский");
        lstSource2.add("Быховский");
        lstSource2.add("Глусский");
        lstSource2.add("Горецкий");
        lstSource2.add("Дрибинский");
        lstSource2.add("Кировский");
        lstSource2.add("Климовичский");
        lstSource2.add("Кличевский");
        lstSource2.add("Костюковичский");
        lstSource2.add("Краснопольский");
        lstSource2.add("Круглянский");
        lstSource2.add("Могилевский");
        lstSource2.add("Мстиславский");
        lstSource2.add("Осиповичский");
        lstSource2.add("Славгородский");
        lstSource2.add("Хотимский");
        lstSource2.add("Чаусский");
        lstSource2.add("Чериковский");
        lstSource2.add("Шкловский");
    }

    private void insertDataMinsk()
    {
        //lstSource2.clear();
        lstSource2.add("Березинский");
        lstSource2.add("Борисовский");
        lstSource2.add("Вилейский");
        lstSource2.add("Воложинский");
        lstSource2.add("Дзержинский");
        lstSource2.add("Клецкий");
        lstSource2.add("Копыльский");
        lstSource2.add("Крупский");
        lstSource2.add("Логойский");
        lstSource2.add("Любанский");
        lstSource2.add("Минский");
        lstSource2.add("Молодечненский");
        lstSource2.add("Мядельский");
        lstSource2.add("Несвижский");
        lstSource2.add("Пуховичский");
        lstSource2.add("Слуцкий");
        lstSource2.add("Смолевичский");
        lstSource2.add("Солигорский");
        lstSource2.add("Стародорожский");
        lstSource2.add("Столбцовский");
        lstSource2.add("Узденский");
        lstSource2.add("Червенский");
    }

    private void insertDataGomel()
    {
        //lstSource2.clear();
        lstSource2.add("Брагинский");
        lstSource2.add("Буда-Кошелевский");
        lstSource2.add("Ветковский");
        lstSource2.add("Гомельский");
        lstSource2.add("Добрушский");
        lstSource2.add("Ельский");
        lstSource2.add("Житковичский");
        lstSource2.add("Жлобинский");
        lstSource2.add("Калинковичский");
        lstSource2.add("Кормянский");
        lstSource2.add("Лельчицкий");
        lstSource2.add("Лоевский");
        lstSource2.add("Мозырский");
        lstSource2.add("Наровлянский");
        lstSource2.add("Октябрьский");
        lstSource2.add("Петриковский");
        lstSource2.add("Речицкий");
        lstSource2.add("Рогачёвский");
        lstSource2.add("Светлогорский");
        lstSource2.add("Хойникский");
        lstSource2.add("Чечерский");
    }

    private void insertDataGrodno()
    {
        //lstSource2.clear();
        lstSource2.add("Берестовицкий");
        lstSource2.add("Волковысский");
        lstSource2.add("Вороновский");
        lstSource2.add("Гродненский");
        lstSource2.add("Дятловский");
        lstSource2.add("Зельвенский");
        lstSource2.add("Ивьевский");
        lstSource2.add("Кореличский");
        lstSource2.add("Лидский");
        lstSource2.add("Мостовский");
        lstSource2.add("Новогрудский");
        lstSource2.add("Островецкий");
        lstSource2.add("Ошмянский");
        lstSource2.add("Свислочский");
        lstSource2.add("Слонимский");
        lstSource2.add("Сморгонский");
        lstSource2.add("Щучинский");
    }

    private void startData()
    {
        lstSource1.add("Минск");
        lstSource1.add("Минская");
        lstSource1.add("Гомельская");
        lstSource1.add("Витебская");
        lstSource1.add("Могилёвская");
        lstSource1.add("Гродненская");
        lstSource1.add("Брестская");
    }


}