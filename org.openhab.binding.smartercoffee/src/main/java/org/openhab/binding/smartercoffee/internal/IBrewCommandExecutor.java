package org.openhab.binding.smartercoffee.internal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link IBrewCommandExecutor} is responsible for invoking commands, which are
 * sent to ibrew.
 *
 * @author Nikson Kanti Paul - Initial contribution
 *
 */
public class IBrewCommandExecutor {
    private final Logger logger = LoggerFactory.getLogger(IBrewCommandExecutor.class);

    private String host = "192.168.1.4";
    private int port = 2081;

    // ibrew command args variable and defaults value
    private int number_of_cups = 1;
    private boolean hotplate_state = false;
    private int hotplate_timer = 5;
    private boolean grind_state = false;
    private String strength = "weak";

    public IBrewCommandExecutor(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private String send(String command) {
        Process p;
        StringBuffer output = new StringBuffer();

        try {
            if (host != null) {
                command = appendHost(command);
            }

            logger.debug("iBrew::Executing command: " + command);

            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            logger.debug("iBrew::Response: " + output.toString());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return output.toString();
    }

    private String appendHost(String cmd) {
        return String.format("%s %s:%d", cmd, host, port);
    }

    public CommandResponse getStatus() {
        CommandResponse cr = new CommandResponse();
        cr.setResponse("Offline");
        cr.setRawdata(this.send("ibrew shortstatus"));
        if (cr.getRawdata().contains("ready")) {
            cr.setResponse("Ready");
            cr.setStatus(true);
        } else if (cr.getRawdata().contains("refused")) {
            cr.setStatus(false);
        } else {
            cr.setStatus(false);
        }

        return cr;
    }

    public CommandResponse getMode() {
        CommandResponse cr = new CommandResponse();
        cr.setRawdata(this.send("ibrew dump mode"));
        cr.setStatus(false);
        if (cr.getRawdata().contains("Cup mode")) {
            cr.setResponse("cup");
        } else if (cr.getRawdata().contains("Carafe mode")) {
            cr.setResponse("carafe");
        } else {
            cr.setStatus(false);
        }
        return cr;
    }

    public void useBeans() {
        // FixMe: Didn't find unset command in ibrew

        String response = this.send("ibrew beans");
    }

    public CommandResponse makeCoffee() {
        CommandResponse response = new CommandResponse();
        response.setStatus(true);

        // example command: ibrew 1 5 on weak brew
        String cmdStr = String.format("ibrew brew %d %d %s %s", getNumber_of_cups(), getHotplate_timer(),
                isGrind_state() ? "on" : "off", getStrength());

        response.setRawdata(this.send(cmdStr));

        if (response.getRawdata().contains("error") || response.getRawdata().contains("failed")) {
            response.setStatus(false);
        }

        logger.debug("Response status: " + response.isStatus());

        return response;

    }

    public CommandResponse stopCoffee() {
        CommandResponse response = new CommandResponse();
        response.setStatus(true);

        response.setRawdata(this.send("ibrew stop"));

        if (response.getRawdata().contains("error") || response.getRawdata().contains("failed")) {
            response.setStatus(false);
        }

        logger.debug("Response status: " + response.isStatus());

        return response;

    }

    public int getNumber_of_cups() {
        return number_of_cups;
    }

    public void setNumber_of_cups(int number_of_cups) {
        this.number_of_cups = number_of_cups;
    }

    public boolean isHotplate_state() {
        return hotplate_state;
    }

    public void setHotplate_state(boolean hotplate_state) {
        this.hotplate_state = hotplate_state;
    }

    public int getHotplate_timer() {
        return hotplate_timer;
    }

    public void setHotplate_timer(int hotplate_timer) {
        this.hotplate_timer = hotplate_timer;
    }

    public boolean isGrind_state() {
        return grind_state;
    }

    public void setGrind_state(boolean grind_state) {
        this.grind_state = grind_state;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}
