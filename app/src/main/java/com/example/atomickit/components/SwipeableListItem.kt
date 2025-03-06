package com.example.atomickit.components
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Swipe action data class representing an action that can be revealed by swiping.
 *
 * @param icon The icon for the action.
 * @param backgroundColor The background color of the action.
 * @param iconColor The color of the icon.
 * @param weight The width weight of this action (relative to other actions).
 * @param onAction Callback when the action is triggered.
 */
data class SwipeAction(
    val icon: ImageVector,
    val backgroundColor: Color,
    val iconColor: Color = Color.White,
    val weight: Float = 1f,
    val onAction: () -> Unit
)

/**
 * Swipe configuration data class.
 *
 * @param leftActions List of actions to show when swiping from left to right.
 * @param rightActions List of actions to show when swiping from right to left.
 * @param threshold The threshold (percentage of width) at which the swipe will trigger an action.
 * @param animationSpec The animation spec for swipe animations.
 * @param maxSwipeWidth The maximum width that can be swiped, or null for full width.
 * @param swipeEnabled Whether swiping is enabled.
 */
data class SwipeConfig(
    val leftActions: List<SwipeAction> = emptyList(),
    val rightActions: List<SwipeAction> = emptyList(),
    val threshold: Float = 0.4f,
    val animationSpec: AnimationSpec<Float> = SpringSpec(stiffness = 800f),
    val maxSwipeWidth: Dp? = null,
    val swipeEnabled: Boolean = true,
)

/**
 * A swipeable list item component that reveals actions when swiped from either direction.
 *
 * @param content The content of the list item.
 * @param modifier The modifier to apply to the container.
 * @param swipeConfig The configuration for swipe behavior.
 */
@Composable
fun SwipeableListItem(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    swipeConfig: SwipeConfig = SwipeConfig()
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    // Store item width in pixels
    var itemWidthPx by remember { mutableStateOf(0f) }
    var maxSwipeWidthPx by remember { mutableStateOf(0f) }

    // Update maxSwipeWidth when config or item width changes
    LaunchedEffect(swipeConfig.maxSwipeWidth, itemWidthPx) {
        maxSwipeWidthPx = swipeConfig.maxSwipeWidth?.let { with(density) { it.toPx() } } ?: itemWidthPx
    }

    // Current offset of the content
    val offsetX = remember { Animatable(0f) }

    // Calculate the actual maximum swipe based on configuration
    val leftMaxPx = if (swipeConfig.leftActions.isEmpty()) 0f else maxSwipeWidthPx
    val rightMaxPx = if (swipeConfig.rightActions.isEmpty()) 0f else maxSwipeWidthPx

    // Calculate thresholds for activating actions
    val leftThresholdPx = leftMaxPx * swipeConfig.threshold
    val rightThresholdPx = rightMaxPx * swipeConfig.threshold

    // Current swipe state
    val leftSwipeProgress = if (leftMaxPx > 0f) (offsetX.value / leftMaxPx).coerceIn(0f, 1f) else 0f
    val rightSwipeProgress = if (rightMaxPx > 0f) (-offsetX.value / rightMaxPx).coerceIn(0f, 1f) else 0f

    // Reset offset when actions change
    LaunchedEffect(swipeConfig.leftActions, swipeConfig.rightActions) {
        offsetX.snapTo(0f)
    }

    // Function to snap to the nearest valid state
    fun snapToNearestState() {
        coroutineScope.launch {
            val current = offsetX.value
            when {
                // Swiping right (showing left actions)
                current > 0 -> {
                    if (current > leftThresholdPx) {
                        // Snap to fully revealed left actions
                        offsetX.animateTo(
                            targetValue = leftMaxPx,
                            animationSpec = swipeConfig.animationSpec
                        )
                    } else {
                        // Snap back to center
                        offsetX.animateTo(
                            targetValue = 0f,
                            animationSpec = swipeConfig.animationSpec
                        )
                    }
                }
                // Swiping left (showing right actions)
                current < 0 -> {
                    if (abs(current) > rightThresholdPx) {
                        // Snap to fully revealed right actions
                        offsetX.animateTo(
                            targetValue = -rightMaxPx,
                            animationSpec = swipeConfig.animationSpec
                        )
                    } else {
                        // Snap back to center
                        offsetX.animateTo(
                            targetValue = 0f,
                            animationSpec = swipeConfig.animationSpec
                        )
                    }
                }
            }
        }
    }

    // Function to process swipe action
    fun processSwipeAction() {
        when {
            offsetX.value >= leftThresholdPx && swipeConfig.leftActions.isNotEmpty() -> {
                // Trigger the action proportional to the swipe distance
                val actionIndex = ((swipeConfig.leftActions.size - 1) * leftSwipeProgress).roundToInt()
                    .coerceIn(0, swipeConfig.leftActions.size - 1)
                swipeConfig.leftActions[actionIndex].onAction()
                // Snap back to center
                coroutineScope.launch {
                    offsetX.animateTo(0f, swipeConfig.animationSpec)
                }
            }
            offsetX.value <= -rightThresholdPx && swipeConfig.rightActions.isNotEmpty() -> {
                // Trigger the action proportional to the swipe distance
                val actionIndex = ((swipeConfig.rightActions.size - 1) * rightSwipeProgress).roundToInt()
                    .coerceIn(0, swipeConfig.rightActions.size - 1)
                swipeConfig.rightActions[actionIndex].onAction()
                // Snap back to center
                coroutineScope.launch {
                    offsetX.animateTo(0f, swipeConfig.animationSpec)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onGloballyPositioned { coordinates ->
                itemWidthPx = coordinates.size.width.toFloat()
            }
    ) {
        // Left actions (revealed when swiping right)
        if (swipeConfig.leftActions.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(end = with(density) { maxSwipeWidthPx.toDp() }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                swipeConfig.leftActions.forEach { action ->
                    SwipeActionContent(
                        action = action,
                        modifier = Modifier
                            .weight(action.weight)
                            .fillMaxSize(),
                        progress = leftSwipeProgress
                    )
                }
            }
        }

        // Right actions (revealed when swiping left)
        if (swipeConfig.rightActions.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterEnd)
                    .padding(start = with(density) { maxSwipeWidthPx.toDp() }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                swipeConfig.rightActions.forEach { action ->
                    SwipeActionContent(
                        action = action,
                        modifier = Modifier
                            .weight(action.weight)
                            .fillMaxSize(),
                        progress = rightSwipeProgress
                    )
                }
            }
        }

        // Main content with swipe behavior
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .fillMaxWidth()
                .then(
                    if (swipeConfig.swipeEnabled) {
                        Modifier.draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                coroutineScope.launch {
                                    // Calculate new offset with boundaries
                                    val newOffset = offsetX.value + delta
                                    val boundedOffset = when {
                                        newOffset > 0 -> newOffset.coerceIn(0f, leftMaxPx)
                                        newOffset < 0 -> newOffset.coerceIn(-rightMaxPx, 0f)
                                        else -> newOffset
                                    }
                                    offsetX.snapTo(boundedOffset)
                                }
                            },
                            onDragStopped = {
                                snapToNearestState()
                                // Check if we should trigger the action
                                processSwipeAction()
                            }
                        )
                    } else {
                        Modifier
                    }
                )
        ) {
            content()
        }
    }
}

