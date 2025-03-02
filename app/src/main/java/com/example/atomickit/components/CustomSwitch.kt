package com.example.atomickit.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A custom switch component built from scratch using Compose Foundation.
 *
 * @param checked Whether the switch is checked or not.
 * @param onCheckedChange The callback to be invoked when the switch is clicked.
 * @param modifier The modifier to be applied to the switch.
 * @param enabled Controls the enabled state of the switch.
 * @param width The width of the switch track.
 * @param height The height of the switch track.
 * @param trackShape The shape of the switch track.
 * @param thumbShape The shape of the switch thumb.
 * @param trackColor The color of the track when the switch is checked.
 * @param trackColorUnchecked The color of the track when the switch is unchecked.
 * @param thumbColor The color of the thumb when the switch is checked.
 * @param thumbColorUnchecked The color of the thumb when the switch is unchecked.
 * @param disabledTrackColor The color of the track when the switch is disabled.
 * @param disabledThumbColor The color of the thumb when the switch is disabled.
 * @param borderWidth The width of the border around the track.
 * @param borderColor The color of the border around the track.
 * @param thumbSize The size of the thumb.
 * @param thumbPadding The padding around the thumb.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this switch.
 */
@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    width: Dp = 52.dp,
    height: Dp = 32.dp,
    trackShape: Shape = RoundedCornerShape(16.dp),
    thumbShape: Shape = CircleShape,
    trackColor: Color = Color(0xFF3B82F6), // Blue
    trackColorUnchecked: Color = Color(0xFFE5E7EB), // Light gray
    thumbColor: Color = Color.White,
    thumbColorUnchecked: Color = Color.White,
    disabledTrackColor: Color = Color(0xFFE5E7EB).copy(alpha = 0.6f),
    disabledThumbColor: Color = Color(0xFFD1D5DB),
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color(0xFFD1D5DB),
    thumbSize: Dp = height - 8.dp,
    thumbPadding: Dp = 2.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // Calculate track and thumb properties
    val thumbRadius = thumbSize / 2
    val trackAlpha = if (enabled) 1f else 0.6f

    // Calculate thumb position based on checked state
    val thumbPosition by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        label = "thumbPosition"
    )

    // Animate thumb size when pressed
    val thumbSizeAnimated by animateDpAsState(
        targetValue = thumbSize,
        label = "thumbSize"
    )

    // Determine colors based on state
    val finalTrackColor = when {
        !enabled -> disabledTrackColor
        checked -> trackColor
        else -> trackColorUnchecked
    }

    val finalThumbColor = when {
        !enabled -> disabledThumbColor
        checked -> thumbColor
        else -> thumbColorUnchecked
    }

    // Calculate the thumb offset
    val thumbOffset = (width - thumbSize - (thumbPadding * 2)) * thumbPosition

    // Set up toggle semantics
    val toggleableState = if (checked) ToggleableState.On else ToggleableState.Off

    Box(
        modifier = modifier
            .semantics {
                role = Role.Switch
                this.toggleableState = toggleableState
            }
            .width(width)
            .height(height)
            .clip(trackShape)
            .background(finalTrackColor.copy(alpha = trackAlpha))
            .then(
                if (borderWidth > 0.dp) {
                    Modifier.border(borderWidth, borderColor, trackShape)
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Switch,
                onClick = { onCheckedChange(!checked) }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        // Thumb
        Box(
            modifier = Modifier
                .padding(start = thumbPadding + thumbOffset, end = thumbPadding)
                .size(thumbSizeAnimated)
                .clip(thumbShape)
                .background(finalThumbColor)
        )
    }
}

/**
 * A labeled switch component that combines a switch with a text label.
 */
@Composable
fun LabeledSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    labelSide: LabelSide = LabelSide.START,
    spacing: Dp = 8.dp,
    trackColor: Color = Color(0xFF3B82F6),
    trackColorUnchecked: Color = Color(0xFFE5E7EB),
    thumbColor: Color = Color.White,
    thumbColorUnchecked: Color = Color.White
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = enabled,
                role = Role.Switch,
                onClick = { onCheckedChange(!checked) }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (labelSide == LabelSide.START) Arrangement.Start else Arrangement.End
    ) {
        if (labelSide == LabelSide.START) {
            Box(modifier = Modifier.weight(1f)) {
                label()
            }
            Spacer(modifier = Modifier.width(spacing))
            CustomSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                trackColor = trackColor,
                trackColorUnchecked = trackColorUnchecked,
                thumbColor = thumbColor,
                thumbColorUnchecked = thumbColorUnchecked
            )
        } else {
            CustomSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                trackColor = trackColor,
                trackColorUnchecked = trackColorUnchecked,
                thumbColor = thumbColor,
                thumbColorUnchecked = thumbColorUnchecked
            )
            Spacer(modifier = Modifier.width(spacing))
            Box(modifier = Modifier.weight(1f)) {
                label()
            }
        }
    }
}

enum class LabelSide {
    START, END
}

// Helper extension for a switch with a simple text label
@Composable
fun LabeledSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    labelSide: LabelSide = LabelSide.START,
    enabled: Boolean = true,
    trackColor: Color = Color(0xFF3B82F6),
    thumbColor: Color = Color.White
) {
    LabeledSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        label = {
            Text(
                text = labelText,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = if (enabled) Color(0xFF1F2937) else Color(0xFF9CA3AF)
                )
            )
        },
        modifier = modifier,
        labelSide = labelSide,
        enabled = enabled,
        trackColor = trackColor,
        thumbColor = thumbColor
    )
}