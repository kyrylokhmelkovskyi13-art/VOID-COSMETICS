# VOID COSMETICS

Client-only fantasy cosmetics for Minecraft Java 26.2 (Fabric).

## First playable prototype scope

- Press **K** to open the local cosmetic wardrobe.
- Choose one of eight block-shaped accessories: demonic wings, angel wings, obsidian horns, sunlit halo, void crown, raven mask, dragon tail, or spectral swords.
- The selected cosmetic is saved locally in `config/voidcosmetics.properties`.
- Accessories are submitted through the Minecraft 26.2 render-layer pipeline.
- No server component or synchronization is used; other players do not receive the cosmetic data.

## Visual direction

Native Minecraft proportions, crisp cuboid silhouettes, pixel-like color blocks, and restrained fantasy palettes. No smooth plastic-looking meshes.

## Current verification status

GitHub Actions confirms that the project compiles and packages a JAR. The new accessory geometry has not yet been visually verified in a running Minecraft client. Treat the current geometry as an early render prototype: attachment offsets, visibility, player model variants, armor overlap, and first-person behavior still need in-game testing and adjustment.

The geometry is currently generated in code as colored cuboids. Final pixel-art PNG textures, animated wing movement, cosmetic particles, shoulder pets, and a polished in-game preview are not implemented yet.

## Build

The workflow builds with Java 25, Gradle 9.5.1, Fabric Loom 1.17, and the configured Minecraft 26.2 dependencies. Download the JAR from the successful GitHub Actions run.

## License

Project code and original assets will receive an explicit license before public release.
