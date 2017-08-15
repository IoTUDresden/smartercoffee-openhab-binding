/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.smartercoffee.handler;

import static org.openhab.binding.smartercoffee.SmarterCoffeeBindingConstants.*;

import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.smartercoffee.internal.CommandResponse;
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
        } else if (channelUID.getId().equals(CHANNEL_MACHINE_MODE)) {
            // logger.info(ibrew.getMode().getRawdata());
        } else if (channelUID.getId().equals(CHANNEL_GENERAL_REFRESH)) {
            logger.debug("Getting status: " + command.toString());
            if (command.toString().toUpperCase().equals("ON")) {
                checkStatus();
                // updateState(CHANNEL_GENERAL_REFRESH, OnOffType.OFF);
            }
        } else if (channelUID.getId().equals(CHANNEL_COFFEE_USE_BEANS)) {
            logger.debug("Use beans item changed: " + command.toString());
            ibrew.useBeans();

        } else if (channelUID.getId().equals(CHANNEL_COFFEE_NO_OF_CUPS)) {
            logger.debug("Cup item changed: " + command.toString());
            ibrew.setNumber_of_cups(Integer.valueOf(command.toString()));
            logger.info("cups: " + ibrew.getNumber_of_cups());
        } else if (channelUID.getId().equals(CHANNEL_COFFEE_HOTPLATE)) {
            // ToDo
        } else if (channelUID.getId().equals(CHANNEL_COFFEE_HOTPLATE_TIMER)) {
            logger.debug("Hotplate item changed: " + command.toString());
            ibrew.setHotplate_timer(Integer.valueOf(command.toString()));
            logger.info("hotplate timer: " + ibrew.getHotplate_timer());
        } else if (channelUID.getId().equals(CHANNEL_COFFEE_GRIND)) {
            logger.debug("Grind item changed: " + command.toString());
            if (command.toString().toUpperCase().equals("ON")) {
                ibrew.setGrind_state(true);
            } else {
                ibrew.setGrind_state(false);
            }
        } else if (channelUID.getId().equals(CHANNEL_COFFEE_STRENGTH)) {
            logger.debug("Strength item changed: " + command.toString());
            ibrew.setStrength(command.toString().toLowerCase());
        } else if (channelUID.getId().equals(CHANNEL_COFFEE_BREW)) {
            logger.debug("Start-Cofffee item changed: " + command.toString());
            // send makeCoffee command
            if (command.toString().toUpperCase().equals("ON")) {
                ibrew.makeCoffee();
            } else {
                ibrew.stopCoffee();
            }
        }

    }

    private void checkStatus() {
        CommandResponse response = ibrew.getStatus();
        if (response.isStatus()) {
            updateState(CHANNEL_MACHINE_STATUS, new StringType("ONLINE"));
            parseStatusResponse(response.getRawdata());
        } else {
            updateState(CHANNEL_MACHINE_STATUS, new StringType("OFFLINE"));
        }
    }

    private void parseStatusResponse(String data) {

        // FixMe:
        try {
            // ready carafe on base, water half, setting: brew 1 cup of weak beans coffee, carafe required, carafe mode

            for (String line : data.split("\n")) {
                if (!line.isEmpty() && line.startsWith("ready")) {

                    String[] block = line.split(",");

                    String water_level = block[1].replace("water", "");
                    updateState(CHANNEL_GENERAL_WATER_LEVEL, new StringType(water_level));

                    String current_setting = block[2].replace("setting: brew", "");
                    updateState(CHANNEL_GENERAL_SETTINGS, new StringType(current_setting));
                }
            }

        } catch (IndexOutOfBoundsException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
