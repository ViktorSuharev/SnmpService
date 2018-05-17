package com.visu.snmp;

import com.visu.snmp.receiver.SnmpReceiverThread;
import com.visu.snmp.sender.SnmpSender;
import com.visu.snmp.sender.SnmpSenderThread;

public class SnmpServiceImpl implements SnmpService {

    private final SnmpSender sender = new SnmpSender();

    public static void main(String[] args) {
        String ipAddress = "127.0.0.1";
        int port = 162;
        SnmpSenderThread senderThread = new SnmpSenderThread(ipAddress, port);
        SnmpReceiverThread receiverThread = new SnmpReceiverThread(ipAddress, port);

        receiverThread.start();
        senderThread.start();
    }

    public void sendMessage(String ipAddress, int port) {
        sender.sendSnmpTrap(ipAddress, port);
    }

    public void listen(String ipAddress, int port) {
        SnmpReceiverThread receiverThread = new SnmpReceiverThread(ipAddress, port);
        receiverThread.start();
    }
}
