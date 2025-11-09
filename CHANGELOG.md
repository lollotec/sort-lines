<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Sort Lines with Comment Changelog

## [Unreleased]
### Added
- Highlighting for lines where the group or split and key regex can't be found
- Error message and log message for actions that can't complete a sort
- "Go to" action in error notification that moves the cursor to the problem sort

### Changed
- Upgrade dependencies: org.jetbrains.intellij.platform 2.10.4, jetbrains/qodana-jvm-community 2025.2, gradle 9.2.0

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

[unreleased]: https://github.com/lollotec/sort-lines/compare/1.0.1...dev
[1.0.1]: https://github.com/lollotec/sort-lines/compare/1.0.0...1.0.1
[1.0.0]: https://github.com/lollotec/sort-lines/releases/tag/1.0.0
