package org.apache.commons.lang3.concurrent;


import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.NoSuchElementException;

public class MultiBackgroundInitializerResultsProduct {
	private MultiBackgroundInitializerResultsProductInitializers multiBackgroundInitializerResultsProductInitializers;
	private final Map<String, Object> resultObjects;

	public MultiBackgroundInitializerResultsProduct(Map<String, BackgroundInitializer<?>> inits,
			Map<String, Object> results) {
		this.multiBackgroundInitializerResultsProductInitializers = new MultiBackgroundInitializerResultsProductInitializers(
						inits);
		resultObjects = results;
	}

	/**
	* Returns a set with the names of all  {@code  BackgroundInitializer} objects managed by the  {@code  MultiBackgroundInitializer} .
	* @return  an (unmodifiable) set with the names of the managed  {@code BackgroundInitializer}  objects
	*/
	public Set<String> initializerNames() {
		return multiBackgroundInitializerResultsProductInitializers.initializerNames();
	}

	/**
	* Checks whether an initializer with the given name exists. If not, throws an exception. If it exists, the associated child initializer is returned.
	* @param name  the name to check
	* @return  the initializer with this name
	* @throws NoSuchElementException  if the name is unknown
	*/
	public BackgroundInitializer<?> checkName(final String name) {
		return multiBackgroundInitializerResultsProductInitializers.checkName(name);
	}

	/**
	* Returns the result object produced by the  {@code BackgroundInitializer}  with the given name. This is the object returned by the initializer's  {@code  initialize()}  method. If this {@code  BackgroundInitializer}  caused an exception, <b>null</b> is returned. If the name cannot be resolved, an exception is thrown.
	* @param name  the name of the  {@code  BackgroundInitializer}
	* @return  the result object produced by this  {@code BackgroundInitializer}
	* @throws NoSuchElementException  if the name cannot be resolved
	*/
	public Object getResultObject(final String name) {
		multiBackgroundInitializerResultsProductInitializers.checkName(name);
		return resultObjects.get(name);
	}
}