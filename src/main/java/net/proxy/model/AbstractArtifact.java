package net.proxy.model;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.proxy.ProxyVersionedInterface;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static net.proxy.ProxyUtils.UNKNOWN_VALUE;
import static net.proxy.ProxyUtils.UNKNOWN_VERSION;

public abstract class AbstractArtifact implements ArtifactInterface {
    public static final ArtifactInterface UKNOWN_ARTIFACT = new AbstractArtifact("/",
            UNKNOWN_VALUE,
            ImmutableSet.of(),
            AbstractArtifact.class,
    null,
    null,
            UNKNOWN_VERSION) {
        @Override
        public SourceType getSourceType() {
            return SourceType.UNKNOWN;
        }

        @Override
        public LibCallback getCallback() {
            return null;
        }

        @Override
        public List<URL> load() {
            return ImmutableList.of();
        }

        @Override
        public ArtifactInterface fromVersion(ProxyVersionedInterface version) {
            return this;
        }

        @Override
        public String getLabel() {
            return this.getVersion().getLabel();
        }
    };

    private final String root;
    private final String name;
    private final Set<String> extensions;
    protected final Class<?> clazz;
    private final File destination;
    protected final Predicate<String> predicate;
    private final ProxyVersionedInterface versionInfo;

    public AbstractArtifact(String root,
                            String name,
                            Set<String> extensions,
                            Class<?> clazz,
                            File destination,
                            Predicate<String> predicate,
                            ProxyVersionedInterface versionInfo) {
        this.root = root;
        this.name = name;
        this.extensions = extensions;
        this.clazz = clazz;
        this.destination = destination;
        this.predicate = predicate;
        this.versionInfo = versionInfo;
    }

    @Override
    public File toPath() {
        URL resource = clazz.getResource("/" + getPath());
        if (resource == null) {
            return new File(getPath());
        }

        return new File(resource.getFile());
    }

    @Override
    public File toParentPath() {
        URL resource = clazz.getResource("/");
        if (resource == null) {
            return new File("/" + getPath());
        }

        return new File(
                new File(resource.getFile()).getParentFile(), getPath()
        );
    }

    @Override
    public String getRoot() {
        return root;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<String> getExtensions() {
        return extensions;
    }

    @Override
    public File getDestination(String from) {
        return destination;
    }

    @Override
    public boolean hasFallback() {
        return false;
    }

    @Override
    public ArtifactInterface getFallback() {
        return null;
    }

    @Override
    public boolean isTemp() {
        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("root", root)
                .add("name", name)
                .add("extension", Joiner.on(',').join(extensions))
                .add("destination", destination.getAbsolutePath())
                .add("sourceType", this.getSourceType())
                .toString();
    }

    protected String getPath() {
        return this.root + "/" + this.name;
    }

    @Override
    public Predicate<String> getPredicate() {
        return this.predicate;
    }

    @Override
    public List<String> forClasses() {
        return ImmutableList.of();
    }

    @Override
    public boolean isUseSystemLoader() {
        return true;
    }

    @Override
    public ProxyVersionedInterface getVersion() {
        return this.versionInfo;
    }
}