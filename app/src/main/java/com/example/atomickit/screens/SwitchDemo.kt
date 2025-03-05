package com.example.atomickit.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.CustomSwitch
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.components.LabelSide
import com.example.atomickit.components.LabeledSwitch
import com.example.atomickit.components.Text

/**
 * A demonstration screen for the CustomSwitch component with various styles and configurations.
 */
@Composable
fun SwitchDemo() {
    // State variables for interactive switches
    var basicSwitchState by remember { mutableStateOf(false) }
    var themeSwitchState by remember { mutableStateOf(false) }
    var notificationSwitchState by remember { mutableStateOf(true) }
    var wifiSwitchState by remember { mutableStateOf(true) }
    var settingsSwitchState by remember { mutableStateOf(false) }
    var customColorSwitchState by remember { mutableStateOf(false) }
    var sizeSwitchState by remember { mutableStateOf(false) }
    var customShapeSwitchState by remember { mutableStateOf(false) }
    var disabledSwitchState by remember { mutableStateOf(true) }

    // Colors
    val primary = Color(0xFF6366F1)
    val secondary = Color(0xFFF59E0B)
    val green = Color(0xFF10B981)
    val red = Color(0xFFEF4444)
    val purple = Color(0xFF8B5CF6)
    val pink = Color(0xFFEC4899)
    val background = Color(0xFFF9FAFB)
    val surface = Color(0xFFFFFFFF)
    val onSurface = Color(0xFF1F2937)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        // App Bar
        CustomTopAppBar(
            titleText = "Switch Components",
            backgroundColor = primary,
            contentColor = Color.White,
            elevation = 4.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Introduction
            Text(
                text = "Switch Variants",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = onSurface
                )
            )

            Text(
                text = "Showcase of different switch styles and configurations",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = onSurface.copy(alpha = 0.7f)
                )
            )

            // Basic switches section
            SectionTitle("Basic Switches")

            // Simple switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Basic Switch",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = onSurface
                    )
                )

                CustomSwitch(
                    checked = basicSwitchState,
                    onCheckedChange = { basicSwitchState = it }
                )
            }

            // Labeled switch
            LabeledSwitch(
                checked = themeSwitchState,
                onCheckedChange = { themeSwitchState = it },
                label = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Dark Mode",
                            tint = if (themeSwitchState) primary else onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "Dark Mode",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = if (themeSwitchState) FontWeight.Medium else FontWeight.Normal,
                                color = if (themeSwitchState) primary else onSurface
                            )
                        )
                    }
                }
            )

            // Card with switches
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                contentColor = onSurface,
                boxShadow = BoxShadow(
                    offsetY = 2.dp,
                    blurRadius = 6.dp,
                    color = Color(0x20000000)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Settings",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    // Notifications switch
                    LabeledSwitch(
                        checked = notificationSwitchState,
                        onCheckedChange = { notificationSwitchState = it },
                        label = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = onSurface.copy(alpha = 0.7f)
                                )
                                Text("Notifications")
                            }
                        },
                        trackColor = primary
                    )

                    // WiFi switch
                    LabeledSwitch(
                        checked = wifiSwitchState,
                        onCheckedChange = { wifiSwitchState = it },
                        label = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "WiFi",
                                    tint = onSurface.copy(alpha = 0.7f)
                                )
                                Text("WiFi")
                            }
                        },
                        trackColor = primary
                    )

                    // Settings sync switch
                    LabeledSwitch(
                        checked = settingsSwitchState,
                        onCheckedChange = { settingsSwitchState = it },
                        label = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Sync Settings",
                                    tint = onSurface.copy(alpha = 0.7f)
                                )
                                Text("Sync Settings")
                            }
                        },
                        trackColor = primary
                    )
                }
            }

            // Custom colors section
            SectionTitle("Custom Colors")

            // Row of switches with different colors
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Primary colored switch
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomSwitch(
                        checked = true,
                        onCheckedChange = { },
                        trackColor = primary,
                        thumbColor = Color.White
                    )
                    Text(
                        text = "Primary",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }

                // Green colored switch
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomSwitch(
                        checked = true,
                        onCheckedChange = { },
                        trackColor = green,
                        thumbColor = Color.White
                    )
                    Text(
                        text = "Success",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }

                // Red colored switch
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomSwitch(
                        checked = true,
                        onCheckedChange = { },
                        trackColor = red,
                        thumbColor = Color.White
                    )
                    Text(
                        text = "Danger",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }

                // Amber colored switch
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomSwitch(
                        checked = true,
                        onCheckedChange = { },
                        trackColor = secondary,
                        thumbColor = Color.White
                    )
                    Text(
                        text = "Warning",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Custom colored switch with different unchecked color
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Custom Track Colors",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = onSurface
                    )
                )

                CustomSwitch(
                    checked = customColorSwitchState,
                    onCheckedChange = { customColorSwitchState = it },
                    trackColor = purple,
                    trackColorUnchecked = pink.copy(alpha = 0.3f),
                    thumbColor = Color.White,
                    thumbColorUnchecked = pink.copy(alpha = 0.8f)
                )
            }

            // Size variations section
            SectionTitle("Size Variations")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Small switch
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomSwitch(
                        checked = true,
                        onCheckedChange = { },
                        width = 36.dp,
                        height = 20.dp,
                        thumbSize = 14.dp,
                        trackColor = primary
                    )
                    Text(
                        text = "Small",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }

                // Medium switch (default)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomSwitch(
                        checked = true,
                        onCheckedChange = { },
                        trackColor = primary
                    )
                    Text(
                        text = "Medium",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }

                // Large switch
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomSwitch(
                        checked = true,
                        onCheckedChange = { },
                        width = 64.dp,
                        height = 38.dp,
                        thumbSize = 30.dp,
                        trackColor = primary
                    )
                    Text(
                        text = "Large",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }
            }

            // Interactive size switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Adjustable Size",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = onSurface
                    )
                )

                // Switch size changes based on state
                CustomSwitch(
                    checked = sizeSwitchState,
                    onCheckedChange = { sizeSwitchState = it },
                    width = if (sizeSwitchState) 64.dp else 52.dp,
                    height = if (sizeSwitchState) 38.dp else 32.dp,
                    thumbSize = if (sizeSwitchState) 30.dp else 24.dp,
                    trackColor = primary
                )
            }

            // Custom shapes section
            SectionTitle("Custom Shapes")

            // Custom shaped switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Square Corners",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = onSurface
                    )
                )

                CustomSwitch(
                    checked = customShapeSwitchState,
                    onCheckedChange = { customShapeSwitchState = it },
                    trackShape = RoundedCornerShape(4.dp),
                    thumbShape = RoundedCornerShape(2.dp),
                    trackColor = green
                )
            }

