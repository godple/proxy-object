package net.proxy.lib;

import com.google.common.collect.ImmutableMap;
import net.proxy.lib.model.ArtifactInterface;

import java.util.Map;

public class ProxyStaticCaller extends AbstractCaller {
    public ProxyStaticCaller(Class clazz,
                             ArtifactInterface artifact) {
        this(clazz, artifact, ImmutableMap.<String, MethodDesriptor.Result>of());
    }

    public ProxyStaticCaller(Class clazz,
                             ArtifactInterface artifact,
                             Map<String, MethodDesriptor.Result> data) {
        super(clazz, artifact, data);
    }

    @Override
    public Object see() {
        return null;
    }
}
