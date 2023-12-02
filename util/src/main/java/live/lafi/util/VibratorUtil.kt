package live.lafi.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class VibratorUtil(private val activity: Activity) {
    private val vibrator: Vibrator by lazy { createVibrator() }

    fun vibrateOneShot(
        duration: Long,
        power: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(duration, power)
            )
        } else {
            vibrator.vibrate(duration)
        }
    }

    private fun createVibrator() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (activity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
}