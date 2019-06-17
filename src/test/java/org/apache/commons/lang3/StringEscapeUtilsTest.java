/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityEscaper;
import org.junit.Test;

/**
 * Unit tests for {@link StringEscapeUtils}.
 */
@Deprecated
public class StringEscapeUtilsTest {
    private static final String FOO = "foo";

    @Test
    public void testConstructor() {
        assertNotNull(new StringEscapeUtils());
        final Constructor<?>[] cons = StringEscapeUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(StringEscapeUtils.class.getModifiers()));
        assertFalse(Modifier.isFinal(StringEscapeUtils.class.getModifiers()));
    }

    @Test
    public void testEscapeJava() throws IOException {
        assertNull(StringEscapeUtils.escapeJava(null));
        try {
            StringEscapeUtils.ESCAPE_JAVA.translate(null, null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            StringEscapeUtils.ESCAPE_JAVA.translate("", null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }

        assertEscapeJava("empty string", "", "");
        assertEscapeJava(FOO, FOO);
        assertEscapeJava("tab", "\\t", "\t");
        assertEscapeJava("backslash", "\\\\", "\\");
        assertEscapeJava("single quote should not be escaped", "'", "'");
        assertEscapeJava("\\\\\\b\\t\\r", "\\\b\t\r");
        assertEscapeJava("\\u1234", "\u1234");
        assertEscapeJava("\\u0234", "\u0234");
        assertEscapeJava("\\u00EF", "\u00ef");
        assertEscapeJava("\\u0001", "\u0001");
        assertEscapeJava("Should use capitalized Unicode hex", "\\uABCD", "\uabcd");

        assertEscapeJava("He didn't say, \\\"stop!\\\"",
                "He didn't say, \"stop!\"");
        assertEscapeJava("non-breaking space", "This space is non-breaking:" + "\\u00A0",
                "This space is non-breaking:\u00a0");
        assertEscapeJava("\\uABCD\\u1234\\u012C",
                "\uABCD\u1234\u012C");
    }

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-421
     */
    @Test
    public void testEscapeJavaWithSlash() {
        final String input = "String with a slash (/) in it";

        final String expected = input;
        final String actual = StringEscapeUtils.escapeJava(input);

        /**
         * In 2.4 StringEscapeUtils.escapeJava(String) escapes '/' characters, which are not a valid character to escape
         * in a Java string.
         */
        assertEquals(expected, actual);
    }

    private void assertEscapeJava(final String escaped, final String original) throws IOException {
        assertEscapeJava(null, escaped, original);
    }

    private void assertEscapeJava(String message, final String expected, final String original) throws IOException {
        final String converted = StringEscapeUtils.escapeJava(original);
        message = "escapeJava(String) failed" + (message == null ? "" : (": " + message));
        assertEquals(message, expected, converted);

        final StringWriter writer = new StringWriter();
        StringEscapeUtils.ESCAPE_JAVA.translate(original, writer);
        assertEquals(expected, writer.toString());
    }

    @Test
    public void testUnescapeJava() throws IOException {
        assertNull(StringEscapeUtils.unescapeJava(null));
        try {
            StringEscapeUtils.UNESCAPE_JAVA.translate(null, null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            StringEscapeUtils.UNESCAPE_JAVA.translate("", null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            StringEscapeUtils.unescapeJava("\\u02-3");
            fail();
        } catch (final RuntimeException ex) {
        }

        assertUnescapeJava("", "");
        assertUnescapeJava("test", "test");
        assertUnescapeJava("\ntest\b", "\\ntest\\b");
        assertUnescapeJava("\u123425foo\ntest\b", "\\u123425foo\\ntest\\b");
        assertUnescapeJava("'\foo\teste\r", "\\'\\foo\\teste\\r");
        assertUnescapeJava("", "\\");
        //foo
        assertUnescapeJava("lowercase Unicode", "\uABCDx", "\\uabcdx");
        assertUnescapeJava("uppercase Unicode", "\uABCDx", "\\uABCDx");
        assertUnescapeJava("Unicode as final character", "\uABCD", "\\uabcd");
    }

    private void assertUnescapeJava(final String unescaped, final String original) throws IOException {
        assertUnescapeJava(null, unescaped, original);
    }

    private void assertUnescapeJava(final String message, final String unescaped, final String original) throws IOException {
        final String expected = unescaped;
        final String actual = StringEscapeUtils.unescapeJava(original);

        assertEquals("unescape(String) failed" +
                (message == null ? "" : (": " + message)) +
                ": expected '" + StringEscapeUtils.escapeJava(expected) +
                // we escape this so we can see it in the error message
                "' actual '" + StringEscapeUtils.escapeJava(actual) + "'",
                expected, actual);

        final StringWriter writer = new StringWriter();
        StringEscapeUtils.UNESCAPE_JAVA.translate(original, writer);
        assertEquals(unescaped, writer.toString());

    }

    @Test
    public void testEscapeEcmaScript() {
        assertNull(StringEscapeUtils.escapeEcmaScript(null));
        try {
            StringEscapeUtils.ESCAPE_ECMASCRIPT.translate(null, null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            StringEscapeUtils.ESCAPE_ECMASCRIPT.translate("", null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }

        assertEquals("He didn\\'t say, \\\"stop!\\\"", StringEscapeUtils.escapeEcmaScript("He didn't say, \"stop!\""));
        assertEquals("document.getElementById(\\\"test\\\").value = \\'<script>alert(\\'aaa\\');<\\/script>\\';",
                StringEscapeUtils.escapeEcmaScript("document.getElementById(\"test\").value = '<script>alert('aaa');</script>';"));
    }

    @Test
    public void testUnescapeEcmaScript() {
        assertNull(StringEscapeUtils.escapeEcmaScript(null));
        try {
            StringEscapeUtils.UNESCAPE_ECMASCRIPT.translate(null, null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            StringEscapeUtils.UNESCAPE_ECMASCRIPT.translate("", null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }

        assertEquals("He didn't say, \"stop!\"", StringEscapeUtils.unescapeEcmaScript("He didn\\'t say, \\\"stop!\\\""));
        assertEquals("document.getElementById(\"test\").value = '<script>alert('aaa');</script>';",
                StringEscapeUtils.unescapeEcmaScript("document.getElementById(\\\"test\\\").value = \\'<script>alert(\\'aaa\\');<\\/script>\\';"));
    }


    // HTML and XML
    //--------------------------------------------------------------

    private static final String[][] HTML_ESCAPES = {
        {"no escaping", "plain text", "plain text"},
        {"no escaping", "plain text", "plain text"},
        {"empty string", "", ""},
        {"null", null, null},
        {"ampersand", "bread &amp; butter", "bread & butter"},
        {"quotes", "&quot;bread&quot; &amp; butter", "\"bread\" & butter"},
        {"final character only", "greater than &gt;", "greater than >"},
        {"first character only", "&lt; less than", "< less than"},
        {"apostrophe", "Huntington's chorea", "Huntington's chorea"},
        {"languages", "English,Fran&ccedil;ais,\u65E5\u672C\u8A9E (nihongo)", "English,Fran\u00E7ais,\u65E5\u672C\u8A9E (nihongo)"},
        {"8-bit ascii shouldn't number-escape", "\u0080\u009F", "\u0080\u009F"},
    };

    @Test
    public void testEscapeHtml() {
        for (final String[] element : HTML_ESCAPES) {
            final String message = element[0];
            final String expected = element[1];
            final String original = element[2];
            assertEquals(message, expected, StringEscapeUtils.escapeHtml4(original));
            final StringWriter sw = new StringWriter();
            try {
                StringEscapeUtils.ESCAPE_HTML4.translate(original, sw);
            } catch (final IOException e) {
            }
            final String actual = original == null ? null : sw.toString();
            assertEquals(message, expected, actual);
        }
    }

    @Test
    public void testUnescapeHtml4() {
        for (final String[] element : HTML_ESCAPES) {
            final String message = element[0];
            final String expected = element[2];
            final String original = element[1];
            assertEquals(message, expected, StringEscapeUtils.unescapeHtml4(original));

            final StringWriter sw = new StringWriter();
            try {
                StringEscapeUtils.UNESCAPE_HTML4.translate(original, sw);
            } catch (final IOException e) {
            }
            final String actual = original == null ? null : sw.toString();
            assertEquals(message, expected, actual);
        }
        // \u00E7 is a cedilla (c with wiggle under)
        // note that the test string must be 7-bit-clean (Unicode escaped) or else it will compile incorrectly
        // on some locales
        assertEquals("funny chars pass through OK", "Fran\u00E7ais", StringEscapeUtils.unescapeHtml4("Fran\u00E7ais"));

        assertEquals("Hello&;World", StringEscapeUtils.unescapeHtml4("Hello&;World"));
        assertEquals("Hello&#;World", StringEscapeUtils.unescapeHtml4("Hello&#;World"));
        assertEquals("Hello&# ;World", StringEscapeUtils.unescapeHtml4("Hello&# ;World"));
        assertEquals("Hello&##;World", StringEscapeUtils.unescapeHtml4("Hello&##;World"));
    }

    @Test
    public void testUnescapeHexCharsHtml() {
        // Simple easy to grok test
        assertEquals("hex number unescape", "\u0080\u009F", StringEscapeUtils.unescapeHtml4("&#x80;&#x9F;"));
        assertEquals("hex number unescape", "\u0080\u009F", StringEscapeUtils.unescapeHtml4("&#X80;&#X9F;"));
        // Test all Character values:
        for (char i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
            final Character c1 = new Character(i);
            final Character c2 = new Character((char)(i+1));
            final String expected = c1.toString() + c2.toString();
            final String escapedC1 = "&#x" + Integer.toHexString((c1.charValue())) + ";";
            final String escapedC2 = "&#x" + Integer.toHexString((c2.charValue())) + ";";
            assertEquals("hex number unescape index " + (int)i, expected, StringEscapeUtils.unescapeHtml4(escapedC1 + escapedC2));
        }
    }

    @Test
    public void testUnescapeUnknownEntity() throws Exception {
        assertEquals("&zzzz;", StringEscapeUtils.unescapeHtml4("&zzzz;"));
    }

    @Test
    public void testEscapeHtmlVersions() throws Exception {
        assertEquals("&Beta;", StringEscapeUtils.escapeHtml4("\u0392"));
        assertEquals("\u0392", StringEscapeUtils.unescapeHtml4("&Beta;"));

        // TODO: refine API for escaping/unescaping specific HTML versions
    }

    @Test
    public void testEscapeXml() throws Exception {
        assertEquals("&lt;abc&gt;", StringEscapeUtils.escapeXml("<abc>"));
        assertEquals("<abc>", StringEscapeUtils.unescapeXml("&lt;abc&gt;"));

        assertEquals("XML should not escape >0x7f values",
                "\u00A1", StringEscapeUtils.escapeXml("\u00A1"));
        assertEquals("XML should be able to unescape >0x7f values",
                "\u00A0", StringEscapeUtils.unescapeXml("&#160;"));
        assertEquals("XML should be able to unescape >0x7f values with one leading 0",
                "\u00A0", StringEscapeUtils.unescapeXml("&#0160;"));
        assertEquals("XML should be able to unescape >0x7f values with two leading 0s",
                "\u00A0", StringEscapeUtils.unescapeXml("&#00160;"));
        assertEquals("XML should be able to unescape >0x7f values with three leading 0s",
                "\u00A0", StringEscapeUtils.unescapeXml("&#000160;"));

        assertEquals("ain't", StringEscapeUtils.unescapeXml("ain&apos;t"));
        assertEquals("ain&apos;t", StringEscapeUtils.escapeXml("ain't"));
        assertEquals("", StringEscapeUtils.escapeXml(""));
        assertNull(StringEscapeUtils.escapeXml(null));
        assertNull(StringEscapeUtils.unescapeXml(null));

        StringWriter sw = new StringWriter();
        try {
            StringEscapeUtils.ESCAPE_XML.translate("<abc>", sw);
        } catch (final IOException e) {
        }
        assertEquals("XML was escaped incorrectly", "&lt;abc&gt;", sw.toString() );

        sw = new StringWriter();
        try {
            StringEscapeUtils.UNESCAPE_XML.translate("&lt;abc&gt;", sw);
        } catch (final IOException e) {
        }
        assertEquals("XML was unescaped incorrectly", "<abc>", sw.toString() );
    }

    @Test
    public void testEscapeXml10() throws Exception {
        assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", StringEscapeUtils.escapeXml10("a<b>c\"d'e&f"));
        assertEquals("XML 1.0 should not escape \t \n \r",
                "a\tb\rc\nd", StringEscapeUtils.escapeXml10("a\tb\rc\nd"));
        assertEquals("XML 1.0 should omit most #x0-x8 | #xb | #xc | #xe-#x19",
                "ab", StringEscapeUtils.escapeXml10("a\u0000\u0001\u0008\u000b\u000c\u000e\u001fb"));
        assertEquals("XML 1.0 should omit #xd800-#xdfff",
                "a\ud7ff  \ue000b", StringEscapeUtils.escapeXml10("a\ud7ff\ud800 \udfff \ue000b"));
        assertEquals("XML 1.0 should omit #xfffe | #xffff",
                "a\ufffdb", StringEscapeUtils.escapeXml10("a\ufffd\ufffe\uffffb"));
        assertEquals("XML 1.0 should escape #x7f-#x84 | #x86 - #x9f, for XML 1.1 compatibility",
                "a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b", StringEscapeUtils.escapeXml10("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"));
    }

    @Test
    public void testEscapeXml11() throws Exception {
        assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", StringEscapeUtils.escapeXml11("a<b>c\"d'e&f"));
        assertEquals("XML 1.1 should not escape \t \n \r",
                "a\tb\rc\nd", StringEscapeUtils.escapeXml11("a\tb\rc\nd"));
        assertEquals("XML 1.1 should omit #x0",
                "ab", StringEscapeUtils.escapeXml11("a\u0000b"));
        assertEquals("XML 1.1 should escape #x1-x8 | #xb | #xc | #xe-#x19",
                "a&#1;&#8;&#11;&#12;&#14;&#31;b", StringEscapeUtils.escapeXml11("a\u0001\u0008\u000b\u000c\u000e\u001fb"));
        assertEquals("XML 1.1 should escape #x7F-#x84 | #x86-#x9F",
                "a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b", StringEscapeUtils.escapeXml11("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"));
        assertEquals("XML 1.1 should omit #xd800-#xdfff",
                "a\ud7ff  \ue000b", StringEscapeUtils.escapeXml11("a\ud7ff\ud800 \udfff \ue000b"));
        assertEquals("XML 1.1 should omit #xfffe | #xffff",
                "a\ufffdb", StringEscapeUtils.escapeXml11("a\ufffd\ufffe\uffffb"));
    }

    /**
     * Tests Supplementary characters.
     * <p>
     * From http://www.w3.org/International/questions/qa-escapes
     * </p>
     * <blockquote>
     * Supplementary characters are those Unicode characters that have code points higher than the characters in
     * the Basic Multilingual Plane (BMP). In UTF-16 a supplementary character is encoded using two 16-bit surrogate code points from the
     * BMP. Because of this, some people think that supplementary characters need to be represented using two escapes, but this is incorrect
     * - you must use the single, code point value for that character. For example, use &amp;&#35;x233B4&#59; rather than
     * &amp;&#35;xD84C&#59;&amp;&#35;xDFB4&#59;.
     * </blockquote>
     * @see <a href="http://www.w3.org/International/questions/qa-escapes">Using character escapes in markup and CSS</a>
     * @see <a href="https://issues.apache.org/jira/browse/LANG-728">LANG-728</a>
     */
    @Test
    public void testEscapeXmlSupplementaryCharacters() {
        final CharSequenceTranslator escapeXml =
            StringEscapeUtils.ESCAPE_XML.with( NumericEntityEscaper.between(0x7f, Integer.MAX_VALUE) );

        assertEquals("Supplementary character must be represented using a single escape", "&#144308;",
                escapeXml.translate("\uD84C\uDFB4"));

        assertEquals("Supplementary characters mixed with basic characters should be encoded correctly", "a b c &#144308;",
                        escapeXml.translate("a b c \uD84C\uDFB4"));
    }

    @Test
    public void testEscapeXmlAllCharacters() {
        // http://www.w3.org/TR/xml/#charsets says:
        // Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF] /* any Unicode character,
        // excluding the surrogate blocks, FFFE, and FFFF. */
        final CharSequenceTranslator escapeXml = StringEscapeUtils.ESCAPE_XML
                .with(NumericEntityEscaper.below(9), NumericEntityEscaper.between(0xB, 0xC), NumericEntityEscaper.between(0xE, 0x19),
                        NumericEntityEscaper.between(0xD800, 0xDFFF), NumericEntityEscaper.between(0xFFFE, 0xFFFF), NumericEntityEscaper.above(0x110000));

        assertEquals("&#0;&#1;&#2;&#3;&#4;&#5;&#6;&#7;&#8;", escapeXml.translate("\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008"));
        assertEquals("\t", escapeXml.translate("\t")); // 0x9
        assertEquals("\n", escapeXml.translate("\n")); // 0xA
        assertEquals("&#11;&#12;", escapeXml.translate("\u000B\u000C"));
        assertEquals("\r", escapeXml.translate("\r")); // 0xD
        assertEquals("Hello World! Ain&apos;t this great?", escapeXml.translate("Hello World! Ain't this great?"));
        assertEquals("&#14;&#15;&#24;&#25;", escapeXml.translate("\u000E\u000F\u0018\u0019"));
    }

    /**
     * Reverse of the above.
     *
     * @see <a href="https://issues.apache.org/jira/browse/LANG-729">LANG-729</a>
     */
    @Test
    public void testUnescapeXmlSupplementaryCharacters() {
        assertEquals("Supplementary character must be represented using a single escape", "\uD84C\uDFB4",
                StringEscapeUtils.unescapeXml("&#144308;") );

        assertEquals("Supplementary characters mixed with basic characters should be decoded correctly", "a b c \uD84C\uDFB4",
                StringEscapeUtils.unescapeXml("a b c &#144308;") );
    }

    // Tests issue #38569
    // http://issues.apache.org/bugzilla/show_bug.cgi?id=38569
    @Test
    public void testStandaloneAmphersand() {
        assertEquals("<P&O>", StringEscapeUtils.unescapeHtml4("&lt;P&O&gt;"));
        assertEquals("test & <", StringEscapeUtils.unescapeHtml4("test & &lt;"));
        assertEquals("<P&O>", StringEscapeUtils.unescapeXml("&lt;P&O&gt;"));
        assertEquals("test & <", StringEscapeUtils.unescapeXml("test & &lt;"));
    }

    @Test
    public void testLang313() {
        assertEquals("& &", StringEscapeUtils.unescapeHtml4("& &amp;"));
    }

    @Test
    public void testEscapeCsvString() throws Exception {
        assertEquals("foo.bar",            StringEscapeUtils.escapeCsv("foo.bar"));
        assertEquals("\"foo,bar\"",        StringEscapeUtils.escapeCsv("foo,bar"));
        assertEquals("\"foo\nbar\"",       StringEscapeUtils.escapeCsv("foo\nbar"));
        assertEquals("\"foo\rbar\"",       StringEscapeUtils.escapeCsv("foo\rbar"));
        assertEquals("\"foo\"\"bar\"",     StringEscapeUtils.escapeCsv("foo\"bar"));
        assertEquals("foo\uD84C\uDFB4bar", StringEscapeUtils.escapeCsv("foo\uD84C\uDFB4bar"));
        assertEquals("",   StringEscapeUtils.escapeCsv(""));
        assertNull(StringEscapeUtils.escapeCsv(null));
    }

    @Test
    public void testEscapeCsvWriter() throws Exception {
        checkCsvEscapeWriter("foo.bar",            "foo.bar");
        checkCsvEscapeWriter("\"foo,bar\"",        "foo,bar");
        checkCsvEscapeWriter("\"foo\nbar\"",       "foo\nbar");
        checkCsvEscapeWriter("\"foo\rbar\"",       "foo\rbar");
        checkCsvEscapeWriter("\"foo\"\"bar\"",     "foo\"bar");
        checkCsvEscapeWriter("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar");
        checkCsvEscapeWriter("", null);
        checkCsvEscapeWriter("", "");
    }

    private void checkCsvEscapeWriter(final String expected, final String value) {
        try {
            final StringWriter writer = new StringWriter();
            StringEscapeUtils.ESCAPE_CSV.translate(value, writer);
            assertEquals(expected, writer.toString());
        } catch (final IOException e) {
            fail("Threw: " + e);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testEscapeCsvIllegalStateException() throws IOException {
        final StringWriter writer = new StringWriter();
        StringEscapeUtils.ESCAPE_CSV.translate("foo", -1, writer);
    }

    @Test
    public void testUnescapeCsvString() throws Exception {
        assertEquals("foo.bar",              StringEscapeUtils.unescapeCsv("foo.bar"));
        assertEquals("foo,bar",              StringEscapeUtils.unescapeCsv("\"foo,bar\""));
        assertEquals("foo\nbar",             StringEscapeUtils.unescapeCsv("\"foo\nbar\""));
        assertEquals("foo\rbar",             StringEscapeUtils.unescapeCsv("\"foo\rbar\""));
        assertEquals("foo\"bar",             StringEscapeUtils.unescapeCsv("\"foo\"\"bar\""));
        assertEquals("foo\uD84C\uDFB4bar",   StringEscapeUtils.unescapeCsv("foo\uD84C\uDFB4bar"));
        assertEquals("",   StringEscapeUtils.unescapeCsv(""));
        assertNull(StringEscapeUtils.unescapeCsv(null));

        assertEquals("\"foo.bar\"",          StringEscapeUtils.unescapeCsv("\"foo.bar\""));
    }

    @Test
    public void testUnescapeCsvWriter() throws Exception {
        checkCsvUnescapeWriter("foo.bar",            "foo.bar");
        checkCsvUnescapeWriter("foo,bar",            "\"foo,bar\"");
        checkCsvUnescapeWriter("foo\nbar",           "\"foo\nbar\"");
        checkCsvUnescapeWriter("foo\rbar",           "\"foo\rbar\"");
        checkCsvUnescapeWriter("foo\"bar",           "\"foo\"\"bar\"");
        checkCsvUnescapeWriter("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar");
        checkCsvUnescapeWriter("", null);
        checkCsvUnescapeWriter("", "");

        checkCsvUnescapeWriter("\"foo.bar\"",        "\"foo.bar\"");
    }

    private void checkCsvUnescapeWriter(final String expected, final String value) {
        try {
            final StringWriter writer = new StringWriter();
            StringEscapeUtils.UNESCAPE_CSV.translate(value, writer);
            assertEquals(expected, writer.toString());
        } catch (final IOException e) {
            fail("Threw: " + e);
        }
    }

    @Test(expected = IllegalStateException.class)
        public void testUnescapeCsvIllegalStateException() throws IOException {
        final StringWriter writer = new StringWriter();
        StringEscapeUtils.UNESCAPE_CSV.translate("foo", -1, writer);
    }

    /**
     * Tests // https://issues.apache.org/jira/browse/LANG-480
     */
    @Test
    public void testEscapeHtmlHighUnicode() {
        // this is the utf8 representation of the character:
        // COUNTING ROD UNIT DIGIT THREE
        // in Unicode
        // codepoint: U+1D362
        final byte[] data = new byte[] { (byte)0xF0, (byte)0x9D, (byte)0x8D, (byte)0xA2 };

        final String original = new String(data, Charset.forName("UTF8"));

        final String escaped = StringEscapeUtils.escapeHtml4( original );
        assertEquals( "High Unicode should not have been escaped", original, escaped);

        final String unescaped = StringEscapeUtils.unescapeHtml4( escaped );
        assertEquals( "High Unicode should have been unchanged", original, unescaped);

// TODO: I think this should hold, needs further investigation
//        String unescapedFromEntity = StringEscapeUtils.unescapeHtml4( "&#119650;" );
//        assertEquals( "High Unicode should have been unescaped", original, unescapedFromEntity);
    }

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-339
     */
    @Test
    public void testEscapeHiragana() {
        // Some random Japanese Unicode characters
        final String original = "\u304B\u304C\u3068";
        final String escaped = StringEscapeUtils.escapeHtml4(original);
        assertEquals( "Hiragana character Unicode behaviour should not be being escaped by escapeHtml4",
        original, escaped);

        final String unescaped = StringEscapeUtils.unescapeHtml4( escaped );

        assertEquals( "Hiragana character Unicode behaviour has changed - expected no unescaping", escaped, unescaped);
    }

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-708
     *
     * @throws IOException
     *             if an I/O error occurs
     */
    @Test
    public void testLang708() throws IOException {
//      final byte[] inputBytes = Files.readAllBytes(Paths.get("src/test/resources/lang-708-input.txt"));
//      final String input = new String(inputBytes, StandardCharsets.UTF_8);
//  	Extract runnable jar fails to copy resource file
    	final String input = "[{\"geonameFeatureClass\":\"L\",\"values\":{\"eu\":\"Mundua\",\"ro\":\"PamÃ¢nt\",\"it\":\"Globo\",\"ca\":\"el mÃ³n\",\"tr\":\"YeryÃ¼zÃ¼\",\"no\":\"Jorden\",\"hu\":\"FÃ¶ld\",\"lv\":\"Zeme\",\"de\":\"Welt\",\"el\":\"Î¥Î´ÏÏÎ³ÎµÎ¹Î¿Ï\",\"fi\":\"Maa\",\"la\":\"Terra\",\"fr\":\"Monde\",\"eo\":\"Mondo\",\"en\":\"World\",\"ru\":\"ÐÐµÐ¼Ð»Ñ\",\"es\":\"el planeta\",\"nl\":\"Aarde\"},\"geonameFeatureCode\":\"AREA\",\"_id\":32,\"name\":\"\",\"auto\":true,\"type\":\"GEO\",\"geonameId\":6295630,\"valueCode\":\"\"},{\"geonameFeatureClass\":\"L\",\"values\":{\"ro\":\"Europa\",\"zh\":\"æ¬§æ´²\",\"ca\":\"Europa\",\"vi\":\"ChÃ¢u Ãu\",\"tr\":\"Avrupa\",\"no\":\"Europa\",\"hu\":\"EurÃ³pa\",\"lv\":\"Eiropa\",\"hi\":\"à¤¯à¥à¤°à¥à¤ª\",\"lt\":\"Europa\",\"bs\":\"Evropa\",\"ga\":\"an Eoraip\",\"th\":\"à¸¢à¸¸à¹à¸£à¸\",\"id\":\"Eropa\",\"de\":\"Europa\",\"fi\":\"Eurooppa\",\"fr\":\"Europe\",\"sv\":\"Europa\",\"bg\":\"ÐÐ²ÑÐ¾Ð¿Ð°\",\"da\":\"Europa\",\"eu\":\"Europa\",\"is\":\"EvrÃ³pa\",\"it\":\"Europa\",\"cy\":\"Ewrop\",\"ar\":\"Ø£ÙØ±ÙØ¨Ø§\",\"se\":\"EurohpÃ¡\",\"he\":\"×××¨××¤×\",\"cs\":\"Evropa\",\"el\":\"ÎÏÏÏÏÎ·\",\"nb\":\"Europa\",\"pl\":\"Europa\",\"la\":\"Europa\",\"pt\":\"Europa\",\"eo\":\"EÅ­ropo\",\"en\":\"Europe\",\"ru\":\"ÐÐ²ÑÐ¾Ð¿Ð°\",\"es\":\"Europa\",\"ja\":\"ã¨ã¼ã­ãã\",\"nl\":\"Europa\"},\"geonameFeatureCode\":\"CONT\",\"_id\":33,\"name\":\"\",\"auto\":true,\"type\":\"GEO\",\"geonameId\":6255148,\"valueCode\":\"\"},{\"geonameFeatureClass\":\"A\",\"values\":{\"no\":\"Spania\",\"nn\":\"Spania\",\"fy\":\"Spanje\",\"gd\":\"An SpÃ inn\",\"ga\":\"An SpÃ¡inn\",\"oc\":\"Espanha\",\"arc\":\"ÜÜ£Ü¦Ü¢ÜÜ\",\"fi\":\"Espanja\",\"fr\":\"Espagne\",\"fo\":\"Spania\",\"udm\":\"ÐÑÐ¿Ð°Ð½Ð¸Ñ\",\"os\":\"ÐÑÐ¿Ð°Ð½Ð¸\",\"he\":\"×¡×¤×¨×\",\"gn\":\"EpaÃ±a\",\"gl\":\"EspaÃ±a\",\"gv\":\"Yn Spaainey\",\"pl\":\"Hiszpania\",\"gu\":\"àª¸à«àªªà«àªàª¨\",\"lo\":\"àºªàº°à»àºàº\",\"ln\":\"Espania\",\"vi\":\"TÃ¢y Ban Nha\",\"dz\":\"Spain\",\"pms\":\"Spagna\",\"lv\":\"SpÄnija\",\"lt\":\"Ispanija\",\"vo\":\"SpanyÃ¤n\",\"de\":\"Spanien\",\"mg\":\"Espaina\",\"fur\":\"Spagne\",\"mk\":\"Ð¨Ð¿Ð°Ð½Ð¸ÑÐ°\",\"ml\":\"à´¸àµà´ªàµà´¯àµà´¨àµ\\u200D\",\"ceb\":\"Espanya\",\"mi\":\"PÄniora\",\"uk\":\"ÐÑÐ¿Ð°Ð½ÑÑ\",\"eu\":\"Espainia\",\"mr\":\"à¤¸à¥à¤ªà¥à¤¨\",\"ug\":\"Ø¦ÙØ³Ù¾Ø§ÙÙÙÛ\",\"mt\":\"Spanja\",\"ms\":\"Sepanyol\",\"ur\":\"Ø³Ù¾ÛÙ\",\"fa\":\"Ø§Ø³Ù¾Ø§ÙÛØ§\",\"ty\":\"Paniora\",\"new\":\"à¤¸à¥à¤ªà¥à¤¨\",\"na\":\"Pain\",\"el\":\"ÎÏÏÎ±Î½Î¯Î±\",\"nb\":\"Spania\",\"ne\":\"à¤¸à¥à¤ªà¥à¤¨\",\"vls\":\"Spanje\",\"eo\":\"Hispanio\",\"en\":\"Kingdom of Spain\",\"et\":\"Hispaania\",\"es\":\"la Madre Patria\",\"nl\":\"Spanje\",\"vec\":\"Spagna\",\"to\":\"Sepeni\",\"ca\":\"Espanya\",\"tl\":\"Espanya\",\"tr\":\"Ä°spanya\",\"tg\":\"ÐÑÐ¿Ð¾Ð½Ð¸Ñ\",\"haw\":\"Sepania\",\"bs\":\"Å panija\",\"br\":\"Spagn\",\"th\":\"à¸à¸£à¸°à¹à¸à¸¨à¸ªà¹à¸à¸\",\"bn\":\"à¦¸à§à¦ªà§à¦¨\",\"bo\":\"à½¦à½²à¼à½à½à¼\",\"ta\":\"à®¸à¯à®ªà¯à®¯à®¿à®©à¯\",\"sv\":\"Spanien\",\"bg\":\"ÐÑÐ¿Ð°Ð½Ð¸Ñ\",\"ka\":\"áá¡áááááá\",\"st\":\"Spain\",\"sw\":\"Hispania\",\"be\":\"ÐÑÐ¿Ð°Ð½ÑÑ\",\"kw\":\"Spayn\",\"sl\":\"Å panija\",\"sk\":\"Å panielsko\",\"da\":\"Spanien\",\"ang\":\"SpÄonland\",\"nds\":\"Spanien\",\"ks\":\"SpÄna\",\"so\":\"Isbeyn\",\"ku\":\"Spanya\",\"sr\":\"Ð¨Ð¿Ð°Ð½Ð¸ÑÐ°\",\"sq\":\"Spanja\",\"ko\":\"ìì¤íë\",\"sc\":\"Ispagna\",\"cy\":\"Sbaen\",\"se\":\"EspÃ¡njja\",\"sh\":\"Å panija\",\"cv\":\"ÐÑÐ¿Ð°Ð½Ð¸\",\"km\":\"á¢áááááá¶á\",\"cs\":\"Å panÄlsko\",\"li\":\"Spanje\",\"co\":\"Spagna\",\"default\":\"Spain\",\"jbo\":\"sangu'e\",\"la\":\"Hesperia\",\"ru\":\"ÐÑÐ¿Ð°Ð½Ð¸Ñ\",\"lb\":\"Spuenien\",\"sco\":\"Spain\",\"tet\":\"EspaÃ±a\",\"scn\":\"Spagna\",\"hr\":\"Å panjolska\",\"zh\":\"è¥¿ç­ç\",\"ro\":\"Spania\",\"rm\":\"Spagna\",\"ht\":\"Espay\",\"hu\":\"SpanyolorszÃ¡g\",\"ast\":\"EspaÃ±a\",\"hi\":\"à¤¸à¥à¤ªà¥à¤¨\",\"hsb\":\"Å paniska\",\"nah\":\"CaxtillÄn\",\"war\":\"Espanya\",\"lad\":\"Espanya\",\"id\":\"Spanyol\",\"ia\":\"Espania\",\"nrm\":\"Espangne\",\"hy\":\"Ô»Õ½ÕºÕ¡Õ¶Õ«Õ¡\",\"qu\":\"IspaÃ±a\",\"ilo\":\"Espania\",\"az\":\"Ä°spaniya\",\"is\":\"SpÃ¡nn\",\"it\":\"Spagna\",\"tpi\":\"Spen\",\"ar\":\"Ø£Ø³Ø¨Ø§ÙÙØ§\",\"io\":\"Hispania\",\"pam\":\"Espanya\",\"frp\":\"Ãspagne\",\"am\":\"á¥áµááá«\",\"an\":\"EspaÃ±a\",\"csb\":\"SzpaÅskÃ´\",\"pt\":\"Espanha\",\"ja\":\"ã¹ãã¤ã³\",\"ps\":\"Ø§Ø³Ù¾Ø§ÙÙØ§\",\"yi\":\"×©×¤×× ××¢\",\"af\":\"Spanje\"},\"geonameFeatureCode\":\"PCLI\",\"_id\":260,\"name\":\"\",\"auto\":true,\"type\":\"GEO\",\"geonameId\":2510769,\"valueCode\":\"\"},{\"geonameFeatureClass\":\"A\",\"values\":{\"ca\":\"Andalusia\",\"tr\":\"EndÃ¼lÃ¼s\",\"krc\":\"ÐÐ½Ð´Ð°Ð»ÑÑÐ¸Ñ\",\"no\":\"AndalucÃ­a\",\"fy\":\"AndalÃ»sje\",\"bs\":\"Andaluzija\",\"br\":\"Andalouzia\",\"ext\":\"Andaluzia\",\"ga\":\"An AndalÃºis\",\"th\":\"à¹à¸à¸§à¹à¸à¸­à¸±à¸à¸à¸²à¸¥à¸¹à¸à¸µà¸­à¸²\",\"bn\":\"à¦à¦¨à§à¦¦à¦¾à¦²à§à¦¸à¦¿à¦¯à¦¼à¦¾\",\"oc\":\"Andalosia\",\"ka\":\"áááááá£á¡áá\",\"sv\":\"Andalusien\",\"fr\":\"Andalousie\",\"bg\":\"ÐÐ½Ð´Ð°Ð»ÑÑÐ¸Ñ\",\"glk\":\"Ø¢ÙØ¯Ø§ÙÙØ³ÛØ§\",\"be\":\"ÐÐ½Ð´Ð°Ð»ÑÑÑÑ\",\"kw\":\"Andalousi\",\"sk\":\"AndalÃºzia\",\"os\":\"ÐÐ½Ð´Ð°Ð»ÑÑÐ¸\",\"da\":\"Andalusien\",\"sr\":\"ÐÐ½Ð´Ð°Ð»ÑÐ·Ð¸ÑÐ°\",\"ku\":\"Endulus\",\"ko\":\"ìë¬ë£¨ìì ì§ë°©\",\"he\":\"×× ××××¡××\",\"sh\":\"Andaluzija\",\"arz\":\"Ø§ÙØ¯ÙÙØ³ÙØ§\",\"cs\":\"Andalusie\",\"default\":\"Andalusia\",\"stq\":\"Andalusien\",\"la\":\"Vandalitia\",\"pl\":\"Andaluzja\",\"ru\":\"ÐÐ½Ð´Ð°Ð»ÑÑÐ¸Ñ\",\"lb\":\"Andalusien\",\"tet\":\"Andaluzia\",\"got\":\"ðð°ð½ð³ð°ð»ð¹ðð¾ð°\",\"hr\":\"Andaluzija\",\"zh\":\"å®éé­¯è¥¿äº\",\"ro\":\"Andaluzia\",\"hu\":\"AndalÃºzia\",\"pms\":\"AndalusÃ¬a\",\"lv\":\"AndalÅ«zija\",\"lt\":\"AndalÅ«zija\",\"nah\":\"Andalucia\",\"lad\":\"Andaluziya\",\"de\":\"Andalusien\",\"als\":\"Andalusien\",\"qu\":\"Andalusiya\",\"hy\":\"Ô±Õ¶Õ¤Õ¡Õ¬Õ¸ÖÕ¦Õ«Õ¡\",\"eu\":\"Andaluzia\",\"is\":\"AndalÃºsÃ­a\",\"uk\":\"ÐÐ½Ð´Ð°Ð»ÑÑÑÑ\",\"az\":\"Andalusiya\",\"mr\":\"à¤à¤à¤¦à¤¾à¤²à¥à¤¸à¤¿à¤¯à¤¾\",\"ug\":\"Andalusiye\",\"fa\":\"Ø§ÙØ¯ÙØ³\",\"ar\":\"Ø£ÙØ¯ÙÙØ³ÙØ§\",\"rmy\":\"Andalusiya\",\"io\":\"Andaluzia\",\"el\":\"ÎÎ½Î´Î±Î»Î¿ÏÏÎ¯Î±\",\"frp\":\"Andalosie\",\"pt\":\"Andaluzia\",\"eo\":\"Andaluzio\",\"en\":\"Andalusia\",\"et\":\"Andaluusia\",\"es\":\"AndalucÃ­a\",\"ja\":\"ã¢ã³ãã«ã·ã¢å·\",\"nl\":\"AndalusiÃ«\",\"af\":\"AndalusiÃ«\",\"vec\":\"AndalusÃ¬a\"},\"geonameFeatureCode\":\"ADM1\",\"_id\":261,\"name\":\"\",\"auto\":true,\"type\":\"GEO\",\"geonameId\":2593109,\"valueCode\":\"\"},{\"geonameFeatureClass\":\"A\",\"values\":{\"de\":\"Granada\",\"default\":\"Province of Granada\",\"fr\":\"Grenade\",\"en\":\"Province of Granada\",\"es\":\"Provincia de Granada\",\"ja\":\"ã°ã©ãã\"},\"geonameFeatureCode\":\"ADM2\",\"_id\":262,\"name\":\"\",\"auto\":true,\"type\":\"GEO\",\"geonameId\":2517115,\"valueCode\":\"\"},{\"geonameFeatureClass\":\"A\",\"values\":{\"default\":\"Monachil\"},\"geonameFeatureCode\":\"ADM3\",\"_id\":263,\"name\":\"\",\"auto\":true,\"type\":\"GEO\",\"geonameId\":6357744,\"valueCode\":\"\"},{\"geonameFeatureClass\":\"P\",\"values\":{\"default\":\"Sierra Nevada\"},\"geonameFeatureCode\":\"PPL\",\"_id\":264,\"name\":\"\",\"auto\":true,\"type\":\"GEO\",\"geonameId\":6544329,\"valueCode\":\"\"}]";
        final String escaped = StringEscapeUtils.escapeEcmaScript(input);
        // just the end:        
        assertTrue(escaped, escaped.endsWith("}]"));
        // a little more:
        assertTrue(escaped, escaped.endsWith("\"valueCode\\\":\\\"\\\"}]"));
    }

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-720
     */
    @Test
    public void testLang720() {
        final String input = "\ud842\udfb7" + "A";
        final String escaped = StringEscapeUtils.escapeXml(input);
        assertEquals(input, escaped);
    }

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-911
     */
    @Test
    public void testLang911() {
        final String bellsTest = "\ud83d\udc80\ud83d\udd14";
        final String value = StringEscapeUtils.escapeJava(bellsTest);
        final String valueTest = StringEscapeUtils.unescapeJava(value);
        assertEquals(bellsTest, valueTest);
    }

    @Test
    public void testEscapeJson() {
        assertNull(StringEscapeUtils.escapeJson(null));
        try {
            StringEscapeUtils.ESCAPE_JSON.translate(null, null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            StringEscapeUtils.ESCAPE_JSON.translate("", null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }

        assertEquals("He didn't say, \\\"stop!\\\"", StringEscapeUtils.escapeJson("He didn't say, \"stop!\""));

        final String expected = "\\\"foo\\\" isn't \\\"bar\\\". specials: \\b\\r\\n\\f\\t\\\\\\/";
        final String input ="\"foo\" isn't \"bar\". specials: \b\r\n\f\t\\/";

        assertEquals(expected, StringEscapeUtils.escapeJson(input));
    }

    @Test
    public void testUnescapeJson() {
        assertNull(StringEscapeUtils.unescapeJson(null));
        try {
            StringEscapeUtils.UNESCAPE_JSON.translate(null, null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            StringEscapeUtils.UNESCAPE_JSON.translate("", null);
            fail();
        } catch (final IOException ex) {
            fail();
        } catch (final IllegalArgumentException ex) {
        }

        assertEquals("He didn't say, \"stop!\"", StringEscapeUtils.unescapeJson("He didn't say, \\\"stop!\\\""));

        final String expected ="\"foo\" isn't \"bar\". specials: \b\r\n\f\t\\/";
        final String input = "\\\"foo\\\" isn't \\\"bar\\\". specials: \\b\\r\\n\\f\\t\\\\\\/";

        assertEquals(expected, StringEscapeUtils.unescapeJson(input));
    }
}
