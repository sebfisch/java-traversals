package sebfisch.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import sebfisch.util.Traversal;

/** Mutable text representation extending string buffers with additional functionality. */
public class Text implements Cloneable, Serializable, Appendable, CharSequence {

  private final StringBuffer contents;

  /** Creates mutable empty text. */
  public Text() {
    contents = new StringBuffer();
  }

  /**
   * Creates mutable text with the given initial contents.
   *
   * @param chars initial text contents
   */
  public Text(final CharSequence chars) {
    this();
    append(chars);
  }

  /**
   * Helper function for index arithmetic. The result will be within 0..length inclusive. Negative
   * arguments are interpreted to count from the end. Out of bounds indices are mapped to the
   * closest bound, i.e., 0 or length.
   *
   * @param index index to convert
   * @return valid index
   */
  private int validIndex(final int index) {
    return Math.max(0, Math.min(length(), index < 0 ? length() + index : index));
  }

  @Override
  public Text append(final char c) {
    contents.append(c);
    return this;
  }

  @Override
  public Text append(final CharSequence cs) {
    contents.append(cs);
    return this;
  }

  @Override
  public Text append(final CharSequence cs, final int begin, final int end) {
    contents.append(cs, validIndex(begin), validIndex(end));
    return this;
  }

  /**
   * Append all given parts to this texts contents.
   *
   * @param parts multiple parts with characters to append
   * @return this text, mutated
   */
  public Text append(final Iterable<? extends CharSequence> parts) {
    parts.forEach(this::append);
    return this;
  }

  @Override
  public char charAt(final int index) {
    return contents.charAt(validIndex(index));
  }

  @Override
  public int length() {
    return contents.length();
  }

  @Override
  public Text subSequence(final int begin, final int end) {
    return new Text(contents.subSequence(validIndex(begin), validIndex(end)));
  }

  @Override
  public String toString() {
    return contents.toString();
  }

  @Override
  public Text clone() {
    return subSequence(0, length());
  }

  @Override
  public boolean equals(final Object that) {
    return (that instanceof CharSequence) && equals((CharSequence) that);
  }

  /**
   * Restricted version of equals method for character sequences.
   *
   * @param chars character sequence
   * @return true if the given sequence contains the same characters as this text
   */
  public boolean equals(final CharSequence chars) {
    return toString().equals(chars.toString());
  }

  /**
   * Deletes the text contents.
   *
   * @return this text, mutated
   */
  public Text delete() {
    return delete(0, length());
  }

  /**
   * Deletes the character at the given index.
   *
   * @param index index of character to delete
   * @return this text, mutated
   */
  public Text delete(final int index) {
    contents.deleteCharAt(validIndex(index));
    return this;
  }

  /**
   * Deletes the characters in the given range.
   *
   * @param begin index of first character to delete
   * @param end end of range to delete, exclusive
   * @return this text, mutated
   */
  public Text delete(final int begin, final int end) {
    contents.delete(validIndex(begin), validIndex(end));
    return this;
  }

  /**
   * Inserts the given characters after the characters specified by the given offset.
   *
   * @param offset number of characters after which to insert
   * @param chars characters to insert
   * @return this text, mutated
   */
  public Text insert(final int offset, final CharSequence chars) {
    contents.insert(offset, chars);
    return this;
  }

  /**
   * Replaces the character at the given index with the given character.
   *
   * @param index index of character to replace
   * @param c new character to replace old character with
   * @return this text, mutated
   */
  public Text replace(final int index, final char c) {
    contents.setCharAt(validIndex(index), c);
    return this;
  }

  /**
   * Replaces this texts contents with the given characters.
   *
   * @param chars characters to replace this texts contents with
   * @return this text, mutated
   */
  public Text replace(final CharSequence chars) {
    return replace(0, length(), chars);
  }

  /**
   * Replaces the character at the given index with the given characters.
   *
   * @param index index of character to replace
   * @param chars characters to replace the specified character with
   * @return this text, mutated
   */
  public Text replace(final int index, final CharSequence chars) {
    return replace(index, index + 1, chars);
  }

  /**
   * Replaces the characters in the specified range with the given characters.
   *
   * @param begin index of first character to replace
   * @param end end of replaced range, exclusive
   * @param chars characters to replace the specified characters with
   * @return this text, mutated
   */
  public Text replace(final int begin, final int end, final CharSequence chars) {
    final int validBegin = validIndex(begin);
    contents.delete(validBegin, validIndex(end));
    contents.insert(validBegin, chars);
    return this;
  }

  /**
   * Modifies this text to contain only characters accepted by the given predicate.
   *
   * @param pred predicate selecting kept characters
   * @return this text, mutated
   */
  public Text filter(final CharPredicate pred) {
    return filter(0, length(), pred);
  }

  /**
   * Removes the character at the given index if it fails the given predicate.
   *
   * @param index index of character to remove conditionally
   * @param pred predicate determining if the character should be kept
   * @return this text, mutated
   */
  public Text filter(final int index, final CharPredicate pred) {
    return filter(index, index + 1, pred);
  }

