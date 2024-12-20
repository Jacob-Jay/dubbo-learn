package base.group;

import base.ProviderInterface;
import base.ProviderInterfaceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.TimeUnit;

public class Provider1 {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("provider");


        ProtocolConfig exposeProtocol = new ProtocolConfig();
        exposeProtocol.setName("dubbo");
        exposeProtocol.setPort(12026);


        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");


        ServiceConfig<ProviderInterface> serviceConfig = new ServiceConfig();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRef(new ProviderInterfaceImpl("provider1"));
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(exposeProtocol);
        serviceConfig.setGroup("a");
        serviceConfig.setInterface(ProviderInterface.class);
        serviceConfig.export();


        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
