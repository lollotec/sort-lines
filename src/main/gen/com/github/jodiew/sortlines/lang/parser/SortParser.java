// This is a generated file. Not intended for manual editing.
package com.github.jodiew.sortlines.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.jodiew.sortlines.lang.psi.SortTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class SortParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return sortFile(b, l + 1);
  }

  /* ********************************************************** */
  // sortOptions|CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    if (!nextTokenIs(b, "", CRLF, ORDER)) return false;
    boolean r;
    r = sortOptions(b, l + 1);
    if (!r) r = consumeToken(b, CRLF);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean sortFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sortFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "sortFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ORDER
  public static boolean sortOptions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sortOptions")) return false;
    if (!nextTokenIs(b, ORDER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ORDER);
    exit_section_(b, m, SORT_OPTIONS, r);
    return r;
  }

}
