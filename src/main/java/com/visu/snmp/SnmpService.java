package com.visu.snmp;

public interface SnmpService {

    void sendMessage(String ipAddress, int port);

    void listen(String idAddress, int port);
}
