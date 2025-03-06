package com.example.atomickit.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.Text
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Enum class representing different states of a list.
 */
enum class ListState {
    LOADING,
    CONTENT,
    EMPTY,
    ERROR
}

/**
 * Data class for pull-to-refresh configuration.
 *
 * @param enabled Whether pull-to-refresh is enabled.
 * @param indicatorHeight The height of the refresh indicator when fully expanded.
 * @param threshold The drag distance needed to trigger a refresh.
 * @param indicatorColor The color of the refresh indicator.
 */
data class PullRefreshConfig(
    val enabled: Boolean = true,
    val indicatorHeight: Dp = 56.dp,
    val threshold: Dp = 80.dp,
    val indicatorColor: Color = Color(0xFF6366F1) // Indigo color
)

/**
 * An enhanced lazy column with built-in support for different list states and pull-to-refresh.
 *
 * @param listState The current state of the list (LOADING, CONTENT, EMPTY, ERROR).
 * @param modifier The modifier to be applied to the list.
 * @param contentPadding The padding around the list content.
 * @param verticalArrangement The arrangement of items in the list.
 * @param state The lazy list state.
 * @param pullRefreshConfig Configuration for pull-to-refresh behavior.
 * @param onRefresh Callback when a refresh is triggered.
 * @param loadingContent Custom content to display in the loading state.
 * @param emptyContent Custom content to display when the list is empty.
 * @param errorContent Custom content to display in the error state.
 * @param content The content of the list.
 */
@Composable
fun EnhancedLazyColumn(
    listState: ListState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    state: LazyListState = rememberLazyListState(),
    pullRefreshConfig: PullRefreshConfig = PullRefreshConfig(),
    onRefresh: (() -> Unit)? = null,
    loadingContent: @Composable BoxScope.() -> Unit = { DefaultLoadingContent() },
    emptyContent: @Composable BoxScope.() -> Unit = { DefaultEmptyContent() },
    errorContent: @Composable BoxScope.() -> Unit = { DefaultErrorContent() },
    content: LazyListScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var refreshInProgress by remember { mutableStateOf(false) }
    var pullRefreshState by remember { mutableStateOf(0f) }
    val animatedPullRefreshState by animateFloatAsState(
        targetValue = pullRefreshState,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "pullRefreshAnimation"
    )

    // Create pull refresh handler
    val pullRefreshEnabled = pullRefreshConfig.enabled && onRefresh != null

    // Custom pull refresh connection - simplified implementation
    val nestedScrollConnection = remember(pullRefreshConfig) {
        if (pullRefreshEnabled) {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: androidx.compose.ui.geometry.Offset,
                    source: androidx.compose.ui.input.nestedscroll.NestedScrollSource
                ): androidx.compose.ui.geometry.Offset {
                    // We don't want to intercept pre-scroll
                    return androidx.compose.ui.geometry.Offset.Zero
                }

                override fun onPostScroll(
                    consumed: androidx.compose.ui.geometry.Offset,
                    available: androidx.compose.ui.geometry.Offset,
                    source: androidx.compose.ui.input.nestedscroll.NestedScrollSource
                ): androidx.compose.ui.geometry.Offset {
                    // If we have unconsumed scroll and we're not refreshing, update pull state
                    if (available.y > 0 && !refreshInProgress) {
                        pullRefreshState = (pullRefreshState + available.y * 0.5f)
                            .coerceIn(0f, pullRefreshConfig.threshold.value * 1.5f)

                        // Check if we've reached the threshold
                        if (pullRefreshState >= pullRefreshConfig.threshold.value && !refreshInProgress) {
                            refreshInProgress = true
                            coroutineScope.launch {
                                if (onRefresh != null) {
                                    onRefresh()
                                }
                                // Allow some time for the refresh animation to play
                                delay(500)
                                refreshInProgress = false
                                pullRefreshState = 0f
                            }
                        }

                        return available.copy(x = 0f)
                    }
                    return androidx.compose.ui.geometry.Offset.Zero
                }

                // When scrolling stops, reset pull state if not refreshing
                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    if (!refreshInProgress) {
                        pullRefreshState = 0f
                    }
                    return Velocity.Zero
                }
            }
        } else null
    }

    // Indicator factor (0f - 1f)
    val refreshProgress by remember(animatedPullRefreshState, pullRefreshConfig) {
        derivedStateOf {
            (animatedPullRefreshState / pullRefreshConfig.threshold.value).coerceIn(0f, 1f)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = listState,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "listStateTransition"
        ) { currentState ->
            when (currentState) {
                ListState.LOADING -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = loadingContent
                )

                ListState.EMPTY -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = emptyContent
                )

                ListState.ERROR -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = errorContent
                )

                ListState.CONTENT -> {
                    val listModifier = if (nestedScrollConnection != null) {
                        Modifier.nestedScroll(nestedScrollConnection)
                    } else {
                        Modifier
                    }

                    LazyColumn(
                        state = state,
                        contentPadding = contentPadding,
                        verticalArrangement = verticalArrangement,
                        horizontalAlignment = horizontalAlignment,
                        modifier = listModifier.fillMaxSize(),
                        content = content
                    )
                }
            }
        }

        // Pull to refresh indicator
        if (pullRefreshConfig.enabled && (refreshProgress > 0f || refreshInProgress)) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp)
                    .size(pullRefreshConfig.indicatorHeight * refreshProgress),
                contentAlignment = Alignment.Center
            ) {
                val rotation = if (refreshInProgress) {
                    360f * (System.currentTimeMillis() % 2000) / 2000f
                } else {
                    refreshProgress * 180f
                }

                Image(
                    imageVector = Icons.Default.Warning, // Use appropriate refresh icon
                    contentDescription = "Pull to refresh",
                    colorFilter = ColorFilter.tint(pullRefreshConfig.indicatorColor),
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
    }
}

