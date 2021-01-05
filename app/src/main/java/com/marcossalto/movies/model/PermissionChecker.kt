package com.marcossalto.movies.model

/**
 * Created by Marcos Salto on 04/01/2021.
 */
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionChecker(private val application: Application, private val permission: String) {

    fun check(): Boolean = ContextCompat.checkSelfPermission(
        application,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}