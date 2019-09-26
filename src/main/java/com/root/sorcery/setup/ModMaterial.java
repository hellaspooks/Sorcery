package com.root.sorcery.setup;

import com.root.sorcery.item.ModItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;

import java.util.function.Supplier;

    public enum ModMaterial implements IItemTier {
        CHONDRITE(2, 300, 6.5F, 2.0F, 18, () -> {
            return Ingredient.fromItems(ModItem.chondrite_ingot);
        }),
        SIDERITE(3, 1800, 8.5F, 3.5F, 20, () -> {
            return Ingredient.fromItems(ModItem.siderite_ingot);
        });

        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final LazyLoadBase<Ingredient> repairMaterial;

        private ModMaterial(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
            this.harvestLevel = harvestLevelIn;
            this.maxUses = maxUsesIn;
            this.efficiency = efficiencyIn;
            this.attackDamage = attackDamageIn;
            this.enchantability = enchantabilityIn;
            this.repairMaterial = new LazyLoadBase(repairMaterialIn);
        }

        public int getMaxUses() {
            return this.maxUses;
        }

        public float getEfficiency() {
            return this.efficiency;
        }

        public float getAttackDamage() {
            return this.attackDamage;
        }

        public int getHarvestLevel() {
            return this.harvestLevel;
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public Ingredient getRepairMaterial() {
            return (Ingredient)this.repairMaterial.getValue();
        }

    }