/**
 * Composable for a single swipe action's content.
 *
 * @param action The swipe action.
 * @param modifier The modifier to apply.
 * @param progress The progress of revealing this action (0-1).
 */
@Composable
private fun SwipeActionContent(
    action: SwipeAction,
    modifier: Modifier = Modifier,
    progress: Float
) {
    Box(
        modifier = modifier
            .background(action.backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            imageVector = action.icon,
            contentDescription = null,
            tint = action.iconColor,
            modifier = Modifier.padding(8.dp)
        )
    }
}

/**
 * Composable that provides a standard swipeable list item with delete action.
 */
@Composable
fun SwipeableDeleteItem(
    content: @Composable () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    swipeEnabled: Boolean = true
) {
    SwipeableListItem(
        content = content,
        modifier = modifier,
        swipeConfig = SwipeConfig(
            rightActions = listOf(
                SwipeAction(
                    icon = Icons.Default.Delete,
                    backgroundColor = Color(0xFFEF4444),
                    iconColor = Color.White,
                    onAction = onDelete
                )
            ),
            swipeEnabled = swipeEnabled
        )
    )
}

/**
 * Composable that provides a standard swipeable list item with favorite and delete actions.
 */
@Composable
fun SwipeableFavoriteDeleteItem(
    content: @Composable () -> Unit,
    onFavorite: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    swipeEnabled: Boolean = true
) {
    SwipeableListItem(
        content = content,
        modifier = modifier,
        swipeConfig = SwipeConfig(
            leftActions = listOf(
                SwipeAction(
                    icon = Icons.Default.Star,
                    backgroundColor = if (isFavorite) Color(0xFFF59E0B) else Color(0xFF6B7280),
                    iconColor = Color.White,
                    onAction = onFavorite
                )
            ),
            rightActions = listOf(
                SwipeAction(
                    icon = Icons.Default.Delete,
                    backgroundColor = Color(0xFFEF4444),
                    iconColor = Color.White,
                    onAction = onDelete
                )
            ),
            swipeEnabled = swipeEnabled
        )
    )
}

/**
 * Composable that provides a swipeable list item with multiple actions on the right.
 */
@Composable
fun SwipeableMultiActionItem(
    content: @Composable () -> Unit,
    rightActions: List<SwipeAction>,
    modifier: Modifier = Modifier,
    leftActions: List<SwipeAction> = emptyList(),
    maxSwipeWidth: Dp = 150.dp,
    swipeEnabled: Boolean = true
) {
    SwipeableListItem(
        content = content,
        modifier = modifier,
        swipeConfig = SwipeConfig(
            leftActions = leftActions,
            rightActions = rightActions,
            maxSwipeWidth = maxSwipeWidth,
            swipeEnabled = swipeEnabled
        )
    )
}