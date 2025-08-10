package com.mdt168.mysticEnchantments.custom;

import com.destroystokyo.paper.MaterialTags;
import com.mdt168.mysticEnchantments.MysticEnchantments;
import com.mdt168.mysticEnchantments.craft.builders.ItemStackBuilder;
import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import com.mdt168.mysticEnchantments.enchants.*;
import com.mdt168.mysticEnchantments.craft.recipes.MysticRecipe;
import com.mdt168.mysticEnchantments.dataUtils.ScrollOfPreservationUtils;
import com.mdt168.mysticEnchantments.settings.Setting;
import com.mdt168.mysticEnchantments.settings.Settings;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.registry.keys.tags.BlockTypeTagKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static com.mdt168.mysticEnchantments.enchants.MysticEnchants.NIGHT_OWL;

@SuppressWarnings("UnstableApiUsage")
public class Helper {

    public static final Map<UUID, BukkitTask> bossBarShowers = new HashMap<>();

    public static List<Setting> settings = getSettingsList();

    public static List<Setting> getSettings() {
        return settings;
    }

    public static <T> List<List<T>> partitionList(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }


    public static ItemStack getPagerRight() {
        return ItemStackBuilder.of(Material.PLAYER_HEAD)
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM5OWU1ZGE4MmVmNzc2NWZkNWU0NzJmMzE0N2VkMTE4ZDk4MTg4NzczMGVhN2JiODBkN2ExYmVkOThkNWJhIn19fQ==")
                .setDisplayName(Gradient.GREEN + "<!i>Next Page")
                .build();
    }
    public static ItemStack getPagerLeft() {
        return ItemStackBuilder.of(Material.PLAYER_HEAD)
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZlYmFhNDFkMWQ0MDVlYjZiNjA4NDViYjlhYzcyNGFmNzBlODVlYWM4YTk2YTU1NDRiOWUyM2FkNmM5NmM2MiJ9fX0=")
                .setDisplayName(Gradient.GREEN + "Previous Page")
                .build();
    }

