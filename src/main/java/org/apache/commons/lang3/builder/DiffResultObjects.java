/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.apache.commons.lang3.builder;


import java.util.List;

public class DiffResultObjects {
	private final Object lhs;
	private final Object rhs;

	public DiffResultObjects(Object lhs, Object rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	/**
	* <p> Builds a  {@code  String}  description of the differences contained within this  {@code  DiffResult} , using the supplied  {@code  ToStringStyle} . </p>
	* @param style the  {@code  ToStringStyle}  to use when outputting the objects
	* @return  a  {@code  String}  description of the differences.
	*/
	public String toString(final ToStringStyle style, List<Diff<?>> thisDiffs) {
		if (thisDiffs.isEmpty()) {
			return DiffResult.OBJECTS_SAME_STRING;
		}
		final ToStringBuilder lhsBuilder = new ToStringBuilder(lhs, style);
		final ToStringBuilder rhsBuilder = new ToStringBuilder(rhs, style);
		for (final Diff<?> diff : thisDiffs) {
			lhsBuilder.append(diff.getFieldName(), diff.getLeft());
			rhsBuilder.append(diff.getFieldName(), diff.getRight());
		}
		return String.format("%s %s %s", lhsBuilder.build(), DiffResult.DIFFERS_STRING, rhsBuilder.build());
	}
}