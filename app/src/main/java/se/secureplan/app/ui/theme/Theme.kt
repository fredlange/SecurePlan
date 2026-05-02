package se.secureplan.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary          = DeepBlue40,
    onPrimary        = OnDeepBlue40,
    primaryContainer = Color(0xFFD6E4FF),
    secondary        = SteelBlue40,
    onSecondary      = OnSteelBlue40,
    tertiary         = Amber40,
    onTertiary       = OnAmber40,
    error            = ErrorRed40,
    background       = BackgroundLight,
    surface          = SurfaceLight,
    surfaceVariant   = SurfaceVariant,
)

private val DarkColorScheme = darkColorScheme(
    primary          = DeepBlue80,
    onPrimary        = Color(0xFF003783),
    primaryContainer = Color(0xFF004AAD),
    secondary        = SteelBlue80,
    tertiary         = Amber80,
    error            = ErrorRed80,
    background       = BackgroundDark,
    surface          = SurfaceDark,
    surfaceVariant   = SurfaceVariantDark,
)

@Composable
fun SecurePlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = SecurePlanTypography,
        content     = content
    )
}
