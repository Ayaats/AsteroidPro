package com.udacity.asteroidradar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = "sYRPPDhbm8FkiL8ZoNMdQ41eT91n03L4L1uYWwVX"
    val start = LocalDateTime.now()
    val end = LocalDateTime.now().minusDays(7)

}