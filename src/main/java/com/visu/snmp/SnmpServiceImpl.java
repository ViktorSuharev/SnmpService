package com.visu.snmp;

import com.visu.snmp.receiver.SnmpReceiverThread;
import com.visu.snmp.sender.SnmpSender;

public class SnmpServiceImpl implements SnmpService {

    public void sendMessage(String ipAddress, int port) {
        SnmpSender sender = new SnmpSender(ipAddress, port);
        sender.sendSnmpTrap();
    }

    public void listen(String ipAddress, int port) {
        SnmpReceiverThread receiverThread = new SnmpReceiverThread(ipAddress, port);
        receiverThread.start();
    }
}
