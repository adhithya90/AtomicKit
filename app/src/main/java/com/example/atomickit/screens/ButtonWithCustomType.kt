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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomButton
import com.example.atomickit.components.Text
import com.example.atomickit.ui.theme.AppTypography
import com.example.atomickit.ui.theme.LocalAppTypography

/**
 * Button showcase with custom typography
 */
@Composable
fun ButtonTypographyDemo() {
    // Create custom typography with Bricolage Grotesque for headings and Open Sans for body
    val typography = AppTypography.create(
        headingColor = Color(0xFF212529),
        bodyColor = Color(0xFF495057)
    )

    // Provide typography to the composition
    CompositionLocalProvider(LocalAppTypography provides typography) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .verticalScroll(rememberScrollState())
                .padding(vertical = 40.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Header
            Column {
                Text(
                    text = "Components",
                    style = LocalAppTypography.current.h1,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Using Bricolage Grotesque for headings and Open Sans for body text",
                    style = LocalAppTypography.current.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Primary Buttons Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Primary Buttons",
                    style = LocalAppTypography.current.h3
                )

                Text(
                    text = "Buttons with custom box shadows and corner radii",
                    style = LocalAppTypography.current.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Standard button
                CustomButton(
                    onClick = { /* do nothing */ },
                    text = "Standard Button"
                )


                // Standard button (full width)
                CustomButton(
                    onClick = { /* action */ },
                    text = "Full Width",
                    modifier = Modifier.fillMaxWidth()
                )

// Button with max width (centered)
                CustomButton(
                    onClick = { /* action */ },
                    text = "Constrained Width",
                    modifier = Modifier.fillMaxWidth(),
                    maxWidth = 180.dp
                )


                // Asymmetric corners
                CustomButton(
                    onClick = { /* do nothing */ },
                    text = "Asymmetric Corners",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFF8B5CF6),
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 4.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 20.dp
                    ),
                    boxShadow = BoxShadow(
                        offsetY = 3.dp,
                        blurRadius = 6.dp,
                        color = Color(0x408B5CF6)
                    )
                )

                // Horizontal shadow
                CustomButton(
                    onClick = { /* do nothing */ },
                    text = "Horizontal Shadow",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFF10B981),
                    boxShadow = BoxShadow(
                        offsetX = 6.dp,
                        offsetY = 0.dp,
                        blurRadius = 8.dp,
                        color = Color(0x4010B981)
                    )
                )
            }

            // Secondary Buttons Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Secondary Buttons",
                    style = LocalAppTypography.current.h3
                )

                Text(
                    text = "Outlined and text variants with custom styling",
                    style = LocalAppTypography.current.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Outlined button
                CustomButton(
                    onClick = { /* do nothing */ },
                    text = "Outlined Button",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.Transparent,
                    contentColor = Color(0xFF000000),
                    border = BorderStroke(1.dp, Color(0xFF000000))
                )

                // Text button
                CustomButton(
                    onClick = { /* do nothing */ },
                    text = "Text Button",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.Transparent,
                    contentColor = Color(0xFF2196F3),
                    boxShadow = BoxShadow(0.dp, 0.dp, 0.dp, 0.dp, color = Color.Transparent)
                )
            }

            // Special Corners Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Special Corner Styles",
                    style = LocalAppTypography.current.h3
                )

                Text(
                    text = "Different corner configurations for unique designs",
                    style = LocalAppTypography.current.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                    // Top corners only
                    CustomButton(
                        onClick = { /* do nothing */ },
                        text = "Top Corners",
                        modifier = Modifier.weight(1f),
                        backgroundColor = Color(0xFFF59E0B),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        boxShadow = BoxShadow(
                            offsetY = 3.dp,
                            blurRadius = 5.dp,
                            color = Color(0x40F59E0B)
                        )
                    )

                    // Bottom corners only
                    CustomButton(
                        onClick = { /* do nothing */ },
                        text = "Bottom Corners",
                        modifier = Modifier.weight(1f),
                        backgroundColor = Color(0xFFEC4899),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        ),
                        boxShadow = BoxShadow(
                            offsetY = 3.dp,
                            blurRadius = 5.dp,
                            color = Color(0x40EC4899)
                        )
                    )


            }

            // Typography Showcase Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Typography Showcase",
                    style = LocalAppTypography.current.h3
                )

                // Heading showcase
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bricolage Grotesque",
                        style = LocalAppTypography.current.h2.copy(
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Extra Bold Headings",
                        style = LocalAppTypography.current.h4.copy(
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Open Sans",
                        style = LocalAppTypography.current.bodyLarge.copy(
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Clean and readable body text that works well with the distinctive headings.",
                        style = LocalAppTypography.current.bodyMedium.copy(
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@PreviewScreenSizes
@Preview(showBackground = true, heightDp = 1200)
@Composable
fun ButtonTypographyDemoPreview() {
    ButtonTypographyDemo()
}