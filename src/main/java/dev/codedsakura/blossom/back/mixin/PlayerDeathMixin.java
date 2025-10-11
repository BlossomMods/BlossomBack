package dev.codedsakura.blossom.back.mixin;

import dev.codedsakura.blossom.back.BlossomBack;
import dev.codedsakura.blossom.lib.teleport.TeleportUtils.TeleportDestination;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
class PlayerDeathMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    void onPlayerDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity self = (ServerPlayerEntity) (Object) this;

        TeleportDestination deathPoint = new TeleportDestination(self);
        BlossomBack.DEATHS.put(self.getUuid(), deathPoint);
        BlossomBack.onPlayerDeathHook();
        BlossomBack.LOGGER.info("{} ({}) died at {}", self.getGameProfile().name(), self.getUuid(), deathPoint);
    }
}
