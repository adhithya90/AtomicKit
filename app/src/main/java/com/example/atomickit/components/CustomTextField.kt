package com.example.atomickit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A custom text field component built from scratch using Compose Foundation.
 *
 * @param value The input text to be shown in the text field.
 * @param onValueChange The callback that is triggered when the input service updates the text.
 * @param modifier The modifier to be applied to the text field.
 * @param enabled Controls the enabled state of the text field.
 * @param readOnly Controls the editable state of the text field.
 * @param textStyle The style to be applied to the input text.
 * @param label Optional label to be displayed above the text field.
 * @param placeholder Optional placeholder to be displayed when the text field is empty.
 * @param leadingIcon Optional leading icon to be displayed at the start of the text field.
 * @param trailingIcon Optional trailing icon to be displayed at the end of the text field.
 * @param isError Whether the text field's current value is in error state.
 * @param errorMessage Optional error message to be displayed when isError is true.
 * @param visualTransformation The visual transformation to be applied to the input text.
 * @param keyboardOptions The keyboard options to configure the soft keyboard.
 * @param keyboardActions The keyboard actions to configure the IME action button.
 * @param singleLine Whether the text field is single line.
 * @param maxLines The maximum number of lines to display.
 * @param shape The shape of the text field's container.
 * @param colors The colors to be used for the text field in different states.
 * @param contentPadding The padding values to be applied to the content of the text field.
 * @param interactionSource The InteractionSource representing the stream of Interactions for this text field.
 */
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF1F2937)
    ),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = Color(0xFFF9FAFB),
    cursorColor: Color = Color(0xFF3B82F6),
    focusedBorderColor: Color = Color(0xFF3B82F6),
    unfocusedBorderColor: Color = Color(0xFFD1D5DB),
    disabledBorderColor: Color = Color(0xFFE5E7EB),
    errorBorderColor: Color = Color(0xFFEF4444),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // State to track focus
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    // Determine border color based on state
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> disabledBorderColor
            isError -> errorBorderColor
            isFocused -> focusedBorderColor
            else -> unfocusedBorderColor
        },
        label = "borderColor"
    )

    // Determine border width based on focus state
    val animatedBorderWidth by animateDpAsState(
        targetValue = if (isFocused) borderWidth * 1.5f else borderWidth,
        label = "borderWidth"
    )

    Column(modifier = modifier) {
        // Label if provided
        if (label != null) {
            Box(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .fillMaxWidth()
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isError) errorBorderColor else Color(0xFF4B5563)
                    )
                ) {
                    label()
                }
            }
        }

        // Text field container
        Box(
            modifier = Modifier
                .clip(shape)
                .background(if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.6f))
                .border(
                    width = animatedBorderWidth,
                    color = borderColor,
                    shape = shape
                )
                .focusRequester(focusRequester)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leading icon if provided
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier.padding(end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        leadingIcon()
                    }
                }

                // Text field with placeholder
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { /* Handle focus changes if needed */ },
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = textStyle,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        visualTransformation = visualTransformation,
                        interactionSource = interactionSource,
                        cursorBrush = SolidColor(cursorColor)
                    )

                    // Show placeholder if the text field is empty
                    if (value.isEmpty() && placeholder != null) {
                        CompositionLocalProvider(
                            LocalTextStyle provides textStyle.copy(color = Color(0xFF9CA3AF))
                        ) {
                            placeholder()
                        }
                    }
                }

                // Trailing icon if provided
                if (trailingIcon != null) {
                    Box(
                        modifier = Modifier.padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        trailingIcon()
                    }
                }
            }
        }

        // Error message if there's an error
        if (isError && errorMessage != null) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides TextStyle(
                        fontSize = 12.sp,
                        color = errorBorderColor
                    )
                ) {
                    errorMessage()
                }
            }
        }
    }
}

// TextField with TextFieldValue variant
@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF1F2937)
    ),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = Color(0xFFF9FAFB),
    cursorColor: Color = Color(0xFF3B82F6),
    focusedBorderColor: Color = Color(0xFF3B82F6),
    unfocusedBorderColor: Color = Color(0xFFD1D5DB),
    disabledBorderColor: Color = Color(0xFFE5E7EB),
    errorBorderColor: Color = Color(0xFFEF4444),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // Implementation similar to the String version but handling TextFieldValue
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> disabledBorderColor
            isError -> errorBorderColor
            isFocused -> focusedBorderColor
            else -> unfocusedBorderColor
        },
        label = "borderColor"
    )

    val animatedBorderWidth by animateDpAsState(
        targetValue = if (isFocused) borderWidth * 1.5f else borderWidth,
        label = "borderWidth"
    )

    Column(modifier = modifier) {
        if (label != null) {
            Box(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .fillMaxWidth()
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isError) errorBorderColor else Color(0xFF4B5563)
                    )
                ) {
                    label()
                }
            }
        }

        Box(
            modifier = Modifier
                .clip(shape)
                .background(if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.6f))
                .border(
                    width = animatedBorderWidth,
                    color = borderColor,
                    shape = shape
                )
                .focusRequester(focusRequester)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier.padding(end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        leadingIcon()
                    }
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { /* Handle focus changes if needed */ },
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = textStyle,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        visualTransformation = visualTransformation,
                        interactionSource = interactionSource,
                        cursorBrush = SolidColor(cursorColor)
                    )

                    if (value.text.isEmpty() && placeholder != null) {
                        CompositionLocalProvider(
                            LocalTextStyle provides textStyle.copy(color = Color(0xFF9CA3AF))
                        ) {
                            placeholder()
                        }
                    }
                }

                if (trailingIcon != null) {
                    Box(
                        modifier = Modifier.padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        trailingIcon()
                    }
                }
            }
        }

        if (isError && errorMessage != null) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides TextStyle(
                        fontSize = 12.sp,
                        color = errorBorderColor
                    )
                ) {
                    errorMessage()
                }
            }
        }
    }
}

// Helper extension for a text field with simple text label and placeholder
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    placeholderText: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    backgroundColor: Color = Color(0xFFF9FAFB),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    focusedBorderColor: Color = Color(0xFF3B82F6),
    unfocusedBorderColor: Color = Color(0xFFD1D5DB),
    cursorColor: Color = Color(0xFF3B82F6),
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF1F2937)
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    shape: Shape = RoundedCornerShape(8.dp),
    enabled: Boolean = true,
    readOnly: Boolean = false
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = labelText) },
        placeholder = { Text(text = placeholderText) },
        isError = isError,
        errorMessage = errorText?.let { { Text(text = it) } },
        backgroundColor = backgroundColor,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        cursorColor = cursorColor,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        shape = shape,
        enabled = enabled,
        readOnly = readOnly
    )
}