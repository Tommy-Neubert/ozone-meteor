/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.sopremo.packages;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import eu.stratosphere.sopremo.ISopremoType;

/**
 * Default implementation of {@link IRegistry}.
 */
public class DefaultRegistry<T extends ISopremoType> extends AbstractRegistry<T> implements IRegistry<T> {
	private final Map<String, T> elements = new HashMap<String, T>();

	/**
	 * Initializes DefaultRegistry.
	 */
	public DefaultRegistry() {
	}

	public DefaultRegistry(final NameChooser nameChooser) {
		super(nameChooser);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("Registry: {");
		boolean first = true;
		for (final Entry<String, T> method : this.elements.entrySet()) {
			if (first)
				first = false;
			else
				appendable.append(", ");
			appendable.append(method.getKey()).append(": ");
			method.getValue().appendAsString(appendable);
		}
		appendable.append("}");
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		final DefaultRegistry<?> other = (DefaultRegistry<?>) obj;
		return this.elements.equals(other.elements);
	}

	@Override
	public T get(final String name) {
		return this.elements.get(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.elements.hashCode();
		return result;
	}

	@Override
	public Set<String> keySet() {
		return Collections.unmodifiableSet(this.elements.keySet());
	}

	@Override
	public void put(final String name, final T element) {
		this.elements.put(name, element);
	}
}
