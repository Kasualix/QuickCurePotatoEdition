package com.teampotato.quickcurepe.mixin;

import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ZombieVillagerEntity.class)
public abstract class MixinZombieVillagerEntity extends ZombieEntity {
    @Shadow protected abstract void finishConversion(ServerWorld serverWorld);

    public MixinZombieVillagerEntity(World world) {
        super(world);
    }

    @Inject(method = "startConverting", at = @At("HEAD"), cancellable = true)
    private void onStartConverting(UUID pConversionStarter, int pConversionTime, CallbackInfo ci) {
        if (this.level instanceof ServerWorld){
            this.finishConversion((ServerWorld) this.level);
            ci.cancel();
        }
    }
}