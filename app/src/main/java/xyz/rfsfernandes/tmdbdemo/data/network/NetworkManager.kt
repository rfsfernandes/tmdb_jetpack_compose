package xyz.rfsfernandes.tmdbdemo.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

class NetworkManager(context: Context) : LiveData<Boolean>() {

    companion object {
        private const val TAG = "NetworkManager"
    }

    private val connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.i(TAG, "Network available: $network")
            postValue(true)
        }

        override fun onLost(network: Network) {
            Log.i(TAG, "Network lost: $network")
            postValue(false)
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            Log.i(TAG, "Capabilities changed: $networkCapabilities")
            val isConnected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            postValue(isConnected)
        }
    }

    override fun onActive() {
        super.onActive()
        // Register the network callback
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) // More reliable check
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        // Initial check for network connectivity
        postValue(isConnected())
    }

    override fun onInactive() {
        super.onInactive()
        // Unregister the network callback
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) // Ensures actual internet access
    }
}