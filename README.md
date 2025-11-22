# Sort Lines with Comment IntelliJ Plugin

![Build](https://github.com/lollotec/sort-lines/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/com.github.jodiew.sortlines.svg)][plugin]
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.github.jodiew.sortlines.svg)][plugin]

## Introduction

![Gif of sort highlighting and quickfix](/media/simple-sort.gif)

<!-- Plugin description -->
Sort blocks of lines with a comment.

On the line before the lines to be sorted use one of the following sort patterns:

- Simple `sort: [asc|desc]`
- Group `sort: { order: [asc|desc], group: /regex/ }`
- Split `sort: { order: [asc|desc], split: /regex/, key: #}`

The plugin will check the order of the following lines until there is a blank line, an indent level change, or another
sort comment. To force a different end to the sort include `sort: end` on the line where the sort should end.

## Features

- Highlights unsorted lines with a warning and provides a quick fix to sort them
- Syntax highlighting and sort order completion for the sort comment
- Action in the tools menu to sort all the lines in the open file
- Option to sort lines on save for all files
- Custom settings for ascending and descending sort orders
- Gutter icons showing sort comment location and sort order

<!-- Plugin description end -->

## Usage

Check out the [Documentation][documentation]

---

Plugin based on the [IntelliJ Platform Plugin Template][template] and inspired by [IntelliJ Plugin: Building Docker Security Analysis Tools][cloud-security-plugin]

[plugin]: https://plugins.jetbrains.com/plugin/28626-sort-lines-with-comment
[documentation]: https://lollotec.github.io/sort-lines
[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[cloud-security-plugin]: https://protsenko.dev/2025/03/24/how-i-made-docker-linter-for-intellij-idea-and-other-jetbrains-ide/
