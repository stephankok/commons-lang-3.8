package org.apache.commons.lang3.concurrent;


import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.NoSuchElementException;

public class MultiBackgroundInitializerResultsProductInitializers {
	private final Map<String, BackgroundInitializer<?>> initializers;

	public MultiBackgroundInitializerResultsProductInitializers(Map<String, BackgroundInitializer<?>> inits) {
		initializers = inits;
	}

	/**
	* Returns a set with the names of all   {@code   BackgroundInitializer}  objects managed by the   {@code   MultiBackgroundInitializer}  .
	* @return   an (unmodifiable) set with the names of the managed   {@code  BackgroundInitializer}   objects
	*/
	public Set<String> initializerNames() {
		return Collections.unmodifiableSet(initializers.keySet());
	}

	/**
	* Checks whether an initializer with the given name exists. If not, throws an exception. If it exists, the associated child initializer is returned.
	* @param name   the name to check
	* @return   the initializer with this name
	* @throws NoSuchElementException   if the name is unknown
	*/
	public BackgroundInitializer<?> checkName(final String name) {
		final BackgroundInitializer<?> init = initializers.get(name);
		if (init == null) {
			throw new NoSuchElementException("No child initializer with name " + name);
		}
		return init;
	}
}