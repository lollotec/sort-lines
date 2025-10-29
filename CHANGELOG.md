<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Sort Lines with Comment Changelog

## [Unreleased]
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
