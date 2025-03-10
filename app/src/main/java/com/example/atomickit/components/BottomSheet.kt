package com.example.atomickit.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.Shape



import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Values representing different states of the bottom sheet
 */
enum class SimpleSheetValue {
    Hidden, Expanded
}

/**
 * Data class for configuring backdrop blur
 */
data class SimpleBlurConfig(
    val isEnabled: Boolean = false,
    val radius: Dp = 10.dp,
    val tint: Color = Color.Black.copy(alpha = 0.6f)
)

/**
 * A simple bottom sheet implementation that provides the core functionality
 * without the complexity of the more advanced implementations.
 *
 * @param sheetContent Content of the bottom sheet
 * @param modifier Modifier for the entire layout
 * @param visible Whether the sheet is currently visible
 * @param onDismissRequest Called when the sheet is dismissed
 * @param sheetShape Shape of the bottom sheet
 * @param sheetBackgroundColor Background color of the sheet
 * @param sheetContentColor Content color of the sheet
 * @param sheetElevation Elevation of the sheet
 * @param scrimColor Color of the scrim behind the sheet
 * @param sheetContentPadding Padding for the sheet content
 * @param enableDragging Whether sheet dragging is enabled
 * @param blurConfig Configuration for backdrop blur
 * @param showDragHandle Whether to show a drag handle indicator
 * @param showCloseButton Whether to show a close button in the sheet
 * @param content Content behind the sheet
 */
@Composable
fun SimpleBottomSheet(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    onDismissRequest: () -> Unit = {},
    sheetShape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    sheetBackgroundColor: Color = Color.White,
    sheetContentColor: Color = Color.Black,
    sheetElevation: Dp = 8.dp,
    scrimColor: Color = Color.Black.copy(alpha = 0.5f),
    sheetContentPadding: PaddingValues = PaddingValues(16.dp),
    enableDragging: Boolean = true,
    blurConfig: SimpleBlurConfig = SimpleBlurConfig(),
    showDragHandle: Boolean = true,
    showCloseButton: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    // Current visibility state
    var isVisible by remember { mutableStateOf(visible) }
    val currentVisible = visible

    // Update visibility when the parameter changes
    LaunchedEffect(currentVisible) {
        isVisible = currentVisible
    }

    // Animation values
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    // State for the sheet position (0f = hidden, 1f = fully expanded)
    var sheetOffsetY by remember { mutableStateOf(0f) }

    // State for tracking sheet dimensions
    var sheetHeightPx by remember { mutableStateOf(0f) }
    var containerHeightPx by remember { mutableStateOf(0f) }

    // Animate the visibility
    val sheetVisibleFraction by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "sheetVisibility"
    )

    // For backdrop blur effect
    val blurRadius by animateFloatAsState(
        targetValue = if (isVisible && blurConfig.isEnabled) blurConfig.radius.value else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "blurRadius"
    )

    // Draggable state for the sheet
    val dragState = rememberDraggableState { dragAmount ->
        if (!enableDragging) return@rememberDraggableState

        // Calculate new offset with some resistance when dragging down
        val newOffset = sheetOffsetY + dragAmount

        // Apply the drag with some bounds
        if (newOffset <= 0) {
            sheetOffsetY = newOffset * 0.5f // Add resistance when dragging up
        } else {
            // When dragging down, allow full movement with threshold for dismissal
            sheetOffsetY = newOffset

            // If dragged down more than 30% of the sheet height, dismiss
            if (newOffset > sheetHeightPx * 0.3f) {
                coroutineScope.launch {
                    isVisible = false
                    onDismissRequest()
                }
            }
        }
    }

    // Main layout with box for positioning
    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                containerHeightPx = it.size.height.toFloat()
            }
    ) {
        // Main content with optional blur
        if (blurConfig.isEnabled && blurRadius > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = with(density) { blurRadius.dp }),
                content = content
            )

            // Apply tint over the blurred content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(sheetVisibleFraction * blurConfig.tint.alpha)
                    .background(blurConfig.tint.copy(alpha = 1f))
            )
        } else {
            // Regular content without blur
            Box(
                modifier = Modifier.fillMaxSize(),
                content = content
            )

            // Apply scrim over content when sheet is visible
            if (sheetVisibleFraction > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(sheetVisibleFraction)
                        .background(scrimColor)
                        .then(
                            if (enableDragging) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        coroutineScope.launch {
                                            isVisible = false
                                            onDismissRequest()
                                        }
                                    }
                                )
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }

        // Bottom sheet surface
        if (sheetVisibleFraction > 0f) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .onGloballyPositioned {
                        sheetHeightPx = it.size.height.toFloat()
                    }
                    .offset {
                        IntOffset(
                            x = 0,
                            y = (sheetOffsetY + ((1f - sheetVisibleFraction) * sheetHeightPx)).roundToInt()
                        )
                    }
                    .then(
                        if (enableDragging) {
                            Modifier.draggable(
                                state = dragState,
                                orientation = Orientation.Vertical,
                                onDragStopped = {
                                    // If dragged down more than halfway, dismiss
                                    if (sheetOffsetY > sheetHeightPx * 0.5f) {
                                        coroutineScope.launch {
                                            isVisible = false
                                            onDismissRequest()
                                        }
                                    } else {
                                        // Otherwise snap back to fully expanded
                                        sheetOffsetY = 0f
                                    }
                                }
                            )
                        } else {
                            Modifier
                        }
                    ),
                shape = sheetShape,
                color = sheetBackgroundColor,
                contentColor = sheetContentColor,
                shadowElevation = sheetElevation,
                tonalElevation = sheetElevation / 2
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(sheetContentPadding)
                ) {
                    // Sheet header with handle and/or close button
                    if (showDragHandle || showCloseButton) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = if (showDragHandle) 8.dp else 0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (showDragHandle) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(32.dp)
                                            .height(4.dp)
                                            .background(
                                                Color.LightGray,
                                                RoundedCornerShape(2.dp)
                                            )
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }

                            if (showCloseButton) {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            isVisible = false
                                            onDismissRequest()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    // Sheet content
                    sheetContent()
                }
            }
        }
    }
}

