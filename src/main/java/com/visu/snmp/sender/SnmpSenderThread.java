package com.visu.snmp.sender;

public class SnmpSenderThread extends Thread {

    private final SnmpSender snmpSender;

    public SnmpSenderThread(String ipAddress, int port) {
        this.snmpSender = new SnmpSender(ipAddress, port);
    }

    @Override
    public void run() {

        try {
            while (true) {
                snmpSender.sendSnmpTrap();
                Thread.sleep(30000L);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception");
        }
    }
}
