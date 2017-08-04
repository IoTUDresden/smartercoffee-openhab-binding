/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.smartercoffee;

import java.util.Set;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.Sets;

/**
 * The {@link SmarterCoffeeBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Nikson Kanti Paul - Initial contribution
 */
public class SmarterCoffeeBindingConstants {

    public static final String BINDING_ID = "smartercoffee";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SMARTERCOFFEE_MACHINE = new ThingTypeUID(BINDING_ID, "coffeemachine");

    // List of all Channel ids
    public static final String CHANNEL_SMARTER_MACHINE_STATUS = "status";

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Sets
            .newHashSet(THING_TYPE_SMARTERCOFFEE_MACHINE);

}
