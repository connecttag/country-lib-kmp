package org.connecttag.lib.country.search

import org.connecttag.lib.country.model.Country
import org.connecttag.lib.country.model.CountryCatalog
import org.connecttag.lib.text.lowercaseStable

public class CountryRepository public constructor(
    private val countries: List<Country> = CountryCatalog.all,
) {
    public fun all(): List<Country> = countries.sortedBy(Country::orderIndex)

    public fun findById(id: String): Country? = countries.firstOrNull { country ->
        country.id.equals(id, ignoreCase = true) ||
            country.alternateIds.any { alternateId -> alternateId.equals(id, ignoreCase = true) }
    }

    public fun findByIso(code: String): Country? = countries.firstOrNull { country ->
        country.iso2.equals(code, ignoreCase = true) ||
            country.alpha3?.equals(code, ignoreCase = true) == true ||
            country.isoAliases.any { alias -> alias.equals(code, ignoreCase = true) }
    }

    public fun findByPhoneCountryCode(code: Int): Country? =
        findByCallingCode(code.toString()).singleOrNull()

    public fun findByCallingCode(code: String): List<Country> {
        val normalized = code.filter(Char::isDigit)
        if (normalized.isBlank()) return emptyList()
        return countries.filter { country ->
            country.callingCodes.any { callingCode -> callingCode == normalized }
        }.sortedBy(Country::orderIndex)
    }

    public fun search(query: String, localeTag: String? = null): List<Country> {
        val normalized = query.trim().lowercaseStable()
        if (normalized.isBlank()) {
            return all()
        }
        return countries.filter { country ->
                country.id.lowercaseStable().contains(normalized) ||
                country.iso2.lowercaseStable().contains(normalized) ||
                country.alpha3?.lowercaseStable()?.contains(normalized) == true ||
                country.numericCode?.contains(normalized) == true ||
                country.callingCodes.any { callingCode -> callingCode.contains(normalized) } ||
                country.isoAliases.any { alias -> alias.lowercaseStable().contains(normalized) } ||
                country.alternateIds.any { alternateId -> alternateId.lowercaseStable().contains(normalized) } ||
                country.displayName.lowercaseStable().contains(normalized) ||
                country.localizedNames.values.any { name -> name.lowercaseStable().contains(normalized) } ||
                country.keywords.any { keyword -> keyword.lowercaseStable().contains(normalized) } ||
                localeTag?.let { tag -> country.localizedNames[tag]?.lowercaseStable()?.contains(normalized) } == true
        }.sortedBy(Country::orderIndex)
    }
}
