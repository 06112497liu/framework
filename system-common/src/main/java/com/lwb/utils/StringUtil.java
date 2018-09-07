package com.lwb.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static       Pattern  REPLACE_BLANK_PATTERN = Pattern.compile("\\s*|\t|\r|\n");
    static final         Splitter DEFAULT_SPLITTER      = Splitter.on(",").omitEmptyStrings();
    private static final char[]   DIGITS                = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[]   DIGITS_NOCASE         = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final Pattern  CRLF_PATTERN          = Pattern.compile("\\r|\\n|\\r\\n");
    private static final Random   RANDOM                = new Random();

    public StringUtil() {
    }

    public static int getLength(String str) {
        return str == null ? 0 : str.length();
    }

    public static boolean isEqualsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        } else {
            return str1.equalsIgnoreCase(str2);
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String defaultIfEmpty(String str, String defaultStr) {
        return str != null && str.length() != 0 ? str : defaultStr;
    }

    public static String defaultIfBlank(String str, String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        } else {
            String result = str.trim();
            return result != null && result.length() != 0 ? result : null;
        }
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static String trim(String str, String stripChars) {
        return trim(str, stripChars, 0);
    }

    public static String trimStart(String str) {
        return trim(str, (String) null, -1);
    }

    public static String trimStart(String str, String stripChars) {
        return trim(str, stripChars, -1);
    }

    public static String trimEnd(String str) {
        return trim(str, (String) null, 1);
    }

    public static String trimEnd(String str, String stripChars) {
        return trim(str, stripChars, 1);
    }

    public static String trimToNull(String str, String stripChars) {
        String result = trim(str, stripChars);
        return result != null && result.length() != 0 ? result : null;
    }

    public static String trimToEmpty(String str, String stripChars) {
        String result = trim(str, stripChars);
        return result == null ? "" : result;
    }

    private static String trim(String str, String stripChars, int mode) {
        if (str == null) {
            return null;
        } else {
            int length = str.length();
            int start = 0;
            int end = length;
            if (mode <= 0) {
                if (stripChars == null) {
                    while (start < end && Character.isWhitespace(str.charAt(start))) {
                        ++start;
                    }
                } else {
                    if (stripChars.length() == 0) {
                        return str;
                    }

                    while (start < end && stripChars.indexOf(str.charAt(start)) != -1) {
                        ++start;
                    }
                }
            }

            if (mode >= 0) {
                if (stripChars == null) {
                    while (start < end && Character.isWhitespace(str.charAt(end - 1))) {
                        --end;
                    }
                } else {
                    if (stripChars.length() == 0) {
                        return str;
                    }

                    while (start < end && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                        --end;
                    }
                }
            }

            return start <= 0 && end >= length ? str : str.substring(start, end);
        }
    }

    public static String capitalize(String str) {
        int strLen;
        return str != null && (strLen = str.length()) != 0 ? (new StringBuilder(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString() : str;
    }

    public static String uncapitalize(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            return strLen > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0)) ? str : (new StringBuilder(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
        } else {
            return str;
        }
    }

    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    public static String toCamelCase(String str) {
        return (new StringUtil.AbstractWordTokenizer() {
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            protected void startWord(StringBuilder buffer, char ch) {
                if (!this.isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(Character.toUpperCase(ch));
                } else {
                    buffer.append(Character.toLowerCase(ch));
                }

            }

            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void startDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void inDelimiter(StringBuilder buffer, char ch) {
                if (ch != '_') {
                    buffer.append(ch);
                }

            }
        }).parse(str);
    }

    public static String toPascalCase(String str) {
        return (new StringUtil.AbstractWordTokenizer() {
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            protected void startWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void startDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void inDelimiter(StringBuilder buffer, char ch) {
                if (ch != '_') {
                    buffer.append(ch);
                }

            }
        }).parse(str);
    }

    public static String toUpperCaseWithUnderscores(String str) {
        return (new StringUtil.AbstractWordTokenizer() {
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            protected void startWord(StringBuilder buffer, char ch) {
                if (!this.isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append('_');
                }

                buffer.append(Character.toUpperCase(ch));
            }

            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void startDigitWord(StringBuilder buffer, char ch) {
                if (!this.isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append('_');
                }

                buffer.append(ch);
            }

            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void inDelimiter(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }
        }).parse(str);
    }

    public static String toLowerCaseWithUnderscores(String str) {
        return (new StringUtil.AbstractWordTokenizer() {
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            protected void startWord(StringBuilder buffer, char ch) {
                if (!this.isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append('_');
                }

                buffer.append(Character.toLowerCase(ch));
            }

            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void startDigitWord(StringBuilder buffer, char ch) {
                if (!this.isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append('_');
                }

                buffer.append(ch);
            }

            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void inDelimiter(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }
        }).parse(str);
    }

    public static String[] split(String str, char separatorChar) {
        if (str == null) {
            return null;
        } else {
            int length = str.length();
            if (length == 0) {
                return new String[0];
            } else {
                List<String> list = Lists.newLinkedList();
                int i = 0;
                int start = 0;
                boolean match = false;

                while (i < length) {
                    if (str.charAt(i) == separatorChar) {
                        if (match) {
                            list.add(str.substring(start, i));
                            match = false;
                        }

                        ++i;
                        start = i;
                    } else {
                        match = true;
                        ++i;
                    }
                }

                if (match) {
                    list.add(str.substring(start, i));
                }

                return (String[]) list.toArray(new String[list.size()]);
            }
        }
    }

    public static List<String> splitAsList(String str, CharSequence sequence) {
        if (Objects.isNull(sequence)) {
            String sequence1 = ",";
        }

        return Objects.isNull(str) ? Lists.newArrayList() : Lists.newArrayList(DEFAULT_SPLITTER.split(str));
    }

    public static String[] split(String str, String separatorChars) {
        return split(str, separatorChars, -1);
    }

    public static String[] split(String str, String separatorChars, int max) {
        if (str == null) {
            return null;
        } else {
            int length = str.length();
            if (length == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                List<String> list = Lists.newLinkedList();
                int sizePlus1 = 1;
                int i = 0;
                int start = 0;
                boolean match = false;
                if (separatorChars == null) {
                    while (i < length) {
                        if (Character.isWhitespace(str.charAt(i))) {
                            if (match) {
                                if (sizePlus1++ == max) {
                                    i = length;
                                }

                                list.add(str.substring(start, i));
                                match = false;
                            }

                            ++i;
                            start = i;
                        } else {
                            match = true;
                            ++i;
                        }
                    }
                } else if (separatorChars.length() == 1) {
                    char sep = separatorChars.charAt(0);

                    while (i < length) {
                        if (str.charAt(i) == sep) {
                            if (match) {
                                if (sizePlus1++ == max) {
                                    i = length;
                                }

                                list.add(str.substring(start, i));
                                match = false;
                            }

                            ++i;
                            start = i;
                        } else {
                            match = true;
                            ++i;
                        }
                    }
                } else {
                    while (i < length) {
                        if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                            if (match) {
                                if (sizePlus1++ == max) {
                                    i = length;
                                }

                                list.add(str.substring(start, i));
                                match = false;
                            }

                            ++i;
                            start = i;
                        } else {
                            match = true;
                            ++i;
                        }
                    }
                }

                if (match) {
                    list.add(str.substring(start, i));
                }

                return (String[]) list.toArray(new String[list.size()]);
            }
        }
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int arraySize = array.length;
            int bufSize;
            if (arraySize == 0) {
                bufSize = 0;
            } else {
                int firstLength = array[0] == null ? 16 : array[0].toString().length();
                bufSize = arraySize * (firstLength + separator.length());
            }

            StringBuilder buf = new StringBuilder(bufSize);

            for (int i = 0; i < arraySize; ++i) {
                if (separator != null && i > 0) {
                    buf.append(separator);
                }

                if (array[i] != null) {
                    buf.append(array[i]);
                }
            }

            return buf.toString();
        }
    }

    public static String join(Iterable<?> list, String separator) {
        if (list == null) {
            return null;
        } else {
            StringBuilder buf = new StringBuilder(256);
            Iterator i = list.iterator();

            while (i.hasNext()) {
                Object obj = i.next();
                if (obj != null) {
                    buf.append(obj);
                }

                if (separator != null && i.hasNext()) {
                    buf.append(separator);
                }
            }

            return buf.toString();
        }
    }

    public static int indexOf(String str, char searchChar) {
        return str != null && str.length() != 0 ? str.indexOf(searchChar) : -1;
    }

    public static int indexOf(String str, char searchChar, int startPos) {
        return str != null && str.length() != 0 ? str.indexOf(searchChar, startPos) : -1;
    }

    public static int indexOf(String str, String searchStr) {
        return str != null && searchStr != null ? str.indexOf(searchStr) : -1;
    }

    public static int indexOf(String str, String searchStr, int startPos) {
        if (str != null && searchStr != null) {
            return searchStr.length() == 0 && startPos >= str.length() ? str.length() : str.indexOf(searchStr, startPos);
        } else {
            return -1;
        }
    }

    public static int indexOfAny(String str, char[] searchChars) {
        if (str != null && str.length() != 0 && searchChars != null && searchChars.length != 0) {
            for (int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);
                char[] var4 = searchChars;
                int var5 = searchChars.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    char searchChar = var4[var6];
                    if (searchChar == ch) {
                        return i;
                    }
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static int indexOfAny(String str, String searchChars) {
        if (str != null && str.length() != 0 && searchChars != null && searchChars.length() != 0) {
            for (int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);

                for (int j = 0; j < searchChars.length(); ++j) {
                    if (searchChars.charAt(j) == ch) {
                        return i;
                    }
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static int indexOfAnyBut(String str, char[] searchChars) {
        if (str != null && str.length() != 0 && searchChars != null && searchChars.length != 0) {
            label29:
            for (int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);

                for (int j = 0; j < searchChars.length; ++j) {
                    if (searchChars[j] == ch) {
                        continue label29;
                    }
                }

                return i;
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static int indexOfAnyBut(String str, String searchChars) {
        if (str != null && str.length() != 0 && searchChars != null && searchChars.length() != 0) {
            for (int i = 0; i < str.length(); ++i) {
                if (searchChars.indexOf(str.charAt(i)) < 0) {
                    return i;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static int lastIndexOf(String str, char searchChar) {
        return str != null && str.length() != 0 ? str.lastIndexOf(searchChar) : -1;
    }

    public static int lastIndexOf(String str, char searchChar, int startPos) {
        return str != null && str.length() != 0 ? str.lastIndexOf(searchChar, startPos) : -1;
    }

    public static int lastIndexOf(String str, String searchStr) {
        return str != null && searchStr != null ? str.lastIndexOf(searchStr) : -1;
    }

    public static int lastIndexOf(String str, String searchStr, int startPos) {
        return str != null && searchStr != null ? str.lastIndexOf(searchStr, startPos) : -1;
    }

    public static boolean contains(String str, char searchChar) {
        if (str != null && str.length() != 0) {
            return str.indexOf(searchChar) >= 0;
        } else {
            return false;
        }
    }

    public static boolean contains(String str, String searchStr) {
        if (str != null && searchStr != null) {
            return str.indexOf(searchStr) >= 0;
        } else {
            return false;
        }
    }

    public static boolean containsOnly(String str, char[] valid) {
        if (valid != null && str != null) {
            if (str.length() == 0) {
                return true;
            } else if (valid.length == 0) {
                return false;
            } else {
                return indexOfAnyBut(str, valid) == -1;
            }
        } else {
            return false;
        }
    }

    public static boolean containsOnly(String str, String valid) {
        return str != null && valid != null ? containsOnly(str, valid.toCharArray()) : false;
    }

    public static boolean containsNone(String str, char[] invalid) {
        if (str != null && invalid != null) {
            int strSize = str.length();
            int validSize = invalid.length;

            for (int i = 0; i < strSize; ++i) {
                char ch = str.charAt(i);

                for (int j = 0; j < validSize; ++j) {
                    if (invalid[j] == ch) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean containsNone(String str, String invalidChars) {
        return str != null && invalidChars != null ? containsNone(str, invalidChars.toCharArray()) : true;
    }

    public static int countMatches(String str, String subStr) {
        if (str != null && str.length() != 0 && subStr != null && subStr.length() != 0) {
            int count = 0;

            for (int index = 0; (index = str.indexOf(subStr, index)) != -1; index += subStr.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        } else {
            if (start < 0) {
                start += str.length();
            }

            if (start < 0) {
                start = 0;
            }

            return start > str.length() ? "" : str.substring(start);
        }
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }

            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(str.length() - len);
        }
    }

    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        } else if (len >= 0 && pos <= str.length()) {
            if (pos < 0) {
                pos = 0;
            }

            return str.length() <= pos + len ? str.substring(pos) : str.substring(pos, pos + len);
        } else {
            return "";
        }
    }

    public static String substringBefore(String str, String separator) {
        if (str != null && separator != null && str.length() != 0) {
            if (separator.length() == 0) {
                return "";
            } else {
                int pos = str.indexOf(separator);
                return pos == -1 ? str : str.substring(0, pos);
            }
        } else {
            return str;
        }
    }

    public static String substringAfter(String str, String separator) {
        if (str != null && str.length() != 0) {
            if (separator == null) {
                return "";
            } else {
                int pos = str.indexOf(separator);
                return pos == -1 ? "" : str.substring(pos + separator.length());
            }
        } else {
            return str;
        }
    }

    public static String substringBeforeLast(String str, String separator) {
        if (str != null && separator != null && str.length() != 0 && separator.length() != 0) {
            int pos = str.lastIndexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        } else {
            return str;
        }
    }

    public static String substringAfterLast(String str, String separator) {
        if (str != null && str.length() != 0) {
            if (separator != null && separator.length() != 0) {
                int pos = str.lastIndexOf(separator);
                return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
            } else {
                return "";
            }
        } else {
            return str;
        }
    }

    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag, 0);
    }

    public static String substringBetween(String str, String open, String close) {
        return substringBetween(str, open, close, 0);
    }

    public static String substringBetween(String str, String open, String close, int fromIndex) {
        if (str != null && open != null && close != null) {
            int start = str.indexOf(open, fromIndex);
            if (start != -1) {
                int end = str.indexOf(close, start + open.length());
                if (end != -1) {
                    return str.substring(start + open.length(), end);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String deleteWhitespace(String str) {
        if (str == null) {
            return null;
        } else {
            int sz = str.length();
            StringBuilder buffer = new StringBuilder(sz);

            for (int i = 0; i < sz; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    buffer.append(str.charAt(i));
                }
            }

            return buffer.toString();
        }
    }

    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    public static String replace(String text, String repl, String with, int max) {
        if (text != null && repl != null && with != null && repl.length() != 0 && max != 0) {
            StringBuilder buf = new StringBuilder(text.length());
            int start = 0;
            boolean var6 = false;

            int end;
            while ((end = text.indexOf(repl, start)) != -1) {
                buf.append(text.substring(start, end)).append(with);
                start = end + repl.length();
                --max;
                if (max == 0) {
                    break;
                }
            }

            buf.append(text.substring(start));
            return buf.toString();
        } else {
            return text;
        }
    }

    public static String replaceChar(String str, char searchChar, char replaceChar) {
        return str == null ? null : str.replace(searchChar, replaceChar);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Matcher m = REPLACE_BLANK_PATTERN.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }

    public static String overlay(String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (overlay == null) {
                overlay = "";
            }

            int len = str.length();
            if (start < 0) {
                start = 0;
            }

            if (start > len) {
                start = len;
            }

            if (end < 0) {
                end = 0;
            }

            if (end > len) {
                end = len;
            }

            if (start > end) {
                int temp = start;
                start = end;
                end = temp;
            }

            return (new StringBuilder(len + start - end + overlay.length() + 1)).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
        }
    }

    public static String repeat(String str, int repeat) {
        if (str == null) {
            return null;
        } else if (repeat <= 0) {
            return "";
        } else {
            int inputLength = str.length();
            if (repeat != 1 && inputLength != 0) {
                int outputLength = inputLength * repeat;
                switch (inputLength) {
                    case 1:
                        char ch = str.charAt(0);
                        char[] output1 = new char[outputLength];

                        for (int i = repeat - 1; i >= 0; --i) {
                            output1[i] = ch;
                        }

                        return new String(output1);
                    case 2:
                        char ch0 = str.charAt(0);
                        char ch1 = str.charAt(1);
                        char[] output2 = new char[outputLength];

                        for (int i = repeat * 2 - 2; i >= 0; --i) {
                            output2[i] = ch0;
                            output2[i + 1] = ch1;
                            --i;
                        }

                        return new String(output2);
                    default:
                        StringBuilder buf = new StringBuilder(outputLength);

                        for (int i = 0; i < repeat; ++i) {
                            buf.append(str);
                        }

                        return buf.toString();
                }
            } else {
                return str;
            }
        }
    }

    public static String chomp(String str) {
        if (str != null && str.length() != 0) {
            if (str.length() == 1) {
                char ch = str.charAt(0);
                return ch != '\r' && ch != '\n' ? str : "";
            } else {
                int lastIdx = str.length() - 1;
                char last = str.charAt(lastIdx);
                if (last == '\n') {
                    if (str.charAt(lastIdx - 1) == '\r') {
                        --lastIdx;
                    }
                } else if (last != '\r') {
                    ++lastIdx;
                }

                return str.substring(0, lastIdx);
            }
        } else {
            return str;
        }
    }

    public static String chomp(String str, String separator) {
        if (str != null && str.length() != 0 && separator != null) {
            return str.endsWith(separator) ? str.substring(0, str.length() - separator.length()) : str;
        } else {
            return str;
        }
    }

    public static String chop(String str) {
        if (str == null) {
            return null;
        } else {
            int strLen = str.length();
            if (strLen < 2) {
                return "";
            } else {
                int lastIdx = strLen - 1;
                String ret = str.substring(0, lastIdx);
                char last = str.charAt(lastIdx);
                return last == '\n' && ret.charAt(lastIdx - 1) == '\r' ? ret.substring(0, lastIdx - 1) : ret;
            }
        }
    }

    public static String reverse(String str) {
        return str != null && str.length() != 0 ? (new StringBuilder(str)).reverse().toString() : str;
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        if (str == null) {
            return null;
        } else {
            if (maxWidth < 4) {
                maxWidth = 4;
            }

            if (str.length() <= maxWidth) {
                return str;
            } else {
                if (offset > str.length()) {
                    offset = str.length();
                }

                if (str.length() - offset < maxWidth - 3) {
                    offset = str.length() - (maxWidth - 3);
                }

                if (offset <= 4) {
                    return str.substring(0, maxWidth - 3) + "...";
                } else {
                    if (maxWidth < 7) {
                        maxWidth = 7;
                    }

                    return offset + maxWidth - 3 < str.length() ? "..." + abbreviate(str.substring(offset), maxWidth - 3) : "..." + str.substring(str.length() - (maxWidth - 3));
                }
            }
        }
    }

    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        } else if (str2 == null) {
            return str1;
        } else {
            int index = indexOfDifference(str1, str2);
            return index == -1 ? "" : str2.substring(index);
        }
    }

    public static int indexOfDifference(String str1, String str2) {
        if (str1 != null && str2 != null && !str1.equals(str2)) {
            int i;
            for (i = 0; i < str1.length() && i < str2.length() && str1.charAt(i) == str2.charAt(i); ++i) {
                ;
            }

            return i >= str2.length() && i >= str1.length() ? -1 : i;
        } else {
            return -1;
        }
    }

    public static String longToString(long longValue) {
        return longToString(longValue, false);
    }

    public static String longToString(long longValue, boolean noCase) {
        char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
        int digitsLength = digits.length;
        if (longValue == 0L) {
            return String.valueOf(digits[0]);
        } else {
            if (longValue < 0L) {
                longValue = -longValue;
            }

            StringBuilder strValue = new StringBuilder();

            while (longValue != 0L) {
                int digit = (int) (longValue % (long) digitsLength);
                longValue /= (long) digitsLength;
                strValue.append(digits[digit]);
            }

            return strValue.toString();
        }
    }

    public static String bytesToString(byte[] bytes) {
        return bytesToString(bytes, false);
    }

    public static String bytesToString(byte[] bytes, boolean noCase) {
        char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
        int digitsLength = digits.length;
        if (ArrayUtils.isEmpty(bytes)) {
            return String.valueOf(digits[0]);
        } else {
            StringBuilder strValue = new StringBuilder();
            int value = 0;
            int limit = 8388607;
            int i = 0;

            while (true) {
                while (i >= bytes.length || value >= limit) {
                    while (value >= digitsLength) {
                        strValue.append(digits[value % digitsLength]);
                        value /= digitsLength;
                    }

                    if (i >= bytes.length) {
                        if (value != 0 || strValue.length() == 0) {
                            strValue.append(digits[value]);
                        }

                        return strValue.toString();
                    }
                }

                value = (value << 8) + (255 & bytes[i++]);
            }
        }
    }

    public static String removePrefix(String str, String prefix) {
        if (str == null) {
            return null;
        } else {
            return str.startsWith(prefix) ? str.substring(prefix.length()) : str;
        }
    }

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        } else {
            int i = str.lastIndexOf(46);
            return i >= 0 ? str.substring(i + 1) : null;
        }
    }

    public static boolean contains(String str, String... keywords) {
        if (str == null) {
            return false;
        } else if (keywords == null) {
            throw new IllegalArgumentException("'keywords' must be not null");
        } else {
            String[] var2 = keywords;
            int var3 = keywords.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String keyword = var2[var4];
                if (str.contains(keyword.toLowerCase())) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String removeSeperatorFirstLetterUpperCase(String str, String seperator) {
        String[] strs = str.toLowerCase().split(seperator);
        String result = "";
        String preStr = "";

        for (int i = 0; i < strs.length; ++i) {
            if (preStr.length() == 1) {
                result = result + strs[i];
            } else {
                result = result + capitalize(strs[i]);
            }

            preStr = strs[i];
        }

        return result;
    }

    public static String randomNumeric(int count) {
        return random(count, false, true);
    }

    public static String randomString(int count) {
        return random(count, true, false);
    }

    public static String random(int count) {
        return random(count, true, false);
    }

    public static String random(int count, boolean letters, boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }

    private static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return random(count, start, end, letters, numbers, (char[]) null, RANDOM);
    }

    private static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        } else {
            if (start == 0 && end == 0) {
                end = 123;
                start = 32;
                if (!letters && !numbers) {
                    start = 0;
                    end = 2147483647;
                }
            }

            char[] buffer = new char[count];
            int gap = end - start;

            while (true) {
                while (true) {
                    while (count-- != 0) {
                        char ch;
                        if (chars == null) {
                            ch = (char) (random.nextInt(gap) + start);
                        } else {
                            ch = chars[random.nextInt(gap) + start];
                        }

                        if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
                            if (ch >= '\udc00' && ch <= '\udfff') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = ch;
                                    --count;
                                    buffer[count] = (char) ('\ud800' + random.nextInt(128));
                                }
                            } else if (ch >= '\ud800' && ch <= '\udb7f') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = (char) ('\udc00' + random.nextInt(128));
                                    --count;
                                    buffer[count] = ch;
                                }
                            } else if (ch >= '\udb80' && ch <= '\udbff') {
                                ++count;
                            } else {
                                buffer[count] = ch;
                            }
                        } else {
                            ++count;
                        }
                    }

                    return new String(buffer);
                }
            }
        }
    }

    public static String toUnderscoreName(String name) {
        return addSeperatorFirstLetterUpperCase(name, "_");
    }

    public static String addSeperatorFirstLetterUpperCase(String name, String seperator) {
        if (name == null) {
            return null;
        } else {
            String filteredName = name;
            if (name.equals(name.toUpperCase())) {
                filteredName = name.toUpperCase();
            }

            StringBuffer result = new StringBuffer();
            if (filteredName != null && filteredName.length() > 0) {
                result.append(filteredName.substring(0, 1).toLowerCase());

                for (int i = 1; i < filteredName.length(); ++i) {
                    String preChart = filteredName.substring(i - 1, i);
                    String c = filteredName.substring(i, i + 1);
                    if (c.equals(seperator)) {
                        result.append(seperator);
                    } else if (preChart.equals(seperator)) {
                        result.append(c.toLowerCase());
                    } else if (c.matches("\\d")) {
                        result.append(c);
                    } else if (c.equals(c.toUpperCase())) {
                        result.append(seperator);
                        result.append(c.toLowerCase());
                    } else {
                        result.append(c);
                    }
                }
            }

            return result.toString();
        }
    }

    public static String changeIDCard(String idCard) {
        return isNotBlank(idCard) ? idCard.replace("x", "X") : null;
    }

    private abstract static class AbstractWordTokenizer {
        protected static final char UNDERSCORE = '_';

        private AbstractWordTokenizer() {
        }

        public String parse(String str) {
            if (StringUtil.isEmpty(str)) {
                return str;
            } else {
                int length = str.length();
                StringBuilder buffer = new StringBuilder(length);

                for (int index = 0; index < length; ++index) {
                    char ch = str.charAt(index);
                    if (!Character.isWhitespace(ch)) {
                        if (!Character.isUpperCase(ch)) {
                            if (Character.isLowerCase(ch)) {
                                index = this.parseLowerCaseWord(buffer, str, index);
                            } else if (Character.isDigit(ch)) {
                                index = this.parseDigitWord(buffer, str, index);
                            } else {
                                this.inDelimiter(buffer, ch);
                            }
                        } else {
                            int wordIndex;
                            for (wordIndex = index + 1; wordIndex < length; ++wordIndex) {
                                char wordChar = str.charAt(wordIndex);
                                if (!Character.isUpperCase(wordChar)) {
                                    if (Character.isLowerCase(wordChar)) {
                                        --wordIndex;
                                    }
                                    break;
                                }
                            }

                            if (wordIndex != length && wordIndex <= index) {
                                index = this.parseTitleCaseWord(buffer, str, index);
                            } else {
                                index = this.parseUpperCaseWord(buffer, str, index, wordIndex);
                            }
                        }
                    }
                }

                return buffer.toString();
            }
        }

        private int parseUpperCaseWord(StringBuilder buffer, String str, int index, int length) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startSentence(buffer, ch);
            } else {
                this.startWord(buffer, ch);
            }

            while (index < length) {
                ch = str.charAt(index);
                this.inWord(buffer, ch);
                ++index;
            }

            return index - 1;
        }

        private int parseLowerCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startSentence(buffer, ch);
            } else {
                this.startWord(buffer, ch);
            }

            for (int length = str.length(); index < length; ++index) {
                ch = str.charAt(index);
                if (!Character.isLowerCase(ch)) {
                    break;
                }

                this.inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseTitleCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startSentence(buffer, ch);
            } else {
                this.startWord(buffer, ch);
            }

            for (int length = str.length(); index < length; ++index) {
                ch = str.charAt(index);
                if (!Character.isLowerCase(ch)) {
                    break;
                }

                this.inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseDigitWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startDigitSentence(buffer, ch);
            } else {
                this.startDigitWord(buffer, ch);
            }

            for (int length = str.length(); index < length; ++index) {
                ch = str.charAt(index);
                if (!Character.isDigit(ch)) {
                    break;
                }

                this.inDigitWord(buffer, ch);
            }

            return index - 1;
        }

        protected boolean isDelimiter(char ch) {
            return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
        }

        protected abstract void startSentence(StringBuilder var1, char var2);

        protected abstract void startWord(StringBuilder var1, char var2);

        protected abstract void inWord(StringBuilder var1, char var2);

        protected abstract void startDigitSentence(StringBuilder var1, char var2);

        protected abstract void startDigitWord(StringBuilder var1, char var2);

        protected abstract void inDigitWord(StringBuilder var1, char var2);

        protected abstract void inDelimiter(StringBuilder var1, char var2);
    }
}
