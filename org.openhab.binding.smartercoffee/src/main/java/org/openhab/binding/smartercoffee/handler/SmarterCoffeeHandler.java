/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.smartercoffee.handler;

import static org.openhab.binding.smartercoffee.SmarterCoffeeBindingConstants.CHANNEL_MACHINE_STATUS;

import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.smartercoffee.internal.IBrewCommandExecutor;
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

    // ibrew command interpreter and executor
    private IBrewCommandExecutor ibrew;

    public SmarterCoffeeHandler(Thing thing) {
        super(thing);
    }

    public SmarterCoffeeHandler(Thing thing, String host, Integer port) {
        super(thing);

        ibrew = new IBrewCommandExecutor(host, port);
    }

    @Override
    public void initialize() {
        logger.debug("Initializing thing {}", getThing().getUID());
        super.initialize();

        updateStatus(ThingStatus.ONLINE);
        checkStatus();

        logger.debug("Thing {} initialized {}", getThing().getUID(), getThing().getStatus());
    }

    @Override
    public void dispose() {
        logger.debug("Thing {} disposed", getThing().getUID());
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        if (RefreshType.REFRESH == command) {
            return;
        }

        if (channelUID.getId().equals(CHANNEL_MACHINE_STATUS)) {
            checkStatus();
        }
    }

    private void checkStatus() {
        if (ibrew.getStatus().isStatus()) {
            updateState(CHANNEL_MACHINE_STATUS, new StringType("ONLINE"));
        } else {
            updateState(CHANNEL_MACHINE_STATUS, new StringType("OFFLINE"));
        }
    }
}
