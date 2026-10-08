# Connect with Your Coach — Compose Multiplatform UI

UI-only project (no AI, no messaging, no backend). Sample data lives in `model/UiModels.kt`.

## Run it
1. Open this folder in **Android Studio** (Ladybug or newer, JDK 17+) and let Gradle sync.
2. **Android:** pick the `composeApp` configuration, choose an emulator/device, press Run.
3. **Desktop (fastest preview, phone-sized window):**
   `./gradlew :composeApp:run`   (Windows: `gradlew.bat :composeApp:run`)
4. **iOS (Mac only):** the Gradle targets and Swift sources (`iosApp/iosApp/`) are included,
   but not an `.xcodeproj`. Generate a project at https://kmp.jetbrains.com with package
   `com.pfe.connectcoach`, then copy `composeApp/src/commonMain` into it.

On Mac/Linux run `chmod +x gradlew` once.

## Structure
composeApp/src/commonMain/kotlin/com/pfe/connectcoach/
  App.kt                  tab navigation
  model/UiModels.kt       UI state + sample data
  ui/theme/Theme.kt       palette
  ui/components/Common.kt rings, bars, chips, bottom nav
  ui/screens/             ClientHomeScreen, CoachScreen, AssistantScreen
