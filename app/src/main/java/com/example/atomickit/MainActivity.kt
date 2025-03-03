package com.example.atomickit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.atomickit.screens.ButtonTypographyDemo
import com.example.atomickit.screens.SimpleDemoNoTheme
import com.example.atomickit.ui.theme.AtomicKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ButtonTypographyDemo()

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleScreenPreview() {
    ButtonTypographyDemo()
}
