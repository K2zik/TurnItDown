# TurnItDown

<details>
  <summary><strong>English description (click to expand)</strong></summary>

TurnItDown is a lightweight client-side Minecraft mod for Fabric that lets players fine-tune the playback volume of music items (`music_discs`) and displays brief, unobtrusive HUD notifications.

## Features
- **Volume control:** Adjust jukebox volume by scrolling the mouse wheel while holding **Shift** while looking at a `Jukebox`.
- **Visual feedback:** A short HUD message appears above the hotbar when the volume changes, e.g.  
  `Player volume: +25%` or `Player volume: -10%`.
- **Integration:** Uses the Simple Voice Chat API to read and write the `music_discs` volume value.

## HUD Appearance
- Message is centered above the hotbar with no background.
- The label `Player volume:` is rendered in **orange**, the percentage in **yellow**.
- The message fades out smoothly over ~3 seconds.

## Structure
- `VolumeScroller.java` — handles scroll input, volume calculation, message formatting.
- `VolumeTextRenderer.java` — renders the HUD notification.
- `Config.java` — stores `initialVolume` in `config/turnitdown.properties`.

This README contains only a description of the project and intentionally omits installation or usage instructions.

</details>

---

<details>
  <summary><strong>Русское описание (нажмите, чтобы развернуть)</strong></summary>

TurnItDown — лёгкий клиентский клиентский мод для Minecraft (Fabric), позволяющий управлять громкостью воспроизведения музыкальных вставок (`music_discs`) и показывать короткие HUD-уведомления.

## Возможности
- **Управление громкостью:** изменение громкости проигрывателя (например, jukebox) прокруткой колеса мыши при зажатом **Shift**, когда игрок смотрит на `Jukebox`.
- **Визуальная обратная связь:** над хотбаром кратковременно появляется сообщение вида  
  `Громкость проигрывателя: +25%` или `Громкость проигрывателя: -10%`.
- **Интеграция:** используется API Simple Voice Chat для чтения и записи значения категории `music_discs`.

## Особенности отображения
- Сообщение выводится по центру над хотбаром без фоновой подложки.
- Надпись `Громкость проигрывателя:` отображается **оранжевым**, значение процента — **жёлтым**.
- Уведомление плавно исчезает примерно за 3 секунды.

## Структура проекта
- `VolumeScroller.java` — обработка колеса мыши, расчёт громкости, формирование текста сообщения.
- `VolumeTextRenderer.java` — рендер HUD-уведомления.
- `Config.java` — хранение `initialVolume` в `config/turnitdown.properties`.

README намеренно содержит только описание проекта, без инструкций по установке и использованию.

</details>
