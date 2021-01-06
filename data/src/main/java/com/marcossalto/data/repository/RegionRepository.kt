package com.marcossalto.data.repository

/**
 * Created by Marcos Salto on 06/01/2021.
 */
import com.marcossalto.data.source.LocationDataSource

class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {
    companion object {
        private const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}