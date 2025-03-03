package com.example.atomickit.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.Text

@Composable
fun CustomCardShowcase() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "CustomCard Showcase",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )
        )

        // Basic card with box shadow
        SectionTitle("Basic Card with Box Shadow")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            contentColor = Color(0xFF1F2937),
            boxShadow = BoxShadow(
                offsetY = 4.dp,
                blurRadius = 8.dp,
                spreadRadius = 0.dp,
                color = Color(0x40000000)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Basic Card with Shadow",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card uses a custom box shadow effect with rounded corners.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Card with gradient background
        SectionTitle("Card with Gradient Background")
        val gradientColors = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundGradient = Brush.linearGradient(
                colors = gradientColors,
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, 0f)
            ),
            contentColor = Color.White,
            boxShadow = BoxShadow(
                offsetY = 2.dp,
                blurRadius = 8.dp,
                spreadRadius = 0.dp,
                color = Color(0x406366F1)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Gradient Background",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card uses a horizontal linear gradient as its background instead of a solid color.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Card with border and no shadow
        SectionTitle("Card with Border")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            contentColor = Color(0xFF1F2937),
            boxShadow = BoxShadow(
                offsetY = 0.dp,
                blurRadius = 0.dp,
                spreadRadius = 0.dp,
                color = Color.Transparent
            ),
            border = BorderStroke(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF6366F1), Color(0xFFA855F7)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Gradient Border Card",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card uses a gradient border with no shadow for a flat design.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Card with asymmetric corners
        SectionTitle("Card with Asymmetric Corners")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFF0F9FF),
            contentColor = Color(0xFF0C4A6E),
            boxShadow = BoxShadow(
                offsetX = 2.dp,
                offsetY = 2.dp,
                blurRadius = 8.dp,
                spreadRadius = 0.dp,
                color = Color(0x300C4A6E)
            ),
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 4.dp,
                bottomStart = 4.dp,
                bottomEnd = 24.dp
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Asymmetric Corners",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card has different corner radii for each corner, creating a unique shape.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Card with click interaction
        SectionTitle("Interactive Card")
        val interactionSource = remember { MutableInteractionSource() }
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            contentColor = Color(0xFF0369A1),
            boxShadow = BoxShadow(
                offsetY = 3.dp,
                blurRadius = 8.dp,
                spreadRadius = 0.dp,
                color = Color(0x400369A1)
            ),
            pressedBoxShadow = BoxShadow(
                offsetY = 1.dp,
                blurRadius = 3.dp,
                spreadRadius = 0.dp,
                color = Color(0x200369A1)
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = { /* Click action */ },
            interactionSource = interactionSource
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Interactive Card (Press Me)",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card responds to press interactions by changing its shadow depth.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Card with title and content slots
        SectionTitle("Card with Title and Content Slots")
        CustomCard(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFF0EA5E9), CircleShape)
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Icon placeholder (replace with your icon)
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                    Text(
                        text = "Titled Card with Icon",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            },
            content = {
                Text(
                    text = "This card uses the title and content slots for structured layout. The title includes an icon for visual interest.",
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            contentColor = Color(0xFF1F2937),
            boxShadow = BoxShadow(
                offsetY = 2.dp,
                blurRadius = 6.dp,
                spreadRadius = 0.dp,
                color = Color(0x300EA5E9)
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Card with custom content padding
        SectionTitle("Card with Custom Content Padding")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFF8FAFC),
            contentColor = Color(0xFF1E293B),
            boxShadow = BoxShadow(
                offsetY = 1.dp,
                blurRadius = 4.dp,
                spreadRadius = 0.dp,
                color = Color(0x201E293B)
            ),
            contentPadding = PaddingValues(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column {
                Text(
                    text = "Custom Padding Card",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "This card uses custom content padding values for each edge, creating an asymmetric internal layout.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Card with radial gradient background
        SectionTitle("Card with Radial Gradient")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundGradient = Brush.radialGradient(
                colors = listOf(Color(0xFFDCFCE7), Color(0xFF22C55E)),
                center = Offset(100f, 50f),
                radius = 500f
            ),
            contentColor = Color(0xFF064E3B),
            boxShadow = BoxShadow(
                offsetX = 2.dp,
                offsetY = 3.dp,
                blurRadius = 10.dp,
                spreadRadius = 0.dp,
                color = Color(0x4022C55E)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Radial Gradient Card",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card uses a radial gradient background with an offset center point.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Nested cards with combined effects
        SectionTitle("Nested Cards with Combined Effects")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFEFF6FF),
            contentColor = Color(0xFF1E40AF),
            boxShadow = BoxShadow(
                offsetY = 4.dp,
                blurRadius = 12.dp,
                spreadRadius = 1.dp,
                color = Color(0x301E40AF)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nested Cards Example",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Parent card with child cards showcasing different styles:",
                    style = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Child cards in a row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Child card 1
                    CustomCard(
                        modifier = Modifier.weight(1f),
                        backgroundGradient = Brush.verticalGradient(
                            colors = listOf(Color(0xFFE9D5FF), Color(0xFFA855F7))
                        ),
                        contentColor = Color.White,
                        boxShadow = BoxShadow(
                            offsetY = 2.dp,
                            blurRadius = 6.dp,
                            spreadRadius = 0.dp,
                            color = Color(0x40A855F7)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Child 1",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Vertical gradient",
                                style = TextStyle(fontSize = 12.sp)
                            )
                        }
                    }

                    // Child card 2
                    CustomCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = Color.White,
                        contentColor = Color(0xFF6366F1),
                        boxShadow = BoxShadow(
                            offsetY = 0.dp,
                            blurRadius = 0.dp,
                            spreadRadius = 0.dp,
                            color = Color.Transparent
                        ),
                        border = BorderStroke(1.dp, Color(0xFF6366F1)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Child 2",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Bordered style",
                                style = TextStyle(fontSize = 12.sp)
                            )
                        }
                    }
                }
            }
        }

        // Card with sweeping gradient and large padding
        SectionTitle("Premium Card Design")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundGradient = Brush.sweepGradient(
                colors = listOf(
                    Color(0xFF1E293B),
                    Color(0xFF334155),
                    Color(0xFF0F172A),
                    Color(0xFF1E293B)
                )
            ),
            contentColor = Color.White,
            boxShadow = BoxShadow(
                offsetX = 0.dp,
                offsetY = 8.dp,
                blurRadius = 24.dp,
                spreadRadius = 0.dp,
                color = Color(0x600F172A)
            ),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PREMIUM",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFFACC15), Color(0xFFF59E0B))
                                ),
                                shape = CircleShape
                            )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Premium Membership",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "This premium card showcases a sweep gradient background with dramatic shadow.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White.copy(alpha = 0.15f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Unlock all premium features with advanced card styling options",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }

        // Card with floating effect
        SectionTitle("Floating Card Effect")
        CustomCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            contentColor = Color(0xFF1F2937),
            boxShadow = BoxShadow(
                offsetY = 12.dp,
                blurRadius = 32.dp,
                spreadRadius = -8.dp,
                color = Color(0x401F2937)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Floating Card Effect",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card creates a floating effect with a spread radius that makes the shadow appear to float beneath the card.",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        // Multiple shadows card
        SectionTitle("Card with Multi-directional Shadow")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Bottom shadow
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
                contentColor = Color(0xFF1F2937),
                boxShadow = BoxShadow(
                    offsetY = 8.dp,
                    blurRadius = 16.dp,
                    spreadRadius = -4.dp,
                    color = Color(0x30000000)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                // Content with side shadow - need to nest cards to get multiple shadows
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Multi-directional Shadow",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This card demonstrates how to create the illusion of multi-directional shadows by using shadow offset and spread radius to control shadow direction and size.",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }

        // Simple titled card example
        SectionTitle("Simple Titled Card")
        CustomCard(
            titleText = "Simplified API Example",
            contentText = "This card uses the simplified API with title and content text parameters for quick creation of standard cards with proper typography.",
            backgroundColor = Color.White,
            contentColor = Color(0xFF1F2937),
            boxShadow = BoxShadow(
                offsetY = 2.dp,
                blurRadius = 8.dp,
                color = Color(0x30000000)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Preview(showBackground = true, heightDp = 1200)
@Composable
fun CustomCardShowcasePreview() {
    CustomCardShowcase()
}