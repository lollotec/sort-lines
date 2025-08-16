// This is a generated file. Not intended for manual editing.
package com.github.jodiew.sortlines.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.jodiew.sortlines.lang.psi.impl.*;

public interface SortTypes {

  IElementType INDEX = new SortElementType("INDEX");
  IElementType OPTIONS = new SortElementType("OPTIONS");
  IElementType PATTERN = new SortElementType("PATTERN");
  IElementType SORT = new SortElementType("SORT");

  IElementType COLON = new SortTokenType("COLON");
  IElementType COMMA = new SortTokenType("COMMA");
  IElementType END = new SortTokenType("END");
  IElementType FSLASH = new SortTokenType("FSLASH");
  IElementType GROUP = new SortTokenType("GROUP");
  IElementType KEY = new SortTokenType("KEY");
  IElementType LBRACE = new SortTokenType("LBRACE");
  IElementType NUMBER = new SortTokenType("NUMBER");
  IElementType ORDER = new SortTokenType("ORDER");
  IElementType RBRACE = new SortTokenType("RBRACE");
  IElementType REGEX = new SortTokenType("REGEX");
  IElementType SPLIT = new SortTokenType("SPLIT");
  IElementType STRING = new SortTokenType("STRING");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == INDEX) {
        return new SortIndexImpl(node);
      }
      else if (type == OPTIONS) {
        return new SortOptionsImpl(node);
      }
      else if (type == PATTERN) {
        return new SortPatternImpl(node);
      }
      else if (type == SORT) {
        return new SortSortImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
