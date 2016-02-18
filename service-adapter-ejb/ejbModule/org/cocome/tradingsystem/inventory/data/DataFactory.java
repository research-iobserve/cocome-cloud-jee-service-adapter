/***************************************************************************
 * Copyright 2013 DFG SPP 1593 (http://dfg-spp1593.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package org.cocome.tradingsystem.inventory.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Factory for creating the Data component.
 * 
 * @author Yannick Welsch
 */
public final class DataFactory {

	private static IData __dataSingleton = null;

	//

	private DataFactory() {
		// utility class, not to be instantiated
	}

	/**
	 * @return A data access component implementing the {@link IData} interface.
	 */
	synchronized public static IData getInstance() {
		if (__dataSingleton == null) {
			__dataSingleton = new DataComponent();
		}

		return __dataSingleton;
	}
	
	synchronized public static IData getInstance(EntityManagerFactory emf) {
		if (__dataSingleton == null) {
			__dataSingleton = new DataComponent(emf);
		}

		return __dataSingleton;
	}
	

}
