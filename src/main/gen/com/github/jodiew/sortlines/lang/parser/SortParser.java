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
  // options
  static boolean item_(PsiBuilder b, int l) {
    return options(b, l + 1);
  }

  /* ********************************************************** */
  // (LBRACE ORDER COLON SORT (COMMA GROUP COLON FSLASH PATTERN FSLASH)? RBRACE)|SORT|END
  public static boolean options(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTIONS, "<options>");
    r = options_0(b, l + 1);
    if (!r) r = consumeToken(b, SORT);
    if (!r) r = consumeToken(b, END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // LBRACE ORDER COLON SORT (COMMA GROUP COLON FSLASH PATTERN FSLASH)? RBRACE
  private static boolean options_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LBRACE, ORDER, COLON, SORT);
    r = r && options_0_4(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA GROUP COLON FSLASH PATTERN FSLASH)?
  private static boolean options_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options_0_4")) return false;
    options_0_4_0(b, l + 1);
    return true;
  }

  // COMMA GROUP COLON FSLASH PATTERN FSLASH
  private static boolean options_0_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options_0_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, GROUP, COLON, FSLASH, PATTERN, FSLASH);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_?
  static boolean sortFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sortFile")) return false;
    item_(b, l + 1);
    return true;
  }

}
