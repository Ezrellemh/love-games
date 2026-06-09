# Love Games

A collection of intentional, two-player Android games designed to deepen connection. Currently includes one game — **36 Questions to Fall in Love** — and is architected so additional love games can drop in alongside it.

Built entirely with Kotlin and Jetpack Compose using Material 3.

## The games

### 36 Questions to Fall in Love

Based on [Arthur Aron's classic NYT Modern Love study](https://www.nytimes.com/2015/01/11/style/modern-love-to-fall-in-love-with-anyone-do-this.html). Two people take turns reading and answering all 36 questions, then finish with a four-minute silent gaze.

**What it does for the players:**

- Walks both players through 36 prompts, one at a time, with a small colored dot that follows the turn between corners of the screen.
- A four-minute timer kicks in on question 11 ("Take four minutes and tell your partner your life story"), and again on the closing stare.
- Optional **symmetry mode**: shows each question rotated 180° for the partner sitting opposite, so both players read right-side-up without passing the phone.
- A subtle themed background icon hints at each question's topic (a heart for question 9, a calendar for question 19, a trophy for question 15, and so on).
- **Press-and-hold anywhere** on a timer screen to fast-forward the timer at 2×.
- A "Feeling Adventurous?" finale invites the pair to keep going.

## Architecture

Three Gradle modules:

| Module | Responsibility |
|---|---|
| `:app` | App entry point, home screen, navigation, AdMob initialization. |
| `:thirtysixforlove` | The 36-Questions game — screens, ViewModel, state, theme. |
| `:common` | Shared building blocks reusable by future games: the `MoreGamesButton` composable and the `InterstitialAdManager` (Google AdMob wrapper). |

```
LoveGames/
├── app/                                # App shell, home screen, nav
├── common/                             # Shared: MoreGamesButton + AdMob
└── thirtysixforlove/                   # The 36 Questions game
    └── src/main/kotlin/com/lovegames/thirtysixforlove/
        ├── ThirtySixQuestionsViewModel.kt
        ├── TimerCompletionAction.kt
        ├── compose/                    # Screens (disclaimer, instructions, main, congrats, kiss)
        └── ui/                         # ThirtySixQuestionsTheme + state
```

Future love games are expected to follow the same pattern: their own `:gameName` module with a dedicated `Theme` composable using their own color palette, plus a `MoreGamesButton(onAdClosed = …)` call at the end of the flow to show an interstitial ad and return to the home screen.

## Tech

- **Language**: Kotlin 1.9
- **UI**: Jetpack Compose (BOM 2024.04.01) + Material 3
- **Min SDK**: 24 (Android 7.0) · **Target / Compile SDK**: 34 (Android 14)
- **Java toolchain**: 17
- **AdMob**: `play-services-ads` 23.6.0
- **Navigation**: `androidx.navigation.compose`

State is driven by `ViewModel` + `MutableStateFlow`; screens collect state via `collectAsState` and re-render. Animations use `animateDpAsState` and `animateFloatAsState` with spring/tween specs. Touch handling uses `awaitEachGesture` at the `Initial` pointer pass so observation never consumes events from child clickables.

## Theming

`ThirtySixQuestionsTheme` is a Material 3 color scheme keyed off the game's red/magenta palette — primary = red, secondary = magenta, soft pinks for containers, blush for the background. Each screen wraps its content in this theme so the entire 36 Questions experience reads as one cohesive look. Future games will get their own themes; the home-screen card for each game previews that game's palette by wrapping the card in the game's theme.

## Build & run

1. Open the project in Android Studio (Hedgehog or newer recommended).
2. Let Gradle sync — the first sync downloads Compose BOM, AdMob, and the Material icons extended library.
3. Run the `app` configuration on an emulator or device running Android 7.0+.

From the command line:

```bash
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

## AdMob setup

This repo ships with Google's **test** AdMob credentials so the app builds and runs out of the box without an AdMob account:

- App ID (in `app/src/main/AndroidManifest.xml`): `ca-app-pub-3940256099942544~3347511713`
- Interstitial unit ID (in `common/src/main/kotlin/com/lovegames/common/ads/InterstitialAdManager.kt`): `ca-app-pub-3940256099942544/1033173712`

Before publishing to the Play Store, replace both with your own AdMob account's IDs. You can either edit the constant directly, or call `InterstitialAdManager.configure("your-real-unit-id")` from `MainActivity.onCreate` before `preload(this)`.

## Credits

- **Arthur Aron** for the original [36 Questions](https://www.nytimes.com/2015/01/11/style/modern-love-to-fall-in-love-with-anyone-do-this.html) study, and the NYT Modern Love essay that popularized it.
- The folks behind [36questionsinlove.com](https://36questionsinlove.com) — heavy inspiration for this app's experience.
- **Philipp Lackner** for the circular countdown-timer pattern used in `Timer.kt`. [Video reference](https://youtu.be/2mKhmMrt2Ok).

## License

Personal project. Not licensed for redistribution — feel free to fork for reference, but please replace AdMob credentials and don't republish to the Play Store under the same package name.
