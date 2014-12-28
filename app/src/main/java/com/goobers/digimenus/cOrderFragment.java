package com.goobers.digimenus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Business.iItem;

/**
 * Created by tyler on 12/23/2014.
 */
public class cOrderFragment extends Fragment
{
    public ListView myitems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        myitems = (ListView) getView().findViewById(R.id.listView);
        List<iItem> items = cMenu.GetOrderedItems();
        if(items != null)
        {
            List<String> foodnames = new ArrayList<String>();
            for (iItem i : items)
            {
                foodnames.add(i.GetName());
            }
            ListAdapter list_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_text_layout, foodnames);
            myitems.setAdapter(list_adapter);
        }
    }

}
