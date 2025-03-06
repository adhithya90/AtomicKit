package com.example.atomickit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomButton
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.components.Text
import com.example.atomickit.layouts.ResponsiveBreakpoints
import com.example.atomickit.layouts.ResponsiveLayout
import com.example.atomickit.layouts.ResponsiveLayoutProvider
import com.example.atomickit.layouts.ResponsiveUI
import com.example.atomickit.layouts.ScreenSizeRange
import com.example.atomickit.layouts.rememberScreenSizeRange


/**
 * Demonstration screen for responsive layout components using the fixed implementation
 */
@Composable
fun ResponsiveLayoutDemoFixed() {
    ResponsiveLayoutProvider {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .verticalScroll(scrollState)
                .padding(bottom = 32.dp)
        ) {
            // App Bar with responsive title
            CustomTopAppBar(
                title = {
                    ResponsiveBreakpoints(
                        breakpoints = mapOf(
                            0.dp to {
                                Text(
                                    "Responsive Demo",
                                    style = TextStyle(fontSize = 18.sp)
                                )
                            },
                            600.dp to {
                                Text(
                                    "Responsive Layout Demo",
                                    style = TextStyle(fontSize = 18.sp)
                                )
                            },
                            900.dp to {
                                Text(
                                    "AtomicKit Responsive Layout Demo",
                                    style = TextStyle(fontSize = 18.sp)
                                )
                            }
                        )
                    )
                },
                backgroundColor = Color(0xFF6366F1),
                contentColor = Color.White,
                elevation = 4.dp
            )

            // Current dimensions info
            ResponsiveUI { dimensions ->
                CustomCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    backgroundColor = Color.White,
                    boxShadow = BoxShadow(
                        offsetY = 2.dp,
                        blurRadius = 6.dp,
                        color = Color(0x40000000)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Current Screen Dimensions",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1F2937)
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Width: ${dimensions.widthDp.toInt()} dp (${dimensions.widthPx}px)",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF4B5563)
                            )
                        )

                        Text(
                            text = "Height: ${dimensions.heightDp.toInt()} dp (${dimensions.heightPx}px)",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF4B5563)
                            )
                        )

                        Text(
                            text = "Aspect Ratio: ${
                                String.format(
                                    "%.2f",
                                    dimensions.widthDp / dimensions.heightDp
                                )
                            }",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF4B5563)
                            )
                        )
                    }
                }
            }

            // Section 1: ResponsiveLayout with discrete breakpoints
            SectionTitle("Responsive Layout with Breakpoints")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                // Use composable-based ranges
                ResponsiveLayout(
                    ranges = mapOf(
                        // Mobile layout (0-599dp width)
                        rememberScreenSizeRange(0.dp, 599.dp) to {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ResponsiveBox("Mobile Layout", Color(0xFFEF4444))
                                Text(
                                    text = "Single column for small screens",
                                    style = TextStyle(fontSize = 14.sp),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        },

                        // Tablet layout (600-899dp width)
                        rememberScreenSizeRange(600.dp, 899.dp) to {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    ResponsiveBox(
                                        "Tablet Left",
                                        Color(0xFF10B981),
                                        Modifier.weight(1f)
                                    )
                                    ResponsiveBox(
                                        "Tablet Right",
                                        Color(0xFF10B981),
                                        Modifier.weight(1f)
                                    )
                                }
                                Text(
                                    text = "Two-column layout for medium screens",
                                    style = TextStyle(fontSize = 14.sp),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        },

                        // Desktop layout (900dp+ width)
                        rememberScreenSizeRange(900.dp, null) to {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    ResponsiveBox(
                                        "Desktop 1",
                                        Color(0xFF6366F1),
                                        Modifier.weight(1f)
                                    )
                                    ResponsiveBox(
                                        "Desktop 2",
                                        Color(0xFF6366F1),
                                        Modifier.weight(1f)
                                    )
                                    ResponsiveBox(
                                        "Desktop 3",
                                        Color(0xFF6366F1),
                                        Modifier.weight(1f)
                                    )
                                }
                                Text(
                                    text = "Three-column layout for large screens",
                                    style = TextStyle(fontSize = 14.sp),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    )
                )
            }

            // Section 2: Orientation-based layout
            SectionTitle("Orientation-Based Layouts")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                ResponsiveLayout(
                    ranges = mapOf(
                        // Portrait orientation
                        ScreenSizeRange.PORTRAIT to {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ResponsiveBox("Portrait Layout", Color(0xFFF59E0B))
                                Text(
                                    text = "Optimized for portrait orientation",
                                    style = TextStyle(fontSize = 14.sp),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        },

                        // Landscape orientation
                        ScreenSizeRange.LANDSCAPE to {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    ResponsiveBox(
                                        "Landscape Left",
                                        Color(0xFF8B5CF6),
                                        Modifier.weight(1f)
                                    )
                                    ResponsiveBox(
                                        "Landscape Right",
                                        Color(0xFF8B5CF6),
                                        Modifier.weight(1f)
                                    )
                                }
                                Text(
                                    text = "Optimized for landscape orientation",
                                    style = TextStyle(fontSize = 14.sp),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    )
                )
            }

            // Section 3: Simple Breakpoints API
            SectionTitle("Simple Breakpoints API")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Simple breakpoints for button size
                    ResponsiveBreakpoints(
                        breakpoints = mapOf(
                            0.dp to {
                                CustomButton(
                                    onClick = { /* no-op */ },
                                    text = "Small Button",
                                    backgroundColor = Color(0xFF6366F1),
                                    modifier = Modifier.width(120.dp)
                                )
                            },
                            600.dp to {
                                CustomButton(
                                    onClick = { /* no-op */ },
                                    text = "Medium Button",
                                    backgroundColor = Color(0xFF6366F1),
                                    modifier = Modifier.width(180.dp)
                                )
                            },
                            900.dp to {
                                CustomButton(
                                    onClick = { /* no-op */ },
                                    text = "Large Button",
                                    backgroundColor = Color(0xFF6366F1),
                                    modifier = Modifier.width(240.dp)
                                )
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Button size changes based on screen width breakpoints",
                        style = TextStyle(fontSize = 14.sp, color = Color(0xFF4B5563))
                    )
                }
            }

            // Section 4: Custom responsive functionality with ResponsiveUI
            SectionTitle("Custom Responsive Behavior")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ResponsiveUI { dimensions ->
                        // Calculate a scale factor based on screen width
                        val widthFactor = (dimensions.widthDp / 360f).coerceIn(1f, 1.5f)

                        // Dynamically sized button that scales with screen size
                        CustomButton(
                            onClick = { /* no-op */ },
                            text = "Dynamic Scaling Button",
                            backgroundColor = Color(0xFF8B5CF6),
                            modifier = Modifier.width((140 * widthFactor).dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Button scales fluidly with screen width",
                        style = TextStyle(fontSize = 14.sp, color = Color(0xFF4B5563))
                    )
                }
            }

            // Section 5: Fluid Typography
            SectionTitle("Fluid Typography")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ResponsiveUI { dimensions ->
                        // Calculate font size based on screen width
                        // Similar to CSS clamp() function for fluid typography
                        val minFontSize = 18f
                        val maxFontSize = 28f
                        val minWidth = 320f
                        val maxWidth = 1200f

                        val fontScale = ((dimensions.widthDp - minWidth) / (maxWidth - minWidth))
                            .coerceIn(0f, 1f)
                        val fontSize = minFontSize + (maxFontSize - minFontSize) * fontScale

                        Column {
                            Text(
                                text = "Fluid Typography",
                                style = TextStyle(
                                    fontSize = fontSize.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1F2937),
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "Font size: ${fontSize.toInt()}sp",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF4B5563),
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Text size scales fluidly with screen width",
                        style = TextStyle(fontSize = 14.sp, color = Color(0xFF4B5563))
                    )
                }
            }
        }
    }
}

/**
 * Helper composable for displaying a colored box with text
 */
@Composable
fun ResponsiveBox(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(1.dp, backgroundColor.copy(alpha = 0.7f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun ResponsiveLayoutDemoFixedPreview() {
    ResponsiveLayoutDemoFixed()
}