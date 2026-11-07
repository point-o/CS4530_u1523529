
package com.example.gyroscopedemov2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


/**
 * Canvas for displaying the marble
 *
 * By Ryan Pointer
 * 11/6/26
 */
@Composable
fun MarbleScreen(viewModel: MarbleViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {

            val density = LocalDensity.current
            val marbleSize = 40.dp

            val diameterPx = with(density) {
                marbleSize.toPx()
            }

            val widthPx = constraints.maxWidth.toFloat()
            val heightPx = constraints.maxHeight.toFloat()

            LaunchedEffect(
                viewModel,
                widthPx,
                heightPx,
                diameterPx
            ) {
                viewModel.updateBounds(
                    widthPx,
                    heightPx,
                    diameterPx
                )

                var lastFrame = 0L

                while (true) {
                    withFrameNanos { frameTime ->
                        if (lastFrame != 0L) {
                            val dt = (
                                    (frameTime - lastFrame) /
                                            1_000_000_000f
                                    ).coerceIn(0f, 0.05f)

                            viewModel.step(dt)
                        }

                        lastFrame = frameTime
                    }
                }
            }

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            viewModel.position.x.roundToInt(),
                            viewModel.position.y.roundToInt()
                        )
                    }
                    .size(marbleSize)
                    .background(Color.Blue, CircleShape)
            )
        }
    }
}
