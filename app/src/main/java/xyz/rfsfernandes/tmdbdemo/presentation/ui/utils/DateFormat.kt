package xyz.rfsfernandes.tmdbdemo.presentation.ui.utils

fun formatDuration(minutes: Int?): String {
    if (minutes == null) return ""
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return if (hours > 0) {
        "${hours}h ${remainingMinutes}m"
    } else {
        "${remainingMinutes}m"
    }
}
