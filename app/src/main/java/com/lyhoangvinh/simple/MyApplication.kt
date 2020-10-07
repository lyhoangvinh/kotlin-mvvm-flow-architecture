package com.lyhoangvinh.simple

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.lyhoangvinh.simple.ui.features.MainActivity
import com.lyhoangvinh.simple.ui.features.splash.SplashFragment
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.exitProcess

@HiltAndroidApp
class MyApplication : Application() {

    private var mDeviceWidth = 0

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val displayMetrics = DisplayMetrics()
        val windowManager =
            applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay?.getMetrics(displayMetrics)
        mDeviceWidth = displayMetrics.widthPixels
        handleUncaughtException()
    }

    fun getDeviceWidth(): Int {
        return mDeviceWidth
    }

    /**
     * prevent uncaught exception to crash app
     * restart app for better UX
     */
    private fun handleUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            object : Thread() {
                override fun run() {
                    Looper.prepare()
                    Toast.makeText(applicationContext, R.string.unknown_error, Toast.LENGTH_SHORT)
                        .show()
                    Looper.loop()
                }
            }.start()

            Thread.sleep(2000)

            val intent = Intent(this, MainActivity::class.java)
            // to custom behaviour, add extra params for intent
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
            try {
                exitProcess(2);
            } catch (e: Exception) {
                startActivity(intent)
            }
        }
    }
}