# AtomicKit

AtomicKit is a collection of customizable, atomic Compose UI components built with a focus on simplicity, customization, and modern design patterns.

<p align="center">
  <img src="/screenshots/components1.png" width="32%" />
  <img src="/screenshots/textfield1.png" width="32%" />
  <img src="/screenshots/textfield2.png" width="32%" />
</p>
<p align="center">
  <img src="/screenshots/components2.png" width="100%" />
</p>

## Features

- **Building Block Approach**: Highly customizable atomic components that can be combined to create complex UI elements
- **Standalone Implementation**: Built directly on Compose Foundation, not dependent on Material Design
- **Fully Customizable**: Every aspect of each component can be customized, from colors to shapes to behavior
- **Modern Design**: Supports features like custom box shadows, responsive layouts, and state-based animations
- **Accessibility-Friendly**: Built with accessibility in mind

## Components

- **CustomButton**: Advanced button with support for box shadows, custom shapes, and various states
- **CustomCard**: Card component with flexible styling options
- **CustomTextField**: Text input with extensive customization options
- **CustomSwitch**: Toggle switch with customizable track and thumb
- **CustomTopAppBar**: App bar component with scroll behavior support

## Usage

### CustomButton

```kotlin
CustomButton(
    onClick = { /* your action */ },
    text = "Primary Button",
    backgroundColor = Color(0xFF3B82F6),
    contentColor = Color.White,
    shape = RoundedCornerShape(8.dp),
    boxShadow = BoxShadow(
        offsetY = 2.dp,
        blurRadius = 4.dp,
        color = Color(0x40000000)
    )
)
```

### CustomTextField

```kotlin
var textValue by remember { mutableStateOf("") }

CustomTextField(
    value = textValue,
    onValueChange = { textValue = it },
    labelText = "Email Address",
    placeholderText = "your.email@example.com",
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Email"
        )
    }
)
```

### CustomSwitch

```kotlin
var switchValue by remember { mutableStateOf(false) }

LabeledSwitch(
    checked = switchValue,
    onCheckedChange = { switchValue = it },
    labelText = "Enable Notifications",
    trackColor = Color(0xFF3B82F6)
)
```

## Customization

All components support extensive customization options. Check out the demo app for examples of different styling options.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

```
MIT License

Copyright (c) 2025 Your Name

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```