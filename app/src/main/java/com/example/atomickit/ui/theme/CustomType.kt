package com.example.atomickit.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.atomickit.R

/**
 * Custom typography system for AtomicKit using Bricolage Grotesque for headings
 * and Open Sans for body text.
 */
object AppFonts {
    // Bricolage Grotesque for titles/headings
    val BricolageGrotesque = FontFamily(
        Font(R.font.bricolagegrotesque_regular, FontWeight.Normal),
        Font(R.font.bricolagegrotesque_medium, FontWeight.Medium),
        Font(R.font.bricolagegrotesque_bold, FontWeight.Bold),
        Font(R.font.bricolagegrotesque_extrabold, FontWeight.ExtraBold),
        Font(R.font.bricolagegrotesque_light, FontWeight.Light),
        Font(R.font.bricolagegrotesque_semibold, FontWeight.SemiBold),
        Font(R.font.bricolagegrotesque_light, FontWeight.Thin),
    )

    // Open Sans for body text
    val OpenSans = FontFamily(
        Font(R.font.opensans_regular, FontWeight.Normal),
        Font(R.font.opensans_medium, FontWeight.Medium),
        Font(R.font.opensans_semibold, FontWeight.SemiBold),
        Font(R.font.opensans_bold, FontWeight.Bold),
        Font(R.font.opensans_extrabold, FontWeight.ExtraBold),
        Font(R.font.opensans_light, FontWeight.Light),
        Font(R.font.opensans_lightitalic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.opensans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.opensans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
        Font(R.font.opensans_bolditalic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.opensans_bolditalic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(R.font.opensans_lightitalic, FontWeight.Light, FontStyle.Italic)
    )
}

@Stable
class AppTypography(
    // Headings - Bricolage Grotesque with Extra Bold weight
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,

    // Body - Open Sans with various weights
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,

    // Specialized styles
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
    val button: TextStyle,
    val caption: TextStyle
) {
    companion object {
        @Composable
        fun create(
            headingColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color(0xFF212529),
            bodyColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color(0xFF495057)
        ): AppTypography = AppTypography(
            // Headings with Bricolage Grotesque ExtraBold
            h1 = TextStyle(
                fontFamily = AppFonts.BricolageGrotesque,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = (-0.5).sp,
                color = headingColor
            ),
            h2 = TextStyle(
                fontFamily = AppFonts.BricolageGrotesque,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = (-0.25).sp,
                color = headingColor
            ),
            h3 = TextStyle(
                fontFamily = AppFonts.BricolageGrotesque,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp,
                color = headingColor
            ),
            h4 = TextStyle(
                fontFamily = AppFonts.BricolageGrotesque,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
                color = headingColor
            ),
            h5 = TextStyle(
                fontFamily = AppFonts.BricolageGrotesque,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp,
                color = headingColor
            ),
            h6 = TextStyle(
                fontFamily = AppFonts.BricolageGrotesque,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp,
                color = headingColor
            ),

            // Body text with Open Sans
            bodyLarge = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
                color = bodyColor
            ),
            bodyMedium = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                color = bodyColor
            ),
            bodySmall = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
                color = bodyColor
            ),

            // Other text styles
            labelLarge = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                color = bodyColor
            ),
            labelMedium = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
                color = bodyColor
            ),
            labelSmall = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                lineHeight = 14.sp,
                letterSpacing = 0.5.sp,
                color = bodyColor
            ),
            button = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp
            ),
            caption = TextStyle(
                fontFamily = AppFonts.OpenSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
                color = bodyColor.copy(alpha = 0.8f)
            )
        )
    }
}
// Create a non-composable default instance for the CompositionLocal
private val defaultTypography = AppTypography(
    // Headings with Bricolage Grotesque ExtraBold
    h1 = TextStyle(
        fontFamily = AppFonts.BricolageGrotesque,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp,
        color = androidx.compose.ui.graphics.Color(0xFF212529)
    ),
    h2 = TextStyle(
        fontFamily = AppFonts.BricolageGrotesque,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = androidx.compose.ui.graphics.Color(0xFF212529)
    ),
    h3 = TextStyle(
        fontFamily = AppFonts.BricolageGrotesque,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = androidx.compose.ui.graphics.Color(0xFF212529)
    ),
    h4 = TextStyle(
        fontFamily = AppFonts.BricolageGrotesque,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = androidx.compose.ui.graphics.Color(0xFF212529)
    ),
    h5 = TextStyle(
        fontFamily = AppFonts.BricolageGrotesque,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = androidx.compose.ui.graphics.Color(0xFF212529)
    ),
    h6 = TextStyle(
        fontFamily = AppFonts.BricolageGrotesque,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = androidx.compose.ui.graphics.Color(0xFF212529)
    ),

    // Body text with Open Sans
    bodyLarge = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057)
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057)
    ),
    bodySmall = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057)
    ),

    // Other text styles
    labelLarge = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057)
    ),
    labelMedium = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057)
    ),
    labelSmall = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057)
    ),
    button = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057)
    ),
    caption = TextStyle(
        fontFamily = AppFonts.OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = androidx.compose.ui.graphics.Color(0xFF495057).copy(alpha = 0.8f)
    )
)

// Composition local to provide typography through the composition tree
val LocalAppTypography = staticCompositionLocalOf { defaultTypography }

