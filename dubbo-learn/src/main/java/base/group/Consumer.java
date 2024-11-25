package base.group;

import base.ProviderInterface;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance;

import java.util.concurrent.TimeUnit;


/**
 * provider  和 comsumer   通过setGroup设置分组，    只会返回匹配的provider，如果消费者设置* 返回所有组   没设置一个都不返回
 * 但是好像*时只会返回一个
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
        referenceConfig.setGroup("*");

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
