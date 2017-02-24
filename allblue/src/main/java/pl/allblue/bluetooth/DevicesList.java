package pl.allblue.bluetooth;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import pl.allblue.R;


public class DevicesList extends ListActivity {

    static public final String EXTRA_DEVICE =
            "pl.allblue.bluetooth.DevicesList_ExtraDevice";

    static private BluetoothAdapter btAdapter = null;

    private ArrayAdapter<String> btDeviceNames = null;
    private ArrayList<BluetoothDevice> btDevices = null;


//    private static final UUID SPP_UUID = UUID
//            .fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    @Override
    public void onCreate(Bundle saved_instance_state)
    {
        super.onCreate(saved_instance_state);

        this.btDevices = new ArrayList<>();
        this.btDeviceNames = new ArrayAdapter<String>(this, R.layout.list_item_black);
        this.setListAdapter(this.btDeviceNames);

        IntentFilter intent_filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(this.btReceiver, intent_filter);

        this.btAdapter = BluetoothAdapter.getDefaultAdapter();

        for (BluetoothDevice bt_device : this.btAdapter.getBondedDevices()) {
            this.btDevices.add(bt_device);
            this.btDeviceNames.add(bt_device.getName() + "\n"
                    + bt_device.getAddress() + "\n" );
        }

        this.btAdapter.startDiscovery();

        Toast.makeText(getApplicationContext(),
                this.getResources().getString(R.string.bluetooth_GettingDevicesList),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy()
    {
        this.unregisterReceiver(this.btReceiver);
        super.onDestroy();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Log.d("DevicesList", "Position: " + position + ":" + this.btDevices.size());
        BluetoothDevice bt_device = this.btDevices.get(position);

        Intent i = new Intent();
        i.putExtra(DevicesList.EXTRA_DEVICE, this.btDevices.get(position));

        this.setResult(Activity.RESULT_OK, i);
        this.finish();
    }

    private final BroadcastReceiver btReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice bt_device =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Log.d("DevicesList", "Discovered?");

                try {
                    if (btDevices.indexOf(bt_device) < 0) {
                        Log.d("DevicesList", "Adding?");
                        btDevices.add(bt_device);
                        btDeviceNames.add(bt_device.getName() + "\n"
                                + bt_device.getAddress() + "\n" );
                        btDeviceNames.notifyDataSetInvalidated();
                        Log.d("DevicesList", "Jej?");
                    }
                } catch (Exception e) {
                    Log.d("DevicesList", "Error: " + e.getMessage());
                    // ex.fillInStackTrace();
                }
            }
        }
    };

//    @Override
//    protected void onActivityResult(int reqCode, int resultCode, Intent intent) {
//        super.onActivityResult(reqCode, resultCode, intent);
//
//        switch (reqCode) {
//            case REQUEST_ENABLE_BT:
//
//                if (resultCode == RESULT_OK) {
//                    Set<BluetoothDevice> btDeviceList = DevicesList.BTAdapter
//                            .getBondedDevices();
//                    try {
//                        if (btDeviceList.size() > 0) {
//
//                            for (BluetoothDevice device : btDeviceList) {
//                                if (btDeviceList.contains(device) == false) {
//
//                                    DevicesList.BTDevices.add(device);
//
//                                    DevicesList.BTDeviceNames.add(device.getName() + "\n"
//                                            + device.getAddress());
//                                    DevicesList.BTDeviceNames.notifyDataSetInvalidated();
//                                }
//                            }
//                        }
//                    } catch (Exception ex) {
//                    }
//                }
//
//                break;
//        }
//
//        DevicesList.BTAdapter.startDiscovery();
//    }

//    @Override
//    protected void onListItemClick(ListView l, View v, final int position,
//                                   long id) {
//        super.onListItemClick(l, v, position, id);
//
//        if (DevicesList.BTAdapter == null) {
//            return;
//        }
//
//        if (DevicesList.BTAdapter.isDiscovering()) {
//            DevicesList.BTAdapter.cancelDiscovery();
//        }
//
//        Toast.makeText(
//                getApplicationContext(),
//                "Connecting to " + DevicesList.BTDevices.getItem(position).getName() + ","
//                        + DevicesList.BTDevices.getItem(position).getAddress(),
//                Toast.LENGTH_SHORT).show();
//
//        Thread connectThread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    boolean gotuuid = DevicesList.BTDevices.getItem(position)
//                            .fetchUuidsWithSdp();
//                    UUID uuid = DevicesList.BTDevices.getItem(position).getUuids()[0]
//                            .getUuid();
//                    mbtSocket = DevicesList.BTDevices.getItem(position)
//                            .createRfcommSocketToServiceRecord(uuid);
//
//                    mbtSocket.connect();
//                } catch (IOException ex) {
//                    runOnUiThread(socketErrorRunnable);
//                    try {
//                        mbtSocket.close();
//                    } catch (IOException e) {
//                        // e.printStackTrace();
//                    }
//                    mbtSocket = null;
//                    return;
//                } finally {
//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            finish();
//
//                        }
//                    });
//                }
//            }
//        });
//
//        connectThread.start();
//    }

//    private Runnable socketErrorRunnable = new Runnable() {
//
//        @Override
//        public void run() {
//            Toast.makeText(getApplicationContext(),
//                    "Cannot establish connection", Toast.LENGTH_SHORT).show();
//            DevicesList.BTAdapter.startDiscovery();
//
//        }
//    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        menu.add(0, Menu.FIRST, Menu.None, "Refresh Scanning");
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        switch (item.getItemId()) {
//            case Menu.FIRST:
//                initDevicesList();
//                break;
//        }
//
//        return true;
//    }
}