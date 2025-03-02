package com.example.atomickit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A custom button component built from scratch using Compose Foundation.
 *
 * @param onClick The callback to be invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button.
 * @param shape The shape of the button.
 * @param backgroundColor The background color of the button.
 * @param contentColor The color of the content (text/icon) inside the button.
 * @param disabledBackgroundColor The background color when the button is disabled.
 * @param disabledContentColor The content color when the button is disabled.
 * @param elevation The elevation (shadow) of the button.
 * @param border Optional border for the button.
 * @param contentPadding The padding values to be applied to the content of the button.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this button.
 * @param content The content of the button.
 */
@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = Color(0xFF3B82F6), // Blue color
    contentColor: Color = Color.White,
    disabledBackgroundColor: Color = Color(0xFFBFDBFE), // Light blue
    disabledContentColor: Color = Color(0xFF64748B), // Slate gray
    elevation: Boolean = true,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animate background color based on state
    val bgColor by animateColorAsState(
        targetValue = when {
            !enabled -> disabledBackgroundColor
            isPressed -> backgroundColor.copy(alpha = 0.7f)
            else -> backgroundColor
        },
        label = "buttonBackgroundColor"
    )

    // Animate elevation based on state
    val buttonElevation by animateDpAsState(
        targetValue = when {
            !enabled -> 0.dp
            isPressed -> 1.dp
            elevation -> 4.dp
            else -> 0.dp
        },
        label = "buttonElevation"
    )

    Box(
        modifier = modifier
            .shadow(buttonElevation, shape, clip = false)
            .clip(shape)
            .background(bgColor)
            .then(
                if (border != null) Modifier.padding(1.dp)
                else Modifier
            )
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            )
            .defaultMinSize(minWidth = 64.dp, minHeight = 36.dp)
            .semantics { role = Role.Button },
        contentAlignment = Alignment.Center
    ) {
        // Apply the content color
        val finalContentColor = when {
            !enabled -> disabledContentColor
            else -> contentColor
        }

        androidx.compose.runtime.CompositionLocalProvider(
            LocalContentColor provides finalContentColor
        ) {
            Box(modifier = Modifier.padding(contentPadding)) {
                content()
            }
        }
    }
}

// Define a proper Composition Local for content color
val LocalContentColor = androidx.compose.runtime.compositionLocalOf { Color.Black }

// Text component that consumes the local content color
@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    style: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    androidx.compose.foundation.text.BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(color = color),
        maxLines = maxLines,
        overflow = overflow
    )
}

// Extension for Button with text
@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = Color(0xFF3B82F6),
    contentColor: Color = Color.White,
    disabledBackgroundColor: Color = Color(0xFFBFDBFE),
    disabledContentColor: Color = Color(0xFF64748B),
    elevation: Boolean = true,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
) {
    CustomButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        Text(text = text)
    }
}