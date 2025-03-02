package com.example.atomickit.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A custom card component built from scratch using Compose Foundation.
 *
 * @param modifier The modifier to be applied to the card.
 * @param shape The shape of the card.
 * @param backgroundColor The background color of the card.
 * @param backgroundGradient Optional gradient for the background.
 * @param contentColor The color for the content inside the card.
 * @param border Optional border for the card.
 * @param elevation The elevation (shadow) of the card.
 * @param contentPadding The padding values to be applied to the content of the card.
 * @param onClick Optional click handler for the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param content The content of the card.
 */
@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    backgroundColor: Color = Color(0xFFFAFAFA),
    backgroundGradient: Brush? = null,
    contentColor: Color = Color(0xFF1F2937),
    border: BorderStroke? = null,
    elevation: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isClickable = onClick != null
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animate elevation based on interaction state
    val animatedElevation by animateDpAsState(
        targetValue = when {
            isClickable && isPressed -> elevation / 2
            else -> elevation
        },
        label = "cardElevation"
    )

    // Base modifier with shadow, shape, and background
    val baseModifier = modifier
        .shadow(
            elevation = animatedElevation,
            shape = shape,
            clip = false
        )
        .clip(shape)
        .then(
            if (backgroundGradient != null) {
                Modifier.background(backgroundGradient)
            } else {
                Modifier.background(backgroundColor)
            }
        )

    // Apply border if provided
    val withBorderModifier = if (border != null) {
        baseModifier.then(
            Modifier.border(
                border = border,
                shape = shape
            )
        )
    } else {
        baseModifier
    }

    // Apply click handling if provided
    val finalModifier = if (isClickable) {
        withBorderModifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            role = Role.Button,
            onClick = onClick!!
        )
    } else {
        withBorderModifier
    }

    // Apply content with padding and content color
    androidx.compose.runtime.CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        Box(
            modifier = finalModifier.padding(contentPadding)
        ) {
            content()
        }
    }
}

// Custom border implementation
@Composable
private fun Modifier.border(border: BorderStroke, shape: Shape): Modifier {
    return this.then(
        Modifier
            .padding(1.dp)  // Space for the border
            .background(
                color = border.brush.asBrushOrColor(),
                shape = shape
            )
            .padding(border.width)  // Actual border width
            .background(
                color = Color.Transparent,
                shape = shape
            )
    )
}

// Helper function to convert Brush to Color if needed
private fun Brush.asBrushOrColor(): Color {
    return if (this is SolidColor) {
        this.value
    } else {
        // Fall back to a default color if it's not a SolidColor
        Color.Gray
    }
}

// Extension for Card with title and content slots
@Composable
fun CustomCard(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    backgroundColor: Color = Color(0xFFFAFAFA),
    contentColor: Color = Color(0xFF1F2937),
    border: BorderStroke? = null,
    elevation: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onClick: (() -> Unit)? = null,
) {
    CustomCard(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        Column {
            Box(modifier = Modifier.padding(bottom = 12.dp)) {
                title()
            }
            content()
        }
    }
}

// Simple extension for a titled card with text content
@Composable
fun CustomCard(
    titleText: String,
    contentText: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CustomCard(
        title = {
            Text(
                text = titleText,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalContentColor.current
                )
            )
        },
        content = {
            Text(
                text = contentText,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f)
                )
            )
        },
        modifier = modifier,
        onClick = onClick
    )
}