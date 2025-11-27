---
layout: home
title: About
nav_order: 1
description: "Sort Lines with Comment is an IntelliJ IDEA plugin that helps you maintain sorted code blocks using special comments."
permalink: /
---

# Sort Lines with Comment Plugin for IntelliJ IDEA

Keep your code organized with automatic line sorting using simple comments.

[Getting Started]({{ site.baseurl }}{% link getting-started.md %})

[Features]({{ site.baseurl }}{% link features.md %})

---

## What is Sort Lines with Comment?

Sort Lines with Comment is a powerful IntelliJ IDEA plugin that automatically maintains sorted code blocks using special comment directives. Simply add a comment like `// sort: asc` above a block of lines, and the plugin will ensure they stay sorted.

![Sort Lines Demo]({{ site.baseurl }}/assets/images/simple-sort.gif)

## Why Use This Plugin?

### Maintain Code Organization
Keep imports, dependencies, configuration lists, and other line-based code consistently sorted without manual effort.

### Prevent Merge Conflicts
Sorted code reduces merge conflicts by establishing a predictable order for similar lines.

### Improve Code Readability
Consistently sorted code is easier to scan and understand, making it simpler to find what you're looking for.

### Flexible Sorting Options
Support for simple sorting, grouped sorting with regex patterns, and split-key sorting for complex scenarios.

## Key Features

- **Automatic Detection**: Highlights unsorted lines with warnings
- **Quick Fixes**: One-click sorting to fix order issues
- **Multiple Sort Types**: Simple, grouped, and split-key sorting
- **Custom Sort Orders**: Configure your own ascending/descending keywords
- **Sort on Save**: Optionally sort all blocks when saving files
- **Visual Indicators**: Gutter icons showing sort direction and location
- **Syntax Highlighting**: Color-coded sort comment syntax

## Quick Example

```java
// sort: asc
import java.util.List;
import java.util.Map;
import java.util.Set;
```

The plugin ensures these lines stay alphabetically sorted. If you add a new import out of order, you'll see a warning and can apply the quick fix to resort them or just save the file to have them automatically sorted.

## Platform Compatibility

- IntelliJ IDEA 2025.2.2 and later
- Works with all IntelliJ-based IDEs (PyCharm, WebStorm, etc.)
- In files for languages with IDE or plugin support that use comments

---

Ready to get started? Check out the [Getting Started Guide]({{ site.baseurl }}{% link getting-started.md %}) for installation instructions and basic usage.

Need help? Please add any problems to [GitHub Issues](https://github.com/lollotec/sort-lines/issues).
