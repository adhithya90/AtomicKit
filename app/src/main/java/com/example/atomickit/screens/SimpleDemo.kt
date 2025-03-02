package com.example.atomickit.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.atomickit.components.CustomButton
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.CustomSwitch
import com.example.atomickit.components.CustomTextField
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.components.LabeledSwitch
import com.example.atomickit.components.Text

/**
 * A simple demo of Atomic Kit components without using the custom theme
 */
@Composable
fun SimpleDemoNoTheme() {
    // State for interactive components
    var textValue by remember { mutableStateOf("") }
    var switchValue by remember { mutableStateOf(false) }

    // Color definitions - direct values instead of a theme
    val primary = Color(0xFF6366F1)        // Indigo
    val secondary = Color(0xFFF59E0B)      // Amber
    val background = Color(0xFFF9FAFB)     // Gray-50
    val surface = Color(0xFFFFFFFF)        // White
    val onPrimary = Color(0xFFFFFFFF)      // White
    val onSurface = Color(0xFF1F2937)      // Gray-800
    val error = Color(0xFFEF4444)          // Red

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(background),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Top App Bar
        CustomTopAppBar(
            titleText = "Atomic Kit Components",
            backgroundColor = primary,
            contentColor = onPrimary,
            elevation = 4.dp
        )

        // Heading
        Text(
            text = "Basic Components Demo",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = onSurface
            )
        )

        // Basic buttons
        Text(
            text = "Buttons",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = onSurface
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Primary button
            CustomButton(
                onClick = { /* do nothing */ },
                text = "Primary",
                modifier = Modifier.weight(1f),
                backgroundColor = primary,
                contentColor = onPrimary,
                shape = RoundedCornerShape(8.dp)
            )

            Button(
                onClick = { /* do nothing */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Material Button")
            }


            // Secondary button
            CustomButton(
                onClick = { /* do nothing */ },
                text = "Secondary",
                modifier = Modifier.weight(1f),
                backgroundColor = secondary,
                contentColor = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
        }

        // Text button
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Text Button",
            backgroundColor = Color.Transparent,
            contentColor = primary
        )

        // Rounded button
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Rounded Button",
            backgroundColor = primary,
            contentColor = onPrimary,
            shape = CircleShape
        )

        // Cards
        Text(
            text = "Cards",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = onSurface
            )
        )

        // Basic card
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = surface,
            contentColor = onSurface,
            elevation = 2.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Basic Card",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = onSurface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "This is a simple card component with elevation and rounded corners.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = onSurface.copy(alpha = 0.7f)
                    )
                )
            }
        }

        // Bordered card
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = surface,
            contentColor = onSurface,
            elevation = 0.dp,
            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Bordered Card",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = onSurface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "This card has a border instead of elevation.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = onSurface.copy(alpha = 0.7f)
                    )
                )
            }
        }

        // Text Fields
        Text(
            text = "Text Fields",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = onSurface
            )
        )

        // Basic text field
        CustomTextField(
            value = textValue,
            onValueChange = { newValue -> textValue = newValue },
            labelText = "Basic TextField",
            placeholderText = "Enter some text...",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = surface,
            focusedBorderColor = primary,
            unfocusedBorderColor = Color(0xFFD1D5DB)
        )

        // Error text field
        CustomTextField(
            value = "",
            onValueChange = { /* do nothing */ },
            labelText = "Error TextField",
            placeholderText = "This field has an error",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = surface,
            isError = true,
            errorText = "This field is required",
            focusedBorderColor = error,
            unfocusedBorderColor = error
        )

        // Switches
        Text(
            text = "Switches",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = onSurface
            )
        )

        // Basic switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Basic Switch",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = onSurface
                )
            )

            CustomSwitch(
                checked = false,
                onCheckedChange = { /* do nothing */ },
                trackColor = primary,
                thumbColor = Color.White
            )
        }

        // Interactive switch with label
        LabeledSwitch(
            checked = switchValue,
            onCheckedChange = { newValue -> switchValue = newValue },
            labelText = "Interactive Switch",
            trackColor = primary,
            thumbColor = Color.White
        )

        // Feedback based on switch state
        if (switchValue) {
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = primary.copy(alpha = 0.1f),
                contentColor = primary,
                border = BorderStroke(1.dp, primary.copy(alpha = 0.3f)),
                elevation = 0.dp
            ) {
                Text(
                    text = "The switch is ON",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = primary
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Bottom spacing
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleDemoNoThemePreview() {
    SimpleDemoNoTheme()
}