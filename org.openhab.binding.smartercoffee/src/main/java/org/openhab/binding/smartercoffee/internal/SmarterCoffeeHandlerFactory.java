/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.smartercoffee.internal;

import static org.openhab.binding.smartercoffee.SmarterCoffeeBindingConstants.*;

import java.math.BigDecimal;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.smartercoffee.handler.SmarterCoffeeHandler;

/**
 * The {@link SmarterCoffeeHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Nikson Kanti Paul - Initial contribution
 */
public class SmarterCoffeeHandlerFactory extends BaseThingHandlerFactory {

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(THING_TYPE_SMARTERCOFFEE_MACHINE)) {
            String host = (String) thing.getConfiguration().get(CONFIG_PARAMETER_HOST);
            BigDecimal port = (BigDecimal) thing.getConfiguration().get(CONFIG_PARAMETER_PORT);

            return new SmarterCoffeeHandler(thing, host, port.intValue());
        }

        return null;
    }
}