/**
 * An enhanced lazy row with built-in support for different list states.
 *
 * @param listState The current state of the list (LOADING, CONTENT, EMPTY, ERROR).
 * @param modifier The modifier to be applied to the list.
 * @param contentPadding The padding around the list content.
 * @param horizontalArrangement The arrangement of items in the list.
 * @param state The lazy list state.
 * @param loadingContent Custom content to display in the loading state.
 * @param emptyContent Custom content to display when the list is empty.
 * @param errorContent Custom content to display in the error state.
 * @param content The content of the list.
 */
@Composable
fun EnhancedLazyRow(
    listState: ListState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    state: LazyListState = rememberLazyListState(),
    loadingContent: @Composable BoxScope.() -> Unit = { DefaultLoadingContent() },
    emptyContent: @Composable BoxScope.() -> Unit = { DefaultEmptyContent() },
    errorContent: @Composable BoxScope.() -> Unit = { DefaultErrorContent() },
    content: LazyListScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = listState,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "listStateTransition"
        ) { currentState ->
            when (currentState) {
                ListState.LOADING -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = loadingContent
                )

                ListState.EMPTY -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = emptyContent
                )

                ListState.ERROR -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = errorContent
                )

                ListState.CONTENT -> {
                    LazyRow(
                        state = state,
                        contentPadding = contentPadding,
                        horizontalArrangement = horizontalArrangement,
                        verticalAlignment = verticalAlignment,
                        modifier = Modifier.fillMaxSize(),
                        content = content
                    )
                }
            }
        }
    }
}

/**
 * An enhanced lazy vertical grid with built-in support for different list states.
 *
 * @param listState The current state of the list (LOADING, CONTENT, EMPTY, ERROR).
 * @param columns Number of columns in the grid.
 * @param modifier The modifier to be applied to the grid.
 * @param contentPadding The padding around the grid content.
 * @param verticalArrangement The arrangement of items in the grid.
 * @param horizontalArrangement The horizontal arrangement of items in the grid.
 * @param loadingContent Custom content to display in the loading state.
 * @param emptyContent Custom content to display when the grid is empty.
 * @param errorContent Custom content to display in the error state.
 * @param content The content of the grid.
 */
@Composable
fun EnhancedLazyGrid(
    listState: ListState,
    columns: androidx.compose.foundation.lazy.grid.GridCells,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    state: androidx.compose.foundation.lazy.grid.LazyGridState = rememberLazyGridState(),
    loadingContent: @Composable BoxScope.() -> Unit = { DefaultLoadingContent() },
    emptyContent: @Composable BoxScope.() -> Unit = { DefaultEmptyContent() },
    errorContent: @Composable BoxScope.() -> Unit = { DefaultErrorContent() },
    content: LazyGridScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = listState,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "gridStateTransition"
        ) { currentState ->
            when (currentState) {
                ListState.LOADING -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = loadingContent
                )

                ListState.EMPTY -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = emptyContent
                )

                ListState.ERROR -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = errorContent
                )

                ListState.CONTENT -> {
                    LazyVerticalGrid(
                        columns = columns,
                        state = state,
                        contentPadding = contentPadding,
                        verticalArrangement = verticalArrangement,
                        horizontalArrangement = horizontalArrangement,
                        modifier = Modifier.fillMaxSize(),
                        content = content
                    )
                }
            }
        }
    }
}

/**
 * Default loading content for lists.
 */
@Composable
fun DefaultLoadingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Circular indicator with rotation animation
        var currentRotation by remember { mutableStateOf(0f) }

        // Animate the rotation
        LaunchedEffect(Unit) {
            while (true) {
                delay(16) // roughly 60fps
                currentRotation = (currentRotation + 6) % 360
            }
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .rotate(currentRotation),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Default.Warning, // Use an appropriate circular icon
                contentDescription = "Loading",
                colorFilter = ColorFilter.tint(Color(0xFF6366F1)),
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Loading...",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6366F1)
            )
        )
    }
}

/**
 * Default empty content for lists.
 */
@Composable
fun DefaultEmptyContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            imageVector = Icons.Default.Warning,
            contentDescription = "Empty",
            colorFilter = ColorFilter.tint(Color(0xFF9CA3AF)),
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No items found",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B5563)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "There are no items to display at this time.",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}

/**
 * Default error content for lists.
 */
@Composable
fun DefaultErrorContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            imageVector = Icons.Default.Close,
            contentDescription = "Error",
            colorFilter = ColorFilter.tint(Color(0xFFEF4444)),
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Error loading content",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B5563)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Something went wrong. Please try again later.",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}
