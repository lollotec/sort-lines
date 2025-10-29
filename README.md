# Sort Lines with Comment IntelliJ Plugin

![Build](https://github.com/lollotec/sort-lines/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/com.github.jodiew.sortlines.svg)](https://plugins.jetbrains.com/plugin/28626-sort-lines-with-comment)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.github.jodiew.sortlines.svg)](https://plugins.jetbrains.com/plugin/28626-sort-lines-with-comment)

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

## Usage
![Gif of sort highlighting and quickfix](/media/simple-sort.gif)

### Sort Types

Simple Sort
![Screenshot of simple sort highlighting](/media/highlighting.png)

Group Sort
![Screenshot of group sort in a ruby file](/media/ruby-group.png)

Split Sort
![Screenshot of split sort in a ruby file](/media/ruby-split.png)

### Actions

All the blocks in the file can be sorted manually by using `Sort Lines with Comment` in the `Tools` menu.
![Screenshot showing 'Sort Lines with Comment' action in tool menu](/media/tool-menu.png)

Sort on save can be enabled/disabled in the `Settings` > `Tools` > `Actions on save` page.
![Screenshot showing the 'Actions on save' settings page](/media/actions-on-save.png)

### Settings

Custom sort order options can be added to the `Settings` > `Tools` > `Sort Lines with Comment` settings page.
![Screenshot showing the 'Sort Lines with Comment' settings page](/media/sort-lines-settings.png)
![Screenshot showing sort order completion options](/media/sort-order-completion.png)

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