  /**
   * Modifies this text by filtering characters in the specified range using the gven predicate.
   *
   * @param begin index of first character in filtered range
   * @param end end of filtered range, exclusive
   * @param pred predicate selecting characters kept in range
   * @return this text, mutated
   */
  public Text filter(final int begin, final int end, final CharPredicate pred) {
    int index = validIndex(begin);
    int validEnd = validIndex(end);

    while (index < validEnd) {
      if (pred.test(charAt(index))) {
        index++;
      } else {
        delete(index);
        validEnd--;
      }
    }

    return this;
  }

  /**
   * Applies a given operator to all contained characters.
   *
   * @param op operator to apply to characters
   * @return this text, mutated
   */
  public Text map(final CharUnaryOperator op) {
    return map(0, length(), op);
  }

  /**
   * Applies a given operator to the character at the given index.
   *
   * @param index index of character to modify
   * @param op operator to apply to the character
   * @return this text, mutated
   */
  public Text map(final int index, final CharUnaryOperator op) {
    return map(index, index + 1, op);
  }

  /**
   * Applies a given operator to each character in the specified range.
   *
   * @param begin first character to modify
   * @param end end of modified range, exclusive
   * @param op operator to apply to characters
   * @return this text, mutated
   */
  public Text map(final int begin, final int end, final CharUnaryOperator op) {
    final int validEnd = validIndex(end);

    for (int index = validIndex(begin); index < validEnd; index++) {
      replace(index, op.applyAsChar(charAt(index)));
    }

    return this;
  }

  /**
   * Replaces each contained character with the characters returned by the given function.
   *
   * @param fun function computing character replacements
   * @return this text, mutated
   */
  public Text flatMap(final CharFunction<? extends CharSequence> fun) {
    return flatMap(0, length(), fun);
  }

  /**
   * Replaces the character at the given index with the characters returned by the given function.
   *
   * @param index index of character to replace
   * @param fun function computing the character replacement
   * @return this text, mutated
   */
  public Text flatMap(final int index, final CharFunction<? extends CharSequence> fun) {
    return flatMap(index, index + 1, fun);
  }

  /**
   * Replaces each character in the specified range with the characters returned by the given
   * function.
   *
   * @param begin index of first replaced character
   * @param end end of replaced range, exclusive
   * @param fun function computing character replacements
   * @return this text, mutated
   */
  public Text flatMap(
      final int begin, final int end, final CharFunction<? extends CharSequence> fun) {
    int index = validIndex(begin);
    int validEnd = validIndex(end);

    while (index < validEnd) {
      final CharSequence replacement = fun.apply(charAt(index));
      replace(index, replacement);
      index += replacement.length();
      validEnd += replacement.length() - 1;
    }

    return this;
  }

  /**
   * Groups characters in this text based on the given predicate. The result list contains non-empty
   * texts where all characters either do or do not satisfy the given predicate. For subsequent
   * texts in the result list, the predicate value alternates.
   *
   * @param pred predicate on characters used for grouping
   * @return list of text groups
   */
  public List<Text> group(final CharPredicate pred) {
    final List<Text> result = new ArrayList<>();
    Text nextGroup = new Text();
    char nextChar;
    for (int index = 0; index < length(); index++) {
      nextChar = charAt(index);
      nextGroup.append(nextChar);
      if (index + 1 < length() && pred.test(nextChar) != pred.test(charAt(index + 1))) {
        result.add(nextGroup);
        nextGroup = new Text();
      }
    }
    result.add(nextGroup);
    return result;
  }

  /**
   * Returns a traversal for grouping text. The given predicate characterizes delimiting characters.
   * Traversed groups are never empty and contain those characters between delimiters.
   *
   * @param isDelimiting predicate on characters
   * @return traversal for delimited groups
   */
  public static Traversal<Text, Text> groups(final CharPredicate isDelimiting) {
    return new Traversal.For<Text>()
        .map(text -> text.group(isDelimiting), (text, parts) -> text.delete().append(parts))
        .flatMap(parts -> parts)
        .filter(part -> !isDelimiting.test(part.charAt(0)));
  }

  /**
   * Returns a traversal for grouping text. The given string characterizes delimiting characters.
   * Traversed groups are never empty and contain those characters between delimiters.
   *
   * @param delimiters delimiting characters
   * @return traversal for delimited groups
   */
  public static Traversal<Text, Text> groups(final String delimiters) {
    return groups(c -> delimiters.contains(String.valueOf(c)));
  }

  /**
   * Returns a traversal for collecting or modifying words in text.
   *
   * @return traversal for words
   */
  public static Traversal<Text, Text> words() {
    return groups(Character::isWhitespace);
  }

  /**
   * Returns a traversal for collecting or modifying lines in text.
   *
   * @return traversal for lines
   */
  public static Traversal<Text, Text> lines() {
    final Pattern noLineTerminator = Pattern.compile(".");
    return groups(c -> !noLineTerminator.matcher(String.valueOf(c)).matches());
  }
}
