package com.mdt168.mysticEnchantments.resources.item;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.custom.Gradient;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class MysticSword extends MysticItem {
    private static final NamespacedKey damageKey =
            new NamespacedKey(MysticEnchantments.getInstance(), "damage_modifier");
    private static final NamespacedKey attackSpeedKey =
            new NamespacedKey(MysticEnchantments.getInstance(), "attack_speed_modifier");

    public MysticSword(Material material, String name, String description, double damage, double attackSpeed) {
        super(material, name, description);

        setAttackDamage(this.itemStack, damage);
        setAttackSpeed(this.itemStack, attackSpeed);

        setLore(List.of(
                Gradient.BLUE + "<!i>---------------------",
                Gradient.GREEN + "<!i>" + description,
                "<!i>Damage: " + damage,
                "<!i>Attack Speed: " + attackSpeed,
                Gradient.BLUE + "---------------------"
        ));
    }
    public MysticSword(Material material, int durability, String name, String description, double damage, double attackSpeed) {
        super(material, name, description);

        setAttackDamage(this.itemStack, damage);
        setAttackSpeed(this.itemStack, attackSpeed);

        setDurability(durability);

        setLore(List.of(
                Gradient.BLUE + "<!i>---------------------",
                Gradient.GREEN + "<!i>" + description,
                "<!i>Damage: " + damage,
                "<!i>Attack Speed: " + attackSpeed,
                Gradient.BLUE + "---------------------"
        ));
    }

    private void setAttackDamage(ItemStack item, double totalDamage) {
        ItemAttributeModifiers current = item.getData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();
        if (current != null) {
            for (var entry : current.modifiers()) {
                if (entry.attribute() != Attribute.ATTACK_DAMAGE || entry.getGroup() != EquipmentSlotGroup.MAINHAND) {
                    builder.addModifier(entry.attribute(), entry.modifier(), entry.getGroup());
                }
            }
        }
        builder.addModifier(Attribute.ATTACK_DAMAGE,
                new AttributeModifier(damageKey, totalDamage, AttributeModifier.Operation.ADD_NUMBER),
                EquipmentSlotGroup.MAINHAND);
        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
    }

    private void setAttackSpeed(ItemStack item, double totalSpeed) {
        ItemAttributeModifiers current = item.getData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();
        if (current != null) {
            for (var entry : current.modifiers()) {
                if (entry.attribute() != Attribute.ATTACK_SPEED || entry.getGroup() != EquipmentSlotGroup.MAINHAND) {
                    builder.addModifier(entry.attribute(), entry.modifier(), entry.getGroup());
                }
            }
        }
        builder.addModifier(Attribute.ATTACK_SPEED,
                new AttributeModifier(attackSpeedKey, totalSpeed, AttributeModifier.Operation.ADD_NUMBER),
                EquipmentSlotGroup.MAINHAND);
        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
    }
}
