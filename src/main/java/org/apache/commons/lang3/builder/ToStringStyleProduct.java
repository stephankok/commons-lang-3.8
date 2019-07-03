package org.apache.commons.lang3.builder;


import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;

public class ToStringStyleProduct implements Serializable {
	private String contentStart = "[";
	private String contentEnd = "]";
	private boolean fieldSeparatorAtStart = false;
	private String fieldSeparator = ",";
	private boolean fieldSeparatorAtEnd = false;

	public String getContentStart() {
		return contentStart;
	}

	public String getContentEnd() {
		return contentEnd;
	}

	public boolean getFieldSeparatorAtStart() {
		return fieldSeparatorAtStart;
	}

	public void setFieldSeparatorAtStart(boolean fieldSeparatorAtStart) {
		this.fieldSeparatorAtStart = fieldSeparatorAtStart;
	}

	public String getFieldSeparator() {
		return fieldSeparator;
	}

	public boolean getFieldSeparatorAtEnd() {
		return fieldSeparatorAtEnd;
	}

	public void setFieldSeparatorAtEnd(boolean fieldSeparatorAtEnd) {
		this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
	}

	/**
	* <p>Append to the <code>toString</code> the content start.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	*/
	public void appendContentStart(final StringBuffer buffer) {
		buffer.append(contentStart);
	}

	/**
	* <p>Sets the content start text.</p> <p><code>null</code> is accepted, but will be converted to an empty String.</p>
	* @param contentStart   the new content start text
	*/
	public void setContentStart(String contentStart) {
		if (contentStart == null) {
			contentStart = StringUtils.EMPTY;
		}
		this.contentStart = contentStart;
	}

	/**
	* <p>Append to the <code>toString</code> the content end.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	*/
	public void appendContentEnd(final StringBuffer buffer) {
		buffer.append(contentEnd);
	}

	/**
	* <p>Sets the content end text.</p> <p><code>null</code> is accepted, but will be converted to an empty String.</p>
	* @param contentEnd   the new content end text
	*/
	public void setContentEnd(String contentEnd) {
		if (contentEnd == null) {
			contentEnd = StringUtils.EMPTY;
		}
		this.contentEnd = contentEnd;
	}

	/**
	* <p>Append to the <code>toString</code> another toString.</p> <p>NOTE: It assumes that the toString has been created from the same ToStringStyle. </p> <p>A <code>null</code> <code>toString</code> is ignored.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	* @param toString   the additional <code>toString</code>
	* @since  2.0
	*/
	public void appendToString(final StringBuffer buffer, final String toString) {
		if (toString != null) {
			final int pos1 = toString.indexOf(contentStart) + contentStart.length();
			final int pos2 = toString.lastIndexOf(contentEnd);
			if (pos1 != pos2 && pos1 >= 0 && pos2 >= 0) {
				if (fieldSeparatorAtStart) {
					removeLastFieldSeparator(buffer);
				}
				buffer.append(toString, pos1, pos2);
				appendFieldSeparator(buffer);
			}
		}
	}

	/**
	* <p>Append to the <code>toString</code> the start of data indicator.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	* @param object   the <code>Object</code> to build a <code>toString</code> for
	*/
	public void appendStart(final StringBuffer buffer, final Object object, ToStringStyle toStringStyle) {
		if (object != null) {
			toStringStyle.appendClassName(buffer, object);
			toStringStyle.appendIdentityHashCode(buffer, object);
			appendContentStart(buffer);
			if (fieldSeparatorAtStart) {
				appendFieldSeparator(buffer);
			}
		}
	}

	/**
	* <p>Remove the last field separator from the buffer.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	* @since  2.0
	*/
	public void removeLastFieldSeparator(final StringBuffer buffer) {
		final int len = buffer.length();
		final int sepLen = fieldSeparator.length();
		if (len > 0 && sepLen > 0 && len >= sepLen) {
			boolean match = true;
			for (int i = 0; i < sepLen; i++) {
				if (buffer.charAt(len - 1 - i) != fieldSeparator.charAt(sepLen - 1 - i)) {
					match = false;
					break;
				}
			}
			if (match) {
				buffer.setLength(len - sepLen);
			}
		}
	}

	/**
	* <p>Append to the <code>toString</code> the field separator.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	*/
	public void appendFieldSeparator(final StringBuffer buffer) {
		buffer.append(fieldSeparator);
	}

	/**
	* <p>Sets the field separator text.</p> <p><code>null</code> is accepted, but will be converted to an empty String.</p>
	* @param fieldSeparator   the new field separator text
	*/
	public void setFieldSeparator(String fieldSeparator) {
		if (fieldSeparator == null) {
			fieldSeparator = StringUtils.EMPTY;
		}
		this.fieldSeparator = fieldSeparator;
	}

	/**
	* <p>Append to the <code>toString</code> the end of data indicator.</p>
	* @param buffer   the <code>StringBuffer</code> to populate
	* @param object   the <code>Object</code> to build a <code>toString</code> for.
	*/
	public void appendEnd(final StringBuffer buffer, final Object object) {
		if (!this.fieldSeparatorAtEnd) {
			removeLastFieldSeparator(buffer);
		}
		appendContentEnd(buffer);
		ToStringStyle.unregister(object);
	}
}