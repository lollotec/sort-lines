---
layout: page
title: Features
nav_order: 3
description: "Detailed documentation of all Sort Lines with Comment features"
permalink: /features/
---

# Features
{: .no_toc }

Comprehensive guide to all features and capabilities of Sort Lines with Comment.
{: .fs-6 .fw-300 }

## Table of Contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Sort Types

Sort Lines with Comment supports three different sorting modes to handle various code organization needs.

### Simple Sort

The most straightforward sorting mode - sorts lines alphabetically in ascending or descending order.

**Syntax:**
```
// sort: <order>
```

**Example:**
```java
// sort: asc
import java.util.List;
import java.util.Map;
import java.util.Set;
```

**Use Cases:**
- Import statements
- Simple dependency lists
- Alphabetically ordered configuration values
- Any line-by-line alphabetical sorting

![Simple Sort Example]({{ site.baseurl }}/assets/images/highlighting.png)

---

### Group Sort

Sorts lines based on the text matched by a regex capture group, rather than sorting by the entire line. This allows you to sort by a specific portion of each line.

**Syntax:**
```
// sort: { order: <asc|desc>, group: /regex/ }
```

**Example:**
```ruby
# sort: { order: asc, group: /(:\S+)/ }
attr_reader :name
attr_writer :name
attr_reader :username
attr_reader :id
attr_writer :id
```

In this example, the regex capture group `(:\S+)` extracts just the attribute name (`:name`, `:username`, `:id`, etc.), and lines are sorted alphabetically by those attribute names - regardless of whether they're `attr_reader` or `attr_writer`.

**How It Works:**
1. The regex capture group `(...)` extracts a portion of text from each line
2. Lines are sorted based on the extracted text (not the full line)
3. The sorting order (ascending or descending) is applied to the extracted text

**Key Point:** The entire line is kept together, but the sort order is determined by what the regex capture group matches.

**Use Cases:**
- Sorting mixed attribute declarations by attribute name
- Sorting function calls by a specific argument value
- Sorting any lines where the sort key is embedded within other text

![Group Sort Example]({{ site.baseurl }}/assets/images/ruby-group.png)

---

### Split Sort

Splits each line by a delimiter pattern and sorts by a specific field (key). Perfect for CSV-like data or multi-column formats.

**Syntax:**
```
// sort: { order: <asc|desc>, split: /regex/, key: <number> }
```

**Parameters:**
- `split`: Regular expression to split each line into fields
- `key`: Zero-based index of the field to sort by

**Example:**
```javascript
// sort: { order: asc, split: /[,]/, key: 2 }
const x = value1, value2, alpha;
const y = value1, value2, beta;
const z = value1, value2, gamma;
```

This splits by `,`, then sorts by the third field (index 2).

**Use Cases:**
- Sorting variable assignments by variable name
- CSV or TSV-style data
- Multi-column configurations
- Any structured text with delimiters

![Split Sort Example]({{ site.baseurl }}/assets/images/ruby-split.png)

---

## Code Inspection

The plugin provides real-time code inspection to detect and highlight unsorted lines.

### Warning Highlights

When lines in a sort block are out of order:
- **Yellow/orange underline** appears on unsorted lines
- **Hover tooltip** explains the issue
- **Quick fix available** via `Alt+Enter` / `⌥+Return`

### Inspection Settings

Configure inspection behavior:
1. Go to **Settings** > **Editor** > **Inspections**
2. Search for **"Line order"** or navigate to **"Sort Lines with Comment"**
3. Adjust:
   - **Severity**: Error, Warning, Weak Warning, or Info
   - **Scope**: Which files the inspection applies to
   - **Enable/Disable**: Toggle the inspection on/off

---

## Quick Fixes

Quick fixes provide one-click solutions to sort issues.

### Applying Quick Fixes

When hovering over an unsorted line:
1. Click the **lightbulb icon** that appears
2. Or press **Alt+Enter** (Windows/Linux) or **⌥+Return** (Mac)
3. Select **"Sort lines"**
4. Lines are instantly reordered

---

## Gutter Icons

Visual indicators appear in the editor gutter next to sort comments to show the sort direction.

### Icon Types

Gutter icons indicate the sort order:

| Icon | Meaning |
|------|---------|
| ![Ascending]({{ site.baseurl }}/assets/images/ascSort.svg) | Ascending sort block |
| ![Descending]({{ site.baseurl }}/assets/images/descSort.svg) | Descending sort block |
| ![Unknown]({{ site.baseurl }}/assets/images/noSort.svg) | Unknown sort order |

These icons provide quick visual scanning to identify sort blocks and their direction in your files.
They're visibility can be toggled in the **Settings** > **Editor** > **Gutter Icons** menu under **Sort Lines with Comment**.

---

## Syntax Highlighting

Sort comments receive special syntax highlighting to make them stand out.

### Highlighted Elements

- **Keywords**: `order`, `group`, `split`, `key` appear in distinct colors
- **Sort order**: `asc`, `desc`, and custom keywords are highlighted
- **Regex patterns**: Regular expressions are color-coded
- **Numbers**: Key indices have number highlighting
- **Errors**: Invalid syntax appears in error colors

### Error Detection

