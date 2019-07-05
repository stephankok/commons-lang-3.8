package org.apache.commons.lang3;


public class BitMask {
	private final int _mask;

	public BitMask(int mask) {
		_mask = mask;
	}

	public int get_mask() {
		return _mask;
	}

	/**
	* <p>Obtains the value for the specified BitField, unshifted.</p>
	* @param holder  the int data containing the bits we're interested in
	* @return  the selected bits
	*/
	public int getRawValue(final int holder) {
		return holder & _mask;
	}

	/**
	* <p>Obtains the value for the specified BitField, unshifted.</p>
	* @param holder  the short data containing the bits we're interested in
	* @return  the selected bits
	*/
	public short getShortRawValue(final short holder) {
		return (short) getRawValue(holder);
	}

	/**
	* <p>Returns whether the field is set or not.</p> <p>This is most commonly used for a single-bit field, which is often used to represent a boolean value; the results of using it for a multi-bit field is to determine whether *any* of its bits are set.</p>
	* @param holder  the int data containing the bits we're interested in
	* @return   {@code  true}  if any of the bits are set, else  {@code  false}
	*/
	public boolean isSet(final int holder) {
		return (holder & _mask) != 0;
	}

	/**
	* <p>Returns whether all of the bits are set or not.</p> <p>This is a stricter test than  {@link #isSet(int)} , in that all of the bits in a multi-bit set must be set for this method to return  {@code  true} .</p>
	* @param holder  the int data containing the bits we're interested in
	* @return   {@code  true}  if all of the bits are set, else  {@code  false}
	*/
	public boolean isAllSet(final int holder) {
		return (holder & _mask) == _mask;
	}

	/**
	* <p>Clears the bits.</p>
	* @param holder  the int data containing the bits we're interested in
	* @return  the value of holder with the specified bits cleared (set to  {@code  0} )
	*/
	public int clear(final int holder) {
		return holder & ~_mask;
	}

	/**
	* <p>Clears the bits.</p>
	* @param holder  the short data containing the bits we're interested in
	* @return  the value of holder with the specified bits cleared (set to  {@code  0} )
	*/
	public short clearShort(final short holder) {
		return (short) clear(holder);
	}

	/**
	* <p>Clears the bits.</p>
	* @param holder  the byte data containing the bits we're interested in
	* @return  the value of holder with the specified bits cleared (set to  {@code  0} )
	*/
	public byte clearByte(final byte holder) {
		return (byte) clear(holder);
	}

	/**
	* <p>Sets the bits.</p>
	* @param holder  the int data containing the bits we're interested in
	* @return  the value of holder with the specified bits set to  {@code  1}
	*/
	public int set(final int holder) {
		return holder | _mask;
	}

	/**
	* <p>Sets the bits.</p>
	* @param holder  the short data containing the bits we're interested in
	* @return  the value of holder with the specified bits set to  {@code  1}
	*/
	public short setShort(final short holder) {
		return (short) set(holder);
	}

	/**
	* <p>Sets the bits.</p>
	* @param holder  the byte data containing the bits we're interested in
	* @return  the value of holder with the specified bits set to  {@code  1}
	*/
	public byte setByte(final byte holder) {
		return (byte) set(holder);
	}
}