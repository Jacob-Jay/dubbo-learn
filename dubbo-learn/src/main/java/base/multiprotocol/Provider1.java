package base.multiprotocol;

import base.ProviderInterface;
import base.ProviderInterfaceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 一个provider可以通过多种协议暴露，这是只是使用dubbo不同端口演示
 */
public class Provider1 {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("provider");


        ProtocolConfig exposeProtocol = new ProtocolConfig();
        exposeProtocol.setName("dubbo");
        exposeProtocol.setPort(12026);

        ProtocolConfig exposeProtocol2 = new ProtocolConfig();
        exposeProtocol2.setName("dubbo");
        exposeProtocol2.setPort(12027);


        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");


        ServiceConfig<ProviderInterface> serviceConfig = new ServiceConfig();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRef(new ProviderInterfaceImpl("provider1"));
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocols(Arrays.asList(exposeProtocol,exposeProtocol2));
        serviceConfig.setInterface(ProviderInterface.class);
        serviceConfig.setRegister(false);
        serviceConfig.export();


        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
