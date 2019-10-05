package mx.unam.tic.docencia.volleyserviceexample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

class NetworkConnection(val context: Context) {

    @Suppress("DEPRECATION")
    fun isNetworkConnected():Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(android.os.Build.VERSION.SDK_INT>android.os.Build.VERSION_CODES.M){
            val network=connectivityManager.activeNetwork
            if (network!=null){
                val capabilities=connectivityManager.getNetworkCapabilities(network).run{
                    return when{
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->
                            true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->
                            true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->
                            true
                        else->
                            false
                    }
                }
            }else{
                return false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return this.isConnected
            }
        }
        return false
    }

}