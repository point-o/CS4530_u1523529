
package com.example.gyroscopedemov2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.gyroscopedemov2.ui.theme.GyroscopeDemoV2Theme

/**
 * Main activity
 * Continued by Ryan Pointer
 * 11/6/26
 */
class MainActivity : ComponentActivity(), SensorEventListener {

    private val viewModel: MarbleViewModel by viewModels()

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    // Emulator fallback if the gravity sensor is unavailable.
    private val sensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
            ?: sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            )
    }

    private val filtered = FloatArray(3)
    private var filterInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GyroscopeDemoV2Theme {
                if (sensor != null) {
                    MarbleScreen(viewModel)
                } else {
                    androidx.compose.material3.Text(
                        "No suitable motion sensor available"
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        filterInitialized = false

        sensor?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            // Smooth noisy accelerometer readings.
            val alpha = 0.15f

            if (!filterInitialized) {
                for (i in 0..2) {
                    filtered[i] = event.values[i]
                }
                filterInitialized = true
            } else {
                for (i in 0..2) {
                    filtered[i] += alpha *
                            (event.values[i] - filtered[i])
                }
            }

            viewModel.updateGravity(filtered[0], filtered[1])
        } else {
            viewModel.updateGravity(
                event.values[0],
                event.values[1]
            )
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) = Unit
}
