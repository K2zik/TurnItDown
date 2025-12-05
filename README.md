**English translation**

TurnItDown is a lightweight client-side Minecraft mod (Fabric) that allows adjusting the playback volume of music items (category `music_discs`) and shows brief HUD notifications.

What the mod does:
- Volume control: change the player (jukebox) volume by scrolling the mouse wheel while holding Shift and looking at a `Jukebox`.
- Visual feedback: when the volume changes, a short message appears at the center of the screen above the hotbar, e.g. `Player volume: +25%` or `Player volume: -10%`.
- Integration: the mod uses the Simple Voice Chat API to read and write the `music_discs` category volume value.

Display features:
- The message appears centered above the hotbar without a background.
- The label `Player volume:` is rendered in orange; the percentage value is rendered in yellow.
- The message fades away smoothly over about 3 seconds.

Key classes and structure:
- `src/client/java/org/k1zik/turnitdown/client/event/VolumeScroller.java` — handles scroll input, computes new volume and prepares messages.
- `src/client/java/org/k1zik/turnitdown/client/render/VolumeTextRenderer.java` — renders the HUD notification.
- `src/main/java/org/k1zik/turnitdown/Config.java` — simple config storage for `initialVolume` (file `config/turnitdown.properties`).

This README intentionally contains only a project description and does not include installation or usage instructions.

**TurnItDown — Описание проекта**

TurnItDown — лёгкий клиентский мод для Minecraft (Fabric), предназначенный для управления громкостью воспроизведения музыкальных вставок (категория music_discs) и отображения информативных HUD-сообщений.

**Что делает мод:**
- **Управление громкостью:** позволяет изменять уровень громкости плеера (например, jukebox) при прокрутке колёсика мыши в сочетании с зажатым Shift, когда игрок смотрит на `Jukebox`.
- **Визуальная обратная связь:** при изменении громкости в центре экрана над хотбаром кратковременно отображается сообщение вида
  `Громкость проигрывателя: +25%` или `Громкость проигрывателя: -10%`.
- **Интеграция:** мод взаимодействует с API Simple Voice Chat для чтения и записи значения громкости в категорию `music_discs`.

**Особенности отображения:**
- Сообщение отображается по центру экрана над хотбаром без фона.
- Подпись `Громкость проигрывателя:` рендерится оранжевым цветом, сама величина в процентах — жёлтым.
- Сообщение плавно исчезает в течение ~3 секунд.

**Структура и важные классы:**
- `src/client/java/org/k1zik/turnitdown/client/event/VolumeScroller.java` — обработка прокрутки, логика вычисления новой громкости и создание сообщения.
- `src/client/java/org/k1zik/turnitdown/client/render/VolumeTextRenderer.java` — отрисовка HUD-сообщения.
- `src/main/java/org/k1zik/turnitdown/Config.java` — простой конфиг для хранения значения `initialVolume` (файл `config/turnitdown.properties`).

Этот README содержит только описание проекта и его возможностей; инструкции по установке, настройке или использованию преднамеренно опущены.


