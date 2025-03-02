package com.example.atomickit.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomButton
import com.example.atomickit.components.Text

/**
 * A sample screen demonstrating CustomButton with different corner radii
 */
@Composable
fun CustomCornerRadiusButtons() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Title
        Text(
            text = "Custom Corner Radius Buttons",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212529)
            )
        )

        // Section 1: Preset Custom Corner Buttons
        SectionTitle("Preset Corner Patterns")

        // Only top corners rounded
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Top Corners Rounded",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFF3B82F6),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            boxShadow = BoxShadow(
                offsetY = 3.dp,
                blurRadius = 6.dp,
                color = Color(0x403B82F6)
            ),
            border = BorderStroke(2.dp, Color(0xFFE91E63))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Only bottom corners rounded
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Bottom Corners Rounded",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFF10B981),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            boxShadow = BoxShadow(
                offsetY = 3.dp,
                blurRadius = 6.dp,
                color = Color(0x4010B981)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Diagonal corners rounded
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Diagonal Corners Rounded",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFF59E0B),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 0.dp,
                bottomStart = 0.dp,
                bottomEnd = 16.dp
            ),
            boxShadow = BoxShadow(
                offsetY = 3.dp,
                blurRadius = 6.dp,
                color = Color(0x40F59E0B)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Different radius for each corner
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Different Radius Each Corner",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFF8B5CF6),
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 4.dp,
                bottomStart = 16.dp,
                bottomEnd = 32.dp
            ),
            boxShadow = BoxShadow(
                offsetY = 3.dp,
                blurRadius = 6.dp,
                color = Color(0x408B5CF6)
            )
        )

        // Section 2: Custom Shape Generator
        SectionTitle("Custom Shape Generator")

        // Interactive shape generator
        InteractiveCornerRadiusButton()

        // Section 3: Special Shapes
        SectionTitle("Special Corner Patterns")

        // Ticket-style button (one side flat, one rounded)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Left-side ticket
            CustomButton(
                onClick = { /* do nothing */ },
                text = "Ticket Left",
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFEC4899),
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 0.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 0.dp
                ),
                boxShadow = BoxShadow(
                    offsetY = 3.dp,
                    blurRadius = 6.dp,
                    color = Color(0x40EC4899)
                )
            )

            // Right-side ticket
            CustomButton(
                onClick = { /* do nothing */ },
                text = "Ticket Right",
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFEF4444),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 20.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 20.dp
                ),
                boxShadow = BoxShadow(
                    offsetY = 3.dp,
                    blurRadius = 6.dp,
                    color = Color(0x40EF4444)
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Message bubble shape
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Message Bubble Style",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFF059669),
            shape = getMessageBubbleShape(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            boxShadow = BoxShadow(
                offsetY = 2.dp,
                blurRadius = 4.dp,
                color = Color(0x40059669)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Outline version for contrast
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Custom Corners with Border",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            contentColor = Color(0xFF6366F1),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 4.dp,
                bottomStart = 4.dp,
                bottomEnd = 20.dp
            ),
            border = BorderStroke(2.dp, Color(0xFF6366F1)),
            boxShadow = BoxShadow(
                offsetY = 2.dp,
                blurRadius = 4.dp,
                color = Color(0x20000000)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Interactive button that allows adjusting the corner radii
 */
@Composable
fun InteractiveCornerRadiusButton() {
    val topStartRadius by remember { mutableFloatStateOf(16f) }
    val topEndRadius by remember { mutableFloatStateOf(4f) }
    val bottomStartRadius by remember { mutableFloatStateOf(4f) }
    val bottomEndRadius by remember { mutableFloatStateOf(16f) }

    // Preview area with the custom corner button
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // The button with custom corners
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Custom Corners Button",
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFF0EA5E9),
            shape = RoundedCornerShape(
                topStart = topStartRadius.dp,
                topEnd = topEndRadius.dp,
                bottomStart = bottomStartRadius.dp,
                bottomEnd = bottomEndRadius.dp
            ),
            boxShadow = BoxShadow(
                offsetY = 3.dp,
                blurRadius = 6.dp,
                color = Color(0x400EA5E9)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sliders for adjusting corners
        // In a real app, you'd implement sliders here to control the radius values
        // For this example, we'll just show the values

        Text(
            text = "Corner Radii:",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF374151)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display current corner values
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CornerRadiusInfo("Top Start", topStartRadius)
            CornerRadiusInfo("Top End", topEndRadius)
            CornerRadiusInfo("Bottom Start", bottomStartRadius)
            CornerRadiusInfo("Bottom End", bottomEndRadius)
        }

        // In a real app, you would add sliders here to adjust the values
    }
}

/**
 * Corner radius info item
 */
@Composable
fun CornerRadiusInfo(label: String, value: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFF6B7280)
            )
        )
        Text(
            text = "${value.toInt()} dp",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF374151)
            )
        )
    }
}

/**
 * Section title
 */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

/**
 * Creates a shape like a message bubble
 */
@Composable
fun getMessageBubbleShape(): CornerBasedShape {
    return RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 4.dp,
        bottomEnd = 16.dp
    )
}

/**
 * Preview for the custom corner radius buttons sample
 */
@Preview(showBackground = true)
@Composable
fun CustomCornerRadiusButtonsPreview() {
    CustomCornerRadiusButtons()
}