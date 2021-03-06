/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
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
package eu.stratosphere.sopremo.testing;

import java.util.Arrays;

import eu.stratosphere.core.testing.DefaultEqualer;
import eu.stratosphere.core.testing.DefaultStringifier;
import eu.stratosphere.core.testing.Equaler;
import eu.stratosphere.core.testing.GenericTestRecords;
import eu.stratosphere.core.testing.TypeConfig;
import eu.stratosphere.core.testing.TypeStringifier;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.packages.ITypeRegistry;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordComparatorFactory;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.serialization.SopremoRecordPairComparatorFactory;
import eu.stratosphere.sopremo.serialization.SopremoRecordSerializerFactory;

/**
 */
public class SopremoTestRecords extends GenericTestRecords<SopremoRecord> {

	public SopremoTestRecords() {
		super();
	}

	public SopremoTestRecords(final TypeConfig<SopremoRecord> typeConfig) {
		super(typeConfig);
	}

	public static final TypeConfig<SopremoRecord> getTypeConfig(final SopremoRecordLayout layout,
			final ITypeRegistry typeRegistry) {

		final TypeConfig<SopremoRecord> typeConfig;
		final TypeStringifier<SopremoRecord> typeStringifier = DefaultStringifier.get();
		final Equaler<SopremoRecord> equaler = DefaultEqualer.get();
		final int[] keys = new int[layout.getNumKeys()];
		for (int index = 0; index < keys.length; index++)
			keys[index] = index;
		final boolean[] ascending = new boolean[keys.length];
		Arrays.fill(ascending, true);
		typeConfig =
			new TypeConfig<SopremoRecord>(new SopremoRecordComparatorFactory(layout, typeRegistry, keys, ascending),
				SopremoRecordPairComparatorFactory.get(),
				new SopremoRecordSerializerFactory(layout, typeRegistry), typeStringifier,
				new SopremoKeyExtractor(layout.getKeyExpressions().toArray(new EvaluationExpression[0])),
				equaler);
		return typeConfig;
	}

}
