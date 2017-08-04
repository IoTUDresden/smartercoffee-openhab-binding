package org.openhab.binding.smartercoffee.internal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IBrewCommandExecutor {
    private String host = "192.168.1.4";

    private String send(String command) {
        Process p;
        StringBuffer output = new StringBuffer();

        try {
            if (host != null) {
                command = appendHost(command);
            }

            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    private String appendHost(String cmd) {
        return String.format("%s %s", cmd, host);
    }

    public CommandResponse getStatus() {
        CommandResponse cr = new CommandResponse();
        cr.setRawdata(this.send("ibrew shortstatus"));
        if (cr.getRawdata().contains("ready")) {
            cr.setResponse("Ready");
            cr.setStatus(true);
        } else if (cr.getRawdata().contains("refused")) {
            cr.setResponse("Offline");
            cr.setStatus(true);
        } else {
            cr.setStatus(false);
        }

        return cr;
    }
}
