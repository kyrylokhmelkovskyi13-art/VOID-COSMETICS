# VOID COSMETICS

Client-only fantasy cosmetics project for Minecraft Java 26.2 (Fabric).

## Design direction

The visual style is intended to feel native to Minecraft: crisp pixel shapes, blocky silhouettes, restrained colors, and no smooth plastic-looking accessories.

Planned cosmetic collection:
- Demonic and angel wings
- Obsidian horns, sunlit halo, and void crown
- Raven mask
- Dragon tail
- Spectral swords floating behind the player

## Current prototype

- Client-only Fabric entrypoint.
- Press **K** to open the cosmetic wardrobe.
- The wardrobe has selectable cards for all eight cosmetic types.
- The selected type is saved locally in `config/voidcosmetics.properties`.
- The menu does not pause single-player.
- No server component or synchronization is implemented.

## Important limitations

This is still an early prototype, not a finished playable cosmetics release.

- The wardrobe currently selects and saves a cosmetic type; it does not yet render the selected accessory on the player.
- The model JSON files are original geometry drafts in a neutral project format, not Minecraft runtime models or GeckoLib models.
- Final pixel-art PNG textures, player attachment rendering, and animations are not implemented yet.
- Shoulder pets and particle auras remain planned features and are not in the current eight-item prototype.

With local-only mode, other players will not see the cosmetics. The current menu is a local selection/preview shell only.

## Build

The GitHub Actions workflow builds the mod with Java 25, Gradle 9.5.1, Fabric Loom 1.17, and the configured Minecraft 26.2 dependencies. Check the Actions tab for the exact result and downloadable build artifact.

## License

Project code and original assets will receive an explicit license before public release.
