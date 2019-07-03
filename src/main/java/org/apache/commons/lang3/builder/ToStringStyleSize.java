package org.apache.commons.lang3.builder;


import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;

public class ToStringStyleSize implements Serializable {
	private String sizeStartText = "<size=";
	private String sizeEndText = ">";

	public String getSizeStartText() {
		return sizeStartText;
	}

	public String getSizeEndText() {
		return sizeEndText;
	}

	/**
	* <p>Sets the start text to output when a <code>Collection</code>, <code>Map</code> or array size is output.</p> <p>This is output before the size value.</p> <p><code>null</code> is accepted, but will be converted to an empty String.</p>
	* @param sizeStartText   the new start of size text
	*/
	public void setSizeStartText(String sizeStartText) {
		if (sizeStartText == null) {
			sizeStartText = StringUtils.EMPTY;
		}
		this.sizeStartText = sizeStartText;
	}

	/**
	* <p>Append to the <code>toString</code> a size summary.</p> <p>The size summary is used to summarize the contents of <code>Collections</code>, <code>Maps</code> and arrays.</p> <p>The output consists of a prefix, the passed in size and a suffix.</p> <p>The default format is <code>'&lt;size=n&gt;'</code>.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	* @param fieldName   the field name, typically not used as already appended
	* @param size   the size to append
	*/
	public void appendSummarySize(final StringBuffer buffer, final String fieldName, final int size) {
		buffer.append(sizeStartText);
		buffer.append(size);
		buffer.append(sizeEndText);
	}

	/**
	* <p>Sets the end text to output when a <code>Collection</code>, <code>Map</code> or array size is output.</p> <p>This is output after the size value.</p> <p><code>null</code> is accepted, but will be converted to an empty String.</p>
	* @param sizeEndText   the new end of size text
	*/
	public void setSizeEndText(String sizeEndText) {
		if (sizeEndText == null) {
			sizeEndText = StringUtils.EMPTY;
		}
		this.sizeEndText = sizeEndText;
	}
}