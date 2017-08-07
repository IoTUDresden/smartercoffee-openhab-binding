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

            logger.debug("Executing command: " + command);

            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

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
}
