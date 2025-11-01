package com.mdt168.mysticEnchantments;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import com.mdt168.mysticEnchantments.enchants.MysticTickets;
import com.mdt168.mysticEnchantments.pages.*;
import com.mdt168.mysticEnchantments.craft.recipes.MysticRecipe;
import com.mdt168.mysticEnchantments.custom.*;
import com.mdt168.mysticEnchantments.custom.dataUtils.EntityDataUtils;
import com.mdt168.mysticEnchantments.custom.dataUtils.Guis;
import com.mdt168.mysticEnchantments.custom.pluginoptions.MysticOption;
import com.mdt168.mysticEnchantments.dataUtils.ScrollOfPreservationUtils;
import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import com.mdt168.mysticEnchantments.enchants.HumaneEnchantment;
import com.mdt168.mysticEnchantments.modifiers.ModifierKeys;
import com.mdt168.mysticEnchantments.resources.MysticResource;
import com.mdt168.mysticEnchantments.resources.MysticResources;
import com.mdt168.mysticEnchantments.resources.MysticTools;
import com.mdt168.mysticEnchantments.resources.PagedMysticResourcesGui;
import com.mdt168.mysticEnchantments.settings.Setting;
import com.mdt168.mysticEnchantments.utility.cooldown.PlayerCooldowns;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import static com.mdt168.mysticEnchantments.enchants.MysticEnchants.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class MysticEvents implements Listener {
    
    @EventHandler
    public void blockDropsEnchantmentEffects(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        List<Item> items = event.getItems();
        BlockState block = event.getBlockState();
        Location blockLocation = event.getBlock().getLocation();
        ItemStack tool = player.getInventory().getItemInMainHand();

        // Mystic Resources
        if (!DB.isPlayerPlaced(block)) {
            double commonMultiplier = 1;
            int numberOfItems = 1;
            if (Helper.enchantmentExists(player, COMMON_FORTUNE)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, COMMON_FORTUNE);
                double chance = enchantmentLevel * COMMON_HARVEST.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    numberOfItems++;
                }
            }
            if (Helper.enchantmentExists(player, COMMON_HARVEST)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, COMMON_HARVEST);
                commonMultiplier += 0.05 * enchantmentLevel;
            }
            if (block.getType() == Material.COAL_ORE) if (Helper.rollChance(MysticResources.CARBONITE.getDropChance() * commonMultiplier) && MysticTools.SPLINTERED_PICKAXE.isItem(tool)) Helper.runAfterDelay(1, () -> {
                items.add(world.dropItemNaturally(blockLocation.toCenterLocation(), MysticResources.CARBONITE.getItemStack()));
                Helper.sendResourceActionBar(player, MysticResources.CARBONITE);
            });
            if (block.getType() == Material.COPPER_ORE) if (Helper.rollChance(MysticResources.COPPER_SHARD.getDropChance() * commonMultiplier) && MysticTools.SPLINTERED_PICKAXE.isItem(tool)) Helper.runAfterDelay(1, () -> {
                items.add(world.dropItemNaturally(blockLocation.toCenterLocation(), MysticResources.COPPER_SHARD.getItemStack()));
                Helper.sendResourceActionBar(player, MysticResources.COPPER_SHARD);
            });
        }

        if (Helper.enchantmentExists(player, GLIMMER) && Helper.isValidRawOre(block) && !DB.isPlayerPlaced(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, GLIMMER);
            double chance = GLIMMER.getChancePerLevel() * enchantmentLevel;
            if (Helper.rollChance(chance)) {
                ItemStack nugget = Helper.getNuggetVersion(block);
                Item item = world.dropItemNaturally(blockLocation, nugget);
                items.add(item);
            }
        }
        if (Helper.enchantmentExists(player, ELDRITCH_KEEN_EYE) && !DB.isPlayerPlaced(block)) {
            if (Helper.rollChance(ELDRITCH_KEEN_EYE.getChance())) {
                List<Item> toAdd = new ArrayList<>();
                for (Item item : items) {
                    for (int i = 0; i < 2; i++) {
                        toAdd.add(world.dropItemNaturally(item.getLocation(), item.getItemStack().clone()));
                    }
                }
                items.addAll(toAdd);
            }
        }
        if (Helper.enchantmentExists(player, CROP_BOOST) && Helper.isCrop(block)) {
            if (block.getBlockData() instanceof Ageable data && data.getAge() == data.getMaximumAge()) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, CROP_BOOST);
                double chance = enchantmentLevel * CROP_BOOST.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    ItemStack toDrop = ItemStack.of(Material.AIR);
                    for (ItemStack drop : block.getDrops()) {
                        if (!drop.getType().getKey().value().contains("seed")) {
                            toDrop = drop;
                            break;
                        }
                    }
                    items.add(world.dropItemNaturally(blockLocation.toCenterLocation(), toDrop));
                }
            }
        }
        if (Helper.enchantmentExists(player, SMELTER)) {
            int smelterLevel = Helper.getEnchantmentLevel(player, SMELTER);
            if (Helper.rollChance(smelterLevel * SMELTER.getChancePerLevel())) {
                for (Item item : items) {
                    item.setItemStack(Helper.getSmeltedItem(item.getItemStack()));
                }
            }
        }
        if (Helper.enchantmentExists(player, SAPLING_SEEKER) && Helper.isLeaf(block) && !DB.isPlayerPlaced(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, SAPLING_SEEKER);
            double chance = enchantmentLevel * SAPLING_SEEKER.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                ItemStack itemStack = Helper.getSapling(block);
                Item item = world.dropItemNaturally(blockLocation, itemStack);
                items.add(item);
            }
        }
        if (Helper.enchantmentExists(player, PEBBLE_POLISHER) && block.getType() == Material.STONE && !DB.isPlayerPlaced(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, PEBBLE_POLISHER);
            double chance = enchantmentLevel * PEBBLE_POLISHER.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                ItemStack itemStack = new ItemStack(Material.GRAVEL);
                Item item = world.dropItemNaturally(blockLocation.toCenterLocation(), itemStack);
                item.teleport(blockLocation.toCenterLocation());
                items.add(item);
            }
        }
        if (Helper.enchantmentExists(player, MYSTERIOUS_LUMBERJACK) && !DB.isPlayerPlaced(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, MYSTERIOUS_LUMBERJACK);
            double chance = enchantmentLevel * MYSTERIOUS_LUMBERJACK.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                Helper.doubleItemsList(items);
            }
        }
        if (Helper.enchantmentExists(player, FIREWOORD) && Helper.isLog(block) && !DB.isPlayerPlaced(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, FIREWOORD);
            double chance = enchantmentLevel * FIREWOORD.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                items.add(world.dropItemNaturally(blockLocation.toCenterLocation(), new ItemStack(Material.CHARCOAL)));
            }
        }
        if (Helper.enchantmentExists(player, GRAVEL_SENSE) && block.getType().asBlockType() == BlockType.GRAVEL && !DB.isPlayerPlaced(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, GRAVEL_SENSE);
            double chance = enchantmentLevel * GRAVEL_SENSE.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                for (Item item : items) {
                    if (item.getName().equalsIgnoreCase("gravel")) {
                        item.setItemStack(new ItemStack(Material.FLINT, item.getItemStack().getAmount()));
                    }
                }
            }
        }
        if (Helper.enchantmentExists(player, KEEN_EYE) && !DB.isPlayerPlaced(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, KEEN_EYE);
            double chance = enchantmentLevel * KEEN_EYE.getChancePerLevel();
            if (Helper.rollChance(chance) && Helper.isCorrectTool(block, player)) {
                for (Item item : items) {
                    items.add(world.dropItemNaturally(item.getLocation(), item.getItemStack()));
                }
            }
        }
        if (Helper.enchantmentExists(player, FORTUNE_TOUCH) && Helper.isOre(block)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, FORTUNE_TOUCH);
            double chance = enchantmentLevel * FORTUNE_TOUCH.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                Helper.doubleItemsList(items);
            }
        }
        if (Helper.enchantmentExists(player, TOOL_MASTERY)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, TOOL_MASTERY);
            double chance = enchantmentLevel * TOOL_MASTERY.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                Helper.doubleItemsList(items);
            }
        }
        // Spawning Handlers
        if (Helper.enchantmentExists(player, ENDER_POUCH)) {
            Inventory enderChest = player.getEnderChest();
            List<Item> clone = new ArrayList<>(items);
            for (Item item : clone) {
                if (Helper.canFullyAddToInventory(enderChest, item.getItemStack())) {
                    items.remove(item);
                    enderChest.addItem(item.getItemStack());
                }
            }
        } else if (Helper.enchantmentExists(player, MAGNETISM)) {
            event.setCancelled(true);
            for (Item item : items) {
                Map<Integer, ItemStack> leftovers = player.getInventory().addItem(item.getItemStack());
                for (ItemStack itemStack : leftovers.values()) {
                    if (Helper.isVeinMining && Helper.originBlock != null) {
                        Location location = Helper.originBlock.getLocation().toCenterLocation();
                        item.teleport(location);
                    }
                    world.dropItemNaturally(item.getLocation(), itemStack);
                }
            }
        } else {
            if (Helper.isVeinMining && Helper.originBlock != null) {
                Location location = Helper.originBlock.getLocation().toCenterLocation();
                for (Item item : items) {
                    item.teleport(location);
                }
            }
        }
    }

    @EventHandler
    public void onAnvilStuff(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        boolean enchanted = false;
        ItemStack item = inventory.getFirstItem();
        ItemStack secondItem = inventory.getSecondItem();
        if (item == null || secondItem == null) return;
        ItemStack result = item.clone();
        ItemMeta meta = result.getItemMeta();
        if (ItemDataUtils.hasData(secondItem, "arcane_amplifier")) {
            Map<Enchantment, Integer> enchantments = new HashMap<>(meta.getEnchants());
            if (enchantments.isEmpty()) return;
            for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : enchantments.entrySet()) {
                Enchantment enchantment = enchantmentIntegerEntry.getKey();
                int enchantmentLevel = enchantmentIntegerEntry.getValue();
                if (enchantmentLevel >= enchantment.getMaxLevel()) continue;
                meta.addEnchant(enchantment, enchantmentLevel + 1, true);
                enchanted = true;
                break;
            }
            if (enchanted) {
                result.setItemMeta(meta);
                ItemDataUtils.addData(result, "arcane_amplifier_result");
                event.setResult(result);
            } else inventory.setResult(new ItemStack(Material.AIR));
        } else if (ItemDataUtils.hasData(secondItem, "enchantment_extractor")) {
            Map<Enchantment, Integer> enchantments = new HashMap<>(item.getItemMeta().getEnchants());
            if (enchantments.isEmpty()) return;
            for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : enchantments.entrySet()) {
                Enchantment enchantment = enchantmentIntegerEntry.getKey();
                int enchantmentLevel = enchantmentIntegerEntry.getValue();
                ItemStack resultBook = new ItemStack(Material.ENCHANTED_BOOK);
                resultBook.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments().add(enchantment, enchantmentLevel).build());
                ItemDataUtils.addData(resultBook, "enchantment_extractor_result");
                event.setResult(resultBook);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item == null || item.getType() == Material.AIR) continue;

            if (Helper.isCustomItem(item)) {
                event.getInventory().setResult(new ItemStack(new ItemStack(Material.AIR)));

                if (event.getView().getPlayer() instanceof Player player) {
                    Helper.sendWarningMessage(player, "You can't use Mystic Enchantments Items to craft!");
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();

        if (event.getWhoClicked() instanceof Player player) {
            boolean allowedAnvil = false;
            Inventory inventory = event.getClickedInventory();
            if (inventory != null) {
                if (inventory.getType() == InventoryType.ANVIL && event.getSlotType() == InventoryType.SlotType.RESULT) {
                    if (inventory instanceof AnvilInventory anvilInventory) {

                        ItemStack result = anvilInventory.getResult();
                        if (ItemDataUtils.hasData(result, "arcane_amplifier_result")) {
                            ItemStack firstItem = anvilInventory.getFirstItem();
                            ItemStack secondItem = anvilInventory.getSecondItem();
                            if (player.getItemOnCursor().getType().isAir()) {
                                if (firstItem != null && secondItem != null) {
                                    secondItem.setAmount(secondItem.getAmount() - 1);
                                    anvilInventory.setFirstItem(ItemStack.empty());
                                    if (secondItem.getAmount() == 0) anvilInventory.setSecondItem(ItemStack.empty());

                                    ItemDataUtils.removeData(result);
                                    player.setItemOnCursor(result);
                                    anvilInventory.setResult(ItemStack.empty());
                                }
                            } else {
                                Helper.sendWarningMessage(player, "You can't apply Arcane Amplifier while having something on your cursor");
                                allowedAnvil = true;
                            }
                        }
                        if (ItemDataUtils.hasData(result, "enchantment_extractor_result")) {
                            ItemStack firstItem = anvilInventory.getFirstItem();
                            ItemStack secondItem = anvilInventory.getSecondItem();
                            if (player.getItemOnCursor().getType().isAir()) {
                                if (firstItem != null && secondItem != null) {
                                    secondItem.setAmount(secondItem.getAmount() - 1);
                                    ItemEnchantments enchantments = result.getData(DataComponentTypes.STORED_ENCHANTMENTS);
                                    if (enchantments != null ) {
                                        for (Enchantment enchantment : enchantments.enchantments().keySet()) {
                                            firstItem.removeEnchantment(enchantment);
                                        }
                                    }

                                    if (secondItem.getAmount() == 0) anvilInventory.setSecondItem(ItemStack.empty());

                                    ItemDataUtils.removeData(result);
                                    player.setItemOnCursor(result);
                                    anvilInventory.setResult(ItemStack.empty());
                                }
                            } else {
                                Helper.sendWarningMessage(player, "You can't apply Enchantment Extractor while having something on your cursor");
                                allowedAnvil = true;
                            }
                        }
                    }
                }
            }
                if (Helper.isCustomItem(currentItem)) {
                    InventoryType type = event.getInventory().getType();
                    for (String string : Helper.allowedAnvilItems) {
                        if (ItemDataUtils.hasData(currentItem, string)) {
                            allowedAnvil = true;
                        }
                    }
                    if (type == InventoryType.FURNACE || type == InventoryType.BLAST_FURNACE ||
                            type == InventoryType.SMOKER || (type == InventoryType.ANVIL && !allowedAnvil) || type == InventoryType.GRINDSTONE || type == InventoryType.SMITHING) {
                        event.setCancelled(true);
                        if (event.getWhoClicked() instanceof Player player1) Helper.sendWarningMessage(player1, "You can't use Mystic Enchantments Items in this menu!");
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        if (Helper.isCustomItem(event.getSource())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHopperPickup(InventoryMoveItemEvent event) {
        if (Helper.isCustomItem(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTradePrepare(TradeSelectEvent event) {
        for (ItemStack item : event.getInventory().getContents()) {
            if (Helper.isCustomItem(item)) {
                if (event.getWhoClicked() instanceof Player player) Helper.sendWarningMessage(player, "You can't use Mystic Enchantments Items to trade!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerInteractWithFrame(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof ItemFrame)) return;
        if (Helper.isCustomItem(event.getPlayer().getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            DB.removeBlock(block);
            Block newBlock = block.getRelative(event.getDirection());
            if (block.getType().isSolid()) DB.addBlock(newBlock);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        DB.addBlock(event.getBlock());
    }

    @EventHandler
    public void startBreakingEnchantmentEffects(BlockDamageEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (Helper.enchantmentExists(player, OREFLOW)) {
            if (Helper.isOre(block)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 15, 0, false, false, false));
            }
        }
    }

    @EventHandler
    public void playerHungerEnchantmentEffects(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            int oldFood = player.getFoodLevel();
            int newFood = event.getFoodLevel();
            if (PlayerDataUtils.checkIfDataExists(player, IRON_STOMACH.getId())) {
                if (oldFood > newFood) {
                    if (Helper.rollChance(IRON_STOMACH.getChance())) {
                        event.setCancelled(true);
                    }
                } else if (oldFood < newFood) {
                    event.setFoodLevel(Math.min(newFood + 1, 20));
                }
            }
        }
    }

    @EventHandler
    public void playerHealEnchantmentEffects(EntityRegainHealthEvent event) {
        double modifier = 1;
        if (event.getEntity() instanceof Player player) {
            if (PlayerDataUtils.checkIfDataExists(player, SLIGHT_VIGOR.getId())) {
                if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                    modifier += 0.025;
                }
            }
        }
        event.setAmount(event.getAmount() * modifier);
    }

    @EventHandler
    public void armorWearEnchantmentEffects(PlayerArmorChangeEvent event) {
        ItemStack item = event.getNewItem();
        ItemStack oldItem = event.getOldItem();
        Player player = event.getPlayer();
        if (Helper.enchantmentExists(item, GLOWING)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, -1, 17, false, false, false));
        } else if (Helper.enchantmentExists(oldItem, GLOWING)) {
            PotionEffect effect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
            if (effect != null) {
                if (effect.getDuration() == -1 && effect.getAmplifier() == 17) {
                    player.removePotionEffect(effect.getType());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) Helper.handleSaving(player, event.getInventory());
    }

    @EventHandler
    public void onPlayerCloseEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        Helper.handleSaving(player, inventory);
    }

    @EventHandler
    public void itemHeldEnchantmentEffects(PlayerItemDamageEvent event) {
        event.setCancelled(Helper.enchantmentExists(event.getItem(), UNBREAKABLE));
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (Helper.enchantmentExists(item, THRIFTY_TOUCH)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(item, THRIFTY_TOUCH);
            double chance = enchantmentLevel * THRIFTY_TOUCH.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                event.setCancelled(true);
            }
        }
        if (Helper.enchantmentExists(item, PRESERVE)) {
            if (item.getItemMeta() instanceof Damageable damageable) {
                int maxDurability = item.getType().getMaxDurability();
                int toHeal = (int) Math.round(maxDurability * 0.1);
                if (damageable.getDamage() + event.getDamage() >= maxDurability) {
                    event.setCancelled(true);
                    player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1, 1);
                    damageable.setDamage(Math.max(0, maxDurability - toHeal));
                    item.setItemMeta(damageable);
                    Helper.sendWarningMessage(player, "Preserve saved your item from breaking!");
                    item.removeEnchantment(PRESERVE.getEnchantment());
                }
            }
        }
        if (ScrollOfPreservationUtils.isPreserved(item)) {
            Integer maxDamage = item.getData(DataComponentTypes.MAX_DAMAGE);
            Integer damage = item.getData(DataComponentTypes.DAMAGE);
            if (maxDamage != null && damage != null) {
                int currentDamage = damage + event.getDamage();
                if (maxDamage <= currentDamage) {
                    item.setData(DataComponentTypes.DAMAGE, 0);
                    ScrollOfPreservationUtils.removePreservation(item);
                    event.setCancelled(true);
                    Helper.sendMessage(player, Gradient.PINK + "Scroll Of Preservation saved your item from breaking!");
                }
            }
        }
    }

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event) {
        if (event.getCause() == EntityPotionEffectEvent.Cause.POTION_DRINK) {
            if (event.getAction() == EntityPotionEffectEvent.Action.ADDED ||
                    event.getAction() == EntityPotionEffectEvent.Action.CHANGED) {
                PotionEffect newEffect = event.getNewEffect();
                if (newEffect != null) {
                    int newDuration = (int) (newEffect.getDuration() * 1.1);
                    event.setCancelled(true);

                    if (!(event.getEntity() instanceof Player player)) return;
                    player.addPotionEffect(
                            new PotionEffect(
                                    newEffect.getType(),
                                    newDuration,
                                    newEffect.getAmplifier(),
                                    newEffect.isAmbient(),
                                    newEffect.hasParticles(),
                                    newEffect.hasIcon()
                            )
                    );
                }
            }
        }
    }

    @EventHandler
    public void playerMovementEnchantmentsEffect(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        if (boots != null) {
            if (Helper.enchantmentExists(boots, MOMENTUM) && player.isSprinting()) {
                int enchantmentLevel = Helper.getEnchantmentLevel(boots, MOMENTUM);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, enchantmentLevel - 1, false, false, false));
            }
        }
        if (!PlayerCooldowns.CANCEL_FALL_DAMAGE.isOnCooldown(player)) return;
        if (!event.getFrom().getBlock().getLocation().equals(event.getTo().getBlock().getLocation())) {
            if (Helper.isOnSolidGround(player)) {
                PlayerCooldowns.CANCEL_FALL_DAMAGE.clearCooldown(player);
            }
        }

    }

    @EventHandler
    public void blockBreakEnchantmentEffects(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = player.getWorld();
        if (Helper.enchantmentExists(player, VEIN_MINER)) {
            if (!Helper.ignoredBlockBreakEvent) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, VEIN_MINER);
                if (Helper.rollChance(enchantmentLevel * VEIN_MINER.getChancePerLevel())) {
                    if (Helper.isOre(block)) {
                        player.sendMessage("Ore");
                        List<Block> toBreak = Helper.getOreVein(block);
                        for (Block blockToBreak : toBreak) {
                            Helper.ignoredBlockBreakEvent = true;
                            Helper.isVeinMining = true;
                            Helper.originBlock = block;
                            player.breakBlock(blockToBreak);
                        }
                        Helper.originBlock = null;
                        Helper.isVeinMining = false;
                        Helper.ignoredBlockBreakEvent = false;
                    }
                }
            }
        }
        if (!DB.isPlayerPlaced(block)) {
            MysticCoinHandler.addEffortPoints(player, MysticCoinHandler.getEffortPointsForBlock(player, block));
        }

            if (Helper.enchantmentExists(player, ELITE_WOODKEY)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, ELITE_WOODKEY);
                double chance = enchantmentLevel * ELITE_WOODKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    EnchantedCrateUtils.addKey(player, 1);
                }
            }
            if (Helper.enchantmentExists(player, MYTHIC_WOODKEY)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, MYTHIC_WOODKEY);
                double chance = enchantmentLevel * MYTHIC_WOODKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    EnchantedCrateUtils.addKey(player, 1);
                }
            }
        if (Helper.enchantmentsExist(player, EXP_INCREASE_BLOCKS)) {
            double prospectors = Helper.getProspectorsXpModifier(player);
            double xpModifier = 1 + prospectors;
            event.setExpToDrop((int) Math.round(event.getExpToDrop() * xpModifier));
        }
        if (Helper.enchantmentExists(player, DYNAMITE_PLANTER)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, DYNAMITE_PLANTER);
            double chance = enchantmentLevel * DYNAMITE_PLANTER.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                player.playSound(player, Sound.ENTITY_TNT_PRIMED, 1, 1);
                world.spawnParticle(Particle.FLAME, block.getLocation().toCenterLocation(), 10);
                Helper.sendMessage(player, "<aqua>Be cautious, a dynamite has been planted");
                ItemStack tool = player.getInventory().getItemInMainHand();
                List<Block> blocks = Helper.getNearbyBlocks(block, 1);
                Helper.runAfterDelay(Helper.secondsToTicks(2), () -> {
                    for (Block dynamitedBlock : blocks) {
                        if (dynamitedBlock != null && !dynamitedBlock.getType().isAir()) {
                            if (Helper.isPickaxeMineable(dynamitedBlock)) dynamitedBlock.breakNaturally(tool);
                        }
                    }
                    world.spawnParticle(Particle.EXPLOSION_EMITTER, block.getLocation().toCenterLocation(), 1);
                    player.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                });
            }
        }
        if (Helper.enchantmentExists(player, ELITE_MINEKEY)) {
            if (!DB.isPlayerPlaced(block) && Helper.isPickaxeMineable(block)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, ELITE_MINEKEY);
                double chance = enchantmentLevel * ELITE_MINEKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    EnchantedCrateUtils.addKey(player, 1);
                }
            }
        }
        if (Helper.enchantmentExists(player, MYTHIC_MINEKEY)) {
            if (!DB.isPlayerPlaced(block) && Helper.isPickaxeMineable(block)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, MYTHIC_MINEKEY);
                double chance = enchantmentLevel * MYTHIC_MINEKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    EnchantedCrateUtils.addKey(player, 1);
                }
            }
        }
        if (Helper.enchantmentExists(player, ELITE_DIRTKEY)) {
            if (!DB.isPlayerPlaced(block) && Helper.isShovelMineable(block)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, ELITE_DIRTKEY);
                double chance = enchantmentLevel * ELITE_DIRTKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    EnchantedCrateUtils.addKey(player, 1);
                }
            }
        }
        if (Helper.enchantmentExists(player, MYTHIC_DIRTKEY)) {
            if (!DB.isPlayerPlaced(block) && Helper.isShovelMineable(block)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, MYTHIC_DIRTKEY);
                double chance = enchantmentLevel * MYTHIC_DIRTKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    EnchantedCrateUtils.addKey(player, 1);
                }
            }
        }
        if (Helper.isCrop(block)) {
            if (Helper.enchantmentExists(player, ELITE_CROPKEY)) {
                if (Helper.isAtMaxAge(block)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(player, ELITE_CROPKEY);
                    double chance = ELITE_CROPKEY.getChancePerLevel() * enchantmentLevel;
                    if (Helper.rollChance(chance)) {
                        EnchantedCrateUtils.addKey(player, 1);
                        Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    }
                }
            }
            if (Helper.enchantmentExists(player, MYTHIC_CROPKEY)) {
                if (Helper.isAtMaxAge(block)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(player, MYTHIC_CROPKEY);
                    double chance = MYTHIC_CROPKEY.getChancePerLevel() * enchantmentLevel;
                    if (Helper.rollChance(chance)) {
                        EnchantedCrateUtils.addKey(player, 1);
                        Helper.sendMessage(player, "+1 Enchanted Crate Key");
                    }
                }
            }
        }
        if (Helper.enchantmentExists(player, ECHOING_TOUCH)) {
            int enchantmentLevel = Helper.getEnchantmentLevel(player, ECHOING_TOUCH);
            double chance = enchantmentLevel * ECHOING_TOUCH.getChancePerLevel();
            if (Helper.rollChance(chance)) {
                if (event.isCancelled()) return;
                Collection<ItemStack> firstDrops = block.getDrops(player.getInventory().getItemInMainHand(), player);
                Helper.tripleItemStackList(firstDrops);
                for (ItemStack drop : firstDrops) {
                    block.getWorld().dropItemNaturally(block.getLocation(), drop);
                }
                Collection<ItemStack> secondDrops = block.getDrops(player.getInventory().getItemInMainHand(), player);
                BlockBreakEvent fakeEvent = new BlockBreakEvent(
                        block, player
                );
                Bukkit.getPluginManager().callEvent(fakeEvent);
                if (!fakeEvent.isCancelled()) {
                    for (ItemStack drop : secondDrops) {
                        block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), drop);
                    }
                }
                block.setType(Material.AIR);
            }
        }
        DB.removeBlock(block);
    }

    @EventHandler
    public void onEntityInteractPlayerEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity rightClickedEntity = event.getRightClicked();
        if (rightClickedEntity instanceof LivingEntity entity) {
            if (Helper.enchantmentExists(player, INSPECTOR)) {
                Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize("<aqua><b>" + entity.getName() + "</b>:</aqua> <gold>" + Helper.round(entity.getHealth(), 2) + "<aqua>/<gold>" + Helper.getMaxHealth(entity) + "<red> ❤"));
            }
        }
    }

    @EventHandler
    public void playerInteractEnchantmentEffects(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        BlockFace face = event.getBlockFace();
        World world = player.getWorld();
        PlayerInventory inventory = player.getInventory();
        ItemStack clickedItem = event.getItem();
        if (action.isRightClick()) {
            HumaneEnchantment enchantment = HumaneEnchantment.fromItem(clickedItem);
            if (enchantment != null && clickedItem != null) {
                if (PlayerDataUtils.checkIfDataExists(player, enchantment.getId())) {
                    Helper.sendWarningMessage(player, "You already have " + enchantment.getName() + " on you");
                } else {
                    boolean conflict = false;
                    for (HumaneEnchantment humaneEnchantment : PlayerDataUtils.getEnchantmentsOnPlayer(player)) {
                        if (enchantment.conflictsWith(humaneEnchantment)) {
                            Helper.sendWarningMessage(player, "Humane enchantment failed to be applied! As it conflicts with <yellow>" + humaneEnchantment.getName());
                            conflict = true;
                        }
                    }
                    if (!conflict) {
                        Helper.sendMessage(player, "<green>you have enchanted yourself with <yellow>" + enchantment.getName() + "<green>!");
                        clickedItem.setAmount(clickedItem.getAmount() - 1);
                        PlayerDataUtils.addDataToPlayer(player, enchantment.getId());
                    }
                }
            }
            if (player.isSneaking()) {

                if (Helper.enchantmentExists(clickedItem, LAUNCH_STEP)) {
                    if (PlayerCooldowns.LAUNCH_STEP.isOnCooldown(player)) {
                        Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize("<red>Launch Step Cooldown: " + Helper.formatCooldownTime(PlayerCooldowns.LAUNCH_STEP.getRemainingTime(player))));
                    } else {
                        int enchantmentLevel = Helper.getEnchantmentLevel(clickedItem, LAUNCH_STEP);
                        double strength = 0.9 + (0.3 * enchantmentLevel);
                        clickedItem.damage(2, player);
                        PlayerCooldowns.CANCEL_FALL_DAMAGE.addCooldown(player, Helper.secToMs(3));
                        Helper.launchInFacingDirection(player, strength);
                        PlayerCooldowns.LAUNCH_STEP.addCooldown(player, Helper.secToMs(30));
                    }
                }
                if (Helper.enchantmentExists(clickedItem, TORCH_FLICK)) {
                    ItemStack torch = Helper.findTorchInInventory(player);
                    if (torch != null && block != null) {
                        Block placeAt = block.getRelative(face);
                        if (placeAt.getType().isAir() && block.isSolid()) {
                            if (face == BlockFace.UP) {
                                placeAt.setType(Material.TORCH);
                            } else if (face != BlockFace.DOWN) {
                                placeAt.setType(Material.WALL_TORCH);
                                BlockData data = placeAt.getBlockData();
                                if (data instanceof Directional directional) {
                                    directional.setFacing(face);
                                    placeAt.setBlockData(directional);
                                }
                            }
                            torch.setAmount(torch.getAmount() - 1);
                            player.playSound(player, Sound.BLOCK_WOOD_PLACE, 1, 1);
                        }
                    }
                }
            }
            if (Helper.enchantmentExists(player, BOTANISTS_GRACE)) {
                if (block != null && Helper.isAtMaxAge(block)) {
                    Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand(), player);
                    for (ItemStack itemStack : drops) {
                        if (Helper.canFitInInventory(player, itemStack)) {
                            if (!Helper.safeGiveItem(player, itemStack)) {
                                world.dropItemNaturally(block.getLocation().toCenterLocation(), itemStack);
                            }
                        }
                    }
                    Helper.makeItSmall(block);
                }
            }
            if (ItemDataUtils.hasData(clickedItem, "mystical_enchantments_bag")) {
                clickedItem.editPersistentDataContainer(container -> container.set(ItemDataUtils.bagKeyUuid, PersistentDataType.STRING, UUID.randomUUID().toString()));
                player.openInventory(ItemDataUtils.loadInventoryFromItem(clickedItem, "mystical_bag_noter"));
                event.setCancelled(true);

                PersistentDataContainer container = player.getPersistentDataContainer();
                container.set(ItemDataUtils.mysticalBagKey, PersistentDataType.BYTE_ARRAY, clickedItem.serializeAsBytes());
            }
            if (ItemDataUtils.hasData(clickedItem, "farmer_satchel")) {
                clickedItem.editPersistentDataContainer(container -> container.set(ItemDataUtils.bagKeyUuid, PersistentDataType.STRING, UUID.randomUUID().toString()));
                player.openInventory(ItemDataUtils.loadInventoryFromItem(clickedItem, "farmer_satchel_noter"));
                event.setCancelled(true);

                PersistentDataContainer container = player.getPersistentDataContainer();
                container.set(ItemDataUtils.mysticalBagKey, PersistentDataType.BYTE_ARRAY, clickedItem.serializeAsBytes());
            }
            if (ItemDataUtils.hasData(clickedItem, "potion_of_enlightened_effort")) {
                if (PlayerCooldowns.POTION_OF_ENLIGHTENED_EFFORTS.isOnCooldown(player)) {
                    Helper.sendWarningMessage(player, "You already have this effect on you! " + Helper.formatCooldownTime(PlayerCooldowns.POTION_OF_ENLIGHTENED_EFFORTS.getRemainingTime(player)));
                } else {
                    PlayerCooldowns.POTION_OF_ENLIGHTENED_EFFORTS.addCooldown(player, Helper.minToMs(8));
                    Helper.sendMessage(player, "You got 8 minutes of 1.5x Mystic Efforts Points!");
                    event.setCancelled(true);
                    clickedItem.setAmount(clickedItem.getAmount() - 1);
                }

            }
            if (ItemDataUtils.hasData(clickedItem, "portable_repairer")) {
                event.setCancelled(true);
                ItemStack mainHandItem = inventory.getItemInMainHand();
                ItemStack offHandItem = inventory.getItemInOffHand();
                if (ItemDataUtils.hasData(mainHandItem, "portable_repairer"))
                    Helper.handlePortableRepairer(mainHandItem, offHandItem, player);
                else Helper.handlePortableRepairer(offHandItem, mainHandItem, player);
            }
            if (ItemDataUtils.hasData(clickedItem, "scroll_of_preservation")) {
                event.setCancelled(true);
                ItemStack mainHandItem = inventory.getItemInMainHand();
                ItemStack offHandItem = inventory.getItemInOffHand();
                if (ItemDataUtils.hasData(mainHandItem, "scroll_of_preservation"))
                    Helper.handleScrollOfPreservation(mainHandItem, offHandItem, player);
                else Helper.handleScrollOfPreservation(offHandItem, mainHandItem, player);
            }
            if (ItemDataUtils.hasData(clickedItem, "tome_of_mystical_leveling")) {
                double givenCoins = Helper.round(MysticCoinHandler.getRewardForEfforts(player), 2);
                MysticCoinUtils.addCoin(player, givenCoins);
                event.setCancelled(true);
                clickedItem.setAmount(clickedItem.getAmount() - 1);
                Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.GREEN + "You obtained " + givenCoins + " Mystic Coins from the Tome Of Mystical Leveling"));
                MysticCoinHandler.levelUp(player);
            }
            if (ItemDataUtils.hasData(clickedItem, "tier1_box")) {
                event.setCancelled(true);
                ItemStack book = Helper.rollEnchantedBook(Helper.rollTier(60, 35, 5, 0, 0));
                if (!book.isEmpty()) {
                    if (Helper.safeGiveItem(player, book)) {
                        clickedItem.setAmount(clickedItem.getAmount() - 1);
                        world.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.AQUA + "You opened Tier I Enchantments Box!"));
                    } else {
                        Helper.sendWarningMessage(player, "You don't have enough inventory space!");
                    }
                }
            }
            if (ItemDataUtils.hasData(clickedItem, "tier2_box")) {
                event.setCancelled(true);
                ItemStack book = Helper.rollEnchantedBook(Helper.rollTier(35, 40, 20, 5, 0));
                if (!book.isEmpty()) {
                    if (Helper.safeGiveItem(player, book)) {
                        clickedItem.setAmount(clickedItem.getAmount() - 1);
                        world.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.AQUA + "You opened Tier II Enchantments Box!"));
                    } else {
                        Helper.sendWarningMessage(player, "You don't have enough inventory space!");
                    }
                }
            }
            if (ItemDataUtils.hasData(clickedItem, "tier3_box")) {
                event.setCancelled(true);
                ItemStack book = Helper.rollEnchantedBook(Helper.rollTier(20, 35, 30, 10, 5));
                if (!book.isEmpty()) {
                    if (Helper.safeGiveItem(player, book)) {
                        clickedItem.setAmount(clickedItem.getAmount() - 1);
                        world.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.AQUA + "You opened Tier III Enchantments Box!"));
                    } else {
                        Helper.sendWarningMessage(player, "You don't have enough inventory space!");
                    }
                }
            }
            if (ItemDataUtils.hasData(clickedItem, "tier4_box")) {
                event.setCancelled(true);
                ItemStack book = Helper.rollEnchantedBook(Helper.rollTier(5, 20, 25, 30, 20));
                if (!book.isEmpty()) {
                    if (Helper.safeGiveItem(player, book)) {
                        clickedItem.setAmount(clickedItem.getAmount() - 1);
                        world.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.AQUA + "You opened Tier IV Enchantments Box!"));
                    } else {
                        Helper.sendWarningMessage(player, "You don't have enough inventory space!");
                    }
                }
            }
            if (ItemDataUtils.hasData(clickedItem, "upgrade_crystal")) {

                event.setCancelled(true);
                if (Helper.isOre(block)) {
                    Material blockVersion = Helper.getOreBlock(block);
                    block.setType(blockVersion);
                    clickedItem.setAmount(clickedItem.getAmount() - 1);
                    Helper.drawEntityHitboxParticles(block, Particle.HAPPY_VILLAGER, 1, 0);
                } else {
                    Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.RED + "Right-click an ore to use!"));
                }
            }
        }
        if (action.isLeftClick()) {
            if (ItemDataUtils.hasData(clickedItem, "lightning_wand")) {
                event.setCancelled(true);
                if (PlayerCooldowns.LIGHTNING_WAND.isOnCooldown(player)) {
                    Helper.sendActionBar(player, Gradient.RED + "You are on Cooldown! " + Helper.formatCooldownTime(PlayerCooldowns.LIGHTNING_WAND.getRemainingTime(player)));
                } else {
                    LivingEntity target = Helper.rayTraceFromEyes(player, 20);
                    if (target == null)
                        Helper.sendActionBar(player, Gradient.PINK + "No target found");
                    else {
                        clickedItem.damage(1, player);
                        world.strikeLightningEffect(target.getLocation());
                        target.damage(10, player);
                        target.setFireTicks(100);
                        Helper.applyKnockback(player, target, 0.3, 0.2);
                        Helper.sendActionBar(player, Gradient.BLUE + "You struck " + target.getName() + " <black> | <red>5s Cooldown");
                        PlayerCooldowns.LIGHTNING_WAND.addCooldown(player, Helper.secToMs(5));
                    }
                }
            }

            if (Helper.enchantmentExists(player, SWIFT_SHEAR)) {
                if (block != null) {
                    if (Helper.isLeaf(block.getState())) player.breakBlock(block);
                }
            }
        }

        MysticResource resource = MysticResource.get(clickedItem);
        if (resource != null) {
            clickedItem.setAmount(clickedItem.getAmount() - 1);
            Helper.sendActionBar(player,  Gradient.YELLOW + "You claimed " + resource.getTier().getColorCode() + resource.getName());
            MysticCoinUtils.addCoin(player, resource.getWorth());
        }

        if (ItemDataUtils.hasData(clickedItem, "healing_wand")) {
            event.setCancelled(true);
            if (PlayerCooldowns.HEALING_WAND.isOnCooldown(player)) {
                Helper.sendActionBar(player, Gradient.RED + "You are on cooldown!");
            } else {
                if (action.isLeftClick()) {
                    Player targetPlayer = Helper.rayTracePlayerFromEyes(player, 12);
                    if (targetPlayer == null) Helper.sendActionBar(player, Gradient.PINK + "No Targeted Player Found");
                    else {
                        PlayerCooldowns.HEALING_WAND.addCooldown(player, Helper.secToMs(30));
                        targetPlayer.heal(4);
                        clickedItem.damage(1, player);
                        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Helper.secondsToTicks(10), 1));
                        Helper.sendActionBar(targetPlayer, Gradient.GREEN + player.getName() + " used healing wand upon you");
                    }
                } else if (action.isRightClick()) {
                    PlayerCooldowns.HEALING_WAND.addCooldown(player, Helper.secondsToTicks(30));
                    player.heal(4);
                    clickedItem.damage(1, player);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Helper.secondsToTicks(10), 1));
                    Helper.sendActionBar(player, Gradient.GREEN + player.getName() + " used healing wand upon you");
                }
            }
        }
    }

    @EventHandler
    public void preAttackEvent(PrePlayerAttackEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack weapon = player.getInventory().getItemInMainHand();
        World world = player.getWorld();
        if (ItemDataUtils.hasData(weapon, "lightning_wand")) {
            event.setCancelled(true);
            if (PlayerCooldowns.LIGHTNING_WAND.isOnCooldown(player)) {
                Helper.sendActionBar(player, Gradient.RED + "You are on Cooldown! " + Helper.formatCooldownTime(PlayerCooldowns.LIGHTNING_WAND.getRemainingTime(player)));
            } else {
                LivingEntity target = Helper.rayTraceFromEyes(player, 20);
                if (target == null)
                    Helper.sendActionBar(player, Gradient.PINK + "No target found");
                else {
                    weapon.damage(1, player);
                    world.strikeLightningEffect(target.getLocation());
                    target.damage(10, player);
                    target.setFireTicks(100);
                    Helper.applyKnockback(player, target, 0.3, 0.2);
                    Helper.sendActionBar(player, Gradient.BLUE + "You struck " + target.getName() + " <black> | <red>5s Cooldown");
                    PlayerCooldowns.LIGHTNING_WAND.addCooldown(player, Helper.secToMs(5));
                }
            }
        }
        if (ItemDataUtils.hasData(weapon, "healing_wand")) {
            event.setCancelled(true);
            if (PlayerCooldowns.HEALING_WAND.isOnCooldown(player)) {
                Helper.sendActionBar(player, Gradient.RED + "You are on cooldown!");
            } else {
                Player targetPlayer = Helper.rayTracePlayerFromEyes(player, 12);
                if (targetPlayer == null) Helper.sendActionBar(player, Gradient.PINK + "No Targeted Player Found");
                else {
                    PlayerCooldowns.HEALING_WAND.addCooldown(player, Helper.secToMs(30));
                    targetPlayer.heal(4);
                    weapon.damage(1, player);
                    targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Helper.secondsToTicks(10), 1));
                    Helper.sendActionBar(targetPlayer, Gradient.GREEN + player.getName() + " used healing wand upon you");
                }
            }
        }
    }

    @EventHandler
    public void onMysticalBagClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        Inventory inventory = event.getInventory();
        ItemStack currentItem = event.getCurrentItem();
        if (ItemDataUtils.hasData(currentItem, "mystical_bag_noter") || ItemDataUtils.hasData(currentItem, "farmer_satchel_noter")) event.setCancelled(true);
        if (clickedInventory == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (currentItem == null) return;
        if (Helper.isMysticalBagInventory(inventory)) {
            if (currentItem.getType() != Material.ENCHANTED_BOOK) event.setCancelled(true);
        }
        if (Helper.isFarmerSatchel(inventory)) {
            if (!Helper.isFarmerSatchelSupported(currentItem)) event.setCancelled(true);
        }
    }


    @EventHandler
    public void fishingEnchantmentEffect(PlayerFishEvent event) {
        Player player = event.getPlayer();
        PlayerFishEvent.State state = event.getState();
        ItemStack fishingRod = Helper.getFishingRod(player);
        if (fishingRod != null) {
            if (Helper.enchantmentExists(fishingRod, FISHERMAN_LUCK)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(fishingRod, FISHERMAN_LUCK);
                double chance = FISHERMAN_LUCK.getChancePerLevel() * enchantmentLevel;
                if (Helper.rollChance(chance) && state == PlayerFishEvent.State.CAUGHT_FISH) {
                    Entity caught = event.getCaught();
                    if (caught instanceof Item item) {
                        ItemStack itemStack = item.getItemStack();
                        if (itemStack.getMaxStackSize() == 1) {
                            item.addPassenger(player.getWorld().dropItemNaturally(item.getLocation(), item.getItemStack()));
                        } else {
                            itemStack.setAmount(Math.min(itemStack.getAmount() * 2, itemStack.getMaxStackSize()));
                            item.setItemStack(itemStack);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void deathEnchantmentEffects(PlayerDeathEvent event) {
        List<ItemStack> items = event.getItemsToKeep();
        List<ItemStack> drops = event.getDrops();
        Player player = event.getPlayer();
        World world = player.getWorld();
        Iterator<ItemStack> it = drops.iterator();
        while (it.hasNext()) {
            ItemStack item = it.next();
            if (Helper.enchantmentExists(item, SOULBOUND)) {
                items.add(item);
                it.remove();
            }
        }

        if (PlayerDataUtils.checkIfDataExists(player, UNDYING_KNOWLEDGE.getId())) {
            event.setDroppedExp(0);
            event.setKeepLevel(true);
        }

        if (Helper.enchantmentExists(player, FINAL_RECKONING)) {
            TNTPrimed primed = world.spawn(player.getLocation(), TNTPrimed.class, (TNTPrimed tnt) -> tnt.setFuseTicks(20));
            EntityDataUtils.setData(primed, FINAL_RECKONING.getId() + "_tnt");
            EntityDataUtils.setOwner(primed, player);
            PlayerDataUtils.removeData(player, FINAL_RECKONING.getId());
            Helper.sendMessage(player, "You have used your " + BookTiers.ELDRITCH.getColor() + FINAL_RECKONING.getName());
        }
    }

    @EventHandler
    public void onTntExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (EntityDataUtils.hasData(entity, FINAL_RECKONING.getId() + "_tnt")) {
            event.setCancelled(true);
            List<LivingEntity> killedEntities = new ArrayList<>();
            TNTPrimed tnt = (TNTPrimed) entity;
            Player player = EntityDataUtils.getOwner(tnt);
            for (Entity inRadius : tnt.getNearbyEntities(5, 5, 5)) {
                if (inRadius == player) continue;
                Helper.applyKnockback(tnt, inRadius, 1.2, 1);
                if (inRadius instanceof LivingEntity livingEntity) {
                    livingEntity.damage(16);
                    if (livingEntity.isDead()) {
                        Helper.sendMessage(livingEntity, "You have been killed by " + BookTiers.ELDRITCH.getColor() + FINAL_RECKONING.getName());
                        killedEntities.add(livingEntity);
                    }
                }
            }
            if (player != null) {
                if (killedEntities.isEmpty()) {
                    Helper.sendMessage(player, "You haven't killed any entities using " + BookTiers.ELDRITCH.getColor() + FINAL_RECKONING.getName());
                } else {
                    StringBuilder killedNames = new StringBuilder();
                    for (LivingEntity living : killedEntities) {
                        killedNames.append(living.getName());
                    }
                    Helper.sendMessage(player, BookTiers.ELDRITCH.getColor() + FINAL_RECKONING.getName() + "</gradient> killed " + Gradient.PINK + killedNames);
                }
            }
        }
    }

    @EventHandler
    public void onBowHitEnchantmentEffects(ProjectileHitEvent event) {
        Entity projectile = event.getEntity();
        Entity entity = event.getHitEntity();
        if (entity == null) return;
        World world = projectile.getWorld();
        if (projectile instanceof Arrow arrow && entity instanceof LivingEntity victim) {
            ItemStack bow = arrow.getWeapon();
            if (bow != null) {
                if (Helper.enchantmentExists(bow, VENOMSTRIKE)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(bow, VENOMSTRIKE);
                    double chance = enchantmentLevel * VENOMSTRIKE.getChancePerLevel();
                    int poisonTicks = enchantmentLevel * 40;
                    if (Helper.rollChance(chance)) {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, poisonTicks, 0));
                    }
                }
                if (Helper.enchantmentExists(bow, BURNOUT)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(bow, BURNOUT);
                    double chance = enchantmentLevel * BURNOUT.getChancePerLevel();
                    int fireTicks = 20 + (20 * enchantmentLevel);
                    if (Helper.rollChance(chance)) {
                        victim.setFireTicks(victim.getFireTicks() + fireTicks);
                    }
                }
                if (Helper.enchantmentExists(bow, THUNDERSTRIKE)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(bow, THUNDERSTRIKE);
                    double chance = enchantmentLevel * THUNDERSTRIKE.getChancePerLevel();
                    if (Helper.rollChance(chance)) {
                        victim.setNoDamageTicks(0);
                        world.strikeLightningEffect(victim.getLocation());
                        victim.damage(2 + enchantmentLevel);

                    }
                }
            }
        }
    }
    @EventHandler
    public void onProjectileShootEnchantmentEffects(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();
        if (bow == null) return;
        if (event.getProjectile() instanceof Arrow arrow && event.getEntity() instanceof Player) {
            if (Helper.enchantmentExists(bow, TRUE_SHOT)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(bow, TRUE_SHOT);
                int ticks = 10 + (enchantmentLevel * 10);
                Helper.keepArrowStraight(arrow, ticks);
            }
        }
    }


    @EventHandler
    public void playerSleepEnchantmentEffects(PlayerDeepSleepEvent event) {
        Player player = event.getPlayer();
        if (Helper.enchantmentExists(player, SLEEPER_LUCK)) {
            PlayerCooldowns.SLEEPER_LUCK.addCooldown(player, PlayerCooldowns.SLEEPER_LUCK.getRemainingTime(player) + Helper.minToMs(5));
            Helper.sendMessage(player, Gradient.YELLOW + "You gained " + Helper.formatCooldownTime(PlayerCooldowns.SLEEPER_LUCK.getRemainingTime(player)) + " of 1.5x Xp Boost");
        }
    }

    @EventHandler
    public void onXpOrbPickUpEnchantmentEffects(PlayerPickupExperienceEvent event) {
        Player player = event.getPlayer();
        ExperienceOrb orb = event.getExperienceOrb();
        double modifier = 1;
        if (PlayerDataUtils.checkIfDataExists(player, COLLECTOR.getId())) {
            modifier += 0.1;
        }
        if (PlayerCooldowns.SLEEPER_LUCK.isOnCooldown(player)) modifier += .15;
        // Rest of modifiers here
        event.setCancelled(true);
        int xpGive = (int) Math.round(orb.getExperience() * modifier);
        orb.remove();
        player.setExperienceLevelAndProgress(player.calculateTotalExperiencePoints() + xpGive);
    }

    @EventHandler
    public void entityDamageEnchantmentEffects(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            double damage = event.getDamage();
            double finalDamage = event.getFinalDamage();
            World world = player.getWorld();
            EntityDamageEvent.DamageCause cause = event.getCause();
            if (PlayerDataUtils.checkIfDataExists(player, VANISH_STEP.getId())) {
                if (!Helper.onCooldownVanishStep.contains(player.getUniqueId())) {
                    AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
                    if (attribute != null) {
                        double currentHealth = player.getHealth() - event.getFinalDamage();
                        double someHealth = attribute.getValue() * 0.3;
                        if (someHealth >= currentHealth) {
                            Helper.onCooldownVanishStep.add(player.getUniqueId());
                            PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0, true, true, true);
                            PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 200, 0, true, true, true);
                            player.addPotionEffect(effect);
                            player.addPotionEffect(effect2);
                            Helper.runAfterDelay(180 * 20, () -> Helper.onCooldownVanishStep.remove(player.getUniqueId()));
                        }
                    }
                }
            }
            PlayerInventory inventory = player.getInventory();
            if (Helper.enchantmentExists(player, COMBAT_INTUITION)) {
                if (!Helper.onCooldownCombatIntuition.contains(player.getUniqueId())) {
                    AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
                    if (attribute != null) {
                        double currentHealth = player.getHealth() - event.getFinalDamage();
                        double someHealth = attribute.getValue() * 0.2;
                        if (someHealth >= currentHealth) {
                            Helper.onCooldownCombatIntuition.add(player.getUniqueId());
                            PotionEffect effect = new PotionEffect(PotionEffectType.STRENGTH, 100, 0, true, true, true);
                            player.addPotionEffect(effect);
                            Helper.runAfterDelay(Helper.secondsToTicks(30), () -> Helper.onCooldownCombatIntuition.remove(player.getUniqueId()));
                        }
                    }
                }
            }
            ItemStack chestplateArmor = inventory.getChestplate();
            ItemStack boots = inventory.getBoots();
            if (Helper.enchantmentExists(chestplateArmor, LAST_STAND) && Helper.isDeadAfterDamage(player, finalDamage) && finalDamage >= 3) {
                player.setHealth(Math.min(2, Helper.getMaxHealth(player)));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Helper.secondsToTicks(7), 1));
                world.playSound(player, Sound.ITEM_TOTEM_USE, 1, 1);
                world.spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation(), 2);
                chestplateArmor.removeEnchantment(LAST_STAND.getEnchantment());
                event.setCancelled(true);
                Helper.sendMessage(player, "§eLast Stand §ahas been consumed and saved you from death!");
            }
            if (chestplateArmor != null) {
                if (Helper.enchantmentExists(chestplateArmor, STONE_SKIN)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(chestplateArmor, STONE_SKIN);
                    double chance = enchantmentLevel * STONE_SKIN.getChancePerLevel();
                    if (Helper.rollChance(chance)) {
                        event.setDamage(Math.max(0, damage - 2));
                    }
                }
                if ((cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) && Helper.enchantmentExists(chestplateArmor, REINFORCED_PLATING)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(chestplateArmor, REINFORCED_PLATING);
                    double reduction = Math.min(0.05 * enchantmentLevel, 0.8);
                    double newDamage = damage * (1 - reduction);
                    event.setDamage(newDamage);
                }
            }
            if (cause == EntityDamageEvent.DamageCause.LAVA) {
                ItemStack chestplate = player.getInventory().getChestplate();
                if (Helper.enchantmentExists(chestplate, ENKINDLED_CORE)) {
                    if (PlayerCooldowns.ENKINDLED_CORE.isOnCooldown(player)) {
                        PlayerCooldowns.ENKINDLED_CORE.addCooldown(player, Helper.minToMs(1));
                        PlayerCooldowns.ENKINDLED_CORE_EFFECT.addCooldown(player, Helper.secToMs(5));
                    }
                }
                if (PlayerCooldowns.ENKINDLED_CORE_EFFECT.isOnCooldown(player)) {
                    player.heal(finalDamage);
                    event.setCancelled(true);
                }
            }
            if (cause == EntityDamageEvent.DamageCause.FALL) {
                if (PlayerCooldowns.CANCEL_FALL_DAMAGE.isOnCooldown(player)) {
                    event.setCancelled(true);
                    PlayerCooldowns.CANCEL_FALL_DAMAGE.clearCooldown(player);
                } else {
                    if (boots != null) {
                        if (Helper.enchantmentExists(boots, FEATHERFOOT)) {
                            int enchantmentLevel = Helper.getEnchantmentLevel(boots, FEATHERFOOT);
                            double chance = enchantmentLevel * FEATHERFOOT.getChancePerLevel();
                            if (Helper.rollChance(chance)) {
                                event.setCancelled(true);
                            }
                        }
                        if (Helper.enchantmentExists(boots, SEISMIC_SLAM)) {
                            float fallDistance = player.getFallDistance();
                            if (fallDistance >= 6) {
                                Helper.applyKnockbackAround(player, 7, 1.6, 1.1);
                                world.spawnParticle(Particle.EXPLOSION_EMITTER, player.getLocation(), 1);
                                world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 1.2f);
                            }
                        }
                        if (Helper.enchantmentExists(boots, WINDSTEP)) {
                            float fallDistance = player.getFallDistance();
                            if (fallDistance >= 5) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityHitEnchantmentsEffect(EntityDamageByEntityEvent event) {
        EnchantmentStack stormcaller = STORMCALLER;
        EnchantmentStack twinStrike = TWIN_STRIKE;
        EnchantmentStack lifesteal = LIFESTEAL;
        EnchantmentStack glacialShield = GLACIAL_SHIELD;
        EnchantmentStack rageRedirect = RAGE_REDIRECT;
        EnchantmentStack cripplingEdge = CRIPPLING_EDGE;
        EnchantmentStack soulSever = SOUL_SEVER;
        EnchantmentStack witheringCut = WITHERING_CUT;
        EnchantmentStack blisteringEdge = BLISTERING_EDGE;
        // Player's Attack
        if (event.getDamager() instanceof Player player && event.getEntity() instanceof LivingEntity entity) {
            double damage = event.getDamage();

            if (event.isCritical() && Helper.enchantmentExists(player, ECLIPSE_EDGE) && player.isSneaking()) {
                if (!PlayerCooldowns.ECLIPSE_EDGE.isOnCooldown(player)) {
                    damage *= 2.5;
                    PlayerCooldowns.ECLIPSE_EDGE.addCooldown(player, Helper.secToMs(3));
                }
            }
            if (Helper.enchantmentExists(player, ARCANE_ECHO)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, ARCANE_ECHO);
                double chance = enchantmentLevel * ARCANE_ECHO.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    double newDamage = damage * 0.4;
                    Helper.sendActionBar(player, Gradient.YELLOW + "Arcane Echo activated on the " + Gradient.GREEN + entity.getName());
                    Helper.runAfterDelay(40, () -> {
                        if (!entity.isDead()) {
                            int ticks = entity.getNoDamageTicks();
                            entity.setNoDamageTicks(0);
                            entity.damage(newDamage);
                            entity.setNoDamageTicks(ticks);
                            Helper.sendActionBar(player, Gradient.YELLOW + "Arcane Echo Damaged The " + Gradient.GREEN + entity.getName() + "</gradient> with " + Gradient.GREEN + Helper.round(newDamage, 1));
                        }
                    });
                }
            }
            MysticCoinHandler.addEffortPoints(player, MysticCoinHandler.getEffortsPointsForDamage(player, event.getFinalDamage()));
            World world = entity.getWorld();
            if (entity instanceof Boss) {
                if (Helper.enchantmentExists(player, BOSS_SLAYER)) {
                    damage *= 1.15;
                }
            }
            if (Helper.enchantmentExists(player, stormcaller)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, stormcaller);
                int fireTicks = 20 + (enchantmentLevel - 1) * 30;
                int damageFromLightning = 4 + enchantmentLevel;
                if (Helper.rollChance(enchantmentLevel * stormcaller.getChancePerLevel())) {
                    entity.setNoDamageTicks(0);
                    world.strikeLightningEffect(entity.getLocation());
                    entity.damage(damageFromLightning);
                    entity.setFireTicks(fireTicks);
                }
            }
            if (Helper.enchantmentExists(player, EXECUTIONER_EDGE)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, EXECUTIONER_EDGE);
                double chance = enchantmentLevel * EXECUTIONER_EDGE.getChancePerLevel();
                if (Helper.rollChance(chance) && entity.getHealth() <= (Helper.getMaxHealth(entity) * 0.25)) {
                    damage *= 2;
                }
            }
            if (Helper.enchantmentExists(player, COMBUSTION)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, COMBUSTION);
                double chance = enchantmentLevel * COMBUSTION.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    double strength = 0.6 + (0.1 * enchantmentLevel);
                    double yVelocity = 0.25 + (0.05 * enchantmentLevel);
                    int radius = 4;
                    int damageValue = 3 + (2 * enchantmentLevel);
                    List<LivingEntity> entities = Helper.getNearbyLivingEntities(entity.getLocation(), radius);
                    entities.remove(player);
                    world.playSound(entity.getLocation(), Sound.ENTITY_TNT_PRIMED, 1, 1);
                    world.spawnParticle(Particle.FLAME, entity.getLocation().toCenterLocation(), 10);
                    Helper.runAfterDelay(40, () -> {
                        world.spawnParticle(Particle.FLASH, entity.getLocation().toCenterLocation(), 3);
                        world.playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                        for (LivingEntity currentEntity : entities) {
                            Helper.applyKnockback(entity, currentEntity, strength, yVelocity);
                            currentEntity.damage(damageValue);
                        }
                    });
                }
            }
            if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof Player victim) {
                if (Helper.enchantmentExists(attacker, SHATTER_STRIKE)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(attacker, SHATTER_STRIKE);
                    double chance = enchantmentLevel * SHATTER_STRIKE.getChancePerLevel();
                    if (Helper.rollChance(chance)) {
                        List<ItemStack> victimArmor = Helper.getPlayerArmor(victim);
                        for (ItemStack armor : victimArmor) {
                            armor.damage(10, victim);
                        }
                    }
                }
            } // Two players attack
            if (Helper.enchantmentExists(player, cripplingEdge)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, cripplingEdge);
                double chance = cripplingEdge.getChancePerLevel() * enchantmentLevel;
                PotionEffect slowness = new PotionEffect(PotionEffectType.SLOWNESS, 60, 0, true, true, true);
                PotionEffect fatigue = new PotionEffect(PotionEffectType.MINING_FATIGUE, 120, 1, true, true, true);
                PotionEffect chosen = Helper.rollChance(50) ? slowness : fatigue;
                if (Helper.rollChance(chance)) entity.addPotionEffect(chosen);
            }
            if (Helper.enchantmentExists(player, witheringCut)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, witheringCut);
                double chance = enchantmentLevel * witheringCut.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    PotionEffect wither = new PotionEffect(PotionEffectType.WITHER, 40, 1, true, true, true);
                    entity.addPotionEffect(wither);
                }
            }
            if (Helper.enchantmentExists(player, soulSever)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, soulSever);
                double chance = soulSever.getChancePerLevel() * enchantmentLevel;
                if (Helper.rollChance(chance)) {
                    Helper.pureDamage(entity, player, 99999);
                }
            }
            if (Helper.enchantmentExists(player, twinStrike)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, twinStrike);
                if (Helper.rollChance(enchantmentLevel * twinStrike.getChancePerLevel())) {
                    entity.setNoDamageTicks(0);
                    AttributeInstance attribute = player.getAttribute(Attribute.ATTACK_SPEED);
                    assert attribute != null;
                    double originalValue = attribute.getBaseValue();
                    attribute.setBaseValue(1024);
                    player.attack(entity);
                    Helper.runAfterDelay(2, () -> attribute.setBaseValue(originalValue));
                }
            }
            if (Helper.enchantmentExists(player, MARKING_STRIKE)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, MARKING_STRIKE);
                int effectTicks = 30 * enchantmentLevel;
                PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, effectTicks, 0, true, true, true);
                entity.addPotionEffect(effect);
            }
            if (Helper.enchantmentExists(player, lifesteal)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, lifesteal);
                AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
                if (attribute != null) {
                    if (Helper.rollChance(enchantmentLevel * lifesteal.getChancePerLevel())) {
                        double maxHealth = attribute.getValue();
                        double percent = (double) (15 + (enchantmentLevel * 5)) / 100;
                        double amountToHealAndDamage = maxHealth * percent;
                        player.heal(amountToHealAndDamage);
                        entity.damage(amountToHealAndDamage);
                    }
                }
            }
            if (Helper.enchantmentExists(player, rageRedirect)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, rageRedirect);
                double chance = rageRedirect.getChancePerLevel() * enchantmentLevel;
                if (Helper.rollChance(chance)) {
                    if (entity instanceof Mob mob) {
                        LivingEntity target = Helper.getNearestHostile(mob, 10);
                        if (target != null) {
                            mob.teleport(target.getLocation());
                            mob.setTarget(target);
                            Helper.drawEntityHitboxParticles(entity, Particle.FLAME, 5, 0.25);
                            Helper.sendActionBar(player, Component.text("Redirected The Rage Of The " + mob.getName(), NamedTextColor.YELLOW));
                        }
                    }
                }
            }
            if (Helper.enchantmentExists(player, blisteringEdge)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, blisteringEdge);
                double chance = blisteringEdge.getChancePerLevel() * enchantmentLevel;
                if (Helper.rollChance(chance)) {
                    PotionEffect effect = new PotionEffect(PotionEffectType.WEAKNESS, 60, 0, true, true, true);
                    entity.addPotionEffect(effect);
                }
            }
            if (Helper.enchantmentExists(player, THUMP)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, THUMP);
                double chance = enchantmentLevel * THUMP.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    PotionEffect effect = new PotionEffect(PotionEffectType.SLOWNESS, 60, 0, true, true, true);
                    entity.addPotionEffect(effect);
                }
            }
            if (Helper.enchantmentExists(player, VENOMSTRIKE)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, VENOMSTRIKE);
                double chance = enchantmentLevel * VENOMSTRIKE.getChancePerLevel();
                int poisonTicks = enchantmentLevel * 40;
                if (Helper.rollChance(chance)) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, poisonTicks, 0));
                }
            }
            if (Helper.enchantmentExists(player, CRITICAL_PULSE)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, CRITICAL_PULSE);
                double chance = CRITICAL_PULSE.getChancePerLevel() * enchantmentLevel;
                int magicDamage = 2 + (2 * enchantmentLevel);
                if (Helper.rollChance(chance)) {
                    entity.damage(magicDamage, DamageSource.builder(DamageType.MAGIC).build());
                }
            }
            if (Helper.enchantmentExists(player, OPENING_WOUND)) {
                AttributeInstance attributeInstance = entity.getAttribute(Attribute.MAX_HEALTH);
                if (attributeInstance != null) {
                    double maxHealth = attributeInstance.getValue();
                    double currentHealth = entity.getHealth();
                    if (maxHealth >= currentHealth) {
                        damage *= 1.1;
                    }
                }
            }
            if (Helper.enchantmentExists(player, ALCHEMISTS_TOUCH)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, ALCHEMISTS_TOUCH);
                double chance = enchantmentLevel * ALCHEMISTS_TOUCH.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    List<PotionEffect> potionEffects = List.of(
                            new PotionEffect(PotionEffectType.WITHER, 40, 0, true, true, true),
                            new PotionEffect(PotionEffectType.WEAKNESS, 60, 0, true, true, true),
                            new PotionEffect(PotionEffectType.HUNGER, 80, 0, true, true, true),
                            new PotionEffect(PotionEffectType.SLOWNESS, 50, 0, true, true, true),
                            new PotionEffect(PotionEffectType.POISON, 60, 0, true, true, true),
                            new PotionEffect(PotionEffectType.DARKNESS, 40, 0, true, true, true),
                            new PotionEffect(PotionEffectType.NAUSEA, 100, 1, true, true, true)
                    );
                    entity.addPotionEffect(potionEffects.get(Helper.random.nextInt(potionEffects.size())));
                }
            }
            if (Helper.enchantmentExists(player, POPCORN)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, POPCORN);
                double chance = enchantmentLevel * POPCORN.getChancePerLevel();
                int amplifier = 29 + enchantmentLevel * 2;
                if (Helper.rollChance(chance)) {
                    PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 5, amplifier, false, false, false);
                    entity.addPotionEffect(effect);
                }
            }
            if (Helper.enchantmentExists(player, SKYBREAKER)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, SKYBREAKER);
                double chance = enchantmentLevel * SKYBREAKER.getChancePerLevel();
                int range = 2 + enchantmentLevel;
                if (Helper.rollChance(chance)) {
                    List<LivingEntity> entities = Helper.getNearbyLivingEntities(entity.getLocation(), range);
                    double strength = 1 + (0.1 * enchantmentLevel);
                    double damageValue = 5 + enchantmentLevel;
                    entities.remove(player);
                    for (LivingEntity currentEntity : entities) {
                        Helper.applyKnockback(player, currentEntity, strength);
                        currentEntity.damage(damageValue);
                    }
                    world.spawnParticle(Particle.EXPLOSION, entity.getLocation(), 1);
                    world.playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 1.2f);
                }
            }
            if (Helper.enchantmentExists(player, HUNTER_FOCUS)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, HUNTER_FOCUS);
                double damageIncrease = enchantmentLevel * 0.04;
                if (entity.getSpawnCategory() == SpawnCategory.ANIMAL) {
                    damage *= 1 + damageIncrease;
                }
            }
            if (Helper.enchantmentExists(player, OVERCHARGE)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, OVERCHARGE);
                AttributeInstance attributeInstance = player.getAttribute(Attribute.ATTACK_SPEED);
                double chance = enchantmentLevel * OVERCHARGE.getChancePerLevel();
                double speedAdd = 0.3 + (0.15 * enchantmentLevel);
                AttributeModifier modifier = new AttributeModifier(ModifierKeys.OVERCHARGE_MODIFIER, speedAdd, AttributeModifier.Operation.ADD_NUMBER);
                if (attributeInstance != null && Helper.rollChance(chance)) {
                    attributeInstance.removeModifier(modifier);
                    attributeInstance.addModifier(modifier);
                    Helper.runAfterDelay(Helper.secondsToTicks(5), () ->
                        attributeInstance.removeModifier(modifier)
                    );
                }
            }
            if (Helper.enchantmentExists(player, CHAIN_ZAP)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, CHAIN_ZAP);
                double chance = enchantmentLevel * CHAIN_ZAP.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    int maxEntities = 4;
                    double originalDamage = event.getDamage();
                    LivingEntity currentEntity = entity;
                    Set<UUID> zapped = new HashSet<>();
                    zapped.add(currentEntity.getUniqueId());
                    zapped.add(player.getUniqueId());
                    for (int i = 0; i < maxEntities; i++) {
                        LivingEntity nearestEntity = Helper.getNearestEntity(currentEntity, 3, e ->
                                !zapped.contains(e.getUniqueId()) && e.getType().isAlive());
                        if (nearestEntity != null) {
                            world.playSound(nearestEntity.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 0.5f, 1.2f);
                            Helper.spawnParticleLine(world, Particle.SOUL, currentEntity, nearestEntity, 0.2);
                            originalDamage *= 0.75;
                            nearestEntity.damage(originalDamage);
                            currentEntity = nearestEntity;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (Helper.enchantmentExists(player, GENTLE_HARVEST) && Helper.isAnimal(entity)) {
                if (Helper.rollChance(GENTLE_HARVEST.getChance()) && damage >= 4) {
                    EntityType type = entity.getType();
                    ItemStack toDrop = null;
                    NamespacedKey lootKey = new NamespacedKey("minecraft", "entities/" + type.getKey().getKey());
                    LootTable lootTable = Bukkit.getServer().getLootTable(lootKey);
                    if (lootTable != null) {
                        LootContext context = new LootContext.Builder(entity.getLocation())
                                .lootedEntity(entity)
                                .build();
                        Collection<ItemStack> drops = lootTable.populateLoot(Helper.random, context);
                        for (ItemStack drop : drops) {
                            if (!Helper.isMeat(drop.getType())) {
                                toDrop = new ItemStack(drop.getType(), 1);
                                break;
                            }
                        }
                    }
                    if (toDrop != null && !toDrop.getType().isAir()) {
                        world.dropItemNaturally(entity.getLocation(), toDrop);
                    }
                }

            }

            event.setDamage(damage);
        } else if (event.getDamager() instanceof LivingEntity attacker && event.getEntity() instanceof Player player) {
            List<ItemStack> armors = Helper.getPlayerArmor(player);
            World world = player.getWorld();
            PlayerInventory inventory = player.getInventory();
            ItemStack chestplate = inventory.getChestplate();
            if (Helper.enchantmentExists(player, PHANTOM_STEP)) {
                if (Helper.rollChance(PHANTOM_STEP.getChance())) player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, false));
            }
            if (Helper.enchantmentExists(armors, glacialShield)) {
                int slownessTicks = 0;
                double chance = 0;
                for (ItemStack armor : armors) {
                    if (Helper.enchantmentExists(armor, glacialShield)) {
                        int enchantmentLevel = Helper.getEnchantmentLevel(armor, glacialShield);
                        slownessTicks += enchantmentLevel * 12;
                        chance += enchantmentLevel * glacialShield.getChancePerLevel();
                    }
                }
                if (Helper.rollChance(chance))
                    attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, slownessTicks, 0, true, true, true));
            }
            if (PlayerDataUtils.checkIfDataExists(player, THORN_PULSE.getId())) {
                Helper.applyKnockbackAround(player, 1.3, 0.6);
            }
            if (chestplate != null) {
                if (Helper.enchantmentExists(chestplate, BLAZE_THORNS)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(chestplate, BLAZE_THORNS);
                    double chance = enchantmentLevel * BLAZE_THORNS.getChancePerLevel();
                    int fireTicks = 40 + (20 * enchantmentLevel);
                    double knockBackStrength = 0.4 + (0.1 * enchantmentLevel);
                    int range = 4;
                    List<LivingEntity> nearbyEntities = Helper.getNearbyLivingEntities(player.getLocation(), range);
                    double upwardStrength = 0.2 + (0.1 * enchantmentLevel);
                    nearbyEntities.remove(player);
                    if (Helper.rollChance(chance)) {
                        for (LivingEntity nearbyEntity : nearbyEntities) {
                            Helper.applyKnockback(player, nearbyEntity, knockBackStrength, upwardStrength);
                            nearbyEntity.setFireTicks(fireTicks);
                            world.spawnParticle(Particle.FLAME, nearbyEntity.getLocation(), 3);
                        }
                        world.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
                    }
                }
                if (Helper.enchantmentExists(chestplate, SECOND_WIND)) {
                    int enchantmentLevel = Helper.getEnchantmentLevel(chestplate, SECOND_WIND);
                    double chance = enchantmentLevel * SECOND_WIND.getChancePerLevel();
                    if (Helper.rollChance(chance)) {
                        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, 60, 0, true, true, true);
                        player.addPotionEffect(potionEffect);
                    }
                }
            }
        }
    }


    @EventHandler
    public void mysticCommandEvent(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getSize() < 9) return;
        if (!ItemDataUtils.hasData(inventory.getItem(inventory.getSize() - 5), "viewer")) return;
        event.setCancelled(true);
        HumanEntity humanEntity = event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ClickType click = event.getClick();
        Player player = (Player) humanEntity;
        if (ItemDataUtils.hasData(clickedItem, "basic_tab")) {
            if (click == ClickType.LEFT) {
                player.openInventory(PagedGuis.BASIC.getPage(1, player));
            } else if (click == ClickType.RIGHT) {
                MysticTickets.BASIC_TICKETS.rollTicket(player);
                clickedItem.editMeta(meta -> {
                    List<Component> lore = meta.lore();
                    lore = lore == null ? new ArrayList<>() : lore;
                    lore.set(lore.size() - 1, GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.BASIC_TICKETS.getTickets(player) + "</yellow> Basic Tickets"));
                    meta.lore(lore);
                });
            }
        } else if (ItemDataUtils.hasData(clickedItem, "enhanced_tab")) {
            if (click == ClickType.LEFT) {
                player.openInventory(PagedGuis.ENHANCED.getPage(1, player));
            } else if (click == ClickType.RIGHT) {
                MysticTickets.ENHANCED_TICKETS.rollTicket(player);
                clickedItem.editMeta(meta -> {
                    List<Component> lore = meta.lore();
                    lore = lore == null ? new ArrayList<>() : lore;
                    lore.set(lore.size() - 1, GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.ENHANCED_TICKETS.getTickets(player) + "</yellow> Enhanced Tickets"));
                    meta.lore(lore);
                });
            }
        } else if (ItemDataUtils.hasData(clickedItem, "mystic_resources_tab")) {
            player.openInventory(PagedGuis.RESOURCES.getPage(1, player));
        }
        else if (ItemDataUtils.hasData(clickedItem, "recipes_menu"))
            player.openInventory(PagedGuis.RECIPE.getPage(1, player));

        else if (ItemDataUtils.hasData(clickedItem, "refined_tab")) {
            if (click == ClickType.LEFT) {
                player.openInventory(PagedGuis.REFINED.getPage(1, player));
            } else if (click == ClickType.RIGHT) {
                MysticTickets.REFINED_TICKETS.rollTicket(player);
                clickedItem.editMeta(meta -> {
                    List<Component> lore = meta.lore();
                    lore = lore == null ? new ArrayList<>() : lore;
                    lore.set(lore.size() - 1, GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.REFINED_TICKETS.getTickets(player) + "</yellow> Refined Tickets"));
                    meta.lore(lore);
                });

            }
        } else if (ItemDataUtils.hasData(clickedItem, "elite_tab")) {
            if (click == ClickType.LEFT) {
                player.openInventory(PagedGuis.ELITE.getPage(1, player));
            } else if (click == ClickType.RIGHT) {
                MysticTickets.ELITE_TICKETS.rollTicket(player);
                clickedItem.editMeta(meta -> {
                    List<Component> lore = meta.lore();
                    lore = lore == null ? new ArrayList<>() : lore;
                    lore.set(lore.size() - 1, GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.ELITE_TICKETS.getTickets(player) + "</yellow> Elite Tickets"));
                    meta.lore(lore);
                });
            }
        } else if (ItemDataUtils.hasData(clickedItem, "mythic_tab")) {
            if (click == ClickType.LEFT) {
                player.openInventory(PagedGuis.MYTHIC.getPage(1, player));
            } else if (click == ClickType.RIGHT) {
                MysticTickets.MYTHIC_TICKETS.rollTicket(player);
                clickedItem.editMeta(meta -> {
                    List<Component> lore = meta.lore();
                    lore = lore == null ? new ArrayList<>() : lore;
                    lore.set(lore.size() - 1, GuiHelper.mm.deserialize(Gradient.GREEN + "<!i>You have <yellow>" + MysticTickets.MYTHIC_TICKETS.getTickets(player) + "</yellow> Mythic Tickets"));
                    meta.lore(lore);
                });

            }
        } else if (ItemDataUtils.hasData(clickedItem, "enchanted_crate")) {
            if (click == ClickType.LEFT) {
                player.openInventory(PagedGuis.CRATE.getPage(1, player));
            } else if (click == ClickType.RIGHT) {
                EnchantedCrateUtils.rollCrate(player);
                Helper.updateCrate(player, inventory);
            }
        } else if (ItemDataUtils.hasData(clickedItem, "exit")) humanEntity.closeInventory();
        else if (ItemDataUtils.hasData(clickedItem, "back")) humanEntity.openInventory(Guis.getMainGui(player));
        else if (ItemDataUtils.hasData(clickedItem, "options_tab"))
            player.openInventory(PagedGuis.OPTIONS.getPage(1, player));
        else if (ItemDataUtils.hasData(clickedItem, "humane_viewer")) {
            player.openInventory(Guis.getHumaneEnchantsGui(player));
        }
        else if (ItemDataUtils.hasData(clickedItem, "addons")) {
            Helper.sendMessage(player, "<click:open_url:'https://github.com/MDT168/MysticEnchantments/wiki/2%E2%80%90-Add%E2%80%90ons-creation-setup'>" + Gradient.YELLOW + "Click here</gradient></click> to open the setup guide");
            player.closeInventory();
        }
        else {

            MysticOption option = MysticOption.fromItem(clickedItem);
            if (option != null) {
                double coins = MysticCoinUtils.getCoin(player);
                double price = option.getMysticCoinsPrice();
                if (coins >= price) {
                    if (option.buy(player)) {
                        MysticCoinUtils.removeCoin(player, price);
                        Helper.sendMessage(player, Gradient.YELLOW + "You have got a " + option.getName());
                    }
                } else {
                    Helper.sendWarningMessage(player, "You don't have enough coins!");
                }
            }

            ClickType clickType = event.getClick();
            MysticRecipe recipe = Helper.findMysticRecipe(clickedItem);
            if (recipe != null) {
                if (clickType == ClickType.SHIFT_RIGHT) {
                    player.openInventory(recipe.getResultInventory(player));
                } else if (clickType == ClickType.SHIFT_LEFT) {
                    boolean ableToCraft = true;
                    boolean crafted = false;
                    int amount = 0;
                    while (ableToCraft) {
                        ableToCraft = recipe.craft(player, crafted);
                        if (ableToCraft) {
                            crafted = true;
                            amount++;
                        }
                    }
                    if (crafted) Helper.sendMessage(player, "You have crafted " + amount + "x " + recipe.getName());
                } else if (clickType.isLeftClick()) {
                    if (recipe.craft(player)) Helper.sendMessage(player, "You have crafted " + recipe.getName());
                } else {
                    if (clickType.isRightClick()) player.openInventory(recipe.getRequirementsInventory(player));
                }
            }
        }
        if (PagedEnchantmentsGui.isPaginated(clickedItem)) {
            Inventory next = PagedEnchantmentsGui.getPageFromItem(clickedItem, player);
            if (next != null)
                inventory.setContents(next.getContents());
        } else if (PagedMysticRecipeGui.isPaginated(clickedItem)) {
            Inventory next = PagedMysticRecipeGui.getPageFromItem(clickedItem, player);
            if (next != null)
                inventory.setContents(next.getContents());
        } else if (PagedMysticOptionsGui.isPaginated(clickedItem)) {
            Inventory next = PagedMysticOptionsGui.getPageFromItem(clickedItem, player);
            if (next != null)
                inventory.setContents(next.getContents());
        }  else if (PagedEnchantedCrateGui.isPaginated(clickedItem)) {
            Inventory next = PagedEnchantedCrateGui.getPageFromItem(clickedItem, player);
            if (next != null)
                inventory.setContents(next.getContents());
        } else if (PagedMysticResourcesGui.isPaginated(clickedItem)) {
            Inventory next = PagedMysticResourcesGui.getPageFromItem(clickedItem, player);
            if (next != null)
                inventory.setContents(next.getContents());
        }

        Helper.updateViewer(player, inventory);
    }

    @EventHandler
    public void onMobKillEnchantmentEffects(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        LivingEntity entity = event.getEntity();
        World world = entity.getWorld();
        Player player = entity.getKiller();

        if (player != null) {
            if (entity instanceof Phantom) {
                if (player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
                    if (Helper.rollChance(MysticResources.PHANTOMS_TEAR.getDropChance())) {
                        drops.add(MysticResources.PHANTOMS_TEAR.getItemStack());
                        Helper.sendResourceActionBar(player, MysticResources.PHANTOMS_TEAR);
                    }
                }
            }
            if (entity instanceof Stray) {
                if (player.getInventory().getItemInMainHand().getType() == Material.STONE_SWORD) {
                    if (Helper.rollChance(MysticResources.FROST_CRYSTAL.getDropChance())) {
                        drops.add(MysticResources.FROST_CRYSTAL.getItemStack());
                        Helper.sendResourceActionBar(player, MysticResources.FROST_CRYSTAL);
                    }
                }
            }
            if (entity instanceof EnderDragon) {
                if (Helper.rollChance(MysticResources.DRAGONS_HEART.getDropChance())) {
                    Helper.sendResourceActionBar(player, MysticResources.DRAGONS_HEART);
                    Helper.forceGiveItem(player, MysticResources.DRAGONS_HEART.getItemStack());
                }
            }
        }
        if (player != null) {

            if (Helper.enchantmentExists(player, CORPSE_SINGULARITY)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, CORPSE_SINGULARITY);
                double chance = enchantmentLevel * CORPSE_SINGULARITY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    int range = 4 + enchantmentLevel;
                    double strength = 1 + (0.1 * enchantmentLevel);
                    int damage = 6 + (2 * enchantmentLevel);
                    int fireTicks = 40 + (20 * enchantmentLevel);
                    int nauseaTicks = 40 + (40 * enchantmentLevel);
                    double upwardPush = 0.4 + (0.1 * enchantmentLevel);
                    List<LivingEntity> entities = Helper.getNearbyLivingEntities(entity.getLocation(), range);
                    entities.remove(player);
                    entities.remove(entity);
                    Helper.sendActionBar(player, MiniMessage.miniMessage().deserialize(Gradient.PINK + "Corpse Singularity has been activated on " + entity.getName()));
                    for (LivingEntity livingEntity : entities) {
                        Helper.pullEntity(entity, livingEntity, 0.4, upwardPush);
                    }
                    Helper.runAfterDelay(40, () -> {
                        for (LivingEntity livingEntity : entities) {
                            if (livingEntity.isDead()) continue;
                            livingEntity.damage(damage);
                            livingEntity.setFireTicks(fireTicks);
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, nauseaTicks, 1));
                            Helper.applyKnockbackAround(entity, range, strength, upwardPush, player);
                        }
                        world.spawnParticle(Particle.EXPLOSION_EMITTER, entity.getLocation(), 2);
                        world.playSound(entity.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
                    });

                }
            }
            if (Helper.enchantmentExists(player, ELITE_SOULKEY)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, ELITE_SOULKEY);
                double chance = enchantmentLevel * ELITE_SOULKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    EnchantedCrateUtils.addKey(player, 1);
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                }
            }
            if (Helper.enchantmentExists(player, MYTHIC_SOULKEY)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, MYTHIC_SOULKEY);
                double chance = enchantmentLevel * MYTHIC_SOULKEY.getChancePerLevel();
                if (Helper.rollChance(chance)) {
                    EnchantedCrateUtils.addKey(player, 1);
                    Helper.sendMessage(player, "+1 Enchanted Crate Key");
                }
            }
            if (Helper.enchantmentExists(player, SEARING_BLADE)) {
                if (Helper.isOverallAnimal(entity)) {
                    for (int i = 0; i < drops.size(); i++) {
                        ItemStack drop = drops.get(i);
                        if (Helper.isRawMeat(drop.getType())) drops.set(i, Helper.getSmeltedItem(drop));
                    }
                }
            }
            if (Helper.enchantmentExists(player, SCAVENGER)) {
                if (Helper.rollChance(SCAVENGER.getChance())) {
                    ItemStack randomChosen = drops.get(Helper.random.nextInt(drops.size()));
                    drops.add(new ItemStack(randomChosen.getType(), 1));
                }
            }
            if (Helper.enchantmentExists(player, SOUL_REAVER)) {
                int enchantmentLevel = Helper.getEnchantmentLevel(player, SOUL_REAVER);
                double chance = enchantmentLevel * SOUL_REAVER.getChancePerLevel();
                double stolen = 0.25 + (0.25 * enchantmentLevel);
                AttributeInstance maxHealthAttribute = entity.getAttribute(Attribute.MAX_HEALTH);
                if (Helper.rollChance(chance) && maxHealthAttribute != null) {
                    double maxHealth = maxHealthAttribute.getValue();
                    player.heal(maxHealth * stolen);
                }
            }
        }
    }

    @EventHandler
    public void mysticSettingsCommand(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (ItemDataUtils.hasData(inventory.getItem(0), "guider")) event.setCancelled(true);
        if (!ItemDataUtils.hasData(inventory.getItem(inventory.getSize() - 1), "settings_exit")) return;
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack clickedItem = event.getCurrentItem();

        if (ItemDataUtils.hasData(clickedItem, "settings_exit")) player.closeInventory();

        Setting setting = Helper.findSettingByItem(clickedItem);
        if (setting == null) return;

        setting.invertValue(player);
        player.getWorld().playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        Helper.sendMessage(player, setting.getName() + " has been set to <blue>" + setting.getValue(player));
        Helper.updateSettings(player, inventory);
    }

}
