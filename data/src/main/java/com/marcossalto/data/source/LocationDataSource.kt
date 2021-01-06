package com.marcossalto.data.source

/**
 * Created by Marcos Salto on 06/01/2021.
 */
interface LocationDataSource {
    suspend fun findLastRegion(): String?
}