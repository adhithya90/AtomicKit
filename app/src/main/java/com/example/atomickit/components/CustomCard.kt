package com.example.atomickit.components

import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A class representing box shadow properties, similar to CSS box-shadow
 *
 * @param offsetX The horizontal offset of the shadow. Positive values put the shadow on the right side of the box.
 * @param offsetY The vertical offset of the shadow. Positive values put the shadow below the box.
 * @param blurRadius The blur radius. The larger this value, the bigger the blur.
 * @param spreadRadius The spread radius. Positive values increase the size of the shadow.
 * @param color The color of the shadow.
 */


/**
 * A custom card component built from scratch using Compose Foundation.
 *
 * @param modifier The modifier to be applied to the card.
 * @param shape The shape of the card.
 * @param backgroundColor The background color of the card.
 * @param backgroundGradient Optional gradient for the background.
 * @param contentColor The color for the content inside the card.
 * @param border Optional border for the card.
 * @param boxShadow The box shadow configuration for the card.
 * @param pressedBoxShadow The box shadow configuration when the card is pressed.
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
    boxShadow: BoxShadow = BoxShadow(
        offsetY = 2.dp,
        blurRadius = 4.dp,
        color = Color(0x40000000) // 25% black
    ),
    pressedBoxShadow: BoxShadow = BoxShadow(
        offsetY = 1.dp,
        blurRadius = 2.dp,
        color = Color(0x20000000) // 12.5% black
    ),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isClickable = onClick != null
    val isPressed by interactionSource.collectIsPressedAsState()

    // Determine which shadow to use based on state
    val currentShadow by animateValueAsState(
        targetValue = when {
            isClickable && isPressed -> pressedBoxShadow
            else -> boxShadow
        },
        typeConverter = BoxShadowVectorConverter,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardBoxShadow"
    )

    // Base modifier with shadow, shape, and background
    val baseModifier = modifier
        .drawWithBoxShadow(currentShadow, shape)
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
    CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        Box(
            modifier = finalModifier.padding(contentPadding)
        ) {
            content()
        }
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
    backgroundGradient: Brush? = null,
    contentColor: Color = Color(0xFF1F2937),
    border: BorderStroke? = null,
    boxShadow: BoxShadow = BoxShadow(
        offsetY = 2.dp,
        blurRadius = 4.dp,
        color = Color(0x40000000)
    ),
    pressedBoxShadow: BoxShadow = BoxShadow(
        offsetY = 1.dp,
        blurRadius = 2.dp,
        color = Color(0x20000000)
    ),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onClick: (() -> Unit)? = null,
) {
    CustomCard(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        backgroundGradient = backgroundGradient,
        contentColor = contentColor,
        border = border,
        boxShadow = boxShadow,
        pressedBoxShadow = pressedBoxShadow,
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
    backgroundColor: Color = Color(0xFFFAFAFA),
    contentColor: Color = Color(0xFF1F2937),
    boxShadow: BoxShadow = BoxShadow(
        offsetY = 2.dp,
        blurRadius = 4.dp,
        color = Color(0x40000000)
    ),
    onClick: (() -> Unit)? = null
) {
    CustomCard(
        title = {
            Text(
                text = titleText,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            )
        },
        content = {
            Text(
                text = contentText,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = contentColor.copy(alpha = 0.8f)
                )
            )
        },
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        boxShadow = boxShadow,
        onClick = onClick
    )
}


// Vector converter for BoxShadow animation
private val BoxShadowVectorConverter = androidx.compose.animation.core.TwoWayConverter<BoxShadow, AnimationVector4D>(
    convertToVector = { boxShadow: BoxShadow ->
        AnimationVector4D(
            boxShadow.offsetX.value,
            boxShadow.offsetY.value,
            boxShadow.blurRadius.value,
            boxShadow.spreadRadius.value
        )
    },
    convertFromVector = { vector: AnimationVector4D ->
        BoxShadow(
            offsetX = Dp(vector.v1),
            offsetY = Dp(vector.v2),
            blurRadius = Dp(vector.v3),
            spreadRadius = Dp(vector.v4),
            color = Color(0x40000000) // Default color, can be improved by adding color to the vector
        )
    }
)

