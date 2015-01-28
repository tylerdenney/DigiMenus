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
    Button submitbutton;
    private static boolean listening = false;
    private ListView myitems;
    private ArrayAdapter list_adapter;
    private ArrayList<String> foodnames;
    private List<iItem> _items;

    private TextView txtparty;
    private TextView txttable;
    private TextView txtcost;
    private static TextView txtrequeststatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        requestbutton = (Button) rootView.findViewById(R.id.requestbutton);
        requestbutton.setOnClickListener(this);

        submitbutton = (Button) rootView.findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.requestbutton:
                if(listening == false)
                {
                    listening = true;
                    SendRealRequest r = new SendRealRequest(2);
                    r.execute();
                }
                break;
            case R.id.submitbutton:
                if(listening == false)
                {
                    listening = true;
                    SendRealRequest s = new SendRealRequest(1);
                    s.execute();
                }
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
        txtrequeststatus =(TextView)getActivity().findViewById(R.id.txtrequest);

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

    //Subclass for AsyncTask that will run on a separate thread to not lock up system.
    private static class SendRealRequest extends AsyncTask<String, Integer, Boolean>
    {
        Socket socket;
        BufferedReader bio;
        BufferedWriter bwo;
        //1 is order 2 is request
        int type;
        public SendRealRequest(int type)
        {
            this.type = type;
        }
        protected Boolean doInBackground(String... args)
        {
            boolean result = false;
            try
            {
                SocketAddress address = new InetSocketAddress("192.168.1.2", 4444);
                socket = new Socket();
                socket.connect(address, 5000);//5000ms timeout
                if(socket.isConnected())
                {
                    bio = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bwo = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    publishProgress(2);
                    //TODO: format data to be sent

                    if(type == 1)
                    {
                        String stringorder = cMenu.SubmitOrderOnClick();
                        if(stringorder.contains("Error"))
                        {
                            publishProgress(4);
                            throw(new Exception());

                        }
                        else
                        {
                            bwo.write("ORDER: " + stringorder);
                            bwo.flush();
                        }
                    }
                    if(type == 2)
                    {
                        int tablenum = cMenu.GetTableNum();
                        String tablestring = String.valueOf(tablenum);
                        bwo.write("REQUEST: " +tablestring);
                        bwo.flush();
                    }

                    String incoming = "";
                    boolean done = false;

                    while(!done)
                    {
                        incoming = bio.readLine();
                        if(incoming != "")//if it reads a newline/nothing
                            done = true;
                    }
                    publishProgress(1);//When connection is done, update ui and close connection
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
                publishProgress(3);
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
                    listening = false;
                    bio.close();
                    bwo.close();
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

        protected void onProgressUpdate(Integer... values)
        {
            if(values[0] == 1)
            {
                txtrequeststatus.setText("Message Received");

            }
            else if(values[0] == 2)
            {
                txtrequeststatus.setText("Awaiting Confirmation");
            }
            else if(values[0] == 3)
            {
                txtrequeststatus.setText("Error..Could Not Connect");
            } else if(values[0] == 4)
            {
                txtrequeststatus.setText("Error..Order is missing information");
            }

        }
    }

}
