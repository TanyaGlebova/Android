package com.example.user.android

import android.Manifest;
import android.Manifest.permission.READ_PHONE_STATE
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat
import android.app.AlertDialog;
import android.os.Build
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import com.example.user.android.BuildConfig
import com.example.user.android.R
import com.example.user.android.R.string.imei
import androidx.core.app.ActivityCompat.checkSelfPermission
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val PERMISSIONS_REQUEST_READ_PHONE_STATE = 2375
    private var shouldShowPermissionExplanation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val versionView = findViewById<TextView>(R.id.versionView)
        val imeiView = findViewById<TextView>(R.id.imeiView)
        val versionName = resources.getString(R.string.version)
        val imeiName = resources.getString(R.string.imei)
        versionView.text = String.format("%s: %s", versionName, getVersion())
        imeiView.text = String.format("%s: %s", imeiName, getIMEI())
    }

    private fun showPermissionExplanation() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(R.string.explanation)
        dialogBuilder.setPositiveButton(R.string.close) { dialogInterface, id ->
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    PERMISSIONS_REQUEST_READ_PHONE_STATE)
        }

        dialogBuilder.show()
    }

    protected fun getVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    @SuppressLint("HardwareIds")
    protected fun getIMEI(): String {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                showPermissionExplanation()
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        PERMISSIONS_REQUEST_READ_PHONE_STATE)
                shouldShowPermissionExplanation = true
            }

            return ""
        } else {
            return if (telephonyManager != null) telephonyManager.deviceId else ""
        }
    }
    /*protected String getIMEI() {
        //val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage(R.string.explanation)
                dialogBuilder.setPositiveButton(R.string.close, DialogInterface.OnClickListener { dialogInterface, id ->
                    ActivityCompat.requestPermissions(this@MainActivity,
                            arrayOf(Manifest.permission.READ_PHONE_STATE),
                            PERMISSIONS_REQUEST_READ_PHONE_STATE)
                })

                dialogBuilder.show()
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        PERMISSIONS_REQUEST_READ_PHONE_STATE)
            }
            return ""
        } else {*/
            //return if (telephonyManager != null)  @Suppress("DEPRECATION") telephonyManager.deviceId else ""
        //}
        /*if (checkSelfPermission(this@MainActivity,
                        READ_PHONE_STATE) == PERMISSION_GRANTED) {
            val tel = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            imeiView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tel.imei
            } else {
                @Suppress("DEPRECATION")
                tel.deviceId
            }
        }*/
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                showPermissionExplanation()
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        PERMISSIONS_REQUEST_READ_PHONE_STATE)
                shouldShowPermissionExplanation = true
            }
            return ""
        } else {
            return if (telephonyManager != null) telephonyManager.deviceId else ""
        }
    }*/


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_PHONE_STATE -> {
                val versionView = findViewById<TextView>(R.id.versionView)
                val imeiView = findViewById<TextView>(R.id.imeiView)
                val versionName = resources.getString(R.string.version)
                val imeiName = resources.getString(R.string.imei)
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    versionView.setText(String.format("%s: %s", versionName, getVersion()))
                    imeiView.setText(String.format("%s: %s", imeiName, getIMEI()))
                } else {
                    versionView.setText(String.format("%s: %s", versionName, getVersion()))
                }
            }
        }
    }
}
