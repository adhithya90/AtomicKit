package com.example.atomickit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A custom top app bar component built from scratch using Compose Foundation.
 *
 * @param title The title to be displayed in the app bar.
 * @param modifier The modifier to be applied to the app bar.
 * @param navigationIcon Optional navigation icon to be displayed at the start of the app bar.
 * @param actions Optional actions to be displayed at the end of the app bar.
 * @param backgroundColor The background color of the app bar.
 * @param contentColor The color of the content (text and icons) inside the app bar.
 * @param elevation The elevation (shadow) of the app bar.
 * @param contentPadding The padding values to be applied to the content of the app bar.
 * @param height The height of the app bar.
 * @param shape The shape of the app bar.
 * @param centerTitle Whether the title should be centered.
 * @param scrollBehavior The scroll behavior to enable collapsing or expanding app bar behavior.
 */
@Composable
fun CustomTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    backgroundColor: Color = Color(0xFFFFFFFF),
    contentColor: Color = Color(0xFF1F2937),
    elevation: Dp = 4.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    height: Dp = 56.dp,
    shape: Shape = RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp),
    centerTitle: Boolean = false,
    scrollBehavior: AppBarScrollBehavior? = null
) {
    // Calculate current state based on scroll
    val scrollFraction = scrollBehavior?.scrollFraction ?: 0f

    // Animate elevation based on scroll
    val appBarElevation by animateDpAsState(
        targetValue = if (scrollBehavior != null) {
            if (scrollFraction > 0f) elevation else 0.dp
        } else {
            elevation
        },
        label = "appBarElevation"
    )

    // Animate background color alpha based on scroll
    val appBarBackgroundColor by animateColorAsState(
        targetValue = backgroundColor.copy(alpha = 1f - (scrollFraction * 0.3f)),
        label = "appBarBackgroundColor"
    )

    // Calculate current height based on scroll
    val heightPx = with(LocalDensity.current) { height.toPx() }
    val currentHeight = with(LocalDensity.current) {
        val minHeight = height * 0.7f
        (heightPx * (1f - (scrollFraction * 0.3f))).coerceAtLeast(minHeight.toPx()).toDp()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(currentHeight)
            .shadow(
                elevation = appBarElevation,
                shape = shape,
                clip = false
            )
            .clip(shape)
            .background(appBarBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation icon
            if (navigationIcon != null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColor) {
                        navigationIcon()
                    }
                }
            } else {
                Spacer(modifier = Modifier.width(4.dp))
            }

            // Title
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                contentAlignment = if (centerTitle) Alignment.Center else Alignment.CenterStart
            ) {
                androidx.compose.runtime.CompositionLocalProvider(
                    LocalContentColor provides contentColor
                ) {
                    title()
                }
            }

            // Actions
            if (actions != null) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }
    }
}

/**
 * Represents the scroll behavior for the app bar.
 */
interface AppBarScrollBehavior {
    /**
     * The fraction of scrolling applied to the app bar (0f to 1f).
     */
    val scrollFraction: Float
}

/**
 * A simple implementation of AppBarScrollBehavior that takes a fixed scroll fraction.
 *
 * @param scrollFraction The scroll fraction (0f to 1f).
 */
class SimpleAppBarScrollBehavior(override val scrollFraction: Float) : AppBarScrollBehavior

/**
 * Creates an app bar scroll behavior that responds to a scroll fraction.
 *
 * @param scrollFraction The scroll fraction (0f to 1f), should be computed based on scroll state.
 */
@Composable
fun rememberAppBarScrollBehavior(scrollFraction: Float): AppBarScrollBehavior {
    return SimpleAppBarScrollBehavior(scrollFraction.coerceIn(0f, 1f))
}

// Helper extension function for a simple top app bar with text title
@Composable
fun CustomTopAppBar(
    titleText: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    backgroundColor: Color = Color(0xFFFFFFFF),
    contentColor: Color = Color(0xFF1F2937),
    elevation: Dp = 4.dp,
    centerTitle: Boolean = false
) {
    CustomTopAppBar(
        title = {
            Text(
                text = titleText,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        centerTitle = centerTitle
    )
}