# Sort Lines with Comment IntelliJ Plugin

![Build](https://github.com/lollotec/sort-lines/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

## Introduction

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

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Sort Lines with Comment"</kbd> >
  <kbd>Install</kbd>
  
- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/lollotec/sort-lines/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
