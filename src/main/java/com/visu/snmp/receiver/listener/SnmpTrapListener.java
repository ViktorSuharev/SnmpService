package com.visu.snmp.receiver.listener;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;

public class SnmpTrapListener implements CommandResponder {

    public void processPdu(CommandResponderEvent cmdRespEvent) {
        PDU pdu = cmdRespEvent.getPDU();
        if (pdu != null) {
            System.out.println("Trap Type = " + pdu.getType());
            System.out.println("Variable Bindings = " + pdu.getVariableBindings());
        }
    }
}

