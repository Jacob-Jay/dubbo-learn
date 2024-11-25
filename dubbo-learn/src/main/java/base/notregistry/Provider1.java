package base.notregistry;

import base.ProviderInterface;
import base.ProviderInterfaceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.TimeUnit;


/**
 * 当还未开发完毕的服务提供者 可以不注册到注册中心，通过直连进行测试
 *  serviceConfig.setRegister(false);//不注册
 *   registryConfig.setAddress("zookeeper://127.0.0.1:2181？register=false");
 */
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
        serviceConfig.setInterface(ProviderInterface.class);
        serviceConfig.setRegister(false);//不注册
        serviceConfig.export();


        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
