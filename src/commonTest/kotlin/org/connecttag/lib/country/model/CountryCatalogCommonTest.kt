package org.connecttag.lib.country.model

import org.connecttag.lib.country.search.CountryRepository
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CountryCatalogCommonTest {
    @Test
    fun generatedCatalogContainsRequiredGlobalMetadata() {
        assertTrue(CountryCatalog.all.size >= 240)
        CountryCatalog.all.forEach { country ->
            assertEquals(2, country.iso2.length, country.id)
            assertEquals(3, country.alpha3?.length, country.iso2)
            assertTrue(country.numericCode?.isNotBlank() == true, country.iso2)
            assertTrue(country.localizedNames["ar"]?.isNotBlank() == true, country.iso2)
            assertTrue(country.localizedNames["en"]?.isNotBlank() == true, country.iso2)
            if (country.iso2 != "UM") {
                assertTrue(country.callingCodes.isNotEmpty(), country.iso2)
            }
        }
    }

    @Test
    fun byIsoReturnsSingleExpectedCountry() {
        assertEquals("YE", CountryCatalog.byIso("YE")?.iso2)
        assertEquals("YE", CountryCatalog.byIso("YEM")?.iso2)
        assertEquals("SA", CountryCatalog.byIso("SA")?.iso2)
        assertEquals("US", CountryCatalog.byIso("US")?.iso2)
        assertEquals("CA", CountryCatalog.byIso("CA")?.iso2)
    }

    @Test
    fun sharedCallingCodesReturnCandidateLists() {
        val plusOne = CountryCatalog.countriesByCallingCode("+1").map { country -> country.iso2 }
        val plusSeven = CountryCatalog.countriesByCallingCode("7").map { country -> country.iso2 }

        assertTrue("US" in plusOne)
        assertTrue("CA" in plusOne)
        assertTrue("RU" in plusSeven)
        assertTrue("KZ" in plusSeven)
    }

    @Test
    fun singleCountryPhoneCodeLookupRejectsSharedCallingCodes() {
        assertEquals("YE", CountryCatalog.byPhoneCountryCode(967)?.iso2)
        assertEquals(null, CountryCatalog.byPhoneCountryCode(1))
        assertEquals(null, CountryCatalog.byPhoneCountryCode(7))
    }

    @Test
    fun repositoryPhoneCodeLookupRejectsSharedCallingCodes() {
        val repository = CountryRepository()

        assertEquals("YE", repository.findByPhoneCountryCode(967)?.iso2)
        assertEquals(null, repository.findByPhoneCountryCode(1))
        assertEquals(null, repository.findByPhoneCountryCode(7))
        assertTrue(repository.findByCallingCode("0").isEmpty())
    }

    @Test
    fun generatedCatalogKeepsDocumentedNonDialableTerritory() {
        val country = requireNotNull(CountryCatalog.byIso("UM"))

        assertTrue(country.callingCodes.isEmpty())
        assertEquals("", country.primaryCallingCode)
    }

    @Test
    fun countryModelSerializationIsStable() {
        val country = assertNotNull(CountryCatalog.byIso("YE"))
        val json = Json.encodeToString(country)
        val deserialized = Json.decodeFromString<Country>(json)

        assertEquals(country.id, deserialized.id)
        assertEquals(country.iso2, deserialized.iso2)
        assertEquals(country.callingCodes, deserialized.callingCodes)
    }
}
