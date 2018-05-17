package com.visu.snmp.receiver;

public class SnmpReceiverThread extends Thread {

    private SnmpReceiver snmpReceiver;

    public SnmpReceiverThread(String ipAddress, int port) {
        snmpReceiver = new SnmpReceiver(ipAddress, port);
    }

    @Override
    public void run() {
        snmpReceiver.listen();
    }
}