# About this fork

This repository is a **modified fork** of [humblemuzzu/dumbtv](https://github.com/humblemuzzu/dumbtv), a Fire TV debloater with a Home-button hook ("Guard").

- **Original author / owner:** the dumbtv contributors. The original copyright and the MIT licence are kept unchanged in [LICENSE](LICENSE).
  Their code remains theirs; the changes made here are offered under the same MIT licence.
- **Forked from upstream commit** `755991b`. The work lives on the branch `guard-target` (the default branch is the untouched upstream).
- **What was changed:** the launcher module under `launcher/` (Guard can start another launcher through `home_target`; a "tvos" layout). The rest is unchanged upstream content.
- **Not affiliated:** this fork is made by a different person for their own Fire TV Stick Lite setup. It is not the official project and is not endorsed
  by the original authors. Product names (Fire TV, Amazon, Apple TV, Netflix and others) belong to their owners and are used only to describe what the
  software works with.
- **No warranty:** see the MIT licence. You change your own device at your own risk.

## Commits added on top of upstream

- feat: add layout=tvos choice and a host test runner
- feat: add TvosStyle constants and glow colour helper
- feat: add Blur, a cached-backdrop box blur
- feat: lift and shadow the focused dock tile in the tvos layout
- feat: add the top shelf above the dock in the tvos layout
- feat: add the cached-blur glass band behind the dock in the tvos layout
- fix: fade the shelf glow out inside the strip instead of at a hard edge
- feat: CleanYourTV-style tvos pass: ambient glow, dark dock, white focus ring, no duplicate labels
- fix: start a row's scroll block at the row body when its title is hidden (tvos)
- feat: Guard can start another launcher (home_target) through the alarm path
