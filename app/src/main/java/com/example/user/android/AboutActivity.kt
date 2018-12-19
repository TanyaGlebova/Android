package com.example.user.android

import android.Manifest;
import android.annotation.SuppressLint
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {

    val PERMISSIONS_REQUEST_READ_PHONE_STATE = 2375
    val state = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val versionName = resources.getString(R.string.version)
        val imeiName = resources.getString(R.string.imei)
        versionView.text = String.format("%s: %s", versionName, getVersion())
        state.putString("imei", getIMEI())
        imeiView.text = String.format("%s: %s", imeiName, state.getString("imei"))


    }

    private fun showPermissionExplanation() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(R.string.explanation)
        dialogBuilder.setPositiveButton(R.string.close) { dialogInterface, id ->
            ActivityCompat.requestPermissions(this@AboutActivity,
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
            }

            return ""
        } else {
            return if (telephonyManager != null) telephonyManager.deviceId else ""
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_PHONE_STATE -> {
                val versionName = resources.getString(R.string.version)
                val imeiName = resources.getString(R.string.imei)
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    versionView.setText(String.format("%s: %s", versionName, getVersion()))
                    imeiView.setText(String.format("%s: %s", imeiName, state.getString("imei")))
                } else {
                    versionView.setText(String.format("%s: %s", versionName, getVersion()))
                }
            }
        }
    }
}
