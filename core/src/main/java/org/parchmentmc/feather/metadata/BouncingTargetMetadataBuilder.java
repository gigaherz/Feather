package org.parchmentmc.feather.metadata;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;

public class BouncingTargetMetadataBuilder implements BouncingTargetMetadata
{

    public static BouncingTargetMetadataBuilder create() {
        return new BouncingTargetMetadataBuilder();
    }

    public static BouncingTargetMetadataBuilder create(final BouncingTargetMetadata bouncingTargetMetadata) {
        if (bouncingTargetMetadata == null)
            return create();

        return new BouncingTargetMetadataBuilder(
          bouncingTargetMetadata.getTarget().orElse(null),
          bouncingTargetMetadata.getOwner().orElse(null)
        );
    }

    private MethodReference target = null;
    private MethodReference owner = null;

    private BouncingTargetMetadataBuilder()
    {
    }

    private BouncingTargetMetadataBuilder(final MethodReference target, final MethodReference owner)
    {
        this.target = target;
        this.owner = owner;
    }

    public BouncingTargetMetadataBuilder withTarget(final MethodReference target) {
        this.target = target;
        return this;
    }

    public BouncingTargetMetadataBuilder withOwner(final MethodReference owner) {
        this.owner = owner;
        return this;
    }

    public BouncingTargetMetadataBuilder merge(final @Nullable BouncingTargetMetadata source) {
        if (source == null)
            return this;

        if (source.getTarget().isPresent()) {
            if (this.target == null) {
                this.target = source.getTarget().get();
            }
            else {
                this.target = MethodReferenceBuilder.create(this.target)
                  .merge(source.getTarget().get())
                  .build();
            }
        }

        if (source.getOwner().isPresent()) {
            if (this.owner == null) {
                this.owner = source.getOwner().get();
            }
            else {
                this.owner = MethodReferenceBuilder.create(this.owner)
                  .merge(source.getOwner().get())
                  .build();
            }
        }

        return this;
    }

    @Override
    public Optional<MethodReference> getTarget()
    {
        return Optional.ofNullable(target);
    }

    @Override
    public Optional<MethodReference> getOwner()
    {
        return Optional.ofNullable(owner);
    }


    public @NonNull BouncingTargetMetadata build() {
        return new ImmutableBouncingTargetMetadata(
          target,
          owner
        );
    }

    @Override
    public @NonNull BouncingTargetMetadata toImmutable()
    {
        return build();
    }
}
