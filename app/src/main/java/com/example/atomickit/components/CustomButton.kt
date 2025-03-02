package com.example.atomickit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
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
data class BoxShadow(
    val offsetX: Dp = 0.dp,
    val offsetY: Dp = 2.dp,
    val blurRadius: Dp = 4.dp,
    val spreadRadius: Dp = 0.dp,
    val color: Color = Color(0x66000000) // 40% black
)

/**
 * Custom Button with advanced box shadow capabilities
 *
 * @param onClick The callback to be invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button.
 * @param shape The shape of the button.
 * @param backgroundColor The background color of the button.
 * @param contentColor The color of the content (text/icon) inside the button.
 * @param disabledBackgroundColor The background color when the button is disabled.
 * @param disabledContentColor The content color when the button is disabled.
 * @param boxShadow The normal state box shadow configuration.
 * @param pressedBoxShadow The box shadow configuration when the button is pressed.
 * @param disabledBoxShadow The box shadow configuration when the button is disabled.
 * @param border Optional border for the button.
 * @param contentPadding The padding values to be applied to the content of the button.
 * @param interactionSource The MutableInteractionSource representing the stream of interactions for this button.
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
    disabledBoxShadow: BoxShadow? = BoxShadow(
        offsetY = 0.dp,
        blurRadius = 0.dp,
        spreadRadius = 0.dp,
        color = Color.Transparent
    ),
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

    // Determine which shadow to use based on state
    val currentShadow by animateValueAsState(
        targetValue = when {
            !enabled -> disabledBoxShadow ?: BoxShadow(color = Color.Transparent)
            isPressed -> pressedBoxShadow
            else -> boxShadow
        },
        typeConverter = BoxShadowVectorConverter,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "boxShadow"
    )

    Box(
        modifier = modifier
            .drawWithBoxShadow(currentShadow, shape)
            .clip(shape)
            .background(bgColor)
            .then(
                if (border != null) Modifier.applyButtonBorder(border, shape)
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

// Extension function to draw a box shadow
fun Modifier.drawWithBoxShadow(shadow: BoxShadow, shape: Shape): Modifier {
    return this.drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()

            // Configure the shadow paint
            frameworkPaint.color = shadow.color.toArgb()

            // The shadow properties - X offset, Y offset, Blur, Color
            frameworkPaint.setShadowLayer(
                shadow.blurRadius.toPx(),
                shadow.offsetX.toPx(),
                shadow.offsetY.toPx(),
                shadow.color.copy(alpha = 0.7f).toArgb()
            )

            // Apply the spread radius by adjusting the drawing area
            val spreadOffset = shadow.spreadRadius.toPx()
            val left = 0f + spreadOffset / 2
            val top = 0f + spreadOffset / 2
            val right = size.width - spreadOffset / 2
            val bottom = size.height - spreadOffset / 2

            // Create the path for the shape and draw it
            // Create the path for the shape with adjusted size for spread
            val outline = shape.createOutline(
                size = size.copy(
                    width = size.width - spreadOffset,
                    height = size.height - spreadOffset
                ),
                layoutDirection = layoutDirection,
                density = androidx.compose.ui.unit.Density(density)
            )

            // Convert the outline to an Android Path
            val path = android.graphics.Path()

            // Handle different types of outlines
            when (outline) {
                is androidx.compose.ui.graphics.Outline.Rectangle -> {
                    path.addRect(
                        outline.rect.left,
                        outline.rect.top,
                        outline.rect.right,
                        outline.rect.bottom,
                        android.graphics.Path.Direction.CW
                    )
                }
                is androidx.compose.ui.graphics.Outline.Rounded -> {
                    val rectF = android.graphics.RectF(
                        outline.roundRect.left,
                        outline.roundRect.top,
                        outline.roundRect.right,
                        outline.roundRect.bottom
                    )

                    // Handle different corner radii
                    // Each corner needs 2 values (x and y radius), so 8 values total
                    val radii = FloatArray(8)
                    // Top-left corner
                    radii[0] = outline.roundRect.topLeftCornerRadius.x
                    radii[1] = outline.roundRect.topLeftCornerRadius.y
                    // Top-right corner
                    radii[2] = outline.roundRect.topRightCornerRadius.x
                    radii[3] = outline.roundRect.topRightCornerRadius.y
                    // Bottom-right corner
                    radii[4] = outline.roundRect.bottomRightCornerRadius.x
                    radii[5] = outline.roundRect.bottomRightCornerRadius.y
                    // Bottom-left corner
                    radii[6] = outline.roundRect.bottomLeftCornerRadius.x
                    radii[7] = outline.roundRect.bottomLeftCornerRadius.y

                    path.addRoundRect(
                        rectF,
                        radii,
                        android.graphics.Path.Direction.CW
                    )
                }
                is androidx.compose.ui.graphics.Outline.Generic -> {
                    // Convert the outline path to Android path
                    path.addPath(outline.path.asAndroidPath())
                }
            }

            // Adjust the path with the spread offset
            path.offset(spreadOffset / 2, spreadOffset / 2)

            // Draw the shadow
            canvas.nativeCanvas.drawPath(path, frameworkPaint)
        }
    }
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
    boxShadow: BoxShadow = BoxShadow(),
    pressedBoxShadow: BoxShadow = BoxShadow(
        offsetY = 1.dp,
        blurRadius = 2.dp,
        color = Color(0x20000000)
    ),
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
        boxShadow = boxShadow,
        pressedBoxShadow = pressedBoxShadow,
        border = border,
        contentPadding = contentPadding
    ) {
        Text(text = text)
    }
}

// Define a vector converter for BoxShadow animation
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
            spreadRadius = Dp(vector.v4)
        )
    }
)

// Helper function for applying a border to the button
private fun Modifier.applyButtonBorder(border: BorderStroke, shape: Shape): Modifier {
    // Extract color from brush if it's a SolidColor brush
    val borderColor = (border.brush as? androidx.compose.ui.graphics.SolidColor)?.value
        ?: Color.Black // Fallback for non-solid color brushes

    return this.then(
        Modifier
            .padding(1.dp)  // Space for the border
            .background(
                color = borderColor,
                shape = shape
            )
            .padding(border.width)  // Actual border width
            .background(
                color = Color.Transparent,
                shape = shape
            )
    )
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

// Preview for the CustomButton
@androidx.compose.ui.tooling.preview.Preview
@Composable
fun CustomButtonPreview() {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        // Default button
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Default Button"
        )

        // Button with custom shadow
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Custom Shadow",
            backgroundColor = Color(0xFF6366F1),
            boxShadow = BoxShadow(
                offsetY = 4.dp,
                blurRadius = 8.dp,
                spreadRadius = 1.dp,
                color = Color(0x406366F1) // Semi-transparent indigo
            )
        )

        // Button with multiple shadows
        CustomButton(
            onClick = { /* do nothing */ },
            text = "No Shadow",
            backgroundColor = Color(0xFFF59E0B),
            boxShadow = BoxShadow(
                offsetX = 0.dp,
                offsetY = 0.dp,
                blurRadius = 0.dp,
                spreadRadius = 0.dp,
                color = Color.Transparent
            )
        )

        // Disabled button
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Disabled Button",
            enabled = false
        )

        // Button with border
        CustomButton(
            onClick = { /* do nothing */ },
            text = "Bordered Button",
            backgroundColor = Color.White,
            contentColor = Color(0xFF3B82F6),
            border = BorderStroke(1.dp, Color(0xFF3B82F6)),
            boxShadow = BoxShadow(
                offsetY = 2.dp,
                blurRadius = 4.dp,
                color = Color(0x203B82F6)
            )
        )
    }
}