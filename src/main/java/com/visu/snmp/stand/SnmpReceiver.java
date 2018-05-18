package com.visu.snmp.stand;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpReceiver implements CommandResponder {

    private final String SYNC_MONITOR = "sync";
    private final Snmp snmp;

    public SnmpReceiver(String ipAddress, int port) {
        AbstractTransportMapping transport = null;
        try {
            TransportIpAddress address = new UdpAddress(ipAddress + "/" + port);
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
        }
        MessageDispatcher disp = new MessageDispatcherImpl();
        disp.addMessageProcessingModel(new MPv2c());

        snmp = new Snmp(disp, transport);
        snmp.addCommandResponder(this);
    }

    public synchronized void listen() {
        synchronized (SYNC_MONITOR) {
            System.out.println("receiver thread started");
            try {
                snmp.listen();
                System.out.println("start listening");
                // notify main thread about starting listening for snmp traps
                SYNC_MONITOR.notify();
                System.out.println("sent notification");
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void processPdu(CommandResponderEvent cmdRespEvent) {
        synchronized (SYNC_MONITOR) {
            PDU pdu = cmdRespEvent.getPDU();
            if (pdu != null) {
                System.out.println("Trap Type = " + pdu.getType());
                System.out.println("Variable Bindings = " + pdu.getVariableBindings());
                // notify main thread about snmp trap catching and asserting
                SYNC_MONITOR.notify();
                System.out.println("sent notification");
                // stop the receiver thread
                Thread.currentThread().interrupt();
            }
        }
    }
}