package org.connecttag.lib.country.model

import org.connecttag.lib.country.generated.GeneratedCountryCatalog

public object CountryCatalog {
    public val all: List<Country> = GeneratedCountryCatalog.all

    public fun byId(id: String): Country? = all.firstOrNull { country ->
        country.id.equals(id, ignoreCase = true) ||
            country.alternateIds.any { alternateId -> alternateId.equals(id, ignoreCase = true) }
    }

    public fun byIso(code: String): Country? = all.firstOrNull { country ->
        country.iso2.equals(code, ignoreCase = true) ||
            country.alpha3?.equals(code, ignoreCase = true) == true ||
            country.isoAliases.any { alias -> alias.equals(code, ignoreCase = true) }
    }

    public fun byCountryCode(code: CountryCode): Country? = byIso(code.normalized)

    public fun byPhoneCountryCode(code: Int): Country? = countriesByCallingCode(code.toString()).singleOrNull()

    public fun countriesByCallingCode(code: String): List<Country> {
        val normalized = code.filter(Char::isDigit)
        if (normalized.isBlank()) return emptyList()
        return all.filter { country ->
            country.callingCodes.any { callingCode -> callingCode == normalized }
        }
    }
}
