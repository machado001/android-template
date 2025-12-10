# Agents Guide

This guide explains how this template is structured, how it boots, and how to work on it safely. Use it as the canonical reference when automating tasks.

## Project Shape
- Root modules: `:app` (Android app) and `:loggur` (logging library).
- Package: `dev.machado001.apptemplate`.
- Architecture: Clean Architecture + MVVM + Jetpack Compose Navigation.
  - `presentation`: UI Composables, navigation, and ViewModels with `Action`/`Event` channels.
  - `domain`: Models and use cases only.
  - `data`: Room DAO/DB, DataStore prefs, repository impls.
  - `core`: App bootstrap (Firebase + App Check + Koin), DI modules, theme, platform helpers (Review/Update).
  - `common`: Shared utilities (`StringUtil.TAG` for logging tags).
  - `loggur`: Logging abstraction (AndroidLoggur/FakeLoggur) shipped as a library module.

## Build & Tooling
- Gradle (Kotlin DSL), AGP `8.13.1`, Kotlin `2.2.21`, KSP `2.2.21-2.0.4`.
- Compose BOM `2025.12.00`, Material3 UI, Navigation Compose.
- Koin for DI (compose + viewModel DSL).
- Room with KSP, DataStore Preferences.
- Firebase (Analytics, Crashlytics, App Check), Play Services Ads, RevenueCat Purchases UI, Play Review/Update, Google Fonts, Lottie, Generative AI client.
- Version catalog: `gradle/libs.versions.toml` holds all versions/plugins.
- Build targets: `minSdk 24`, `target/compileSdk 36`, Java/Kotlin 17.
- Useful commands:
  - `./gradlew assembleDebug` (primary build)
  - `./gradlew :app:dependencies --configuration debugRuntimeClasspath` (resolve issues)
  - `./gradlew :app:kspDebugKotlin` (Room schema generation path)

## Firebase / Google Services
- Placeholder `app/google-services.json` is present for both `dev.machado001.apptemplate` and `.debug`. Replace with real config for production.
- App Check switches Debug/PlayIntegrity in `core/AppApplication.kt`.

## Logging Standard
- Always use the TAG helper: `private val tag = TAG` (from `dev.machado001.apptemplate.common.StringUtil`) and log via `loggur.*(tag, message)`.
- Koin binds `Loggur` to `AndroidLoggur("AppTemplate")` in `core/di/CoreModule.kt`.

## Navigation & UI Pattern
- Routes: `AppDestinations.HOME`, `SETTINGS` with `AppActions` and `AppNavHost`.
- Each screen uses sealed `Action` for UI intents and sealed `Event` for one-off effects:
  - Home: `HomeAction.OnAddPlaceholder`, `OnNavigateToSettings`; `HomeEvent.NavigateToSettings`.
  - Settings: `SettingsAction.OnBack/OnRequestReview/OnCheckForUpdates`; `SettingsEvent.NavigateBack`.
- Theme lives under `core/designsystem/theme` and wraps the app in `core/ui/TemplateApp.kt`.

## Data Layer
- Room DB: `TemplateDatabase` with `TemplateDao` and `TemplateEntity`.
- Repository: `TemplateRepositoryImpl` (logs via TAG), seeds defaults once using DataStore flag.
- Use cases: `GetTemplateItems`, `UpsertTemplateItem`, `SeedTemplateData`.

## DI Modules
- `core/di/AppModules.kt` aggregates:
  - `coreModule`: Loggur, Play Review/Update helpers.
  - `dataModule`: Room DB/DAO, DataStore, repository binding.
  - `domainModule`: Use cases.
  - `viewModelModule`: HomeViewModel, SettingsViewModel.
- Started in `core/AppApplication` via Koin.

## Platform Helpers
- `core/updates/InAppReviewHelper`: Coroutine-friendly Review flow with logging.
- `core/updates/AppUpdateHelper`: Checks Play update availability; stub to extend for in-app updates.

## Testing Notes
- `loggur` offers `FakeLoggur` for silent logging.
- No instrumented/unit tests are defined yet beyond the template defaults.

## Gotchas & Tips
- Keep `google-services.json` valid JSON (not commented) or the Google Services plugin fails.
- When bumping Kotlin/AGP, align KSP version (`ksp = <kotlin>-2.0.x` pattern).
- Deprecated warnings:
  - `resourceConfigurations` and `jvmTarget` can be migrated to `androidResources.localeFilters` and `compilerOptions` when refactoring.
  - Koin ViewModel DSL import is deprecated; consider migrating to `org.koin.core.module.dsl.viewModelOf`.
- Logging: never hardcode tag strings—always use `TAG`.
