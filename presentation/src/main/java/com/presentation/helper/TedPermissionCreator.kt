package com.presentation.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.presentation.R

object TedPermissionCreator {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkNotificationPermission(
        activity: Activity,
        context: Context? = null,
        completion: (Boolean) -> Unit? = {}
    ) {

        val tedPermission = TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    completion(true)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    completion(false)
                }
            })
            .setGotoSettingButton(true)
            .setDeniedMessage(context?.getString(R.string.permission_required_notification) ?: "need permission")
            .setPermissions(Manifest.permission.POST_NOTIFICATIONS)


        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            )
        ) {
            tedPermission.check()
        }
    }

}