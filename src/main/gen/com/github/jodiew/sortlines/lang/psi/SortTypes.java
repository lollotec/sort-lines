// This is a generated file. Not intended for manual editing.
package com.github.jodiew.sortlines.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.jodiew.sortlines.lang.psi.impl.*;

public interface SortTypes {

  IElementType OPTIONS = new SortElementType("OPTIONS");

  IElementType END = new SortTokenType("END");
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
