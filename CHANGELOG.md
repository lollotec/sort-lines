<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Sort Lines with Comment Changelog

## [Unreleased]

## [1.0.2] - 2025-11-27

### Added

- Highlighting for lines where the group or split and key regex can't be found
- Error message and log message for actions that can't complete a sort
- "Go to" action in error notification that moves the cursor to the problem sort
- Improved behaviour for blocks of lines containing blank lines with a `sort: end`
- More debugging logs for sort errors

### Fixed

- Sort not continuing until the `sort: end`, when provided
- CI job for releasing the new version changelog
- Gutter icon not showing for incomplete sort comments

### Changed

- Prefix inspection errors with "Sort lines:"
- Moved usage documentation to a GitHub Pages website
- Frequency of some CI jobs for inspection code and verifying the plugin
- Upgrade dependencies: org.jetbrains.intellij.platform 2.10.4, jetbrains/qodana-jvm-community 2025.2, gradle 9.2.1

## [1.0.1] - 2025-10-29

### Added

- Media images and usage section in the readme on GitHub
- Automated generation for lexer and parser
- Error highlighting for group and split regex

### Fixed

- Sort comment injection after unclosed bracket
- Parser showing symbol name instead of character

### Changed

- Dependency version updates
- When sort comment validation occurs

## [1.0.0] - 2025-10-01

### Added

- `Sort Lines with Comments` action added to `Tools` menu
- `Sort Lines with Comments` action added to the `Settings` > `Tools` > `Actions on save` settings page to sort all blocks in open files.
- Custom options for sort order added to the `Settings` > `Tools` > `Sort lines with comment` settings page
- Gutter icons that show sort direction
- Highlighting for unsorted lines and `Sort lines` quick fix.
- "Sort" language parsing, highlighting, and simple order completion.
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

[Unreleased]: https://github.com/lollotec/sort-lines/compare/v1.0.2...HEAD
[1.0.2]: https://github.com/lollotec/sort-lines/compare/v1.0.1...v1.0.2
[1.0.1]: https://github.com/lollotec/sort-lines/compare/v1.0.0...v1.0.1
[1.0.0]: https://github.com/lollotec/sort-lines/commits/v1.0.0
[unreleased]: https://github.com/lollotec/sort-lines/compare/1.0.1...dev
