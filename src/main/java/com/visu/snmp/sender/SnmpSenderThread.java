package com.visu.snmp.sender;

public class SnmpSenderThread extends Thread {

    private final String ipAddress;
    private final int port;
    private final SnmpSender snmpSender;

    public SnmpSenderThread(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.snmpSender = new SnmpSender();
    }

    @Override
    public void run() {

        try {
            while (true) {
                snmpSender.sendSnmpTrap(ipAddress, port);
                Thread.sleep(30000L);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception");
        }
    }
}
