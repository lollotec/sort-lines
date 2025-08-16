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
  // LBRACE sort_option ((COMMA group_option)|(COMMA split_option COMMA key_option))? RBRACE
  static boolean complex_options(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "complex_options")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && sort_option(b, l + 1);
    r = r && complex_options_2(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((COMMA group_option)|(COMMA split_option COMMA key_option))?
  private static boolean complex_options_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "complex_options_2")) return false;
    complex_options_2_0(b, l + 1);
    return true;
  }

  // (COMMA group_option)|(COMMA split_option COMMA key_option)
  private static boolean complex_options_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "complex_options_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = complex_options_2_0_0(b, l + 1);
    if (!r) r = complex_options_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA group_option
  private static boolean complex_options_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "complex_options_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && group_option(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA split_option COMMA key_option
  private static boolean complex_options_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "complex_options_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && split_option(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && key_option(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // GROUP COLON FSLASH pattern FSLASH
  static boolean group_option(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "group_option")) return false;
    if (!nextTokenIs(b, GROUP)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, GROUP, COLON, FSLASH);
    r = r && pattern(b, l + 1);
    r = r && consumeToken(b, FSLASH);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NUMBER
  public static boolean index(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index")) return false;
    if (!nextTokenIs(b, NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMBER);
    exit_section_(b, m, INDEX, r);
    return r;
  }

  /* ********************************************************** */
  // options
  static boolean item_(PsiBuilder b, int l) {
    return options(b, l + 1);
  }

  /* ********************************************************** */
  // KEY COLON index
  static boolean key_option(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_option")) return false;
    if (!nextTokenIs(b, KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEY, COLON);
    r = r && index(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // complex_options|sort|END
  public static boolean options(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTIONS, "<options>");
    r = complex_options(b, l + 1);
    if (!r) r = sort(b, l + 1);
    if (!r) r = consumeToken(b, END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // REGEX
  public static boolean pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern")) return false;
    if (!nextTokenIs(b, REGEX)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REGEX);
    exit_section_(b, m, PATTERN, r);
    return r;
  }

  /* ********************************************************** */
  // STRING
  public static boolean sort(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sort")) return false;
    if (!nextTokenIs(b, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRING);
    exit_section_(b, m, SORT, r);
    return r;
  }

  /* ********************************************************** */
  // item_
  static boolean sortFile(PsiBuilder b, int l) {
    return item_(b, l + 1);
  }

  /* ********************************************************** */
  // ORDER COLON sort
  static boolean sort_option(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sort_option")) return false;
    if (!nextTokenIs(b, ORDER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ORDER, COLON);
    r = r && sort(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SPLIT COLON FSLASH pattern FSLASH
  static boolean split_option(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "split_option")) return false;
    if (!nextTokenIs(b, SPLIT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SPLIT, COLON, FSLASH);
    r = r && pattern(b, l + 1);
    r = r && consumeToken(b, FSLASH);
    exit_section_(b, m, null, r);
    return r;
  }

}
