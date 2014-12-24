package com.talent.taskmanager.network;

/**
 * Created by acmllaugh on 14-12-24.
 */
public class NetworkState {
    private boolean isConnected;
    private boolean wifiConnected;
    private boolean mobileNetworkConnected;

    public NetworkState(boolean isConnected, boolean wifiConnected, boolean mobileNetworkConnected) {
        this.isConnected = isConnected;
        this.wifiConnected = wifiConnected;
        this.mobileNetworkConnected = mobileNetworkConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean getWifiConnected() {
        return wifiConnected;
    }

    public boolean getMobileNetworkConnected() {
        return mobileNetworkConnected;
    }
}
