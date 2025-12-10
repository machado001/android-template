# App Template

This is a Clean Architecture Android template (Compose + Koin + Room + Firebase) ready to clone and brand for new apps.

## Quick Start
1) Clone and open in Android Studio.
2) Replace placeholder Firebase config:
   - Put your real `google-services.json` under `app/` (both `dev.machado001.apptemplate` and `.debug` clients if you keep the debug suffix).
3) Update app identity:
   - Change `applicationId` and package namespace in `app/build.gradle.kts` and `settings.gradle.kts` (root name).
   - Adjust `app/src/main/AndroidManifest.xml` and package declarations if you rename packages.
   - Update launcher icons and `app/src/main/res/values/strings.xml` for app name.
4) Configure SDKs/keys:
   - AdMob, RevenueCat, Play integrity/review/update, and any API keys (store securely).
5) Run: `./gradlew assembleDebug` (or from Android Studio).

## Architecture Overview
- Layers: `presentation` (Compose + Navigation + Action/Event), `domain` (models/use cases), `data` (Room + DataStore + repository), `core` (App/Firebase/Koin bootstrap, theme, helpers), `common` (utilities), `loggur` (logging module).
- DI: Koin modules in `core/di/*` started by `core/AppApplication.kt`.
- Navigation: `AppNavHost` with `Home` and `Settings` routes and `AppActions`.
- Logging: Always use `dev.machado001.apptemplate.common.TAG` with `Loggur` (provided via `loggur` module).

## Customization Checklist
- Firebase:
  - Replace `app/google-services.json`.
  - Confirm App Check providers (debug vs Play Integrity) in `AppApplication`.
  - Enable Analytics/Crashlytics as needed.
- Branding:
  - Update colors/theme in `core/designsystem/theme/*`.
  - Replace launcher icons and fonts.
  - Edit `strings.xml` and manifest labels.
- Package/App IDs:
  - Rename packages and `applicationId`; update Koin module imports and manifest `android:name` if moving `AppApplication`.
- Features:
  - Add routes in `presentation/navigation/AppNavHost`.
  - Add new ViewModels/use cases/repositories and wire them in `core/di` modules.
  - Replace placeholder Room entity (`TemplateEntity`) with your domain models.
- Monetization/Platform:
  - Configure AdMob IDs, RevenueCat API key/entitlements, Play Review/Update flows.
- Play/Firebase assets:
  - Add signing configs, keystores, and upload to Play Console as required.

## Commands
- Build: `./gradlew assembleDebug`
- Dependencies: `./gradlew :app:dependencies --configuration debugRuntimeClasspath`
- KSP (Room): `./gradlew :app:kspDebugKotlin`

## Notes
- Deprecation warnings (resourceConfigurations, jvmTarget, Koin viewModel DSL) are safe but can be modernized later.
- Placeholder `google-services.json` is only for local builds; must be replaced before release.