    private static List<Setting> getSettingsList() {
        List<Setting> settings = new ArrayList<>();
        Field[] fields = Settings.class.getDeclaredFields();
        for (Field field : fields) {
            if (Setting.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    settings.add((Setting) field.get(null));
                } catch (Exception ignored) {}
            }
        }
        return settings;
    }

    public static void handlePortableRepairer(ItemStack portableRepairer, ItemStack otherItem, Player player) {
        if (otherItem.getType().isAir()) {
            Helper.sendWarningMessage(player, "Hold something in your other hand to repair!");
            return;
        }
        if (!(otherItem.getItemMeta() instanceof Damageable damageable)) {
            Helper.sendWarningMessage(player, Helper.getRawName(otherItem) + " is not damageable!");
        } else if (damageable.hasDamageValue()) {
            Integer maxDamage = otherItem.getData(DataComponentTypes.MAX_DAMAGE);
            if (maxDamage != null) {
                int currentDamage = damageable.getDamage();
                if (currentDamage >= maxDamage) {
                    Helper.sendWarningMessage(player, Helper.getRawName(otherItem) + " is fully repaired!");
                } else {
                    Helper.sendMessage(player, Gradient.YELLOW + getRawName(otherItem) + " has been repaired!");
                    damageable.setDamage(0);
                    portableRepairer.damage(1, player);
                    otherItem.setItemMeta(damageable);
                }
            }
        } else {
            Helper.sendWarningMessage(player, Helper.getRawName(otherItem) + " is not damaged!");
        }
    }

    public static boolean isOnSolidGround(Player player) {
        Location loc = player.getLocation().clone().subtract(0, 0.1, 0); // a bit below feet
        Block below = loc.getBlock();
        return below.getType().isSolid() && below.getType().isOccluding();
    }

    public static int randomInt(int min, int max) {
        if (min > max) return 0;
        if (min == max) return max;

        return random.nextInt(max - min + 1) + min;
    }

    public static List<String> allowedAnvilItems = List.of(
            "arcane_amplifier",
            "enchantment_extractor"
    );
    public static ArrayList<UUID> onCooldownVanishStep = new ArrayList<>();
    public static ArrayList<UUID> onCooldownCombatIntuition = new ArrayList<>();
    public static boolean ignoredBlockBreakEvent = false;
    public static boolean isVeinMining = false;
    public static Block originBlock = null;
    public static final Random random = new Random();


    public static void tickEvent(Player player) {
        PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, 11 * 20, 38, false, false, false);
        if (PlayerDataUtils.checkIfDataExists(player, NIGHT_OWL.getId())) {
            player.addPotionEffect(nightVision);
        }
    }

    public static void handleSaving(Player player, Inventory inventory) {
        if (Helper.isBag(inventory)) {
            PersistentDataContainer container = player.getPersistentDataContainer();
            byte[] bytes = container.get(ItemDataUtils.mysticalBagKey, PersistentDataType.BYTE_ARRAY);
            if (bytes != null) {
                ItemStack bag = ItemStack.deserializeBytes(bytes);
                String sUuid = bag.getItemMeta().getPersistentDataContainer().get(ItemDataUtils.bagKeyUuid, PersistentDataType.STRING);
                if (sUuid != null) {
                    UUID uuid = UUID.fromString(sUuid);
                    for (ItemStack itemStack : player.getInventory().getContents()) {
                        if (itemStack == null) continue;
                        ItemMeta meta = itemStack.getItemMeta();
                        if (meta == null) continue;
                        PersistentDataContainer pdc = meta.getPersistentDataContainer();
                        String uuidString = pdc.get(ItemDataUtils.bagKeyUuid, PersistentDataType.STRING);
                        if (uuidString == null) continue;
                        UUID uuid1 = UUID.fromString(uuidString);
                        if (uuid1.equals(uuid)) {
                            ItemDataUtils.saveInventoryToItem(itemStack, inventory);
                        }
                    }
                }
            }
        }
    }

    public static void applyKnockbackAround(Player player, double radius, double strength) {
        Location playerLoc = player.getLocation();
        List<Entity> nearby = player.getWorld().getNearbyEntities(playerLoc, radius, radius, radius).stream()
                .filter(e -> e instanceof LivingEntity && e != player)
                .toList();

        for (Entity entity : nearby) {
            Vector direction = entity.getLocation().toVector().subtract(playerLoc.toVector()).normalize().multiply(strength);
            direction.setY(0.3); // slight upward push (optional)
            entity.setVelocity(direction);
        }
    }
    public static boolean isPickaxeMineable(Block block) {
        return Tag.MINEABLE_PICKAXE.isTagged(block.getType());
    }

    public static boolean isShovelMineable(Block block) {
        return Tag.MINEABLE_SHOVEL.isTagged(block.getType());
    }

    public static int secondsToTicks(int seconds) {
        return seconds * 20;
    }

    public static List<Block> getNearbyBlocks(Block center, int radius) {
        List<Block> nearby = new ArrayList<>();
        World world = center.getWorld();
        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = world.getBlockAt(cx + x, cy + y, cz + z);
                    nearby.add(b);
                }
            }
        }

        return nearby;
    }

    public static boolean isMeat(Material material) {
        return switch (material) {
            case BEEF, PORKCHOP, MUTTON, CHICKEN, RABBIT, COD, SALMON, TROPICAL_FISH, PUFFERFISH,
                 COOKED_BEEF, COOKED_PORKCHOP, COOKED_MUTTON, COOKED_CHICKEN, COOKED_RABBIT,
                 COOKED_COD, COOKED_SALMON -> true;
            default -> false;
        };
    }

    public static boolean isRawMeat(Material material) {
        return switch (material) {
            case BEEF, PORKCHOP, MUTTON, CHICKEN, RABBIT, COD, SALMON -> true;
            default -> false;
        };
    }

    public static boolean isAnimal(LivingEntity entity) {
        return entity.getSpawnCategory() == SpawnCategory.ANIMAL;
    }

    public static boolean isOverallAnimal(LivingEntity entity) {
        return entity.getSpawnCategory() == SpawnCategory.ANIMAL || entity.getSpawnCategory() == SpawnCategory.WATER_AMBIENT;
    }

    public static String turnToStringTicks(int ticks) {
        int totalSeconds = ticks / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        StringBuilder sb = new StringBuilder();
        if (minutes > 0) {
            sb.append(minutes).append(" minute").append(minutes == 1 ? "" : "s");
        }
        if (minutes > 0 && seconds > 0) {
            sb.append(" and ");
        }
        if (seconds > 0 || (minutes == 0 && seconds == 0)) {
            sb.append(seconds).append(" second").append(seconds == 1 ? "" : "s");
        }

        return sb.toString();
    }

    public static void applyKnockbackAround(Player player, double radius, double strength, double upWardPush) {
        Location playerLoc = player.getLocation();
        List<Entity> nearby = player.getWorld().getNearbyEntities(playerLoc, radius, radius, radius).stream()
                .filter(e -> e instanceof LivingEntity && e != player)
                .toList();

        for (Entity entity : nearby) {
            Vector direction = entity.getLocation().toVector().subtract(playerLoc.toVector()).normalize().multiply(strength);
            direction.setY(upWardPush); // slight upward push (optional)
            entity.setVelocity(direction);
        }
    }
    public static void applyKnockbackAround(LivingEntity from, double radius, double strength, double upWardPush) {
        Location fromLoc = from.getLocation();
        List<Entity> nearby = from.getWorld().getNearbyEntities(fromLoc, radius, radius, radius).stream()
                .filter(e -> e instanceof LivingEntity && e != from)
                .toList();

        for (Entity entity : nearby) {
            Vector direction = entity.getLocation().toVector().subtract(fromLoc.toVector()).normalize().multiply(strength);
            direction.setY(upWardPush); // slight upward push (optional)
            entity.setVelocity(direction);
        }
    }
    public static void applyKnockbackAround(LivingEntity from, double radius, double strength, double upWardPush, LivingEntity bypass) {
        Location fromLoc = from.getLocation();
        List<Entity> nearby = from.getWorld().getNearbyEntities(fromLoc, radius, radius, radius).stream()
                .filter(e -> e instanceof LivingEntity && e != from && e != bypass)
                .toList();

        for (Entity entity : nearby) {
            Vector direction = entity.getLocation().toVector().subtract(fromLoc.toVector()).normalize().multiply(strength);
            direction.setY(upWardPush); // slight upward push (optional)
            entity.setVelocity(direction);
        }
    }

    public static void applyKnockback(LivingEntity centerEntity, LivingEntity entity, double strength) {
        Vector direction = entity.getLocation().toVector().subtract(centerEntity.getLocation().toVector());
        if (direction.lengthSquared() == 0) return; // Prevent NaN
        direction.normalize().multiply(strength).setY(0.3);
        entity.setVelocity(direction);
    }

    public static void applyKnockback(LivingEntity centerEntity, LivingEntity entity, double strength, double upwardPush) {
        Vector direction = entity.getLocation().toVector().subtract(centerEntity.getLocation().toVector());
        if (direction.lengthSquared() == 0) return; // Prevent NaN
        direction.normalize().multiply(strength).setY(upwardPush);
        entity.setVelocity(direction);
    }
    public static void applyKnockback(Entity centerEntity, Entity entity, double strength, double upwardPush) {
        Vector direction = entity.getLocation().toVector().subtract(centerEntity.getLocation().toVector());
        if (direction.lengthSquared() == 0) return; // Prevent NaN
        direction.normalize().multiply(strength).setY(upwardPush);
        entity.setVelocity(direction);
    }

    public static List<LivingEntity> getNearbyLivingEntities(Location location, int range) {
        List<LivingEntity> result = new ArrayList<>();
        for (Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
            if (entity instanceof LivingEntity living) {
                result.add(living);
            }
        }
        return result;
    }

    private static final Collection<BlockType> CROPS = Registry.BLOCK.getTag(BlockTypeTagKeys.CROPS)
            .resolve(Registry.BLOCK);
    private static final Collection<BlockType> LOGS = Registry.BLOCK.getTag(BlockTypeTagKeys.LOGS)
            .resolve(Registry.BLOCK);

    public static boolean isCrop(BlockState state) {
        return CROPS.contains(state.getType().asBlockType());
    }
    public static boolean isCrop(Block block) {
        return CROPS.contains(block.getType().asBlockType());
    }


    public static boolean isAtMaxAge(Block block) {
        return block.getBlockData() instanceof Ageable ageable &&
                ageable.getAge() >= ageable.getMaximumAge();
    }
    public static void makeItSmall(Block block) {
        if (block.getBlockData() instanceof Ageable ageable) {
            ageable.setAge(0);
            block.setBlockData(ageable);
        }
    }

    public static ItemStack getSeed(BlockState state) {
        return switch (state.getType()) {
            case WHEAT_SEEDS -> new ItemStack(Material.WHEAT_SEEDS);
            case BEETROOT_SEEDS -> new ItemStack(Material.BEETROOT_SEEDS);
            case CARROT -> new ItemStack(Material.CARROT);
            case POTATO -> new ItemStack(Material.POTATO);
            default -> new ItemStack(Material.AIR);
        };
    }

    public static Material getSeedMat(BlockState state) {
        return switch (state.getType()) {
            case WHEAT -> Material.WHEAT_SEEDS;
            case BEETROOT -> Material.BEETROOT_SEEDS;
            case POTATOES -> Material.POTATO;
            case CARROTS -> Material.CARROT;
            default -> Material.AIR;
        };
    }

    public static boolean isLog(BlockState state) {
        return LOGS.contains(state.getType().asBlockType());
    }

    public static boolean isOre(Block block) {
        if (block == null) return false;
        return MaterialTags.ORES.isTagged(block);
    }

    public static void doubleItemsList(List<Item> itemsList) {
        List<Item> toAdd = new ArrayList<>();
        if (itemsList.isEmpty()) return;
        World world = itemsList.getFirst().getWorld();
        for (Item item : itemsList) {
            toAdd.add(world.dropItemNaturally(item.getLocation(), item.getItemStack()));
        }
        itemsList.addAll(toAdd);
    }
    public static void tripleItemStackList(Collection<ItemStack> itemsList) {
        Collection<ItemStack> toAdd = new ArrayList<>();
        for (ItemStack itemStack : itemsList) {
            toAdd.add(new ItemStack(itemStack));
            toAdd.add(new ItemStack(itemStack));
        }
        itemsList.addAll(toAdd);
    }
    public static boolean isOre(BlockState blockState) {
        return MaterialTags.ORES.isTagged(blockState);
    }

    public static ItemStack getSapling(BlockState state) {
        return switch (state.getType()) {
            case Material.OAK_LEAVES -> new ItemStack(Material.OAK_SAPLING);
            case Material.SPRUCE_LEAVES -> new ItemStack(Material.SPRUCE_SAPLING);
            case Material.BIRCH_LEAVES -> new ItemStack(Material.BIRCH_SAPLING);
            case Material.ACACIA_LEAVES -> new ItemStack(Material.ACACIA_SAPLING);
            case Material.CHERRY_LEAVES -> new ItemStack(Material.CHERRY_SAPLING);
            case Material.DARK_OAK_LEAVES -> new ItemStack(Material.DARK_OAK_SAPLING);
            case Material.PALE_OAK_LEAVES -> new ItemStack(Material.PALE_OAK_SAPLING);
            default -> new ItemStack(Material.AIR);
        };
    }

    public static void spawnParticleLine(World world, Particle particle, Entity from, Entity to, double spacing) {
        Location start = from.getLocation().add(0, from.getHeight() / 2.0, 0);
        Location end = to.getLocation().add(0, to.getHeight() / 2.0, 0);
        Vector direction = end.toVector().subtract(start.toVector());
        double distance = direction.length();
        direction.normalize().multiply(spacing);
        int steps = (int) (distance / spacing);
        for (int i = 0; i <= steps; i++) {
            Location point = start.clone().add(direction.clone().multiply(i));
            world.spawnParticle(particle, point, 1, 0, 0, 0, 0);
        }
    }

    public static double round(double number, int places) {
        return Math.ceil(number * Math.pow(10, places)) / Math.pow(10, places);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static LiteralCommandNode<CommandSourceStack> getMysticResourcesGiverCommand() {
        return Commands.literal("mysticresources")
                .requires(source -> source.getSender().hasPermission("mysticresources.use"))
                .then(Commands.literal("remove")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.literal("sap")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticCurrencyUtils.removeSap(target, amount);
                                                    Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " saps<gold> has been removed from <green>" + target.getName());
                                                    Helper.sendMessage(target, "<green>" + amount + " saps <yellow>has been removed from your balance!");
                                                    return 0;
                                                })))
                                .then(Commands.literal("fragment")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticCurrencyUtils.removeFragments(target, amount);
                                                    Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " fragments<gold> has been removed from <green>" + target.getName());
                                                    Helper.sendMessage(target, "<green>" + amount + " fragments <yellow>has been removed from your balance!");
                                                    return 0;
                                                })))
                                .then(Commands.literal("soul")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticCurrencyUtils.removeSouls(target, amount);
                                                    Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " souls<gold> has been removed from <green>" + target.getName());
                                                    Helper.sendMessage(target, "<green>" + amount + " souls <yellow>has been removed from your balance!");
                                                    return 0;
                                                })))))
                .then(Commands.literal("set")
                        .then(Commands.literal("sap")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ctx -> {
                                            Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                            int amount = ctx.getArgument("amount", Integer.class);
                                            MysticCurrencyUtils.setSap(target, amount);
                                            Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " saps<gold> has been set as the balance of <green>" + target.getName());
                                            Helper.sendMessage(target, "<green>" + amount + " saps <yellow>has been set as your balance!");
                                            return 0;
                                        })))
                        .then(Commands.literal("fragment")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ctx -> {
                                            Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                            int amount = ctx.getArgument("amount", Integer.class);
                                            MysticCurrencyUtils.setFragments(target, amount);
                                            Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " fragments<gold> has been set as the balance of <green>" + target.getName());
                                            Helper.sendMessage(target, "<green>" + amount + " fragments <yellow>has been set as your balance!");
                                            return 0;
                                        })))
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.literal("soul")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticCurrencyUtils.setSouls(target, amount);
                                                    Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " souls<gold> has been set as the balance of <green>" + target.getName());
                                                    Helper.sendMessage(target, "<green>" + amount + " souls <yellow>has been set as your balance!");
                                                    return 0;
                                                })))))
                .then(Commands.literal("give")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.literal("sap")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticCurrencyUtils.addSap(target, amount);
                                                    Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " saps<gold> was successfully added to <green>" + target.getName());
                                                    Helper.sendMessage(target, "<green>" + amount + " saps <yellow>got added to your balance!");
                                                    return 0;
                                                })))
                                .then(Commands.literal("fragment")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticCurrencyUtils.addFragments(target, amount);
                                                    Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " fragments<gold> was successfully added to <green>" + target.getName());
                                                    Helper.sendMessage(target, "<green>" + amount + " fragments <yellow>got added to your balance!");
                                                    return 0;
                                                })))
                                .then(Commands.literal("soul")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ctx -> {
                                                    Player target = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                                    int amount = ctx.getArgument("amount", Integer.class);
                                                    MysticCurrencyUtils.addSouls(target, amount);
                                                    Helper.sendMessage(ctx.getSource().getSender(), "<green>" + amount + " souls<gold> was successfully added to <green>" + target.getName());
                                                    Helper.sendMessage(target, "<green>" + amount + " souls <yellow>got added to your balance!");
                                                    return 0;
                                                }))))
                ).build();

    }

    @SuppressWarnings("UnstableApiUsage")
    public static LiteralCommandNode<CommandSourceStack> getEnchantedCrateCommand() {
        return Commands.literal("enchantedcrate")
                .requires(source -> source.getSender().hasPermission("enchantedcrate.use"))
                .then(Commands.literal("give")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ctx -> {
                                            Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                            int amount = ctx.getArgument("amount", Integer.class);
                                            EnchantedCrateUtils.addKey(player, amount);
                                            Helper.sendMessage(ctx.getSource().getSender(), amount + " Enchanted Crate Keys has been added to " + Gradient.RED + player.getName());
                                            Helper.sendMessage(player, amount + " Enchanted Crate Keys has been added to your balance");
                                            return 0;
                                        }))))
                .then(Commands.literal("set")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ctx -> {
                                            Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                            int amount = ctx.getArgument("amount", Integer.class);
                                            EnchantedCrateUtils.setKey(player, amount);
                                            Helper.sendMessage(ctx.getSource().getSender(), amount + " Enchanted Crate Keys has been set as " + Gradient.RED + player.getName() + Gradient.YELLOW + " Balance");
                                            Helper.sendMessage(player, amount + " Enchanted Crate Keys has been set as your balance");
                                            return 0;
                                        }))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ctx -> {
                                            Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                            int amount = ctx.getArgument("amount", Integer.class);
                                            EnchantedCrateUtils.removeKey(player, amount);
                                            Helper.sendMessage(ctx.getSource().getSender(), amount + " Enchanted Crate Keys has been removed from " + Gradient.RED + player.getName());
                                            Helper.sendMessage(player, amount + " Enchanted Crate Keys has been removed from your balance");
                                            return 0;
                                        }))))
                .then(Commands.literal("roll")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .executes(ctx -> {
                                    Player player = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
                                    EnchantedCrateUtils.freeRollCrate(player, ctx.getSource().getSender());
                                    return 0;
                                })))
                .build();
    }

    public static boolean isLeaf(BlockState blockState) {
        return blockState.getBlockData() instanceof Leaves;
    }

    public static void runAfterDelay(int ticks, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(MysticEnchantments.getInstance(), runnable, ticks);
    }


    public static void repairTool(ItemStack tool, int repairAmount) {
        if (tool == null || !tool.hasItemMeta()) {
            return;
        }
        ItemMeta toolMeta = tool.getItemMeta();
        if (toolMeta instanceof Damageable damageable) {
            damageable.setDamage(Math.max(0, damageable.getDamage() - repairAmount));
        }
        tool.setItemMeta(toolMeta);
    }

    public static void pureDamage(LivingEntity target, Player player, double damageAmount) {
        // Store armor
        ItemStack[] originalArmor = target.getEquipment() != null
                ? target.getEquipment().getArmorContents()
                : new ItemStack[0];

        // Store potion effects
        Collection<PotionEffect> originalEffects = new ArrayList<>(target.getActivePotionEffects());

        // Store attribute modifiers related to armor & toughness
        Map<Attribute, Collection<AttributeModifier>> originalAttributes = new HashMap<>();
        Attribute[] defensiveAttributes = new Attribute[]{
                Attribute.ARMOR,
                Attribute.ARMOR_TOUGHNESS
        };

        for (Attribute attr : defensiveAttributes) {
            if (target.getAttribute(attr) != null) {
                originalAttributes.put(attr, target.getAttribute(attr).getModifiers());
                // Remove all modifiers
                for (AttributeModifier mod : target.getAttribute(attr).getModifiers()) {
                    target.getAttribute(attr).removeModifier(mod);
                }
            }
        }

        // Remove armor
        if (target.getEquipment() != null) {
            target.getEquipment().setArmorContents(null);
        }

        // Remove potion effects
        for (PotionEffect effect : originalEffects) {
            target.removePotionEffect(effect.getType());
        }

        // Now deal damage ignoring all defenses
        // Use damageAmount and player as source to credit player
        target.damage(damageAmount, player);

        // Restore armor
        if (target.getEquipment() != null) {
            target.getEquipment().setArmorContents(originalArmor);
        }

        // Restore potion effects with remaining duration and amplifier
        for (PotionEffect effect : originalEffects) {
            target.addPotionEffect(effect);
        }

        // Restore attribute modifiers
        for (Map.Entry<Attribute, Collection<AttributeModifier>> entry : originalAttributes.entrySet()) {
            for (AttributeModifier mod : entry.getValue()) {
                target.getAttribute(entry.getKey()).addModifier(mod);
            }
        }
    }

    public static void repairTool(Player player, int repairAmount) {
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (!tool.hasItemMeta()) {
            return;
        }
        ItemMeta toolMeta = tool.getItemMeta();
        if (toolMeta instanceof Damageable damageable) {
            damageable.setDamage(Math.max(0, damageable.getDamage() - repairAmount));
        }
        tool.setItemMeta(toolMeta);
    }

    public static void keepArrowStraight(Arrow arrow, int straightTicks) {
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (arrow.isDead() || ticks++ > straightTicks) {
                    this.cancel();
                    return;
                }
                Vector velocity = arrow.getVelocity();
                velocity.setY(Math.max(velocity.getY(), 0));
                velocity.multiply(1.01);
                arrow.setVelocity(velocity);
            }
        }.runTaskTimer(MysticEnchantments.getInstance(), 1L, 1L);
    }

    public static List<String> readLines(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static boolean safeGiveItem(Player player, ItemStack itemStack) {
        Map<Integer, ItemStack> leftovers = player.getInventory().addItem(itemStack);
        if (!leftovers.isEmpty()) {
            sendWarningMessage(player, "Your inventory is full!");
            return false;
        }
        return true;
    }
    public static boolean safeGiveItem(Player player, ItemStack itemStack, boolean blockMessage) {
        Map<Integer, ItemStack> leftovers = player.getInventory().addItem(itemStack);
        if (!blockMessage) Helper.sendWarningMessage(player, "Your inventory is full!");
        return leftovers.isEmpty();
    }

    public static boolean canFullyAddToInventory(Inventory inventory, ItemStack item) {
        ItemStack[] contentsCopy = inventory.getContents().clone();
        Inventory testInventory = Bukkit.createInventory(null, inventory.getSize());
        testInventory.setContents(contentsCopy);
        Map<Integer, ItemStack> leftovers = testInventory.addItem(item.clone());
        return leftovers.isEmpty();
    }
    public static boolean canFitInInventory(Player player, ItemStack stackToAdd) {
        // Get a "copy" of only the player's main storage slots (typically 36 slots)
        Inventory fakeInventory = org.bukkit.Bukkit.createInventory(null, 36);
        fakeInventory.setContents(player.getInventory().getStorageContents());

        // Try to add the item
        var leftovers = fakeInventory.addItem(stackToAdd);

        // If leftovers is empty, then it fit entirely
        return leftovers.isEmpty();
    }

    public static boolean hasEmptySlot(Player player) {
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return true;
            }
        }
        return false;
    }

    public static boolean giveBook(EnchantmentStack enchantmentStack, Player player) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        int enchantmentLevel = random.nextInt(enchantmentStack.getEnchantment().getMaxLevel()) + 1;
        enchantedBook.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments()
                .add(enchantmentStack.getEnchantment(), enchantmentLevel)
                .build());
        ItemMeta meta = enchantedBook.getItemMeta();
        int maxLevel = enchantmentStack.getEnchantment().getMaxLevel();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        List<Component> lore = new ArrayList<>();
        lore.add(miniMessage.deserialize("<!i><dark_gray>Mystic Enchantments:"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Name: </bold><green>" + enchantmentStack.getName()));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Description: </bold><gold>" + enchantmentStack.getDescription()));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Level: </bold><green>" + toRoman(enchantmentLevel) + " <yellow>(<green>" + enchantmentLevel + "<yellow>)"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Max Level: </bold><green>" + toRoman(maxLevel) + " <yellow>(<green>" + maxLevel + "<yellow>)"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Tier: </bold>" + enchantmentStack.getBookTier().toString()));
        meta.lore(lore);
        enchantedBook.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        enchantedBook.setItemMeta(meta);
        return safeGiveItem(player, enchantedBook);
    }
    public static ItemStack createFunctionalBook(EnchantmentStack enchantmentStack) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        int enchantmentLevel = random.nextInt(enchantmentStack.getEnchantment().getMaxLevel()) + 1;
        enchantedBook.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments()
                .add(enchantmentStack.getEnchantment(), enchantmentLevel)
                .build());
        ItemMeta meta = enchantedBook.getItemMeta();
        int maxLevel = enchantmentStack.getEnchantment().getMaxLevel();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        List<Component> lore = new ArrayList<>();
        lore.add(miniMessage.deserialize("<!i><dark_gray>Mystic Enchantments:"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Name: </bold><green>" + enchantmentStack.getName()));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Description: </bold><gold>" + enchantmentStack.getDescription()));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Level: </bold><green>" + toRoman(enchantmentLevel) + " <yellow>(<green>" + enchantmentLevel + "<yellow>)"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Max Level: </bold><green>" + toRoman(maxLevel) + " <yellow>(<green>" + maxLevel + "<yellow>)"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Tier: </bold>" + enchantmentStack.getBookTier().toString()));
        meta.lore(lore);
        enchantedBook.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        enchantedBook.setItemMeta(meta);
        return enchantedBook;
    }

    public static boolean giveMaxBook(EnchantmentStack enchantmentStack, Player player) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        int enchantmentLevel = enchantmentStack.getEnchantment().getMaxLevel();
        enchantedBook.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments()
                .add(enchantmentStack.getEnchantment(), enchantmentLevel)
                .build());
        ItemMeta meta = enchantedBook.getItemMeta();
        int maxLevel = enchantmentStack.getEnchantment().getMaxLevel();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        List<Component> lore = new ArrayList<>();
        lore.add(miniMessage.deserialize("<!i><dark_gray>Mystic Enchantments:"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Name: </bold><green>" + enchantmentStack.getName()));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Description: </bold><gold>" + enchantmentStack.getDescription()));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Level: </bold><green>" + toRoman(enchantmentLevel) + " <yellow>(<green>" + enchantmentLevel + "<yellow>)"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Max Level: </bold><green>" + toRoman(maxLevel) + " <yellow>(<green>" + maxLevel + "<yellow>)"));
        lore.add(miniMessage.deserialize("<!i><yellow><bold>Tier: </bold>" + enchantmentStack.getBookTier().toString()));
        meta.lore(lore);
        enchantedBook.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        enchantedBook.setItemMeta(meta);
        return safeGiveItem(player, enchantedBook);
    }

    public static String toRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length && num > 0; i++) {
            while (num >= values[i]) {
                num -= values[i];
                result.append(numerals[i]);
            }
        }

        return result.toString();
    }

    public static boolean giveBook(HumaneEnchantment humaneEnchantment, Player player, boolean stopMessage) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        enchantedBook.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        ItemDataUtils.setData(enchantedBook, humaneEnchantment.getId());
        enchantedBook.editMeta(meta -> {
            MiniMessage mm = MiniMessage.miniMessage();
            List<Component> lore = new ArrayList<>();
            lore.add(mm.deserialize("<!i><gray>Right Click While Holding To Enchant Yourself"));
            lore.add(mm.deserialize("<!i>" + humaneEnchantment.getBookTier().getColor() + humaneEnchantment.getName()));
            lore.add(mm.deserialize("<!i><gold>" + humaneEnchantment.getDescription()));
            if (humaneEnchantment.getChance() == 100) {
                lore.add(mm.deserialize("<!i><aqua>Passive"));
            } else {
                lore.add(mm.deserialize("<!i><aqua>" + humaneEnchantment.getChance() + "% Chance"));
            }
            meta.lore(lore);
        });
        return safeGiveItem(player, enchantedBook, stopMessage);
    }

    public static boolean giveBook(HumaneEnchantment humaneEnchantment, Player player) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        enchantedBook.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        ItemDataUtils.setData(enchantedBook, humaneEnchantment.getId());
        enchantedBook.editMeta(meta -> {
            MiniMessage mm = MiniMessage.miniMessage();
            List<Component> lore = new ArrayList<>();
            lore.add(mm.deserialize("<!i><gray>Right Click While Holding To Enchant Yourself"));
            lore.add(mm.deserialize("<!i>" + humaneEnchantment.getBookTier().getColor() + humaneEnchantment.getName()));
            lore.add(mm.deserialize("<!i><gold>" + humaneEnchantment.getDescription()));
            if (humaneEnchantment.getChance() == 100) {
                lore.add(mm.deserialize("<!i><aqua>Passive"));
            } else {
                lore.add(mm.deserialize("<!i><aqua>" + humaneEnchantment.getChance() + "% Chance"));
            }
            meta.lore(lore);
        });
        return safeGiveItem(player, enchantedBook);
    }
    public static ItemStack createFunctionalBook(HumaneEnchantment humaneEnchantment) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        enchantedBook.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        ItemDataUtils.addData(enchantedBook, humaneEnchantment.getId());

        enchantedBook.editMeta(meta -> {
            MiniMessage mm = MiniMessage.miniMessage();
            List<Component> lore = new ArrayList<>();
            lore.add(mm.deserialize("<!i><gray>Right Click While Holding To Enchant Yourself"));
            lore.add(mm.deserialize("<!i>" + humaneEnchantment.getBookTier().getColor() + humaneEnchantment.getName()));
            lore.add(mm.deserialize("<!i><gold>" + humaneEnchantment.getDescription()));
            if (humaneEnchantment.getChance() == 100) {
                lore.add(mm.deserialize("<!i><aqua>Passive"));
            } else {
                lore.add(mm.deserialize("<!i><aqua>" + humaneEnchantment.getChance() + "% Chance"));
            }
            meta.lore(lore);
        });
        return enchantedBook;
    }

    public static @Nullable LivingEntity getNearestHostile(Entity source, double range) {
        Location origin = source.getLocation();
        World world = source.getWorld();

        return world.getNearbyLivingEntities(origin, range).stream()
                .filter(e -> e != source) // not the source itself
                .filter(e -> e instanceof Monster) // only hostile mobs
                .filter(e -> e.isValid() && !e.isDead())
                .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(origin)))
                .orElse(null);
    }

    public static @Nullable LivingEntity getNearestEntity(Entity source, double range) {
        Location origin = source.getLocation();
        World world = source.getWorld();
        return world.getNearbyLivingEntities(origin, range).stream()
                .filter(e -> e != source)
                .filter(Objects::nonNull)
                .filter(e -> e.isValid() && !e.isDead())
                .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(origin)))
                .orElse(null);
    }

    public static @Nullable LivingEntity getNearestEntity(Entity source, double range, @NotNull Predicate<LivingEntity> filter) {
        Location origin = source.getLocation();
        World world = source.getWorld();
        return world.getNearbyLivingEntities(origin, range).stream()
                .filter(e -> e != source)
                .filter(Objects::nonNull)
                .filter(e -> e.isValid() && !e.isDead())
                .filter(filter)
                .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(origin)))
                .orElse(null);
    }


    public static void drawEntityHitboxParticles(Entity entity, Particle particle, int particlesPerEdge, double heightStep) {
        BoundingBox box = entity.getBoundingBox();
        World world = entity.getWorld();

        double minX = box.getMinX() - 0.25;
        double maxX = box.getMaxX() + 0.25;
        double minY = box.getMinY() - 0.25;
        double maxY = box.getMaxY() + 0.25;
        double minZ = box.getMinZ() - 0.25;
        double maxZ = box.getMaxZ() + 0.25;

        // Loop vertically from bottom to top of hitbox
        for (double y = minY; y <= maxY; y += heightStep) {
            // Horizontal rectangle at this Y level
            for (int i = 0; i < particlesPerEdge; i++) {
                double t = i / (double) particlesPerEdge;

                // Four corners interpolated
                double x1 = minX + (maxX - minX) * t;
                double z1 = minZ;
                double x2 = maxX;
                double z2 = minZ + (maxZ - minZ) * t;
                double x3 = maxX - (maxX - minX) * t;
                double z3 = maxZ;
                double x4 = minX;
                double z4 = maxZ - (maxZ - minZ) * t;

                world.spawnParticle(particle, x1, y, z1, 0, 0, 0, 0);
                world.spawnParticle(particle, x2, y, z2, 0, 0, 0, 0);
                world.spawnParticle(particle, x3, y, z3, 0, 0, 0, 0);
                world.spawnParticle(particle, x4, y, z4, 0, 0, 0, 0);
            }
        }
    }
    public static void drawEntityHitboxParticles(Block block, Particle particle, int particlesPerEdge, double heightStep) {
        BoundingBox box = block.getBoundingBox();
        World world = block.getWorld();

        double minX = box.getMinX() - 0.25;
        double maxX = box.getMaxX() + 0.25;
        double minY = box.getMinY() - 0.25;
        double maxY = box.getMaxY() + 0.25;
        double minZ = box.getMinZ() - 0.25;
        double maxZ = box.getMaxZ() + 0.25;

        // Loop vertically from bottom to top of hitbox
        for (double y = minY; y <= maxY; y += heightStep) {
            // Horizontal rectangle at this Y level
            for (int i = 0; i < particlesPerEdge; i++) {
                double t = i / (double) particlesPerEdge;

                // Four corners interpolated
                double x1 = minX + (maxX - minX) * t;
                double z1 = minZ;
                double x2 = maxX;
                double z2 = minZ + (maxZ - minZ) * t;
                double x3 = maxX - (maxX - minX) * t;
                double z3 = maxZ;
                double x4 = minX;
                double z4 = maxZ - (maxZ - minZ) * t;

                world.spawnParticle(particle, x1, y, z1, 0, 0, 0, 0);
                world.spawnParticle(particle, x2, y, z2, 0, 0, 0, 0);
                world.spawnParticle(particle, x3, y, z3, 0, 0, 0, 0);
                world.spawnParticle(particle, x4, y, z4, 0, 0, 0, 0);
            }
        }
    }

    public static ItemStack createBook(BookTiers bookTier, String name, double xpPrice) {
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize("<!i>" + bookTier.getColor() + name));
        meta.lore(List.of(Component.empty(), MiniMessage.miniMessage().deserialize("<!i><green>" + xpPrice + " XP")));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static double getProspectorsXpModifier(Player player) {
        EnchantmentStack basicProspector = MysticEnchants.BASIC_PROSPECTOR;
        EnchantmentStack enhancedProspector = MysticEnchants.ENHANCED_PROSPECTOR;
        EnchantmentStack refinedProspector = MysticEnchants.REFINED_PROSPECTOR;
        EnchantmentStack eliteProspector = MysticEnchants.ELITE_PROSPECTOR;
        EnchantmentStack mythicProspector = MysticEnchants.MYTHIC_PROSPECTOR;
        if (Helper.enchantmentExists(player, basicProspector)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, basicProspector);
            return (0.1 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, enhancedProspector)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, enhancedProspector);
            return (0.15 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, refinedProspector)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, refinedProspector);
            return (0.2 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, eliteProspector)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, eliteProspector);
            return (0.3 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, mythicProspector)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, mythicProspector);
            return (0.4 * enchantmentLevel);
        }
        return 0;
    }

    public static void applyUnbreakableIfNeeded(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.setUnbreakable(Helper.enchantmentExists(item, MysticEnchants.UNBREAKABLE));

        item.setItemMeta(meta);
    }


    public static double getExperiencersMysticCoinsModifier(Player player) {
        EnchantmentStack basicExperiencer = MysticEnchants.BASIC_EXPERIENCER;
        EnchantmentStack enhancedExperiencer = MysticEnchants.ENHANCED_EXPERIENCER;
        EnchantmentStack refindExperiencer = MysticEnchants.REFINED_EXPERIENCER;
        EnchantmentStack eliteExperiencer = MysticEnchants.ELITE_EXPERIENCER;
        EnchantmentStack mythicExperiencer = MysticEnchants.MYTHIC_EXPERIENCER;
        if (Helper.enchantmentExists(player, basicExperiencer)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, basicExperiencer);
            return (0.1 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, enhancedExperiencer)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, enhancedExperiencer);
            return (0.15 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, refindExperiencer)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, refindExperiencer);
            return (0.2 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, eliteExperiencer)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, eliteExperiencer);
            return (0.3 * enchantmentLevel);
        }
        if (Helper.enchantmentExists(player, mythicExperiencer)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, mythicExperiencer);
            return (0.4 * enchantmentLevel);
        }
        return 0;
    }
    /**
     * Returns a random double between min (inclusive) and max (inclusive).
     *
     * @param min The minimum value (inclusive).
     * @param max The maximum value (inclusive).
     * @return A random double between min and max.
     * @throws IllegalArgumentException if min > max
     */
    public static double randomBetweenInclusive(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }
        if (min == max) {
            return min; // If both are same, return that value
        }
        // Add a tiny epsilon to include max in the range
        return ThreadLocalRandom.current().nextDouble(min, Math.nextUp(max));
    }

    public static ItemStack createBook(EnchantmentStack enchantmentStack) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = itemStack.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<!i>" + enchantmentStack.getBookTier().getColor() + enchantmentStack.getName()));
        String thirdRow = "<!i>" + (enchantmentStack.getChancePerLevel() == 100 ? "<#00FFFF>Passive" : "<#FFAA00>" + enchantmentStack.getChancePerLevel() + "%<#D08CFF> Activation Chance Per Level");
        meta.lore(List.of(
                mm.deserialize("<!i><#555555>" + enchantmentStack.getTypeOfEnchantment() + " Enchantment"),
                mm.deserialize("<!i><#D08CFF>" + enchantmentStack.getDescription()),
                mm.deserialize("<!i><#FFAA00>" + enchantmentStack.getEnchantment().getMaxLevel() + "<#D08CFF> Max Level"),
                mm.deserialize(thirdRow),
                Component.empty(),
                mm.deserialize("<!i><#55FF55>" + enchantmentStack.getRollChance() + "%")
        ));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack createItemFromComponent(MysticEnchantmentComponent component) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = itemStack.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        if (component instanceof EnchantmentStack enchantmentStack) {
            meta.displayName(mm.deserialize("<!i>" + enchantmentStack.getBookTier().getColor() + enchantmentStack.getName()));
            String thirdRow = "<!i>" + (enchantmentStack.getChancePerLevel() == 100 ? "<#00FFFF>Passive" : "<#FFAA00>" + enchantmentStack.getChancePerLevel() + "%<#D08CFF> Activation Chance Per Level");
            meta.lore(List.of(
                    mm.deserialize("<!i><#555555>" + enchantmentStack.getTypeOfEnchantment() + " Enchantment"),
                    mm.deserialize("<!i><#D08CFF>" + enchantmentStack.getDescription()),
                    mm.deserialize("<!i><#FFAA00>" + enchantmentStack.getEnchantment().getMaxLevel() + "<#D08CFF> Max Level"),
                    mm.deserialize(thirdRow),
                    Component.empty(),
                    mm.deserialize("<!i><#55FF55>" + enchantmentStack.getRollChance() + "%")
            ));
            itemStack.setItemMeta(meta);
            return itemStack;
        } else if (component instanceof HumaneEnchantment humaneEnchantment) {
            meta.displayName(mm.deserialize("<!i>" + humaneEnchantment.getBookTier().getColor() + humaneEnchantment.getName()));
            String theChance = "<!i>" + (humaneEnchantment.getChance() == 100 ? "<aqua>Passive" : "<aqua>" + humaneEnchantment.getChance() + "% Activation Chance");
            meta.lore(List.of(
                    mm.deserialize("<!i><dark_gray>Humane Enchantment"),
                    mm.deserialize("<!i><light_purple>" + humaneEnchantment.getDescription()),
                    mm.deserialize(theChance),
                    mm.deserialize("<!i><yellow>Right Click <light_purple>While Holding To Consume"),
                    Component.empty(),
                    mm.deserialize("<!i><green>" + humaneEnchantment.getRollChance() + "%")
            ));
            itemStack.setItemMeta(meta);
            return itemStack;
        }
        return null;
    }

    public static ItemStack getFishingRod(Player player) {
        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();

        if (main.getType() == Material.FISHING_ROD) return main;
        if (off.getType() == Material.FISHING_ROD) return off;
        return null;
    }

    public static ItemStack createBook(HumaneEnchantment humaneEnchantment) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = itemStack.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<!i>" + humaneEnchantment.getBookTier().getColor() + humaneEnchantment.getName()));
        String theChance = "<!i>" + (humaneEnchantment.getChance() == 100 ? "<aqua>Passive" : "<aqua>" + humaneEnchantment.getChance() + "% Activation Chance");
        meta.lore(List.of(
                mm.deserialize("<!i><dark_gray>Humane Enchantment"),
                mm.deserialize("<!i><light_purple>" + humaneEnchantment.getDescription()),
                mm.deserialize(theChance),
                mm.deserialize("<!i><yellow>Right Click <light_purple>While Holding To Consume"),
                Component.empty(),
                mm.deserialize("<!i><green>" + humaneEnchantment.getRollChance() + "%")
        ));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack findTorchInInventory(Player player) {
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.getType() == Material.TORCH && stack.getAmount() > 0) {
                return stack;
            }
        }
        return null;
    }

    public static ItemStack findItem(Player player, Material material) {
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.getType() == material && stack.getAmount() > 0) {
                return stack;
            }
        }
        return null;
    }

    public static List<Block> getOreVein(Block start) {
        List<Block> vein = new ArrayList<>();
        Queue<Block> queue = new LinkedList<>();
        Set<Block> visited = new HashSet<>();
        Material type = start.getType();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty() && vein.size() < 32) {
            Block current = queue.poll();
            vein.add(current);

            for (BlockFace face : BlockFace.values()) {
                Block neighbor = current.getRelative(face);
                if (neighbor.getType() == type && visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }

        return vein;
    }

    public static boolean enchantmentsExist(Player player, EnchantmentStack[] enchantmentStacks) {
        for (EnchantmentStack enchantmentStack : enchantmentStacks) {
            if (player.getInventory().getItemInMainHand().containsEnchantment(enchantmentStack.getEnchantment()))
                return true;
        }
        return false;
    }

    public static boolean enchantmentsExist(ItemStack itemStack, EnchantmentStack[] enchantmentStacks) {
        for (EnchantmentStack enchantmentStack : enchantmentStacks) {
            if (itemStack.containsEnchantment(enchantmentStack.getEnchantment())) return true;
        }
        return false;
    }

    public static int getEnchantmentLevel(Player player, EnchantmentStack enchantmentStack) {
        return player.getInventory().getItemInMainHand().getEnchantmentLevel(enchantmentStack.getEnchantment());
    }

    public static int getEnchantmentLevel(ItemStack itemStack, EnchantmentStack enchantmentStack) {
        return itemStack.getEnchantmentLevel(enchantmentStack.getEnchantment());
    }

    public static boolean enchantmentExists(Player player, EnchantmentStack enchantmentStack) {
        return player.getInventory().getItemInMainHand().containsEnchantment(enchantmentStack.getEnchantment());
    }

    public static boolean enchantmentExists(Player player, HumaneEnchantment humaneEnchantment) {
        return PlayerDataUtils.checkIfDataExists(player, humaneEnchantment.getId());
    }

    public static boolean enchantmentExists(List<ItemStack> itemStacks, EnchantmentStack enchantmentStack) {
        for (ItemStack itemStack : itemStacks) {
            return enchantmentExists(itemStack, enchantmentStack);
        }
        return false;
    }

    public static boolean enchantmentExists(ItemStack itemStack, EnchantmentStack enchantmentStack) {
        if (itemStack == null) return false;
        return itemStack.containsEnchantment(enchantmentStack.getEnchantment());
    }

    public static boolean checkArmorForEnchantment(List<ItemStack> armor, EnchantmentStack enchantmentStack) {
        for (ItemStack item : armor) {
            if (enchantmentExists(item, enchantmentStack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkArmorForEnchantments(List<ItemStack> armor, EnchantmentStack[] enchantmentStack) {
        for (ItemStack item : armor) {
            if (enchantmentsExist(item, enchantmentStack)) {
                return true;
            }
        }
        return false;
    }

    public static List<ItemStack> getPlayerArmor(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        ItemStack boots = inventory.getBoots();
        List<ItemStack> armor = new ArrayList<>();
        if (helmet != null && !helmet.getType().isAir()) armor.add(helmet);
        if (chestplate != null && !chestplate.getType().isAir()) armor.add(chestplate);
        if (leggings != null && !leggings.getType().isAir()) armor.add(leggings);
        if (boots != null && !boots.getType().isAir()) armor.add(boots);
        return armor;
    }

    public static void rollBasicEnchantment(Player player) {
        double price = BookTiers.BASIC.getPrice();
        if (MysticCoinUtils.has(player, price)) {
            MysticEnchantmentComponent chosen = rollBasicEnchantment();
            if (chosen instanceof EnchantmentStack enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            } else if (chosen instanceof HumaneEnchantment enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            }
        } else {
            sendWarningMessage(player, "You don't have enough Mystic Coins!");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }

    public static MysticEnchantmentComponent rollBasicEnchantment() {
        List<MysticEnchantmentComponent> enchants = EnchantmentsUtils.getAllEnchantments(BookTiers.BASIC);
        return enchants.get(random.nextInt(enchants.size()));
    }
    public static MysticEnchantmentComponent rollEnhancedEnchantment() {
        List<MysticEnchantmentComponent> enchants = EnchantmentsUtils.getAllEnchantments(BookTiers.ENHANCED);
        return enchants.get(random.nextInt(enchants.size()));
    }
    public static MysticEnchantmentComponent rollEliteEnchantment() {
        List<MysticEnchantmentComponent> enchants = EnchantmentsUtils.getAllEnchantments(BookTiers.ELITE);
        return enchants.get(random.nextInt(enchants.size()));
    }
    public static MysticEnchantmentComponent rollRefinedEnchantment() {
        List<MysticEnchantmentComponent> enchants = EnchantmentsUtils.getAllEnchantments(BookTiers.REFINED);
        return enchants.get(random.nextInt(enchants.size()));
    }
    public static MysticEnchantmentComponent rollMythicEnchantment() {
        List<MysticEnchantmentComponent> enchants = EnchantmentsUtils.getAllEnchantments(BookTiers.MYTHIC);
        return enchants.get(random.nextInt(enchants.size()));
    }
    public static boolean giveBook(Enchantment enchantment, int enchantmentLevel, Player player) {
        int maxLevel = enchantment.getMaxLevel();
        enchantmentLevel = Math.max(1, enchantmentLevel);

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        book.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments()
                .add(enchantment, enchantmentLevel)
                .build());
        return safeGiveItem(player, book);
    }

    public static void rollEnhancedEnchantment(Player player) {
        double price = BookTiers.ENHANCED.getPrice();
        if (MysticCoinUtils.has(player, price)) {
            Object chosen = rollEnhancedEnchantment();
            if (chosen instanceof EnchantmentStack enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            } else if (chosen instanceof HumaneEnchantment enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            }
        } else {
            sendWarningMessage(player, "You don't have enough Mystic Coins!");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }


    public static void rollRefinedEnchantment(Player player) {
        double price = BookTiers.REFINED.getPrice();
        if (MysticCoinUtils.has(player, price)) {
            Object chosen = rollRefinedEnchantment();
            if (chosen instanceof EnchantmentStack enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            } else if (chosen instanceof HumaneEnchantment enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            }
        } else {
            sendWarningMessage(player, "You don't have enough Mystic Coins!");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }

    public static void rollEliteEnchantment(Player player) {
        double price = BookTiers.ELITE.getPrice();
        if (MysticCoinUtils.has(player, price)) {
            Object chosen = rollEliteEnchantment();
            if (chosen instanceof EnchantmentStack enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            } else if (chosen instanceof HumaneEnchantment enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            }
        } else {
            sendWarningMessage(player, "You don't have enough Mystic Coins!");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }

    public static void rollMythicEnchantment(Player player) {
        double price = BookTiers.MYTHIC.getPrice();
        if (MysticCoinUtils.has(player, price)) {
            Object chosen = rollMythicEnchantment();
            if (chosen instanceof EnchantmentStack enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            } else if (chosen instanceof HumaneEnchantment enchantment) {
                if (giveBook(enchantment, player)) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    sendMessage(player, "<#55FF55>You rolled <#FFAA00>" + enchantment.getName() + "<#55FF55>!");
                    MysticCoinUtils.removeCoin(player, price);
                }
            }
        } else {
            sendWarningMessage(player, "You don't have enough Mystic Coins!");
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }

    public static void sendMessage(Player player, String message) {
        if (!Settings.RECEIVE_MESSAGES.getValue(player)) return;
        player.sendRichMessage("<shadow:#000000FF><b><#73e3ff>Mystic Enchantments >> <reset>" + Gradient.GREEN + message);
    }
    public static void sendMessage(CommandSender commandSender, String message) {
        commandSender.sendRichMessage("<b><#73e3ff>Mystic Enchantments >> <reset>" + Gradient.GREEN + message);
    }
    public static void sendMessage(LivingEntity entity, String message) {
        entity.sendMessage(MiniMessage.miniMessage().deserialize("<b><#73e3ff>Mystic Enchantments >> <reset>" + message));
    }
    public static void sendWorldMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Settings.RECEIVE_WORLD_MESSAGES.getValue(player)) continue;
            Helper.sendMessage(player, message);
        }
    }

    public static void sendWarningMessage(Player player, String message) {
        if (!Settings.RECEIVE_WARNING_MESSAGES.getValue(player)) return;
        player.sendRichMessage("<shadow:#000000FF><b><#73e3ff>Mystic Enchantments >> <reset>" + Gradient.RED + message);
    }
    public static void sendWarningMessage(CommandSender sender, String message) {
        sender.sendRichMessage("<shadow:#000000FF><b><#73e3ff>Mystic Enchantments >> <reset>" + Gradient.RED + message);
    }

    public static void dealAbsoluteDamage(LivingEntity victim, Player attacker, double damage) {
        victim.setNoDamageTicks(0);
        victim.damage(damage, attacker);
    }

    public static void tickEvent(Runnable runnable) {
        Bukkit.getScheduler().runTaskTimer(MysticEnchantments.getInstance(), runnable, 0, 5L);
    }

    public static double randomDouble(double min, double max) {
        if (min > max) return 0;
        if (min == max) return min;
        return new Random().nextDouble() * (max - min) + min;
    }

    public static boolean rollChance(double chance) {
        return randomDouble(0, 100) <= chance;
    }

    public static boolean isValidRawOre(BlockState block) {
        return block.getType() == Material.IRON_ORE || block.getType() == Material.GOLD_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE || block.getType() == Material.DEEPSLATE_GOLD_ORE;
    }

    public static ItemStack getNuggetVersion(BlockState block) {
        if (block.getType() == Material.IRON_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE)
            return new ItemStack(Material.IRON_NUGGET);
        if (block.getType() == Material.GOLD_ORE || block.getType() == Material.DEEPSLATE_GOLD_ORE)
            return new ItemStack(Material.GOLD_NUGGET);
        return new ItemStack(Material.AIR);
    }

    public static ItemStack getSmeltedItem(ItemStack input) {
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            if (recipe instanceof FurnaceRecipe furnaceRecipe) {
                if (furnaceRecipe.getInputChoice().test(input)) {
                    ItemStack result = furnaceRecipe.getResult().clone();
                    result.setAmount(input.getAmount());
                    return result;
                }
            }
        }
        return input.clone();
    }

    public static void launchInFacingDirection(Entity entity, double strength) {
        // Get the direction the entity is facing (already normalized)
        Vector direction = entity.getLocation().getDirection().normalize();

        // Multiply by the desired strength
        Vector velocity = direction.multiply(strength);

        // Apply the velocity
        entity.setVelocity(velocity);
    }

    public static String formatHe(String name) {
        return "mysticEnchantmentsHumaneEnchants" + name;
    }

    public static double getMaxHealth(LivingEntity entity) {
        return Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getValue();
    }

    public static boolean isDeadAfterDamage(LivingEntity livingEntity, double finalDamage) {
        return livingEntity.getHealth() - finalDamage <= 0;
    }
    public static boolean isCustomItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(ItemDataUtils.key, PersistentDataType.STRING);
    }
    public static boolean isHostile(LivingEntity entity) {
        return entity instanceof Monster;
    }

    public static void giveXp(Player player, int xpToGive) {
        player.setExperienceLevelAndProgress(player.calculateTotalExperiencePoints() + xpToGive);
    }

    public static boolean isFragmentsOre(Block block) {
        for (Material material : MysticCurrencyUtils.ORE_DROP_MODIFIERS.keySet()) {
            if (block.getType() == material) return true;
        }
        return false;
    }

    public static boolean isCorrectTool(BlockState block, Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (MaterialTags.PICKAXES.isTagged(itemStack)) {
            return Tag.MINEABLE_PICKAXE.isTagged(block.getType());
        } else if (MaterialTags.AXES.isTagged(itemStack)) {
            return Tag.MINEABLE_AXE.isTagged(block.getType());
        } else if (MaterialTags.SHOVELS.isTagged(itemStack)) {
            return Tag.MINEABLE_SHOVEL.isTagged(block.getType());
        } else {
            return false;
        }
    }
    public static double calculateCrateItemChance(int weight) {
        List<EnchantedItem> enchantedItems = EnchantedItem.getEnchantedItems();
        int totalWeight = 0;
        for (EnchantedItem enchantedItem : enchantedItems) {
            totalWeight += enchantedItem.getWeight();
        }
        return round(((double) weight / totalWeight) * 100, 2);
    }
    public static EnchantedItem rollWeightedItem(List<EnchantedItem> items) {
        int totalWeight = 0;
        for (EnchantedItem item : items) {
            totalWeight += item.getWeight();
        }
        if (totalWeight <= 0) return null;
        int roll = ThreadLocalRandom.current().nextInt(totalWeight);
        int cumulative = 0;
        for (EnchantedItem item : items) {
            cumulative += item.getWeight();
            if (roll < cumulative) {
                return item;
            }
        }

        return null; // Should never happen unless weights are zero
    }

    public static MysticRecipe findMysticRecipe(ItemStack itemStack) {
        for (MysticRecipe recipe : GuiHelper.getMysticRecipes()) {
            if (ItemDataUtils.hasData(itemStack, recipe.getId())) return recipe;
        }
        return null;
    }

    public static String getRawName(ItemStack item) {
        return PlainTextComponentSerializer.plainText().serialize(item.effectiveName());
    }

    public static boolean isMysticalBagInventory(Inventory inventory) {
        return ItemDataUtils.hasData(inventory.getItem(inventory.getSize() - 5), "mystical_bag_noter");
    }

    public static boolean isFarmerSatchel(Inventory inventory) {
        return ItemDataUtils.hasData(inventory.getItem(inventory.getSize() - 5), "farmer_satchel_noter");
    }

    public static boolean isBlockCrop(ItemStack itemStack) {
        return switch (itemStack.getType()) {
            case PUMPKIN, MELON, BAMBOO, MELON_SLICE, WHEAT_SEEDS, BEETROOT_SEEDS -> true;
            default -> false;
        };
    }

    public static BookTiers rollTier(int basicTier, int enhancedTier, int refinedTier, int eliteTier, int mythicTier) {
        int roll = new Random().nextInt(100) + 1;
        int cumulative = 0;

        cumulative += basicTier;
        if (roll <= cumulative) return BookTiers.BASIC;

        cumulative += enhancedTier;
        if (roll <= cumulative) return BookTiers.ENHANCED;

        cumulative += refinedTier;
        if (roll <= cumulative) return BookTiers.REFINED;

        cumulative += eliteTier;
        if (roll <= cumulative) return BookTiers.ELITE;

        cumulative += mythicTier;
        if (roll <= cumulative) return BookTiers.MYTHIC;

        throw new IllegalStateException("Invalid tier weights: total chance does not sum to 100.");
    }
    public static ItemStack rollEnchantedBook(BookTiers tier) {
        List<Object> enchantments = tier.getAllEnchantments();
        int randomNumber = randomInt(0, enchantments.size() - 1);
        Object chosen = enchantments.get(randomNumber);
        if (chosen instanceof HumaneEnchantment enchantment) {
            return createFunctionalBook(enchantment);
        } else if (chosen instanceof EnchantmentStack enchantment) {
            return createFunctionalBook(enchantment);
        }
        return ItemStack.empty();
    }

    public static boolean isFarmerSatchelSupported(ItemStack currentItem) {
        return switch (currentItem.getType()) {
            case WHEAT, WHEAT_SEEDS, CARROT, POTATO, BEETROOT, BEETROOT_SEEDS, MELON, MELON_SEEDS, MELON_SLICE, PUMPKIN, PUMPKIN_SEEDS, BAMBOO, GLOW_BERRIES -> true;
            default -> false;
        };
    }

    public static boolean isBag(Inventory inventory) {
        return isMysticalBagInventory(inventory) || isFarmerSatchel(inventory);
    }

    public static void handleScrollOfPreservation(ItemStack scroll, ItemStack otherItem, Player player) {
        if (otherItem.getData(DataComponentTypes.MAX_DAMAGE) == null) {
            if (otherItem == null || otherItem.getType().isAir()) {
                Helper.sendWarningMessage(player, "Hold something in your other hand to preserve!");
            } else {
                Helper.sendWarningMessage(player, getRawName(otherItem) + " cannot be preserved!");
            }
        } else {
            if (ScrollOfPreservationUtils.isPreserved(otherItem)) {
                Helper.sendWarningMessage(player, getRawName(otherItem) + " is already preserved!");
            } else {
                scroll.setAmount(scroll.getAmount() - 1);
                ScrollOfPreservationUtils.preserve(otherItem);
                Helper.sendMessage(player, Gradient.YELLOW + getRawName(otherItem) + " has been preserved!");
            }
        }
    }

    public static boolean isDamageable(ItemStack item) {
        return item.getData(DataComponentTypes.DAMAGE) != null && item.getData(DataComponentTypes.MAX_DAMAGE) != null;
    }

    public static void pullEntity(LivingEntity centerEntity, LivingEntity entity, double strength, double upwardPush) {
        Vector direction = centerEntity.getLocation().toVector().subtract(entity.getLocation().toVector());
        if (direction.lengthSquared() == 0) return; // Prevent NaN or zero vector
        direction.normalize().multiply(strength).setY(upwardPush);
        entity.setVelocity(direction);
    }

    public static void updateViewer(Player player, Inventory inventory) {
        inventory.setItem(inventory.getSize() - 5, GuiHelper.createViewer(player));
    }

    public static Material getOreBlock(Block block) {
        return switch (block.getType()) {
            case IRON_ORE, DEEPSLATE_IRON_ORE -> Material.IRON_BLOCK;
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> Material.DIAMOND_BLOCK;
            case COAL_ORE, DEEPSLATE_COAL_ORE -> Material.COAL_BLOCK;
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE -> Material.LAPIS_BLOCK;
            case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> Material.REDSTONE_BLOCK;
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> Material.COPPER_BLOCK;
            case EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> Material.EMERALD_BLOCK;
            case GOLD_ORE, DEEPSLATE_GOLD_ORE -> Material.GOLD_BLOCK;
            case NETHER_QUARTZ_ORE -> Material.QUARTZ_BLOCK;
            case NETHER_GOLD_ORE -> Material.GOLD_ORE;

            default -> block.getType();
        };
    }

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Inventory cloneInventory(Inventory inventory, String name) {
        Inventory cloned = Bukkit.createInventory(null, inventory.getSize(), mm.deserialize(name));
        cloned.setContents(inventory.getContents());
        return cloned;
    }

    public static ItemStack getCrate(Player player) {
        ItemStack enchantedCrate = new ItemStack(Material.PINK_SHULKER_BOX);
        ItemMeta enchantedCrateMeta = enchantedCrate.getItemMeta();
        enchantedCrateMeta.displayName(mm.deserialize(
                "<gradient:#D08CFF:#FF55FF><!i>✧ Enchanted Crate ✧</gradient>"
        ));
        enchantedCrateMeta.lore(List.of(
                mm.deserialize("<#B388FF><!i>Mystic Enchantments Crate</#B388FF>"),
                Component.empty(),
                mm.deserialize("<#FFACFC><!i>⟡ Left Click</#FFACFC> <#FFFFFF><!i>to inspect crate contents</#FFFFFF>"),
                mm.deserialize("<#FFACFC><!i>⟡ Right Click</#FFACFC> <#FFFFFF><!i>to open with a key</#FFFFFF>"),
                Component.empty(),
                mm.deserialize("<#80D8FF><!i>You have <#FFEE58>" + EnchantedCrateUtils.getKey(player) + "</#FFEE58><!i> Enchanted Crate Keys</#80D8FF>")
        ));
        enchantedCrate.setItemMeta(enchantedCrateMeta);

        ItemDataUtils.setData(enchantedCrate, "enchanted_crate");

        return enchantedCrate;
    }

    public static void updateCrate(Player player, Inventory inventory) {
        inventory.setItem(31, getCrate(player));
    }

    public static void sendActionBar(Player player, String message) {
        if (!Settings.RECEIVE_ACTIONBAR.getValue(player)) return;
        player.sendActionBar(MiniMessage.miniMessage().deserialize(message));
    }
    public static void sendActionBar(Player player, Component message) {
        if (!Settings.RECEIVE_ACTIONBAR.getValue(player)) return;
        player.sendActionBar(message);
    }

    public static Setting findSettingByItem(ItemStack itemStack) {
        for (Setting setting : settings) {
            if (ItemDataUtils.hasData(itemStack, setting.getId())) return setting;
        }
        return null;
    }

    public static void updateSettings(Player player, Inventory inventory) {
        for (int i = 0; i < settings.size() && i < inventory.getSize(); i++) {
            Setting setting = settings.get(i);
            ItemStack toDisplay = setting.getToDisplay().clone();
            boolean value = setting.getValue(player);
            String color = value ? "<!i><green>True" : "<!i><red>False";
            toDisplay.editMeta(itemMeta -> {
                List<Component> lore = itemMeta.lore();
                lore = lore == null ? new ArrayList<>() : lore;
                lore.add(MiniMessage.miniMessage().deserialize(color));
                itemMeta.lore(lore);
            });
            inventory.setItem(i, toDisplay);
        }
    }

    public static LivingEntity rayTraceFromEyes(Player player, int range) {
        RayTraceResult result = player.rayTraceEntities(range);
        if (result == null) return null;
        if (result.getHitEntity() instanceof LivingEntity entity) return entity;
        return null;
    }

    public static Player rayTracePlayerFromEyes(Player player, int range) {
        RayTraceResult result = player.rayTraceEntities(range);
        if (result == null) return null;
        if (result.getHitEntity() instanceof Player targetPlayer) return targetPlayer;
        return null;
    }

    public static String formatCooldownTime(long milliseconds) {
        if (milliseconds < 0) {
            return "0 seconds";
        }

        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append(" day").append(days != 1 ? "s" : "");
            if (hours > 0 || minutes > 0 || seconds > 0) {
                builder.append(", ");
            }
        }

        if (hours > 0) {
            builder.append(hours).append(" hour").append(hours != 1 ? "s" : "");
            if (minutes > 0 || seconds > 0) {
                builder.append(", ");
            }
        }

        if (minutes > 0) {
            builder.append(minutes).append(" minute").append(minutes != 1 ? "s" : "");
            if (seconds > 0) {
                builder.append(", ");
            }
        }

        if (seconds > 0 || builder.isEmpty()) {
            builder.append(seconds).append(" second").append(seconds != 1 ? "s" : "");
        }

        // Clean up any trailing ", " if present
        String result = builder.toString();
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }

        // Replace last comma with " and" for better readability
        int lastComma = result.lastIndexOf(", ");
        if (lastComma != -1) {
            result = result.substring(0, lastComma) + " and" + result.substring(lastComma + 1);
        }

        return result;
    }

    public static long minToMs(int i) {
        return (long) i * 60 * 1000;
    }
    public static long secToMs(int i) {
        return i * 1000L;
    }

    public static int betweenBounds(int index, int min, int max) {
        return Math.max(min, Math.min(max, index));
    }

    public static <T> T rollFromList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
