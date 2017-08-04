package org.openhab.binding.smartercoffee.internal;

public class CommandResponse {

    // plain response text of executed command
    private String rawdata;
    // formated response for OpenHAB
    private String response;
    // response status
    private boolean status;

    public CommandResponse() {
    }

    public CommandResponse(String response, boolean status) {
        this.response = response;
        this.status = status;
    }

    public String getRawdata() {
        return rawdata;
    }

    public void setRawdata(String rawdata) {
        this.rawdata = rawdata;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.response;
    }

}
