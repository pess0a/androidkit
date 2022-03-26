package com.pess0a.androidkit.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.net.InetSocketAddress
import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

internal class ConnectionUtils(private val context: Context) {

    suspend fun isConnectionOn(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return isNetworkAvailable(connectivityManager) && isInternetAvailable()
    }

    private fun isNetworkAvailable(connectivity: ConnectivityManager): Boolean = runCatching {
        val network = connectivity.activeNetwork!!
        val capabilities = connectivity.getNetworkCapabilities(network)!!
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ||
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ||
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED) -> true
            else -> false
        }
    }.getOrDefault(false)

    private suspend fun isInternetAvailable(): Boolean = withContext(Dispatchers.IO) {
        withTimeout(GENERAL_TIMEOUT_MS) {
            runCatching {
                val timeoutMs = TIMEOUT_MS
                val socket = Socket()
                val socketAddress = InetSocketAddress(GOOGLE_PUBLIC_ADDRESS, HTTP_PORT)
                socket.connect(socketAddress, timeoutMs)
                socket.close()
            }.isSuccess
        }
    }

    companion object {
        private const val GOOGLE_PUBLIC_ADDRESS = "google.com"
        private const val TIMEOUT_MS = 1500
        private const val GENERAL_TIMEOUT_MS = 3000L
        private const val HTTP_PORT = 80
    }
}