package com.marcossalto.movies

/**
 * Created by Marcos Salto on 04/01/2021.
 */

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}