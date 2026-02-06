package com.example.hotwirebridge

import android.app.Application
import dev.hotwire.core.BuildConfig
import dev.hotwire.core.bridge.BridgeComponentFactory
import dev.hotwire.core.bridge.KotlinXJsonConverter
import dev.hotwire.core.config.Hotwire
import dev.hotwire.navigation.config.registerBridgeComponents


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        configureApp()
    }


    private fun configureApp() {

        Hotwire.registerBridgeComponents(
            BridgeComponentFactory("button") { name, delegate ->
                ButtonComponent(name, delegate).also {
                    BridgeComponentHolder.buttonComponent = it
                }
            }
        )
        Hotwire.config.debugLoggingEnabled = BuildConfig.DEBUG
        Hotwire.config.webViewDebuggingEnabled = BuildConfig.DEBUG
        Hotwire.config.jsonConverter = KotlinXJsonConverter()
        Hotwire.config.applicationUserAgentPrefix = "Hotwire Bridge"
    }





}

