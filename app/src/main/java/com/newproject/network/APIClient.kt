package com.newproject.network

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.RequestParamsUtils
import com.newproject.apputils.Utils
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import javax.security.cert.CertificateException


internal object APIClient {
    private const val baseURL: String = "https://medical.testme.zone/api/"
//    fun getBaseUrl(): String {
//        if(Debug.SANDBOX_API_URL){
//            return baseURL
//        }else{
//            return baseURL
//        }
//    }

    @Throws(IOException::class)
    fun newRequestRetrofit(context: Context): Retrofit {
        Debug.e("BASE URL", baseURL)
        val authToken = "Bearer " + Utils.getUserAuthToken(context)
        Debug.e("authToken", authToken)
        val okHttpClient = getUnsafeOkHttpClient(context)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header(RequestParamsUtils.AUTHORIZATION, authToken)
                    .header(RequestParamsUtils.CONTENT_TYPE, Constant.APP_JSON)
                    .addHeader("Accept-Language", "en")
                    .addHeader("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()
                val response = chain.proceed(request)
                //String(response.body().bytes())
                response
            }
            .build()
        /*val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                            .header(RequestParamsUtils.AUTHORIZATION,authToken)
                            .header(RequestParamsUtils.CONTENT_TYPE, Constant.APP_JSON)
                            .method(original.method(),original.body())
                            .build()
                    chain.proceed(request)
                }
                .build()*/

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val builder = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
        return builder.build()
    }

    @Throws(IOException::class)
    fun newRequestRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val builder = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
        return builder.build()

    }

    private fun getUnsafeOkHttpClient(context: Context): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<out X509Certificate>? {
                    return arrayOf()
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.getSocketFactory()

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String, session: SSLSession): Boolean {
                    return true
                }
            })

            builder.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request();
                    val response = chain.proceed(request)

                    if (response.code() == 501) {
                        Utils.delLatLong(context)
                        Utils.clearLoginCredentials(context)
                        Utils.deleteUserAuthToken(context)
                        LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(Intent(Constant.FINISH_ACTIVITY))
//                        val intent = Intent(context, SignInActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                        context.startActivity(intent)
                        try {
                            (context as Activity).runOnUiThread {
                                Utils.showToast(
                                    context,
                                    context.getString(R.string.msg_session_time_out)
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        return response
                    }

                    return response
                }

            })

            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}