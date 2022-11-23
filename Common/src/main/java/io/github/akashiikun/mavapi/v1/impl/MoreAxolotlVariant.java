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

package io.github.akashiikun.mavapi.v1.impl;

import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;

public class MoreAxolotlVariant {
    public static boolean p = false;
    private boolean modded = false;
    private Identifier id;
    private AxolotlEntity.Variant type;
    public static MoreAxolotlVariant make(AxolotlEntity.Variant type) {
        MoreAxolotlVariant moreAxolotlVariant = new MoreAxolotlVariant();
        moreAxolotlVariant.type = type;
        return moreAxolotlVariant;
    }

    public void modded() {
        modded = true;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public boolean isModded() {
        return modded;
    }

    public Identifier getId() {
        return !modded ? new Identifier(type.getName()) : id;
    }
}