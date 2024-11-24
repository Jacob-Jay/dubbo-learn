package base;

public class ProviderInterfaceImpl implements ProviderInterface{

    private String name;

    public ProviderInterfaceImpl(String name) {
        this.name = name;
    }

    @Override
    public String say() {
        return name;
    }
}
