package com.visu.snmp.receiver;

import com.visu.snmp.receiver.listener.SnmpTrapListener;
import org.snmp4j.CommandResponder;
import org.snmp4j.Snmp;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpReceiver {

    private final String ipAddress;
    private final int port;
    private final Snmp snmp;

    public SnmpReceiver(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;

        AbstractTransportMapping transport = null;
        try {
            TransportIpAddress address = new UdpAddress(ipAddress + "/" + port);
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
        }

        this.snmp = new Snmp(transport);
        CommandResponder snmpTrapInterceptor = new SnmpTrapListener();
        snmp.addCommandResponder(snmpTrapInterceptor);
    }

    public synchronized void listen() {
        try {
            snmp.listen();
            this.wait();
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
        }
    }
}
