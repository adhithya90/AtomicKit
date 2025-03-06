# AtomicKit

AtomicKit is a collection of customizable, atomic Compose UI components built with a focus on simplicity, customization, and modern design patterns.

<p align="center">
<img src="/screenshots/components1.png" width="30%" />

  <img src="/screenshots/textfield1.png" width="30%" />
  <img src="/screenshots/textfield2.png" width="30%" />
</p>
<p align="center">
  <img src="/screenshots/components2.png" width="75%" />
<img src="/screenshots/recording.gif" width="20%" />
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

# AtomicKit Responsive Layout Components

The `ResponsiveLayout` component system for AtomicKit provides a flexible and powerful way to create responsive UIs that adapt to different screen sizes and orientations, similar to CSS media queries but fully integrated with Jetpack Compose.

## Core Components

### `ResponsiveLayoutProvider`

A container component that measures the screen dimensions and provides them to child components via a CompositionLocal.

```kotlin
ResponsiveLayoutProvider {
    // All responsive components inside will have access to screen dimensions
    Text("Current screen dimensions are available here")
    
    // Your responsive layout components go here
}
```

### `ResponsiveLayout`

The primary component that conditionally renders content based on screen dimensions.

```kotlin
ResponsiveLayout(
    ranges = mapOf(
        // Mobile layout (0-599dp width)
        rememberScreenSizeRange(0.dp, 599.dp) to {
            // Mobile UI
        },
        
        // Tablet layout (600-899dp width)
        rememberScreenSizeRange(600.dp, 899.dp) to {
            // Tablet UI
        },
        
        // Desktop layout (900dp+ width)
        rememberScreenSizeRange(900.dp, null) to {
            // Desktop UI
        }
    )
)
```

### `ResponsiveBreakpoints`

A simplified API for when you only need to consider width breakpoints.

```kotlin
ResponsiveBreakpoints(
    breakpoints = mapOf(
        0.dp to { SmallScreenContent() },
        600.dp to { MediumScreenContent() },
        900.dp to { LargeScreenContent() }
    )
)
```

### `ResponsiveGrid`

A flexible grid system that adapts columns based on screen width.

```kotlin
ResponsiveGrid(
    items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"),
    breakpoints = mapOf(
        0.dp to 1,    // 1 column at 0-499dp
        500.dp to 2,  // 2 columns at 500-699dp
        700.dp to 3,  // 3 columns at 700-999dp
        1000.dp to 4  // 4 columns at 1000dp+
    )
) { item ->
    // Item content
    CustomCard {
        Text(item)
    }
}
```

### `ResponsiveUI`

A utility for building custom responsive UIs with direct access to screen dimensions.

```kotlin
ResponsiveUI { dimensions ->
    // Use dimensions.widthDp and dimensions.heightDp to create responsive UI
    val fontSize = when {
        dimensions.widthDp < 600f -> 14.sp
        dimensions.widthDp < 900f -> 16.sp
        else -> 18.sp
    }
    
    Text("Responsive text", fontSize = fontSize)
}
```

## Predefined Screen Size Ranges

The `ScreenSizeRange` class includes several predefined ranges for common scenarios:

```kotlin
// Size-based ranges
ScreenSizeRange.COMPACT   // 0-600dp width
ScreenSizeRange.MEDIUM    // 600-840dp width
ScreenSizeRange.EXPANDED  // 840dp+ width

// Orientation-based ranges
ScreenSizeRange.PORTRAIT  // height > width
ScreenSizeRange.LANDSCAPE // width >= height

// Device type ranges
ScreenSizeRange.TABLET    // 600-1200dp width
ScreenSizeRange.DESKTOP   // 1200dp+ width, 720dp+ height

// Aspect ratio ranges
ScreenSizeRange.TALL      // aspect ratio > 1.9:1 (height:width)
ScreenSizeRange.STANDARD  // aspect ratio between 1.3:1 and 1.9:1
ScreenSizeRange.WIDE      // aspect ratio > 1.7:1 (width:height)
```

