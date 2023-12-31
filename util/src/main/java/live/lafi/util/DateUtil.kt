package live.lafi.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun getFullDate(): Long =
        SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault())
            .format(
                Date(System.currentTimeMillis())
            ).toLong()
}