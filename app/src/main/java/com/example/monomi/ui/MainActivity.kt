package com.example.monomi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.monomi.navigation.MonomiNavHost
import com.example.monomi.ui.theme.MonomiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MonomiAppRoot() }
    }
}

@Composable
private fun MonomiAppRoot() {
    MonomiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MonomiNavHost()
        }
    }
}
