package com.example.testapp.presentation.ui.util

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

class PermissionManager private constructor(private val fragment: WeakReference<Fragment>) {

    private val requiredPermissions = mutableListOf<Permission>()
    private var callback: (Map<String, Boolean>) -> Unit = {}

    private val permissionCheck =
        fragment.get()?.registerForActivityResult(RequestMultiplePermissions()) { grantResults ->
            sendResultAndCleanUp(grantResults)
        }

    companion object {
        fun from(fragment: Fragment) = PermissionManager(WeakReference(fragment))
    }

    fun request(vararg permission: Permission): PermissionManager {
        requiredPermissions.addAll(permission)
        return this
    }

    fun checkPermission(callback: (Map<String, Boolean>) -> Unit) {
        this.callback = callback
        handlePermissionRequest()
    }

    private fun handlePermissionRequest() {
        fragment.get()?.let { fragment ->
            if (areAllPermissionsGranted(fragment)) {
                callback(getPermissionList().associateWith { true })
            } else {
                requestPermissions()
            }
        }
    }

    private fun sendResultAndCleanUp(grantResults: Map<String, Boolean>) {
        callback(grantResults)
        cleanUp()
    }

    private fun cleanUp() {
        requiredPermissions.clear()
        callback = {}
    }

    private fun requestPermissions() {
        permissionCheck?.launch(getPermissionList())
    }

    private fun areAllPermissionsGranted(fragment: Fragment) =
        requiredPermissions.all { it.isGranted(fragment) }

    private fun getPermissionList() =
        requiredPermissions.flatMap { it.permissions.toList() }.toTypedArray()

    private fun Permission.isGranted(fragment: Fragment) =
        permissions.all { hasPermission(fragment, it) }

    private fun hasPermission(fragment: Fragment, permission: String) =
        ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
}