/**
 * A simple sheet header with title
 */
@Composable
fun SimpleSheetHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier.padding(vertical = 8.dp)
    )
}

/**
 * Simple text component to match the design system
 */
@Composable
private fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
    color: Color = Color.Unspecified
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color
    )
}

/**
 * Utility function to make the Surface background clickable
 */
private fun Modifier.clickable(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    onClick: () -> Unit
): Modifier {
    return this.then(
        clickable(
            interactionSource = interactionSource,
            indication = indication,
            onClick = onClick
        )
    )
}

/**
 * Preview for the simple bottom sheet
 */
@PreviewScreenSizes
@Preview
@Composable
fun SimpleBottomSheetPreview() {
    // Show a simple bottom sheet with content
    val coroutineScope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Simple Bottom Sheet Demo",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            androidx.compose.material3.Button(
                onClick = { showSheet = true }
            ) {
                Text("Show Bottom Sheet")
            }
        }

        // Bottom sheet
        SimpleBottomSheet(
            visible = showSheet,
            onDismissRequest = { showSheet = false },
            sheetContent = {
                SimpleSheetHeader(title = "Simple Bottom Sheet")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "This is a simple bottom sheet implementation with the essential features.",
                    style = TextStyle(fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Features:",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                listOf(
                    "Backdrop blur effect",
                    "Draggable sheet with snap behavior",
                    "Optional drag handle and close button",
                    "Customizable appearance"
                ).forEach { feature ->
                    Text(
                        text = "• $feature",
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
                ) {
                    androidx.compose.material3.TextButton(
                        onClick = { showSheet = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            },
            blurConfig = SimpleBlurConfig(isEnabled = true),
            showDragHandle = true,
            showCloseButton = true
        ) {
            // This is the content behind the sheet (already defined above)
        }
    }
}