package ua.gov.diia.core.models.document.docgroups.v2

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class IconLeft(
    @Json(name = "code")
    val code: String?
): Parcelable