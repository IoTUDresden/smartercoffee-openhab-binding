/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.smartercoffee.handler;

import static org.openhab.binding.smartercoffee.SmarterCoffeeBindingConstants.CHANNEL_SMARTER_MACHINE_STATUS;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link SmarterCoffeeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Nikson Kanti Paul - Initial contribution
 */
public class SmarterCoffeeHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(SmarterCoffeeHandler.class);

    public SmarterCoffeeHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        if (RefreshType.REFRESH == command) {
            logger.debug("Refreshing {}", channelUID);
            return;
        }

        if (channelUID.getId().equals(CHANNEL_SMARTER_MACHINE_STATUS)) {
            // TODO: handle command

            updateStatus(ThingStatus.OFFLINE);

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
        }
    }

    @Override
    public void initialize() {
        logger.debug("Initializing thing {}", getThing().getUID());
        super.initialize();
        // updateStatus(ThingStatus.INITIALIZING);

        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.
        // ToDo: my connect here @nikson
        // updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");

        updateStatus(ThingStatus.ONLINE);
        logger.debug("Thing {} initialized {}", getThing().getUID(), getThing().getStatus());
    }

    @Override
    public void dispose() {
        logger.debug("Thing {} disposed", getThing().getUID());
    }

    private void checkStatus() {

    }
}
