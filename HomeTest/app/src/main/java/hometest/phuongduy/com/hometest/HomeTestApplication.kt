package hometest.phuongduy.com.hometest

import android.app.Application

class HomeTestApplication : Application() {
    companion object {
        private lateinit var mInstance: HomeTestApplication

        val instance
            get() = mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}