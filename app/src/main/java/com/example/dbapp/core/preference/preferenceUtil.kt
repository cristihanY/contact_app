package com.example.dbapp.core.preference

import android.content.Context

    fun saveCartIdToPreferences(context: Context, cartId: Long) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putLong("cart_id", cartId)
            .apply()
    }

    fun getCartIdFromPreferences(context: Context): Long {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getLong("cart_id", -1L) // -1L si no existe
    }

    fun clearCartIdFromPreferences(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .remove("cart_id")
            .apply()
    }
