package ma.ensias.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, MainActivity mActivity){
        this.mManager=mManager;
        this.mChannel=mChannel;
        this.mActivity=mActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            //Check to see if WiFi is enable and notify the activity
            int state=intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if(state==WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                Toast.makeText(context, "Wifi is ON", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Wifi is OFF", Toast.LENGTH_SHORT).show();
            }
        }else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
            //Call WifiP2pManager.requestPeers() to get the list of current peers
            if(mManager!=null){
                mManager.requestPeers(mChannel, mActivity.peerListListener);

            }
        }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){
            //Respond to new connection or disconnection
            if(mManager!=null){

                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

                if(networkInfo.isConnected()){
                    mManager.requestConnectionInfo(mChannel, mActivity.ConnectionInfoListener);

                }else{
                    mActivity.OutPut.setText("Your are disconnect");
                }
            }
        }else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){
            //Respond to this device's WIFI state changing
        }

    }
}