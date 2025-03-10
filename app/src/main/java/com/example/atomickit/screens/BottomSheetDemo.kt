package com.example.atomickit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Upload

import androidx.compose.material3.ExtendedFloatingActionButton

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomButton
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.components.SimpleBlurConfig
import com.example.atomickit.components.SimpleBottomSheet
import com.example.atomickit.components.SimpleSheetHeader
import kotlinx.coroutines.launch

/**
 * A demo screen showcasing the SimpleBottomSheet component in a practical context.
 */
@Composable
fun SimpleBottomSheetDemo() {
    // State for different sheet types
    var showOptionsSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showShareSheet by remember { mutableStateOf(false) }
    var showInfoSheet by remember { mutableStateOf(false) }

    // State for sheet configuration
    var enableBlur by remember { mutableStateOf(true) }
    var showDragHandle by remember { mutableStateOf(true) }
    var showCloseButton by remember { mutableStateOf(true) }

    // Example filter state
    var sortByDate by remember { mutableStateOf(true) }
    var showFavorites by remember { mutableStateOf(false) }
    var priceFilterIndex by remember { mutableStateOf(0) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                titleText = "Bottom Sheet Demo",
                backgroundColor = Color(0xFF6366F1),
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = { showInfoSheet = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showOptionsSheet = true },
                containerColor = Color(0xFF6366F1),
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "Add") },
                text = { Text("Options") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Introduction
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
                boxShadow = BoxShadow(
                    offsetY = 2.dp,
                    blurRadius = 4.dp,
                    color = Color(0x20000000)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Bottom Sheet Examples",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "This demo shows different ways to use the bottom sheet component in your app.",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }

            // Different types of sheets
            DemoSection(
                title = "Sheet Types",
                description = "Different types of bottom sheets for various use cases"
            ) {
                // Filter sheet
                CustomButton(
                    onClick = { showFilterSheet = true },
                    text = "Filter Sheet",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Share sheet
                CustomButton(
                    onClick = { showShareSheet = true },
                    text = "Share Sheet",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFF10B981)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Info sheet
                CustomButton(
                    onClick = { showInfoSheet = true },
                    text = "Info Sheet",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFFF59E0B)
                )
            }

            // Customization Options
            DemoSection(
                title = "Customization",
                description = "Adjust how the bottom sheet looks and behaves"
            ) {
                // Blur effect toggle
                ToggleOption(
                    title = "Backdrop Blur",
                    isChecked = enableBlur,
                    onCheckedChange = { enableBlur = it }
                )

                // Drag handle toggle
                ToggleOption(
                    title = "Drag Handle",
                    isChecked = showDragHandle,
                    onCheckedChange = { showDragHandle = it }
                )

                // Close button toggle
                ToggleOption(
                    title = "Close Button",
                    isChecked = showCloseButton,
                    onCheckedChange = { showCloseButton = it }
                )
            }
        }

        // ======== OPTIONS SHEET ========
        SimpleBottomSheet(
            visible = showOptionsSheet,
            onDismissRequest = { showOptionsSheet = false },
            blurConfig = SimpleBlurConfig(isEnabled = enableBlur),
            showDragHandle = showDragHandle,
            showCloseButton = showCloseButton,
            sheetContent = {
                SimpleSheetHeader(title = "Options")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Choose an action:",
                    style = TextStyle(fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Option buttons
                OptionButton(
                    title = "Filter",
                    icon = Icons.Default.DateRange,
                    onClick = {
                        showOptionsSheet = false
                        coroutineScope.launch {
                            // Small delay for better UX
                            kotlinx.coroutines.delay(300)
                            showFilterSheet = true
                        }
                    }
                )

                OptionButton(
                    title = "Share",
                    icon = Icons.Default.Share,
                    onClick = {
                        showOptionsSheet = false
                        coroutineScope.launch {
                            kotlinx.coroutines.delay(300)
                            showShareSheet = true
                        }
                    }
                )

                OptionButton(
                    title = "Favorites",
                    icon = Icons.Default.Favorite,
                    onClick = {
                        showFavorites = !showFavorites
                        showOptionsSheet = false
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { showOptionsSheet = false }
                    ) {
                        Text("Cancel")
                    }
                }
            },
            content = {
                // Background content is already rendered by Scaffold
            }
        )


        // ======== FILTER SHEET ========
        SimpleBottomSheet(
            visible = showFilterSheet,
            onDismissRequest = { showFilterSheet = false },
            blurConfig = SimpleBlurConfig(isEnabled = enableBlur),
            showDragHandle = showDragHandle,
            showCloseButton = showCloseButton,
            sheetContent = {
                SimpleSheetHeader(title = "Filter Items")

                Spacer(modifier = Modifier.height(16.dp))

                // Sort options
                Text(
                    text = "Sort by:",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = sortByDate,
                        onClick = { sortByDate = true }
                    )

                    Text(
                        text = "Date",
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    RadioButton(
                        selected = !sortByDate,
                        onClick = { sortByDate = false }
                    )

                    Text(
                        text = "Name",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Favorites filter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Show favorites only"
                    )

                    Switch(
                        checked = showFavorites,
                        onCheckedChange = { showFavorites = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Price range
                Text(
                    text = "Price range:",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                val priceRanges = listOf("All", "Under $50", "$50-$100", "Over $100")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    priceRanges.forEachIndexed { index, price ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (priceFilterIndex == index) Color(0xFF6366F1)
                                    else Color(0xFFE5E7EB)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .clickable { priceFilterIndex = index }
                        ) {
                            Text(
                                text = price,
                                color = if (priceFilterIndex == index) Color.White else Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { showFilterSheet = false }
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    androidx.compose.material3.Button(
                        onClick = { showFilterSheet = false }
                    ) {
                        Text("Apply")
                    }
                }
            },
            content = {
                // Background content is already rendered by Scaffold
            }
        )


        // ======== SHARE SHEET ========
        SimpleBottomSheet(
            visible = showShareSheet,
            onDismissRequest = { showShareSheet = false },
            blurConfig = SimpleBlurConfig(isEnabled = enableBlur),
            showDragHandle = showDragHandle,
            showCloseButton = showCloseButton,
            sheetContent = {
                SimpleSheetHeader(title = "Share with")

                Spacer(modifier = Modifier.height(24.dp))

                // Share options grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ShareOption("Email", Icons.Default.Email)
                    ShareOption("Messages", Icons.Default.Chat)
                    ShareOption("Drive", Icons.Default.Upload)
                    ShareOption("More", Icons.Default.MoreVert)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Copy link",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF5F5F5))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "https://example.com/share/abc123",
                        modifier = Modifier.weight(1f),
                        style = TextStyle(fontSize = 14.sp)
                    )

                    IconButton(onClick = { /* Copy link */ }) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = Color(0xFF6366F1)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    androidx.compose.material3.Button(
                        onClick = { showShareSheet = false }
                    ) {
                        Text("Close")
                    }
                }
            },
            content = {
                // Background content is already rendered by Scaffold
            }
        )


        // ======== INFO SHEET ========
        SimpleBottomSheet(
            visible = showInfoSheet,
            onDismissRequest = { showInfoSheet = false },
            blurConfig = SimpleBlurConfig(isEnabled = enableBlur),
            showDragHandle = showDragHandle,
            showCloseButton = showCloseButton,
            sheetContent = {
                SimpleSheetHeader(title = "About Bottom Sheets")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Bottom sheets are surfaces containing supplementary content that are anchored to the bottom of the screen.",
                    style = TextStyle(fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Key Features:",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                FeatureItem("Backdrop blur effect")
                FeatureItem("Draggable with snap behavior")
                FeatureItem("Optional drag handle")
                FeatureItem("Optional close button")
                FeatureItem("Customizable appearance")
                FeatureItem("Smooth animations")

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Implementation",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "This component is built using Jetpack Compose with focus on simplicity and reliability.",
                    style = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { showInfoSheet = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            },
            content = {
                // Background content is already rendered by Scaffold
            }
        )

    }
}

/**
 * A demo section with title, description, and content
 */
@Composable
private fun DemoSection(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    CustomCard(
        modifier = Modifier.fillMaxWidth(),
        boxShadow = BoxShadow(
            offsetY = 2.dp,
            blurRadius = 4.dp,
            color = Color(0x20000000)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            content()
        }
    }
}

/**
 * Toggle option with switch
 */
@Composable
private fun ToggleOption(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 16.sp)
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

/**
 * Option button for options sheet
 */
@Composable
private fun OptionButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6366F1)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                style = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

/**
 * Share option for share sheet
 */
@Composable
private fun ShareOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF6366F1)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

/**
 * Feature item with check icon
 */
@Composable
private fun FeatureItem(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color(0xFF6366F1),
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

///**
// * Clickable modifier
// */
//@Composable
//private fun Modifier.clickable(onClick: () -> Unit): Modifier {
//    return this.then(
//        clickable(
//            onClick = onClick,
//            indication = androidx.compose.material.ripple.rememberRipple(),
//            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
//        )
//    )
//}

/**
 * Missing icons for the demo
 */
private object Icons {
    object Default {
        val ContentCopy = Icons.Default.Clear
        val ChevronRight = Icons.Default.Check
        val Upload = Icons.Default.Share
        val MoreVert = Icons.Default.Info
        val Email = Icons.Default.Info
        val Chat = Icons.Default.Info
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
fun SimpleBottomSheetDemoPreview() {
    MaterialTheme {
        SimpleBottomSheetDemo()
    }
}