The syntax highlighter detects:
- Invalid sort order keywords
- Malformed regex patterns
- Invalid key indices
- Syntax errors in JSON-like options

Errors are underlined in red with descriptive tooltips.

---

## Auto-completion

Get intelligent suggestions when typing sort comments.

### Completion Features

**Order Keywords:**
Type `// sort: ` and press `Ctrl+Space` to see:
- All configured ascending keywords
- All configured descending keywords

**Custom Keywords:**
All keywords configured in Settings appear in auto-completion.

![Auto-completion Example]({{ site.baseurl }}/assets/images/sort-order-completion.png)

---

## Tools Menu Action

Sort all blocks in a file with one action.

### How to Use

1. Open any file with sort comments
2. Go to **Tools** > **Sort Lines with Comment**
3. All sort blocks in the file are sorted immediately

### When to Use

- After adding multiple sort comments
- When refactoring and reorganizing code
- Before committing changes
- As an alternative to individual quick fixes

![Tools Menu]({{ site.baseurl }}/assets/images/tool-menu.png)

---

## Sort on Save

Automatically sort all blocks whenever you save a file.

### Enabling Sort on Save

1. Go to **Settings** > **Tools** > **Actions on Save**
2. Check **"Sort Lines with Comment"**
3. Click **OK**

### How It Works

Every time you save a file (`Ctrl+S` / `⌘+S`):
1. Plugin scans for all sort comments
2. Checks if each block is sorted correctly
3. Automatically reorders any unsorted blocks
4. File is saved with all blocks sorted

### Benefits

- Never commit unsorted code
- Consistent formatting across team
- No manual sorting needed
- Reduces code review noise

![Actions on Save]({{ site.baseurl }}/assets/images/actions-on-save.png)

---

## Custom Settings

Personalize the plugin to match your preferences and coding style.

### Configurable Options

#### Ascending Order Keywords

Define which keywords trigger ascending (A-Z) sorts.

**Default:** `asc, ascending, a-z`

**Examples:**
- `asc, ascending, up, ↑`
- `alphabetical, a-z, forward`
- `↑, 🔺, 🐬`

#### Descending Order Keywords

Define which keywords trigger descending (Z-A) sorts.

**Default:** `desc, descending, z-a`

**Examples:**
- `desc, descending, down, ↓`
- `reverse, z-a, backward`
- `↓, 🔻, 🦜`

### Settings Location

**Project-level Settings:**
- **Path:** Settings > Tools > Sort Lines with Comment
- **Storage:** `.idea/sortlines.xml` in your project
- Settings are per-project, allowing different teams/projects to use different keywords

### Using Custom Keywords

Once configured, your custom keywords:
- Appear in auto-completion
- Are recognized by the parser
- Work in all sort types (simple, group, split)
- Are syntax-highlighted

**Example:**
If you add `↓` as a custom keyword:
```java
// sort: ↓  ← This now works!
import java.util.List;
import java.util.Map;
```

---

## Language Support

The plugin works with file types of programming languages supported by IntelliJ-based IDEs.

### Comment Syntax Detection

The plugin automatically adapts to each language's comment syntax:

| Language | Comment Style | Example |
|----------|---------------|---------|
| Java, JavaScript, C, C++ | `//` | `// sort: asc` |
| Python, Ruby, Shell | `#` | `# sort: asc` |
| SQL | `--` | `-- sort: asc` |
| HTML, XML | `<!-- -->` | `<!-- sort: asc -->` |
| CSS | `/* */` | `/* sort: asc */` |

It won't work in plain-text files, unsupported language files, or language files that don't support comments.

---

## Block Detection

Understanding how the plugin determines where a sort block begins and ends.

### Start of Block

A sort block starts immediately after a sort comment:
```java
// sort: asc
← Block starts here
import java.util.List;
```

### End of Block

A sort block ends when the plugin encounters:

**1. Blank Line:**
```java
// sort: asc
import java.util.List;
import java.util.Map;
← Blank line ends the block
import java.util.Set;  // Not included in sort
```

**2. Indentation Change:**
```java
// sort: asc
import java.util.List;
import java.util.Map;
  import java.util.Set;  // Different indent, not included
```

**3. Another Sort Comment:**
```java
// sort: asc
import java.util.List;
import java.util.Map;
// sort: desc  ← Starts new block
import java.util.Set;
```

**4. Explicit End Marker:**
```java
// sort: asc
import java.util.List;
import java.util.Map;
// sort: end  ← Explicit end
import java.util.Set;  // Not included
```

### Multiple Blocks with `sort: end`

Multiple blocks can be sorted by one sort comment if they are separated by blank lines and closed with an `sort: end` comment
```properties
# sort: a-z
group.action=first
group.name=second
group.title=third

user.action=first
user.name=second
user.title=third

item.action=first
item.name=second
item.title=third
# sort: end
```

### Best Practices

- Use blank lines to naturally separate sort blocks
- Use `// sort: end` when you need precise control
- Maintain consistent indentation within blocks
- Avoid mixing tabs and spaces

---

## Regular Expression Support

Both group and split sort modes use regex patterns for advanced sorting.

Use standard Java regex syntax: [Pattern (Java Platform SE 8)](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)

