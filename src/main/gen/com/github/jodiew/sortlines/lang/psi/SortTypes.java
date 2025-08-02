// This is a generated file. Not intended for manual editing.
package com.github.jodiew.sortlines.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.jodiew.sortlines.lang.psi.impl.*;

public interface SortTypes {

  IElementType OPTIONS = new SortElementType("OPTIONS");

  IElementType COLON = new SortTokenType("COLON");
  IElementType COMMA = new SortTokenType("COMMA");
  IElementType END = new SortTokenType("END");
  IElementType FSLASH = new SortTokenType("FSLASH");
  IElementType GROUP = new SortTokenType("GROUP");
  IElementType LBRACE = new SortTokenType("LBRACE");
  IElementType ORDER = new SortTokenType("ORDER");
  IElementType PATTERN = new SortTokenType("PATTERN");
  IElementType RBRACE = new SortTokenType("RBRACE");
  IElementType SORT = new SortTokenType("SORT");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == OPTIONS) {
        return new SortOptionsImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
