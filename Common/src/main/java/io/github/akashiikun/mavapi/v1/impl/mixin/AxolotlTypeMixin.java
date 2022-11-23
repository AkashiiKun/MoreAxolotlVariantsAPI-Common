/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 AkashiiKun
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.akashiikun.mavapi.v1.impl.mixin;

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.entity.passive.AxolotlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxolotlEntity.Variant.class)
public class AxolotlTypeMixin implements AxolotlTypeExtension {
    @Unique
    private MoreAxolotlVariant metadata;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void mavapi$init(String string, int i, int id, String name, boolean natural, CallbackInfo ci) {
        metadata = MoreAxolotlVariant.make((AxolotlEntity.Variant) (Object) this);
    }
    @Override
    public MoreAxolotlVariant mavapi$metadata() {
        return metadata;
    }


    @Inject(method = "getName", at = @At(("HEAD")), cancellable = true)
    public void mavapi$getName(CallbackInfoReturnable<String> cir) {
        if (MoreAxolotlVariant.p && metadata.isModded()) cir.setReturnValue("car");
    }
}