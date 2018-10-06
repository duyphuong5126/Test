package hometest.phuongduy.com.hometest.network

import android.text.TextUtils
import hometest.phuongduy.com.hometest.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {
    private val mRetrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())

    private var mRetrofit: Retrofit? = null
    private var mBaseUrl: String? = null
    private val mLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val mHttpClientBuilder = OkHttpClient.Builder().addInterceptor(mLoggingInterceptor)
            .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)

    fun setBaseUrl(baseUrl: String) {
        if (!TextUtils.equals(mBaseUrl, baseUrl)) {
            mBaseUrl = baseUrl
            mRetrofitBuilder.baseUrl(mBaseUrl!!)
            mRetrofitBuilder.client(mHttpClientBuilder.build())
            mRetrofit = mRetrofitBuilder.build()
        }
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return mRetrofit!!.create(serviceClass)
    }
}