package com.visu.snmp.stand;

import com.visu.snmp.sender.SnmpSender;

public class Launcher {

    public static final String IP_ADDRESS = "127.0.0.1";
    public static final int PORT = 162;
    private static final String SYNC_MONITOR = "sync";

    public static void main(String... args) {
        SnmpSender snmpSender = new SnmpSender(IP_ADDRESS, PORT);
        SnmpReceiverThread receiverThread = new SnmpReceiverThread(IP_ADDRESS, PORT);
        receiverThread.start();

        synchronized (SYNC_MONITOR) {
            try {
                // waiting for start of receiver listening
                SYNC_MONITOR.wait();
                // send a snmp trap
                snmpSender.sendSnmpTrap();
                System.out.println("snmp trap sent");
                // waiting for receiver listening catching the sent snmp trap
                SYNC_MONITOR.wait();
                System.out.println("Finish of sending");
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception");
            }
        }
    }
}
