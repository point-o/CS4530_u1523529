
package com.example.gyroscopedemov2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlin.math.exp

/**
 * Marble vm
 * By Ryan Pointer
 * 11/6/26
 */
class MarbleViewModel : ViewModel() {

    var position by mutableStateOf(Offset.Zero)
        private set

    private var velocityX = 0f
    private var velocityY = 0f

    private var gravityX = 0f
    private var gravityY = 0f

    private var maxX = 0f
    private var maxY = 0f
    private var initialized = false

    // Pixels per second squared per m/s² of gravity.
    private val accelerationScale = 180f

    // Higher values produce stronger friction.
    private val frictionStrength = 2.5f

    fun updateGravity(x: Float, y: Float) {
        gravityX = x
        gravityY = y
    }

    fun updateBounds(
        width: Float,
        height: Float,
        marbleDiameter: Float
    ) {
        maxX = (width - marbleDiameter).coerceAtLeast(0f)
        maxY = (height - marbleDiameter).coerceAtLeast(0f)

        if (!initialized) {
            position = Offset(maxX / 2f, maxY / 2f)
            initialized = true
        } else {
            position = Offset(
                position.x.coerceIn(0f, maxX),
                position.y.coerceIn(0f, maxY)
            )
        }
    }

    fun step(dt: Float) {
        if (!initialized) return

        velocityX += gravityX * accelerationScale * dt

        velocityY -= gravityY * accelerationScale * dt

        val damping = exp(-frictionStrength * dt)
        velocityX *= damping
        velocityY *= damping

        var nextX = position.x + velocityX * dt
        var nextY = position.y + velocityY * dt

        // clamp to screen boundaries.
        if (nextX < 0f || nextX > maxX) {
            nextX = nextX.coerceIn(0f, maxX)
            velocityX = 0f
        }

        if (nextY < 0f || nextY > maxY) {
            nextY = nextY.coerceIn(0f, maxY)
            velocityY = 0f
        }

        position = Offset(nextX, nextY)
    }
}
