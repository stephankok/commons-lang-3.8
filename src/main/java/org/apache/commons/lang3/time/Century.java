package org.apache.commons.lang3.time;

import java.io.Serializable;

public class Century implements Serializable {
	private static final long serialVersionUID = 1L;
	private int century;
	private int startYear;

	public Century(int centuryStartYear) 
	{	
		century= centuryStartYear / 100 * 100;
        startYear= centuryStartYear - century;        		
	}
	/**
	* Adjust dates to be within appropriate century
	* @param twoDigitYear  The year to adjust
	* @return  A value between centuryStart(inclusive) to centuryStart+100(exclusive)
	*/
	public int adjustYear(final int twoDigitYear) {
		final int trial = century + twoDigitYear;
		return twoDigitYear >= startYear ? trial : trial + 100;
	}
}