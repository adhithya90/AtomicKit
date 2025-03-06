package com.example.atomickit.layouts


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

/**
 * Represents a screen size range with min/max width and height.
 * All values are in dp.
 */
class ScreenSizeRange private constructor(
    val minWidth: Float = 0f,
    val maxWidth: Float? = null,
    val minHeight: Float = 0f,
    val maxHeight: Float? = null,
    val predicate: ((widthDp: Float, heightDp: Float) -> Boolean)? = null
) {
    companion object {
        // Factory methods to create instances without using Dp directly
        fun create(
            minWidthDp: Float = 0f,
            maxWidthDp: Float? = null,
            minHeightDp: Float = 0f,
            maxHeightDp: Float? = null
        ): ScreenSizeRange {
            return ScreenSizeRange(minWidthDp, maxWidthDp, minHeightDp, maxHeightDp)
        }

        fun createWithPredicate(
            minWidthDp: Float = 0f,
            maxWidthDp: Float? = null,
            minHeightDp: Float = 0f,
            maxHeightDp: Float? = null,
            predicate: (widthDp: Float, heightDp: Float) -> Boolean
        ): ScreenSizeRange {
            return ScreenSizeRange(minWidthDp, maxWidthDp, minHeightDp, maxHeightDp, predicate)
        }

        // Predefined screen size ranges
        val COMPACT = create(0f, 600f)
        val MEDIUM = create(600f, 840f)
        val EXPANDED = create(840f, null)

        // Portrait vs landscape
        val PORTRAIT = createWithPredicate { width, height -> height > width }
        val LANDSCAPE = createWithPredicate { width, height -> width >= height }

        // Device types
        val TABLET = create(600f, 1200f)
        val DESKTOP = create(1200f, null, 720f, null)

        // Aspect ratios
        val TALL = createWithPredicate { width, height -> height / width > 1.9f }
        val STANDARD = createWithPredicate { width, height ->
            val ratio = height / width
            ratio >= 1.3f && ratio <= 1.9f
        }
        val WIDE = createWithPredicate { width, height -> width / height > 1.7f }
    }

    fun matches(widthDp: Float, heightDp: Float): Boolean {
        val widthMatches = widthDp >= minWidth && (maxWidth == null || widthDp <= maxWidth)
        val heightMatches = heightDp >= minHeight && (maxHeight == null || heightDp <= maxHeight)
        val baseMatches = widthMatches && heightMatches

        return if (predicate != null) {
            baseMatches && predicate.invoke(widthDp, heightDp)
        } else {
            baseMatches
        }
    }
}

/**
 * Screen dimensions data class holding width/height in both dp and px.
 */
data class ScreenDimensions(
    val widthDp: Float,
    val heightDp: Float,
    val widthPx: Int,
    val heightPx: Int
)

// CompositionLocal for screen dimensions
val LocalScreenDimensions = compositionLocalOf<ScreenDimensions?> { null }

/**
 * Provider that measures screen size and makes dimensions available through LocalScreenDimensions.
 */
@Composable
fun ResponsiveLayoutProvider(
    content: @Composable BoxScope.() -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { size = it }
    ) {
        if (size != IntSize.Zero) {
            val widthPx = size.width
            val heightPx = size.height

            val widthDp = with(density) { widthPx.toDp().value }
            val heightDp = with(density) { heightPx.toDp().value }

            val dimensions = remember(widthDp, heightDp) {
                ScreenDimensions(widthDp, heightDp, widthPx, heightPx)
            }

            CompositionLocalProvider(LocalScreenDimensions provides dimensions) {
                content()
            }
        }
    }
}

/**
 * Composable function to create a ScreenSizeRange inside a composable context.
 */
