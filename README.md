# VOID COSMETICS

Client-only fantasy cosmetics for Minecraft Java 26.2 (Fabric).

## Install the build

1. Install Minecraft Java **26.2**.
2. Install Fabric Loader **0.19.3 or newer** for Minecraft 26.2.
3. Install the matching Fabric API for Minecraft 26.2.
4. Open the repository's [Actions page](https://github.com/kyrylokhmelkovskyi13-art/VOID-COSMETICS/actions).
5. Open a successful **Build mod** run, scroll to **Artifacts**, and download the `void-cosmetics-...` ZIP.
6. Extract the ZIP and put the regular `void-cosmetics-...jar` (not the `-sources.jar`) into your Minecraft `mods` folder.
7. Start the Fabric profile. Press **K** in a world to open the wardrobe.

The artifact for commit `e79736b01d10451cb6e25c1766bb8d8aa0da96f2` is available from [this successful build](https://github.com/kyrylokhmelkovskyi13-art/VOID-COSMETICS/actions/runs/43252219669). GitHub Actions artifacts are temporary and expire; build artifacts are retained for a limited period.

## First playable prototype scope

- Press **K** to open the local cosmetic wardrobe.
- Choose one of eight block-shaped accessories: demonic wings, angel wings, obsidian horns, sunlit halo, void crown, raven mask, dragon tail, or spectral swords.
- The selected cosmetic is saved locally in `config/voidcosmetics.properties`.
- Menu text is localized in English and Russian using the Minecraft language setting.
- Accessories are submitted through the Minecraft 26.2 render-layer pipeline.
- No server component or synchronization is used; other players do not receive the cosmetic data.

## Visual direction

Native Minecraft proportions, crisp cuboid silhouettes, pixel-like color blocks, and restrained fantasy palettes. No smooth plastic-looking meshes.

## Current verification status

GitHub Actions confirms that the project compiles and packages a JAR. The accessory geometry has not yet been visually verified in a running Minecraft client. Treat the current geometry as an early render prototype: attachment offsets, visibility, player model variants, armor overlap, and first-person behavior still need in-game testing and adjustment.

The geometry is currently generated in code as colored cuboids. Final pixel-art PNG textures, animated wing movement, cosmetic particles, shoulder pets, and a polished in-game 3D preview are not implemented yet.

## Build

The workflow builds with Java 25, Gradle 9.5.1, Fabric Loom 1.17, and the configured Minecraft 26.2 dependencies.

## License

Project code and original assets will receive an explicit license before public release.
