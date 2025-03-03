package com.example.atomickit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.CustomTextField
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.components.Text

/**
 * Demo showcasing different variants and styles of CustomTextField component
 */
@Composable
fun TextFieldDemo() {
    // State for interactive components
    var basicText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var searchText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var multilineText by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Colors
    val primary = Color(0xFF6366F1)
    val secondary = Color(0xFFF59E0B)
    val background = Color(0xFFF9FAFB)
    val surface = Color(0xFFFFFFFF)
    val onSurface = Color(0xFF1F2937)
    val error = Color(0xFFEF4444)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 56.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Bar
        CustomTopAppBar(
            titleText = "TextField Components",
            backgroundColor = primary,
            contentColor = Color.White,
            elevation = 4.dp,
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        )

        // Content with padding
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Introduction
            Text(
                text = "Text Field Variants",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = onSurface
                )
            )

            // Basic Text Field
            SectionTitle("Basic Text Field")

            CustomTextField(
                value = basicText,
                onValueChange = { basicText = it },
                labelText = "Name",
                placeholderText = "Enter your name",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                focusedBorderColor = primary
            )

            // Email Text Field with Icon
            SectionTitle("With Leading Icon")

            CustomTextField(
                value = emailText,
                onValueChange = { emailText = it },
                labelText = "Email Address",
                placeholderText = "your.email@example.com",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                focusedBorderColor = primary,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color(0xFF9CA3AF)
                    )
                }
            )

            // Search Field
            SectionTitle("Search Field")

            CustomTextField(
                value = searchText,
                onValueChange = { searchText = it },
                labelText = "",
                placeholderText = "Search...",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                focusedBorderColor = primary,
                shape = RoundedCornerShape(24.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF9CA3AF)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )

            // Password Field
            SectionTitle("Password Field with Trailing Icon")

            CustomTextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                labelText = "Password",
                placeholderText = "Enter your password",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                focusedBorderColor = primary,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password",
                        tint = Color(0xFF9CA3AF)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.Lock else Icons.Default.Lock,
                            contentDescription = if (showPassword) "Hide Password" else "Show Password",
                            tint = Color(0xFF9CA3AF)
                        )
                    }
                }
            )

            // Error State
            SectionTitle("Error State")

            CustomTextField(
                value = "",
                onValueChange = { /* do nothing */ },
                labelText = "Username",
                placeholderText = "Enter your username",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                isError = true,
                errorText = "Username is required",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Username",
                        tint = error
                    )
                }
            )

            // Multiline Text Field
            SectionTitle("Multiline Text Field")

            CustomTextField(
                value = multilineText,
                onValueChange = { multilineText = it },
                labelText = "Bio",
                placeholderText = "Tell us about yourself...",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                focusedBorderColor = primary,
                singleLine = false,
                maxLines = 5
            )

            // Disabled State
            SectionTitle("Disabled State")

            CustomTextField(
                value = "This field is disabled",
                onValueChange = { /* do nothing */ },
                labelText = "Disabled Field",
                placeholderText = "This field is disabled",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = surface,
                enabled = false
            )

            // Custom Styling
            SectionTitle("Custom Styling")

            CustomTextField(
                value = "",
                onValueChange = { /* do nothing */ },
                labelText = "Custom Styling",
                placeholderText = "Enter text here...",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFF0F9FF),
                focusedBorderColor = Color(0xFF0EA5E9),
                unfocusedBorderColor = Color(0xFFBAE6FD),
                cursorColor = Color(0xFF0EA5E9),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF0C4A6E)
                )
            )

            // Text Fields in a Card
            SectionTitle("Text Fields in a Card")

            CustomCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Contact Information",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = onSurface
                        )
                    )

                    CustomTextField(
                        value = "",
                        onValueChange = { /* do nothing */ },
                        labelText = "Full Name",
                        placeholderText = "Enter your full name",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Name",
                                tint = Color(0xFF9CA3AF)
                            )
                        }
                    )

                    CustomTextField(
                        value = "",
                        onValueChange = { /* do nothing */ },
                        labelText = "Email",
                        placeholderText = "Enter your email address",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color(0xFF9CA3AF)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldDemoPreview() {
    TextFieldDemo()
}