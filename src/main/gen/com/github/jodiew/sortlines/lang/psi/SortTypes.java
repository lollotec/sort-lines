// This is a generated file. Not intended for manual editing.
package com.github.jodiew.sortlines.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.jodiew.sortlines.lang.psi.impl.*;

public interface SortTypes {

  IElementType SORT_OPTIONS = new SortElementType("SORT_OPTIONS");

  IElementType COLON = new SortTokenType(":");
  IElementType COMMA = new SortTokenType(",");
  IElementType CRLF = new SortTokenType("CRLF");
  IElementType LBRACE = new SortTokenType("{");
  IElementType ORDER = new SortTokenType("ORDER");
  IElementType RBRACE = new SortTokenType("}");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == SORT_OPTIONS) {
        return new SortSortOptionsImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
