package com.example.hotwirebridge

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import dev.hotwire.navigation.activities.HotwireActivity
import dev.hotwire.navigation.navigator.NavigatorConfiguration
import dev.hotwire.navigation.util.applyDefaultImeWindowInsets

class MainActivity : HotwireActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.main_nav_host)
            .applyDefaultImeWindowInsets()
    }

    override fun navigatorConfigurations() = listOf(
        NavigatorConfiguration(
            name = "main",
            startLocation = "http://192.168.1.19:3000",
            navigatorHostId = R.id.main_nav_host
        )
    )

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        BridgeComponentHolder.buttonComponent?.onPrepareMenu(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (BridgeComponentHolder.buttonComponent
                ?.onMenuItemSelected(item.itemId) == true
        ) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

