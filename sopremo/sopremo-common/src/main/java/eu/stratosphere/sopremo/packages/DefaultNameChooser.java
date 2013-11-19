package eu.stratosphere.sopremo.packages;

import java.io.IOException;
import java.util.Arrays;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.operator.Name;

public class DefaultNameChooser extends AbstractSopremoType implements NameChooser {
	private final int[] preferredOrder;

	public DefaultNameChooser(final int... preferredOrder) {
		this.preferredOrder = preferredOrder;
	}

	/**
	 * Initializes DefaultNameChooser.
	 */
	DefaultNameChooser() {
		this(new int[] { 0, 1, 2, 3 });
	}

	@Override
	public String[] getNames(Name nameAnnotation) {
		return this.choose(nameAnnotation.noun(), nameAnnotation.verb(), nameAnnotation.adjective(),
			nameAnnotation.preposition());
	}

	public String[] choose(final String[] nouns, final String[] verbs, final String[] adjectives,
			final String[] prepositions) {
		final String[][] names = { nouns, verbs, adjectives, prepositions };
		for (final int pos : this.preferredOrder) {
			if (names[pos] != null && names[pos].length > 0)
				return names[pos];
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.IAppending#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append(Arrays.toString(this.preferredOrder));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(this.preferredOrder);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultNameChooser other = (DefaultNameChooser) obj;
		if (!Arrays.equals(this.preferredOrder, other.preferredOrder))
			return false;
		return true;
	}
}