//            // Pill-shaped switch
//            LabeledSwitch(
//                checked = customShapeSwitchState,
//                onCheckedChange = { customShapeSwitchState = it },
//                label = { Text("Pill Shape with Border") },
//                trackColor = Color(0xFF64748B),
//                trackColorUnchecked = Color(0xFFE5E7EB),
//                borderWidth = 2.dp,
//                borderColor = Color(0xFFCBD5E1),
//                width = 56.dp,
//                height = 28.dp,
//                thumbSize = 20.dp
//            )

            // Switch with label position
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Label at start (default)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LabeledSwitch(
                        checked = true,
                        onCheckedChange = { },
                        labelText = "Label Start",
                        labelSide = LabelSide.START,
                        trackColor = primary
                    )
                    Text(
                        text = "Default Position",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }

                // Label at end
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LabeledSwitch(
                        checked = true,
                        onCheckedChange = { },
                        labelText = "Label End",
                        labelSide = LabelSide.END,
                        trackColor = primary
                    )
                    Text(
                        text = "Reversed",
                        style = TextStyle(fontSize = 12.sp, color = onSurface.copy(alpha = 0.7f))
                    )
                }
            }

            // States section
            SectionTitle("Switch States")

            // Disabled switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Disabled Switch",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = onSurface.copy(alpha = 0.5f)
                    )
                )

                CustomSwitch(
                    checked = disabledSwitchState,
                    onCheckedChange = { disabledSwitchState = it },
                    enabled = false,
                    trackColor = primary
                )
            }

            // Disabled labeled switch
            LabeledSwitch(
                checked = true,
                onCheckedChange = { },
                labelText = "Disabled with Label",
                enabled = false,
                trackColor = primary
            )

            // Enabled/disabled toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Toggle Disabled State",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = onSurface
                    )
                )

                CustomSwitch(
                    checked = !disabledSwitchState,
                    onCheckedChange = { disabledSwitchState = !it },
                    trackColor = primary
                )
            }

            // Creative examples section
            SectionTitle("Creative Examples")

            // Switch with gradient track
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gradient Track",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = onSurface
                    )
                )

                // Custom switch with gradient background
                Box(
                    modifier = Modifier
                        .width(52.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(purple, pink)
                            )
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 2.dp + (18.dp * 1f), end = 2.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }

            // Confirmation switch
            LabeledSwitch(
                checked = true,
                onCheckedChange = { },
                label = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Confirmed",
                            tint = green
                        )
                        Text(
                            text = "Confirmed",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = green
                            )
                        )
                    }
                },
                trackColor = green,
                thumbColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(green.copy(alpha = 0.1f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Premium feature switch
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1E293B),
                        Color(0xFF334155)
                    )
                ),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(16.dp),
                boxShadow = BoxShadow(
                    offsetY = 4.dp,
                    blurRadius = 12.dp,
                    color = Color(0x401E293B)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Premium Feature",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "Unlock advanced capabilities",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Gold accent switch
                    CustomSwitch(
                        checked = false,
                        onCheckedChange = { },
                        trackColor = Color(0xFFFACC15),
                        trackColorUnchecked = Color(0xFF475569),
                        thumbColor = Color.White,
                        width = 46.dp,
                        height = 24.dp,
                        thumbSize = 18.dp
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true, heightDp = 2000)
@Composable
fun SwitchDemoPreview() {
    SwitchDemo()
}