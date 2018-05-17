package com.visu.snmp.receiver;

import org.snmp4j.smi.UdpAddress;

import java.io.IOException;

public class SnmpReceiverThread extends Thread {

    @Override
    public void run() {
        try {
            SnmpReceiver snmp4jTrapReceiver = new SnmpReceiver();
            snmp4jTrapReceiver.listen(new UdpAddress("127.0.0.1/162"));
        } catch (IOException e) {
            System.err.println("Error in Listening for Trap");
            System.err.println("Exception Message = " + e.getMessage());
        }
    }
}