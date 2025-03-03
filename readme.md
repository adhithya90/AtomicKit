# AtomicKit

AtomicKit is a collection of customizable, atomic Compose UI components built with a focus on simplicity, customization, and modern design patterns.

<p align="center">
<img src="/screenshots/recording.gif" width="30%" />
  <img src="/screenshots/textfield1.png" width="30%" />
  <img src="/screenshots/textfield2.png" width="30%" />
</p>
<p align="center">
  <img src="/screenshots/components2.png" width="75%" />
<img src="/screenshots/components1.png" width="20%" />
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

## Why Choose AtomicKit?

### Separation of Behavior from Visual Presentation

AtomicKit's core design philosophy is the strict separation of behavior from visual presentation:

- **Controller-Based Design Pattern**: Components use distinct controller objects for state management, separate from their visual representation
- **Independent Customization Paths**: Modify behaviors (animations, interactions, state transitions) independently from visuals
- **Enhanced Maintainability**: Change the look without touching behavior code, and vice versa
- **Future-Proofing**: As design trends evolve, you can update the visual layer without reworking interaction code

This is particularly evident in components like the CustomTopAppBar, where scroll behavior is completely decoupled from visual styling through the AppBarScrollBehavior interface.

### Advanced Visual Styling

- **True CSS-like Box Shadows**: Unlike Material3 components that rely on elevation, CustomButton implements actual box shadows with precise control over offset, blur, spread, and color
- **Fine-grained Corner Control**: Independent control of each corner radius, enabling asymmetric designs like "message bubble" shapes
- **Border Customization**: Full control over border width, color, and style, independent from other visual properties

### Responsive Design Features

- **Built-in Responsive Layout Support**: CustomButton's `maxWidth` parameter with horizontal alignment control makes it easy to create consistent, responsive layouts
- **Proportional Sizing**: Components are designed to work with both fixed and proportional sizing across different form factors

### Enhanced State Management

- **Granular State Control**: Separate visual treatments for normal, pressed, and disabled states with independent shadow configurations
- **Animated State Transitions**: Smooth, customizable animations between states
- **Rich Interaction Feedback**: Multiple visual cues for interaction beyond Material's ripple effect

### Unique Component Features

- **CustomButton**:
    - Gradient background support
    - Configurable box shadow with precise control
    - Horizontal alignment options when using maxWidth

- **CustomTextField**:
    - Enhanced focus visualization with animated border width
    - More customizable placeholder styling

- **CustomSwitch**:
    - Fine-grained animation control for thumb movement
    - Custom thumb and track shapes

- **CustomTopAppBar**:
    - Advanced scroll behavior with custom effects like color opacity changes
    - Support for variable height based on scroll position

- **CustomCard**:
    - Support for gradient backgrounds
    - Built-in title and content slots for common card patterns

### Performance Benefits

- **Optimized Recomposition**: Components minimize unnecessary recompositions
- **Lighter Weight**: Built directly on Compose Foundation without Material Design overhead
- **Selective Animation**: Animations applied only to properties that need to change

## Customization

All components support extensive customization options. Check out the demo app for examples of different styling options.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

```
APACHE 2.0
```