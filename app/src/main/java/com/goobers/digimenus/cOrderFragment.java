package com.goobers.digimenus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Business.iItem;

/**
 * Created by tyler on 12/23/2014.
 */
public class cOrderFragment extends Fragment
{
    private ListView myitems;
    private ArrayAdapter list_adapter;
    private ArrayList<String> foodnames;

    private TextView txtparty;
    private TextView txttable;
    private TextView txtcost;
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
            foodnames = new ArrayList<String>();
            for (iItem i : items)
            {
                foodnames.add(i.GetName());
            }
            list_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_text_layout, foodnames);
            myitems.setAdapter(list_adapter);
        }
        registerForContextMenu(myitems);
        txtparty =(TextView)getActivity().findViewById(R.id.txtpartysize);
        txttable =(TextView)getActivity().findViewById(R.id.txttablenum);
        txtcost =(TextView)getActivity().findViewById(R.id.txttotalcost);

        txtcost.setText(getString(R.string.total_cost) + String.valueOf(cMenu.GetCost()));
        txttable.setText(getString(R.string.table_num) + String.valueOf(cMenu.GetTableNum()));
        txtparty.setText(getString(R.string.party_size) + String.valueOf(cMenu.GetPartySize()));


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Options");
            String[] menuItems = getResources().getStringArray(R.array.context_menu_array);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String itemname = myitems.getAdapter().getItem(info.position).toString();
        cMenu.RemoveItemOnClick(itemname);
        foodnames.remove(info.position);
        list_adapter.notifyDataSetChanged();
        txtcost.setText(getString(R.string.total_cost) + String.valueOf(cMenu.GetCost()));

        return true;
    }

}
