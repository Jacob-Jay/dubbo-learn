package base.thread;

import base.ProviderInterface;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance;

import java.util.concurrent.TimeUnit;


/**
 * 通过referenceConfig.setLoadbalance(RoundRobinLoadBalance.NAME); 设置负载均衡策略，如果没有设置时默认是random的
 */
public class Consumer {
    public static void main(String[] args) {


        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("consumer");

        ReferenceConfig<ProviderInterface> referenceConfig = new ReferenceConfig();
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(ProviderInterface.class);
        referenceConfig.setApplication(applicationConfig);

        referenceConfig.setLoadbalance(RoundRobinLoadBalance.NAME);
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
