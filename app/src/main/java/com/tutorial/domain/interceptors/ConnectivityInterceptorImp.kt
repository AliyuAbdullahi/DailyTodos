package com.tutorial.domain.interceptors

import android.content.Context
import android.net.ConnectivityManager
import com.tutorial.domain.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * [ConnectivityInterceptor] checks for internet connectivity. This will prevent request from
 * going through if there is no internet connection
 */
class ConnectivityInterceptorImp(context: Context): ConnectivityInterceptor {
    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!online()) {
            throw NoConnectivityException()
        }

        return chain.proceed(chain.request())
    }

    private fun online(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}