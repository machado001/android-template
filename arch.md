# Application Architecture & Template

This project is a reusable template that mirrors the architecture used in `hangman` while stripping any domain-specific logic. It is ready to be cloned for new apps and already wires the core stack called out in `hangman/arch.md`.

## 1. Technology Stack

- **Language:** Kotlin 2.x
- **Minimum SDK:** 24
- **Target SDK:** 36
- **UI:** Jetpack Compose (Material 3, Navigation Compose, Google Fonts, Lottie)
- **DI:** Koin
- **Async:** Coroutines/Flow
- **Storage:** Room + DataStore
- **Cloud:** Firebase (Analytics, Crashlytics, App Check)
- **Monetization & Platform:** AdMob, RevenueCat (Purchases UI), Play Review, Play In-App Update
- **Other:** Google Generative AI client, Loggur module for logging

## 2. Architecture Pattern

Clean Architecture with MVVM.

- **Presentation (`presentation`)**: Composables, Navigation, and `HomeViewModel`/`SettingsViewModel`.
- **Domain (`domain`)**: `TemplateItem` model plus use cases (`GetTemplateItems`, `UpsertTemplateItem`, `SeedTemplateData`).
- **Data (`data`)**: Room database (`TemplateDatabase`/`TemplateDao`), preferences (`TemplatePreferencesDataSource`), and repository implementation.
- **Core/Common (`core`)**: Application bootstrap (`AppApplication`), DI modules, theme, and platform helpers (`AppUpdateHelper`, `InAppReviewHelper`).
- **Logging (`:loggur`)**: Separate module exposing `Loggur`/`AndroidLoggur`.

## 3. Dependency Injection (Koin)

`AppApplication` starts Koin with grouped modules (`coreModule`, `dataModule`, `domainModule`, `viewModelModule`) in `core/di/AppModules.kt`. This is where you add feature-specific modules.

## 4. Firebase Configuration

Firebase Analytics, Crashlytics, and App Check dependencies are already declared in `app/build.gradle.kts`. Replace `google-services.json` with your project file and keep the App Check provider switch in `AppApplication`.

## 5. Required Libraries (version catalog)

`gradle/libs.versions.toml` includes:

- **UI:** Compose BOM, Material3, Navigation Compose, Google Fonts, Lottie
- **DI:** `koin-androidx-compose`
- **Storage:** Room (with KSP) and DataStore
- **Firebase:** BOM, Analytics, Crashlytics, App Check (debug + Play Integrity)
- **Platform:** Play Review/Review-KTX, App Update/App Update-KTX, Play Services Ads, RevenueCat Purchases UI
- **Other:** Generative AI client, Kotlinx Serialization

## 6. Project Skeleton & Navigation

- `presentation/navigation/AppNavHost`: Routes for Home and Settings using `AppDestinations` and `AppActions`.
- `presentation/screens/home`: Displays seeded placeholder items and supports adding new items through `HomeViewModel`. Follows the `Action`/`Event` pattern (`HomeAction`, `HomeEvent`) so UI intents and one-off effects are explicit.
- `presentation/screens/settings`: Demonstrates `InAppReviewHelper` and `AppUpdateHelper` usage with `SettingsAction`/`SettingsEvent`.

## 7. Helpers for Play Services

- `core/updates/InAppReviewHelper`: Wraps Review API with a coroutine-friendly API.
- `core/updates/AppUpdateHelper`: Checks update availability; extend to trigger in-app updates as needed.

## 8. Loggur Module

Included as `:loggur` and consumed via Koin (`single<Loggur> { AndroidLoggur("AppTemplate") }`). Use `FakeLoggur` in tests.

## 9. How to Extend

1. Duplicate this repository, rename the applicationId/package, and update branding assets.
2. Add new screens and view models; register them in `AppNavHost` and Koin modules.
3. Replace placeholder Room entities/use cases with your domain logic.
4. Provide your Firebase `google-services.json`, enable App Check providers, and connect Ads/RevenueCat keys.
