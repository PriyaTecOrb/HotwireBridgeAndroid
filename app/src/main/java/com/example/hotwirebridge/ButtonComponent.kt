package com.example.hotwirebridge

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

    private val menuItemId = 12345
    private var buttonTitle: String? = null

    override fun onReceive(message: Message) {
        Log.d("ButtonComponent", "Event: ${message.event}")

        when (message.event) {
            "connect" -> handleConnect(message)
            "disconnect" -> handleDisconnect()
        }
    }

    private fun handleConnect(message: Message) {
        val data = message.data<MessageData>() ?: return
        buttonTitle = data.title
        showToolbar(data.title)
    }

    private fun handleDisconnect() {
        buttonTitle = null
        hideToolbar()
    }

    private fun showToolbar(title: String) {
        val activity = delegate.destination.fragment.activity
        activity?.runOnUiThread {
            val appCompatActivity = activity as? AppCompatActivity ?: return@runOnUiThread

            appCompatActivity.supportActionBar?.apply {
                this.title = title
                setDisplayHomeAsUpEnabled(true)
                show()
            }

            appCompatActivity.invalidateOptionsMenu()
        }
    }

    private fun hideToolbar() {
        val activity = delegate.destination.fragment.activity
        activity?.runOnUiThread {
            val appCompatActivity = activity as? AppCompatActivity ?: return@runOnUiThread

            appCompatActivity.supportActionBar?.apply {
                title = ""
                setDisplayHomeAsUpEnabled(false)
            }

            appCompatActivity.invalidateOptionsMenu()
        }
    }

    fun onPrepareMenu(menu: Menu) {
        menu.clear()
        val title = buttonTitle ?: return
        menu.add(Menu.NONE, menuItemId, Menu.NONE, title).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
    }

    fun onMenuItemSelected(itemId: Int): Boolean {
        return if (itemId == menuItemId) {
            Log.d("ButtonComponent", "Button clicked → sending event")
            replyTo("button_clicked")
            true
        } else {
            false
        }
    }

    @Serializable
    data class MessageData(
        @SerialName("title") val title: String
    )
}
