# ConnectTag Country Library (country-lib-kmp)

[![JitPack](https://jitpack.io/v/connecttag/country-lib-kmp.svg)](https://jitpack.io/#connecttag/country-lib-kmp)

مكتبة بيانات الدول والرموز والمفاتيح الموجهة لجميع منصات **Kotlin Multiplatform**:
- **Android** (API 24+)
- **Desktop JVM** (Windows, macOS, Linux)
- **Web / WasmJs**

---

## 📦 التثبيت والإضافة للمشروع (Installation via JitPack)

### 1. إضافة مستودع JitPack في `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. إضافة التبعية في `build.gradle.kts`:
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            // For Kotlin Multiplatform:
            implementation("com.github.connecttag.country-lib-kmp:country-lib-kmp:1.0.0")
        }
    }
}
```

الحزمة الأساسية للمكتبة: `org.connecttag.lib.country.*`

---

## 🌟 الميزات الأساسية

1. **`CountryCatalog`:**
   - وصول مباشر لكافة بيانات الدول المعيارية (ISO-2, Alpha-3, Calling codes, Emoji flags, Currency).
   - دوال بحث سريعة: `byId()`, `byIso()`, `byPhoneCountryCode()`, `countriesByCallingCode()`.
2. **`CountryRepository`:**
   - محرك بحث متقدم للدول بالاسم أو الكود مع دعم التوطين والترتيب.
3. **`CountryValidator`:**
   - فحص وتأكيد صحة رموز الدول والـ ISO.
