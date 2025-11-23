---
layout: page
title: Getting Started
nav_order: 2
description: "Learn how to install and start using Sort Lines with Comment plugin"
permalink: /getting-started/
---

# Getting Started
{: .no_toc }

Get up and running with Sort Lines with Comment in minutes.
{: .fs-6 .fw-300 }

## Table of Contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Installation

There are several ways to install the Sort Lines with Comment plugin:

### Using the IDEs built-in plugin system

1. Open a JetBrains IDE
2. Go to **Settings/Preferences** > **Plugins**
3. Select the **Marketplace** tab
4. Search for **"Sort Lines with Comment"**
5. Click **Install**
6. Restart your IDE when prompted

### From the JetBrains Marketplace

1. Visit the [Sort Lines with Comment plugin page](https://plugins.jetbrains.com/plugin/28626-sort-lines-with-comment)
2. Click the **Get** button
3. Follow the browser prompts to open the IDE
4. Confirm the installation
5. Restart your IDE when prompted

### Manual Installation

1. Download the latest release from [GitHub Releases](https://github.com/lollotec/sort-lines/releases/latest) or [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/28626-sort-lines-with-comment/versions)
2. In your JetBrains IdE, go to **Settings/Preferences** > **Plugins**
3. Click the gear icon (⚙️) and select **Install Plugin from Disk...**
4. Select the downloaded `.zip` file
5. Restart your IDE when prompted

---

## Basic Usage

### Your First Sort Comment

Let's start with a simple example. Add a sort comment above any block of lines you want to keep sorted:

```java
// sort: asc
import java.util.List;
import java.util.Map;
import java.util.Set;
```

The `// sort: asc` comment tells the plugin to keep the following lines in ascending (alphabetical) order.

### What Happens Next?

Once you've added a sort comment:

1. **The plugin scans the following lines** until it encounters:
   - A blank line
   - A change in indentation level
   - Another sort comment
   - A `// sort: end` comment (if you want to explicitly mark the end)

2. **If lines are out of order**, you'll see:
   - A warning highlight on the unsorted lines

3. **Apply the quick fix**:
   - Hover over the warning
   - Click the lightbulb or press `Alt+Enter` (Windows/Linux) or `⌥+Return` (Mac)
   - Select "Sort lines" to automatically fix the order

![Sort Quick Fix Demo]({{ site.baseurl }}/assets/images/simple-sort.gif)

### Sort Order Keywords

By default, the plugin recognizes these keywords:

**Ascending (A-Z):**
- `asc`
- `ascending`
- `a-z`

**Descending (Z-A):**
- `desc`
- `descending`
- `z-a`

You can customize these keywords in the plugin settings (see [Configuration](#configuration) below).

### Ending a Sort Block

The plugin automatically detects where a sort block ends, but you can explicitly mark the end:

```java
// sort: asc
import java.util.List;
import java.util.Map;
import java.util.Set;
// sort: end
// These lines won't be included in the sort
import java.util.Optional;
```

---

## Configuration

### Accessing Settings

1. Go to **Settings/Preferences** > **Tools** > **Sort Lines with Comment**
2. Configure your preferred sort order keywords

![Settings Page]({{ site.baseurl }}/assets/images/sort-lines-settings.png)

### Custom Sort Order Keywords

You can add your own keywords for ascending and descending order:

- **Ascending Order List**: Comma-separated keywords for ascending sort
  - Default: `asc, ascending, a-z`
  - Example: `↑, 🔺, 🐬`

- **Descending Order List**: Comma-separated keywords for descending sort
  - Default: `desc, descending, z-a`
  - Example: `↓, 🔻, 🦜`

These custom keywords will appear in auto-completion when typing sort comments.

![Sort Order Completion]({{ site.baseurl }}/assets/images/sort-order-completion.png)

### Sort on Save

Enable automatic sorting when saving files:

1. Go to **Settings/Preferences** > **Tools** > **Actions on Save**
2. Check **Sort Lines with Comment**

![Actions on Save]({{ site.baseurl }}/assets/images/actions-on-save.png)

Now all sort blocks will be automatically sorted whenever you save a file.

---

## Sorting All Blocks in a File

You can manually sort all blocks in the currently open file:

1. Go to **Tools** > **Sort Lines with Comment**
2. All sort blocks in the file will be sorted immediately

![Tools Menu]({{ site.baseurl }}/assets/images/tool-menu.png)

This is useful when you want to sort multiple blocks at once without applying quick fixes individually.

---

## Next Steps

Now that you've got the basics down, explore more advanced features:

- [Features]({{ site.baseurl }}{% link features.md %}) - Learn about all available features

---

## Troubleshooting

### Sort comment isn't recognized
- Ensure you're using one of the configured sort order keywords and the `sort:` prefix
- Check that the comment syntax matches your file type (e.g., `//` for Java, `#` for Python)
- Verify the plugin is enabled in Settings > Plugins

### Lines aren't being sorted
- Check that there's no blank line or indentation change interrupting the block
- Look for any syntax errors in your sort comment
- Try using `// sort: end` to explicitly mark the end of the block

### Warning highlights aren't appearing
- Ensure inspections are enabled: Settings > Editor > Inspections > Search for "Line order"
- Check that the inspection severity is set to "Warning" or higher

### "Sort Lines with Comment" plugin error
The sort action couldn't complete a sort
- Ensure there are no errors in the sort comment
- Check that the sort regex, if it's used, can be found in the lines
- Check that the split key, if it's used, is in range for the lines

Need help? Please add any problems to [GitHub Issues](https://github.com/lollotec/sort-lines/issues).
