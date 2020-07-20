package com.application.presidentapplication.Fragments.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DialogMapFragment extends DialogFragment {

    ArrayList<String> mapArray = new ArrayList<>();


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String X = getArguments().getString("X"), Y = getArguments().getString("Y");
        findnavi(X,Y);
        findtaxi(X,Y);
        findyandex(X,Y);
        findgoogle(X,Y);

        final String[] maps =  mapArray.toArray(new String[mapArray.size()]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Открыть с помощью приложения")
                .setItems(maps, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (maps[which].equals("Яндекс Навигатор")) {
                            Intent navigator = new Intent(Intent.ACTION_VIEW, Uri.parse("yandexnavi://build_route_on_map?lat_to=" + X + "&lon_to=" + Y));
                            navigator.setPackage("ru.yandex.yandexnavi");
                            startActivity(navigator);
                        } else if (maps[which].equals("Яндекс Такси")) {
                            Intent taxi = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + X + ", " + Y));
                            taxi.setPackage("ru.yandex.taxi");
                            startActivity(taxi);
                        } else if (maps[which].equals("Яндекс Карта")) {
                            Intent ymap = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + X + ", " + Y));
                            ymap.setPackage("ru.yandex.yandexmaps");
                            startActivity(ymap);
                        } else {
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + X + ", " + Y + "?z=16&q=" + X + ", " + Y));
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }
                });

        return builder.create();
    }

    private void findnavi(String X, String Y){
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("yandexnavi://build_route_on_map?lat_to=" + X + "&lon_to=" + Y));
        intent.setPackage("ru.yandex.yandexnavi");
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean navigator = activities.size() > 0;
        if (navigator)
        {
            mapArray.add("Яндекс Навигатор");
        }
    }

    private void findgoogle(String X, String Y){
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + X + ", " + Y));
        intent.setPackage("com.google.android.apps.maps");
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean gmap = activities.size() > 0;
        if (gmap)
        {
            mapArray.add("Google Карта");
        }
    }

    private void findtaxi(String X, String Y){
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + X + ", " + Y));
        intent.setPackage("ru.yandex.yandexmaps");
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean ymap = activities.size() > 0;
        if (ymap)
        {
            mapArray.add("Яндекс Карта");
        }
    }

    private void findyandex(String X, String Y) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + X + ", " + Y));
        intent.setPackage("ru.yandex.taxi");
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean taxi = activities.size() > 0;
        if (taxi) {
            mapArray.add("Яндекс Такси");
        }
    }

}
