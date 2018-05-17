package com.visu.snmp.sender;

public class SnmpSenderThread extends Thread {

    @Override
    public void run() {
        SnmpSender snmpSender = new SnmpSender();

        try {
            while (true) {
                snmpSender.sendSnmpTrap("127.0.0.1", 162);
                Thread.sleep(30000L);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception");
        }
    }
}
