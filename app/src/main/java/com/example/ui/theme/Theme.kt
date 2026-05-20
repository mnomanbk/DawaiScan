package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    primaryContainer = GreenPrimaryContainer,
    onPrimaryContainer = OnGreenPrimaryContainer,
    secondary = GreenPrimary,
    background = androidx.compose.ui.graphics.Color(0xFF121212),
    surface = androidx.compose.ui.graphics.Color(0xFF1E1E1E),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    onBackground = androidx.compose.ui.graphics.Color.White,
    onSurface = androidx.compose.ui.graphics.Color.White,
    error = ErrorTextColor,
    errorContainer = ErrorBackground,
    onErrorContainer = OnErrorBackground
)

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    primaryContainer = GreenPrimaryContainer,
    onPrimaryContainer = OnGreenPrimaryContainer,
    secondary = GreenPrimary,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    onBackground = OnSurfaceColor,
    onSurface = OnSurfaceColor,
    error = ErrorTextColor,
    errorContainer = ErrorBackground,
    onErrorContainer = OnErrorBackground
)

@Composable
fun DawaiScanTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disabling dynamic colors by default to strictly enforce brand colors
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
