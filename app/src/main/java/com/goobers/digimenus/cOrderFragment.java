package com.goobers.digimenus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import Business.iItem;

/**
 * Created by tyler on 12/23/2014.
 */
public class cOrderFragment extends Fragment implements View.OnClickListener
{
    Button requestbutton;
    private ListView myitems;
    private ArrayAdapter list_adapter;
    private ArrayList<String> foodnames;
    private List<iItem> _items;

    private TextView txtparty;
    private TextView txttable;
    private TextView txtcost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        requestbutton = (Button) rootView.findViewById(R.id.requestbutton);
        requestbutton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.requestbutton:
                SendRealRequest r = new SendRealRequest();
                r.execute();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        myitems = (ListView) getView().findViewById(R.id.listView);
        List<iItem> items = cMenu.GetOrderedItems();
        _items = new ArrayList<iItem>();
        _items = items;
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

        myitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                cViewItemFragment frag = new cViewItemFragment();
                frag.SetItem(_items.get(position));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).addToBackStack(null).commit();
            }
        });
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

    private static class SendRealRequest extends AsyncTask<String, byte[], Boolean>
    {
        Socket socket;
        InputStream is;
        OutputStream os;
        BufferedReader bio;
        BufferedWriter bwo;
        protected Boolean doInBackground(String... args)
        {
            boolean result = false;
            try
            {
                SocketAddress address = new InetSocketAddress("192.168.1.2", 4444);
                socket = new Socket();
                socket.connect(address, 5000);
                if(socket.isConnected())
                {

                    bio = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bwo = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bwo.write("Hello");
                    bwo.flush();

                   // is = socket.getInputStream();
                   //os = socket.getOutputStream();
                   //byte[] buffer = new byte[256];

                    String incoming = "";
                    boolean b = false;
                    //int read = is.read(buffer, 0, 1);
                    while(!b)
                    {
                        incoming = bio.readLine();
                        if(incoming != "")
                            b = true;
                        //byte[] tempdata = new byte[read];
                       // System.arraycopy(buffer, 0, tempd ata, 0, read);
                       // publishProgress(tempdata);
                        //Log.i("AsyncTask", "doInBackground: Got some data");
                        //read = is.read(buffer, 0, 4096);
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
                Log.i("AsyncTask", "doInBackground: IOException");
                result = true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.i("AsyncTask", "doInBackground: Exception");
                result = true;
            }

            finally
            {
                try
                {
                    is.close();
                    os.close();
                    socket.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            return result;
        }

        protected void onProgressUpdate(byte[]... values)
        {
            if(values.length > 0)
            {

            }
        }
    }

}
