package base.multiprotocol;

import base.ProviderInterface;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.util.concurrent.TimeUnit;


/**
 * 不通过注册中心，直接访问给定的服务提供者
 */
public class Consumer {
    public static void main(String[] args) {


        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("consumer");

        ReferenceConfig<ProviderInterface> referenceConfig = new ReferenceConfig();
        referenceConfig.setUrl("dubbo://localhost:12026");
        referenceConfig.setInterface(ProviderInterface.class);
        referenceConfig.setApplication(applicationConfig);

        ProviderInterface providerInterface = referenceConfig.get();

        for (int i = 0; i < 10; i++) {
            System.out.println(providerInterface.say());
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
