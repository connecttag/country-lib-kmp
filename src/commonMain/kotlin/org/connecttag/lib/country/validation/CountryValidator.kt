package org.connecttag.lib.country.validation

import org.connecttag.lib.country.model.Country

public object CountryValidator {
    public fun isValidIso(code: String, country: Country): Boolean {
        return country.iso2.equals(code, ignoreCase = true) ||
            country.isoAliases.any { alias -> alias.equals(code, ignoreCase = true) }
    }
}