@Composable
fun rememberScreenSizeRange(
    minWidth: Dp = 0.dp,
    maxWidth: Dp? = null,
    minHeight: Dp = 0.dp,
    maxHeight: Dp? = null,
    predicate: ((widthDp: Float, heightDp: Float) -> Boolean)? = null
): ScreenSizeRange {
    return remember(minWidth, maxWidth, minHeight, maxHeight, predicate) {
        if (predicate != null) {
            ScreenSizeRange.createWithPredicate(
                minWidth.value,
                maxWidth?.value,
                minHeight.value,
                maxHeight?.value,
                predicate
            )
        } else {
            ScreenSizeRange.create(
                minWidth.value,
                maxWidth?.value,
                minHeight.value,
                maxHeight?.value
            )
        }
    }
}

/**
 * Component that renders different content based on screen dimensions.
 */
@Composable
fun ResponsiveLayout(
    ranges: Map<ScreenSizeRange, @Composable () -> Unit>,
    modifier: Modifier = Modifier,
    defaultContent: (@Composable () -> Unit)? = null
) {
    Box(modifier = modifier) {
        LocalScreenDimensions.current?.let { dimensions ->
            // Find the first matching range
            val matchingContent = ranges.entries.firstOrNull { (range, _) ->
                range.matches(dimensions.widthDp, dimensions.heightDp)
            }?.value ?: defaultContent

            // Render matching content if found
            matchingContent?.invoke()
        }
    }
}

/**
 * Component that adapts content based on width breakpoints.
 */
@Composable
fun ResponsiveBreakpoints(
    breakpoints: Map<Dp, @Composable () -> Unit>,
    modifier: Modifier = Modifier,
    defaultContent: (@Composable () -> Unit)? = null
) {
    // Convert Dp breakpoints to ScreenSizeRange
    val ranges = remember(breakpoints) {
        val sortedBreakpoints = breakpoints.entries.sortedBy { it.key.value }

        sortedBreakpoints.mapIndexed { index, (minWidth, content) ->
            val maxWidth = if (index < sortedBreakpoints.size - 1) {
                sortedBreakpoints[index + 1].key.value - 0.01f
            } else {
                null
            }

            ScreenSizeRange.create(minWidth.value, maxWidth) to content
        }.toMap()
    }

    ResponsiveLayout(
        ranges = ranges,
        modifier = modifier,
        defaultContent = defaultContent
    )
}

/**
 * Component for building custom responsive layouts with direct access to dimensions.
 */
@Composable
fun ResponsiveUI(
    modifier: Modifier = Modifier,
    content: @Composable (dimensions: ScreenDimensions) -> Unit
) {
    Box(modifier = modifier) {
        LocalScreenDimensions.current?.let { dimensions ->
            content(dimensions)
        }
    }
}

/**
 * A responsive grid that adapts column count based on screen width.
 */
@Composable
fun <T> ResponsiveGrid(
    items: List<T>,
    itemContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    spacing: Dp = 16.dp,
    breakpoints: Map<Dp, Int> = mapOf(
        0.dp to 1,
        600.dp to 2,
        900.dp to 3,
        1200.dp to 4
    )
) {
    Box(modifier = modifier) {
        LocalScreenDimensions.current?.let { dimensions ->
            // Find the appropriate number of columns
            val sortedBreakpoints = remember(breakpoints) {
                breakpoints.entries.sortedBy { it.key.value }
            }

            val columns = remember(dimensions, sortedBreakpoints) {
                sortedBreakpoints.lastOrNull { it.key.value <= dimensions.widthDp }?.value
                    ?: sortedBreakpoints.firstOrNull()?.value
                    ?: 1
            }

            // Build the grid layout
            Column(
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(spacing)
            ) {
                val rows = (items.size + columns - 1) / columns

                for (rowIndex in 0 until rows) {
                    Row(
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(spacing)
                    ) {
                        for (colIndex in 0 until columns) {
                            val itemIndex = rowIndex * columns + colIndex

                            androidx.compose.foundation.layout.Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                if (itemIndex < items.size) {
                                    itemContent(items[itemIndex])
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}