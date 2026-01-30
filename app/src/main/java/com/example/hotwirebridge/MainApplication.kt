package com.example.hotwirebridge

import android.app.Application
import dev.hotwire.core.bridge.BridgeComponentFactory
import dev.hotwire.core.bridge.KotlinXJsonConverter
import dev.hotwire.core.config.Hotwire
import dev.hotwire.navigation.config.registerBridgeComponents


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 🔴 THIS LINE IS MANDATORY
        Hotwire.config.jsonConverter = KotlinXJsonConverter()

        // Set configuration options
        Hotwire.registerBridgeComponents(
            BridgeComponentFactory("button", ::ButtonComponent)
        )
    }
}