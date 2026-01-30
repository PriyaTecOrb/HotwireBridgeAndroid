package com.example.hotwirebridge

import android.util.Log
import android.widget.Button
import dev.hotwire.core.bridge.BridgeComponent
import dev.hotwire.core.bridge.BridgeDelegate
import dev.hotwire.core.bridge.Message
import dev.hotwire.navigation.destinations.HotwireDestination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class ButtonComponent(
    name: String,
    private val delegate: BridgeDelegate<HotwireDestination>
) : BridgeComponent<HotwireDestination>(name, delegate) {
    private lateinit var button: Button

    override fun onReceive(message: Message) {
        // Handle incoming messages based on the message `event`.
        Log.d("ButtonComponent", "Received message: ${message.event}")
        Log.d("ButtonComponent", "Message data: ${message}")
        when (message.event) {
            "connect" -> handleConnectEvent(message)
            else -> Log.w("ButtonComponent", "Unknown event for message: $message")
        }
    }

    private fun handleConnectEvent(message: Message) {
        val data = message.data<MessageData>() ?: return

        // Write native code to display a native submit button in the
        // toolbar displayed in the delegate.destination. Use the
        // incoming data.title to set the button title.
    }

    private fun performButtonClick(): Boolean {
        return replyTo("connect")
    }

    // Use kotlinx.serialization annotations to define a serializable
    // data class that represents the incoming message.data json.
    @Serializable
    data class MessageData(
        @SerialName("title") val title: String
    )
}