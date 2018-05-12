package cz.vutbr.fit.openmrdp;

import cz.vutbr.fit.openmrdp.api.OpenmRDPServerAPI;
import cz.vutbr.fit.openmrdp.api.OpenmRDPServerAPIImpl;
import cz.vutbr.fit.openmrdp.exceptions.AddressSyntaxException;
import cz.vutbr.fit.openmrdp.exceptions.NetworkCommunicationException;
import cz.vutbr.fit.openmrdp.logger.MrdpLogger;
import cz.vutbr.fit.openmrdp.logger.MrdpTestLoggerImpl;
import cz.vutbr.fit.openmrdp.security.SecurityConfiguration;
import cz.vutbr.fit.openmrdp.security.SecurityConfigurationFactory;
import cz.vutbr.fit.openmrdp.security.UserAuthorizator;
import cz.vutbr.fit.openmrdp.security.UserAuthorizatorTestImpl;
import cz.vutbr.fit.openmrdp.server.ServerConfiguration;

public class Main {

    private static final String SERVER_IP_ADDRESS = "192.168.1.530";
    private static final int MRDP_PORT = 2774;

    public static void main(String[] args) {

        UserAuthorizator userAuthorizator = new UserAuthorizatorTestImpl();
        SecurityConfiguration securityConfiguration = SecurityConfigurationFactory.createSecureSecurityConfiguration(userAuthorizator);
        ServerConfiguration serverConfiguration = new ServerConfiguration(SERVER_IP_ADDRESS, MRDP_PORT);
        MrdpLogger logger = new MrdpTestLoggerImpl();
        OpenmRDPServerAPI api = new OpenmRDPServerAPIImpl(securityConfiguration, serverConfiguration, logger);

        try {
            api.receiveMessages();
        } catch (AddressSyntaxException | NetworkCommunicationException e) {
            logger.logError(e.getMessage());
        }
    }
}