package com.wisnu.kurniawan.wallee.runtime

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.wisnu.kurniawan.wallee.R
import com.wisnu.kurniawan.wallee.features.host.ui.Host
import com.wisnu.kurniawan.wallee.foundation.window.WindowState
import com.wisnu.kurniawan.wallee.foundation.window.rememberWindowState
import com.wisnu.kurniawan.wallee.runtime.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var windowState: WindowState

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Wallee_Light)
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            windowState = rememberWindowState()

            Host {
                Surface {
                    MainNavHost(windowState)
                }
            }
        }
    }
}
