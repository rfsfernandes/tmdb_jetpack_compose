package xyz.rfsfernandes.tmdbdemo.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

class NetworkManager(context: Context) : LiveData<Boolean>() {

    companion object NM {
        private const val TAG = "NetworkManager"
    }

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.i(TAG, "onAvailable: $network")
            postValue(true)
        }

        override fun onLost(network: Network) {
            Log.i(TAG, "onLost: $network")
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        // Register the network callback
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        // Check initial network status
        postValue(isConnected())
    }

    override fun onInactive() {
        super.onInactive()
        // Unregister the network callback
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