## Creating Custom Ranges

You can create custom screen size ranges within Composable functions using the `rememberScreenSizeRange` function:

```kotlin
// Range with width and height constraints
val tabletPortrait = rememberScreenSizeRange(
    minWidth = 600.dp,
    maxWidth = 900.dp,
    minHeight = 900.dp
)

// Range with custom predicate
val largePhonePortrait = rememberScreenSizeRange(
    minWidth = 400.dp,
    maxWidth = 599.dp,
    predicate = { width, height ->
        // Additional condition: must be in portrait mode
        height > width
    }
)
```

## Important Usage Notes

1. You must wrap your responsive components in a `ResponsiveLayoutProvider` to ensure screen dimensions are available:

```kotlin
ResponsiveLayoutProvider {
    // Your responsive components go here
    ResponsiveLayout(...)
    ResponsiveBreakpoints(...)
    ResponsiveUI { ... }
}
```

2. The responsive components will only render when screen dimensions become available. This typically happens after the first layout pass.

## Example: Responsive Product Page

Here's a real-world example of using the responsive layout system to create a product details page that adapts to different screen sizes:

```kotlin
@Composable
fun ResponsiveProductScreen(product: Product) {
    ResponsiveLayoutProvider {
        Column(modifier = Modifier.fillMaxSize()) {
            // App Bar
            CustomTopAppBar(title = { Text("Product Details") })
            
            // Different layouts based on screen size
            ResponsiveLayout(
                ranges = mapOf(
                    // Mobile layout (vertical stacked)
                    rememberScreenSizeRange(0.dp, 599.dp) to {
                        Column {
                            // Product image takes full width
                            ProductImage(product, Modifier.fillMaxWidth())
                            
                            // Product details below
                            ProductDetails(product)
                        }
                    },
                    
                    // Tablet/Desktop layout (side-by-side)
                    rememberScreenSizeRange(600.dp, null) to {
                        Row {
                            // Product image on left
                            ProductImage(
                                product, 
                                Modifier.weight(1f)
                            )
                            
                            // Product details on right
                            ProductDetails(
                                product,
                                Modifier.weight(1f)
                            )
                        }
                    }
                )
            )
        }
    }
}
```

## Example: Fluid Typography

Use `ResponsiveUI` to create CSS-like fluid typography that scales smoothly with screen size:

```kotlin
ResponsiveUI { dimensions ->
    // Calculate font size based on screen width
    // Similar to CSS clamp() function
    val minFontSize = 18f
    val maxFontSize = 32f
    val minWidth = 320f
    val maxWidth = 1200f
    
    val fontScale = ((dimensions.widthDp - minWidth) / (maxWidth - minWidth))
        .coerceIn(0f, 1f)
    val fontSize = minFontSize + (maxFontSize - minFontSize) * fontScale
    
    Text(
        text = "Fluid Typography",
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    )
}
```

## Benefits Over WindowSizeClass

This ResponsiveLayout system offers several advantages over Android's WindowSizeClass:

1. **More Granular Control**: Define exactly the breakpoints you need rather than using predefined compact/medium/expanded classes.

2. **Combined Dimensions**: Consider both width and height simultaneously with custom predicates.

3. **Orientation and Aspect Ratio**: Built-in support for orientation and aspect ratio-based layouts.

4. **Fluid Adaptations**: Support for continuously adapting values (like font size) rather than discrete breakpoints.

5. **Simple API**: The component-based approach integrates seamlessly with Compose's declarative paradigm.

6. **Composable Integration**: Works within the Compose component hierarchy without additional setup.

## Implementation Details

The `ResponsiveLayout` system is designed to avoid any dependencies on Android's Activity or WindowManager. It works by:

1. Using `onSizeChanged` to measure the container's dimensions
2. Converting pixel measurements to dp using the current density scale
3. Providing dimensions through a CompositionLocal
4. Determining which content to display based on dimension-matching rules

This approach ensures the system works correctly in any Compose environment, including previews.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

```
APACHE 2.0
```