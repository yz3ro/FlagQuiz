package com.yz3ro.flagquiz.data.entity

import com.google.gson.annotations.SerializedName

data class Country
    (
    @SerializedName("translations")
    val translations: Map<String, Map<String, String>>?,
    @SerializedName("flags")
    val flags: Map<String, String>,
    @SerializedName("region")
    val region: String
)

