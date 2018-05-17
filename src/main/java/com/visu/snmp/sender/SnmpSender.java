package com.visu.snmp.sender;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.Date;

public class SnmpSender {

    public void sendSnmpTrap(String ipAddress, int port) {
        Snmp snmp;
        CommunityTarget cTarget;
        TransportMapping transport;

        try {
            // Create Transport Mapping
            transport = new DefaultUdpTransportMapping();
            transport.listen();
        } catch (IOException ex) {
            throw new RuntimeException("Exception while creating transport", ex);
        }

        // Create Target
        cTarget = new CommunityTarget();
        cTarget.setCommunity(new OctetString("public"));
        cTarget.setVersion(SnmpConstants.version2c);
        cTarget.setAddress(new UdpAddress(ipAddress + "/" + port));

        snmp = new Snmp(transport);

        // Create PDU for V2
        PDU pdu = new PDU();

        // need to specify the system up time
        pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new OctetString(new Date().toString())));
        pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID("0.1.2.3.4.5.6")));
        pdu.setType(PDU.NOTIFICATION);

        try {
            snmp.send(pdu, cTarget);
        } catch (IOException ex) {
            throw new RuntimeException("Exception while sending snmp trap " + pdu + " to " + cTarget, ex);
        }
    }
}