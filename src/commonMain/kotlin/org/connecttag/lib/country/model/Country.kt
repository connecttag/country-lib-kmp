package org.connecttag.lib.country.model

import org.connecttag.lib.text.uppercaseStable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
public value class CountryCode public constructor(
    public val value: String,
) {
    public val normalized: String
        get() = value.trim().uppercaseStable()

    override fun toString(): String = normalized
}

@Serializable
public data class Country public constructor(
    public val id: String,
    public val iso2: String,
    public val alpha3: String? = null,
    public val numericCode: String? = null,
    public val isoAliases: Set<String> = emptySet(),
    public val alternateIds: Set<String> = emptySet(),
    public val displayName: String,
    public val localizedNames: Map<String, String> = emptyMap(),
    public val flagEmoji: String,
    public val phoneCountryCode: Int,
    public val callingCodes: List<String> = listOf(phoneCountryCode.toString()),
    public val capital: String? = null,
    public val currency: String? = null,
    public val primaryLanguage: String? = null,
    public val region: String? = null,
    public val subregion: String? = null,
    public val orderIndex: Int = 0,
    public val active: Boolean = true,
    public val keywords: Set<String> = emptySet(),
) {
    public val primaryCallingCode: String
        get() = callingCodes.firstOrNull().orEmpty()
}
