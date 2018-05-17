package com.visu.snmp;

import com.visu.snmp.receiver.SnmpReceiverThread;
import com.visu.snmp.sender.SnmpSenderThread;

public class SnmpService {
    public static void main(String[] args) {
        SnmpSenderThread senderThread = new SnmpSenderThread();
        SnmpReceiverThread receiverThread = new SnmpReceiverThread();

        receiverThread.start();
        senderThread.start();
    }
}
