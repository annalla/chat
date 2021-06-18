/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Xuyen
 */
public class serverInfor {
    int port;
    String name;
    String hostname;

    public serverInfor() {
    }

    public serverInfor(int port, String name, String hostname) {
        this.port = port;
        this.name = name;
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    
}
