package com.visu.snmp.receiver;

import com.visu.snmp.receiver.listener.SnmpTrapListener;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import java.io.IOException;

public class SnmpReceiver {

    public synchronized void listen(TransportIpAddress address) throws IOException {
        AbstractTransportMapping transport = new DefaultUdpTransportMapping((UdpAddress) address);

        CommunityTarget target = new CommunityTarget();
        target.setCommunity( new OctetString("public"));

        CommandResponder snmpTrapInterceptor = new SnmpTrapListener();

        Snmp snmp = new Snmp(transport);
        snmp.addCommandResponder(snmpTrapInterceptor);

        snmp.listen();

        try {
            this.wait();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
