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

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Sets
            .newHashSet(THING_TYPE_SMARTERCOFFEE_MACHINE);

    public static final String CONFIG_PARAMETER_HOST = "host";
    public static final String CONFIG_PARAMETER_PORT = "port";

    // List of all Channel ids
    public static final String CHANNEL_MACHINE_STATUS = "general_grp#status";
    public static final String CHANNEL_MACHINE_MODE = "general_grp#mode";
    public static final String CHANNEL_GENERAL_SETTINGS = "general_grp#current_settings";
    public static final String CHANNEL_GENERAL_WATER_LEVEL = "general_grp#water_level";
    public static final String CHANNEL_GENERAL_REFRESH = "general_grp#refresh";

    public static final String CHANNEL_COFFEE_USE_BEANS = "coffee_grp#use_beans";
    public static final String CHANNEL_COFFEE_NO_OF_CUPS = "coffee_grp#no_of_cups";
    public static final String CHANNEL_COFFEE_HOTPLATE = "coffee_grp#hotplate";
    public static final String CHANNEL_COFFEE_HOTPLATE_TIMER = "coffee_grp#hotplate_timer";
    public static final String CHANNEL_COFFEE_GRIND = "coffee_grp#grind";
    public static final String CHANNEL_COFFEE_STRENGTH = "coffee_grp#strength";
    public static final String CHANNEL_COFFEE_BREW = "coffee_grp#brew";

}
