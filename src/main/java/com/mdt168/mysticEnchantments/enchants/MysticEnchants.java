package com.mdt168.mysticEnchantments.enchants;


import com.mdt168.mysticEnchantments.custom.BookTiers;

@SuppressWarnings("all")
public class MysticEnchants {
    public static final EnchantmentStack MAGNETISM = EnchantmentStack.register(
            "Magnetism",
            "Tools",
            "Block drops go straight to inventory.",
            100,
            BookTiers.ENHANCED,
            true
    );

    public static final EnchantmentStack SMELTER = EnchantmentStack.register(
            "Smelter",
            "Pickaxes",
            "Auto-smelts block drops on break.",
            20,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack SWIFT_SHEAR = EnchantmentStack.register(
            "Swift Shear",
            "Axes",
            "Instantly Breaks Leaves",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack GLOWING = EnchantmentStack.register(
            "Glowing",
            "Helmets",
            "Grants Permanent Night Vision When Worn",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack MARKING_STRIKE = EnchantmentStack.register(
            "Marking Strike",
            "Weapons",
            "Applies Glowing Effect On Enemies (+1.5 Seconds Per Level)",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack PEBBLE_POLISHER = EnchantmentStack.register(
            "Pebble Polisher",
            "Pickaxes",
            "Drops Gravel From Stone",
            1,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack CHAIN_ZAP = EnchantmentStack.register(
            "Chainzap",
            "Weapons",
            "Hits may be transferred to the nearest entity with 75% of the damage, up to 6 entities",
            7.5,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack ELITE_SOULKEY = EnchantmentStack.register(
            "Elite Soulkey",
            "Swords",
            "Gives an Enchanted Key to the player on mob kill",
            0.9,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack ELITE_MINEKEY = EnchantmentStack.register(
            "Elite Minekey",
            "Pickaxes",
            "Gives an Enchanted Key to the player on pickaxe-mineable block mine",
            0.4,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack ELITE_WOODKEY = EnchantmentStack.register(
            "Elite Woodkey",
            "Axes",
            "Gives an Enchanted Key to the player on log break",
            0.3,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack ELITE_DIRTKEY = EnchantmentStack.register(
            "Elite Dirtkey",
            "Shovels",
            "Gives an Enchanted Key to the player on shovel-mineable block mine",
            0.1,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack ELITE_CROPKEY = EnchantmentStack.register(
            "Elite Cropkey",
            "Hoes",
            "Gives an Enchanted Key to the player on grown crops break",
            0.1,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack MYTHIC_SOULKEY = EnchantmentStack.register(
            "Mythic Soulkey",
            "Swords",
            "Gives an Enchanted Key to the player on mob kill",
            1.2,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack MYTHIC_MINEKEY = EnchantmentStack.register(
            "Mythic Minekey",
            "Pickaxes",
            "Gives an Enchanted Key to the player on pickaxe-mineable block mine",
            0.8,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack MYTHIC_WOODKEY = EnchantmentStack.register(
            "Mythic Woodkey",
            "Axes",
            "Gives an Enchanted Key to the player on log break",
            0.6,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack MYTHIC_DIRTKEY = EnchantmentStack.register(
            "Mythic Dirtkey",
            "Shovels",
            "Gives an Enchanted Key to the player on shovel-mineable block mine",
            0.15,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack MYTHIC_CROPKEY = EnchantmentStack.register(
            "Mythic Cropkey",
            "Hoes",
            "Gives an Enchanted Key to the player on grown crops break",
            0.25,
            BookTiers.MYTHIC,
            true
    );

    public static final EnchantmentStack TOOL_MASTERY = EnchantmentStack.register(
            "Toolmastery",
            "Mining Tools",
            "Has a chance to double drops when using the correct tool for the block",
            3,
            BookTiers.REFINED,
            true
    );
    public static final HumaneEnchantment UNDYING_KNOWLEDGE = HumaneEnchantment.register(
            "Undying Knowledge",
            "Saves The Player's XP upon death",
            BookTiers.MYTHIC,
            true
    );
    public static final HumaneEnchantment GENTLE_HARVEST = HumaneEnchantment.register(
            "Gentle Harvest",
            "Extract an animal's drop (Excluding meat), when dealing +2 hearts of damage",
            4,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack ENKINDLED_CORE = EnchantmentStack.register(
            "Enkindled Core",
            "Chestplates",
            "Turns lava damage to healing for 5 seconds (1m CD)",
            100,
            BookTiers.ELITE,
            true
    );
    public static final HumaneEnchantment IRON_STOMACH = HumaneEnchantment.register(
            "Iron Stomach",
            "No hunger consumption and +1 Saturation while eating",
            10,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack BOSS_SLAYER = EnchantmentStack.register(
            "Boss Slayer",
            "Swords",
            "Deals 15% more damage to bosses",
            100,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack BLAZE_THORNS = EnchantmentStack.register(
            "Blaze Thorns",
            "Chestplates",
            "Knocks back enemies and puts them ablaze",
            8,
            BookTiers.REFINED,
            true
    );
    public static final HumaneEnchantment NIGHT_OWL = HumaneEnchantment.register(
            "Night Owl",
            "Grants Permanent Night Vision",
            BookTiers.ENHANCED,
            true
    );

    public static final HumaneEnchantment BREW_BOOST = HumaneEnchantment.register(
            "Brew Boost",
            "Increases drank potion duration by 10%",
            BookTiers.BASIC,
            true
    );
    public static final HumaneEnchantment VANISH_STEP = HumaneEnchantment.register(
            "Vanish Step",
            "Gives short invisibility and speed when dropping below 30% HP (3m CD)",
            BookTiers.ELITE,
            true
    );
    public static final HumaneEnchantment INSPECTOR = HumaneEnchantment.register(
            "Inspector",
            "Right-Clicking a living entity will let you see their health and max health",
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack STONE_SKIN = EnchantmentStack.register(
            "Stone Skin",
            "Chestplates",
            "Slightly Reduces Incoming Damage",
            12.5,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack DYNAMITE_PLANTER = EnchantmentStack.register(
            "Dynamite Planter",
            "Pickaxes",
            "Plants a dynamite that mines nearby Pickaxe-broken blocks",
            3.5,
            BookTiers.ENHANCED,
            true
    );

    public static final EnchantmentStack FEATHERFOOT = EnchantmentStack.register(
            "Featherfoot",
            "Boots",
            "Fully Negates Fall Damage",
            20,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack SEARING_BLADE = EnchantmentStack.register(
            "Searing Blade",
            "Weapons",
            "Drops cooked meat from animals",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final HumaneEnchantment COLLECTOR = HumaneEnchantment.register(
            "Collector",
            "Increases Gotten Mystic Coins By 1.1x ",
            BookTiers.BASIC,
            true
    );
    public static final HumaneEnchantment THORN_PULSE = HumaneEnchantment.register(
            "Thorn Pulse",
            "Reflects small AoE knockback and damage when hit",
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack SOULBOUND = EnchantmentStack.register(
            "Soulbounded",
            "Weapons, Armor And Tools",
            "Gives the item to the player back on death",
            100,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack BLISTERING_EDGE = EnchantmentStack.register(
            "Blistering Edge",
            "Swords",
            "Applies weakness on enemies",
            10,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack GLIMMER = EnchantmentStack.register(
            "Glimmer",
            "Pickaxes",
            "Drops nuggets from ores",
            5,
            BookTiers.BASIC,
            true
    );
    public static final HumaneEnchantment SLIGHT_VIGOR = HumaneEnchantment.register(
            "Slight Vigor",
            "Grants tiny regeneration boost",
            BookTiers.BASIC,
            true
    );

    public static final EnchantmentStack GLACIAL_SHIELD = EnchantmentStack.register(
            "Glacial Shield",
            "Armor",
            "Slows down attackers (Stackable)",
            2,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack STORMCALLER = EnchantmentStack.register(
            "Stormcaller",
            "Weapons",
            "Strikes hit enemies with lightning and fire.",
            10,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack TWIN_STRIKE = EnchantmentStack.register(
            "Twin Strike",
            "Swords",
            "Hit twice on attack.",
            3.5,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack SKYBREAKER = EnchantmentStack.register(
            "Skybreaker",
            "Weapons",
            "creates a shockwave on the enemy that knocks and damages nearby mobs away from the player",
            1,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack SEISMIC_SLAM = EnchantmentStack.register(
            "Seismic Slam",
            "Boots",
            "Creates a shockwave knocking back enemies on high fall (6 Blocks Or More)",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack BURNOUT = EnchantmentStack.register(
            "Burnout",
            "Bows",
            "Applies stacking fire damage per arrow hit",
            8,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack KEEN_EYE = EnchantmentStack.register(
            "Keen Eye",
            "Tools",
            "Doubles block drops",
            2,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack VEIN_MINER = EnchantmentStack.register(
            "Vein Miner",
            "Pickaxes",
            "Mines the whole vein of ores",
            25,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack LIFESTEAL = EnchantmentStack.register(
            "Lifesteal",
            "Swords",
            "Steals life from enemies.",
            17.5,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack MYSTERIOUS_LUMBERJACK = EnchantmentStack.register(
            "Mysterious Lumberjack",
            "Hoes",
            "Doubles the drops from Logs",
            10,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack TRUE_SHOT = EnchantmentStack.register(
            "True Shot",
            "Bows",
            "Arrows fly straighter for longer.",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack RAGE_REDIRECT = EnchantmentStack.register(
            "Rage Redirect",
            "Swords",
            "Chance to redirect the target's anger to a nearby hostile.",
            20,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack RESTORATION = EnchantmentStack.register(
            "Restoration",
            "Durability",
            "Slowly repairs the held item over time.",
            100,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack CRIPPLING_EDGE = EnchantmentStack.register(
            "Crippling Edge",
            "Weapons",
            "Applies slowness or mining fatigue to the enemy",
            5,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack THUNDERSTRIKE = EnchantmentStack.register(
            "Thunderstrike",
            "Bows",
            "Strikes the enemy with a lightning bolt dealing 3 hearts of damage (+1 Per Level)",
            10,
            BookTiers.ELITE,
            true
    );
    public static final HumaneEnchantment BOTANISTS_GRACE = HumaneEnchantment.register(
            "Botanist's Grace",
            "Right-Clicking a fully grown crop will give the player triple the items and replant the crop",
            BookTiers.MYTHIC,
            true
    );

    public static final EnchantmentStack ECHOING_TOUCH = EnchantmentStack.register(
            "Echoing Touch",
            "Tools",
            "Breaks the same block twice, re-triggering effects and giving more drops",
            15,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack SOUL_SEVER = EnchantmentStack.register(
            "Soul Sever",
            "Weapons",
            "Instantly kill low-health mobs",
            7.5,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack BASIC_EXPERIENCER = EnchantmentStack.register(
            "Basic Experiencer",
            "Weapons",
            "Increases the Effort Points dropped from mobs (0.1x Per Level)",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack POPCORN = EnchantmentStack.register(
            "Popcorn",
            "Weapons",
            "Launches enemies into the air (height depends on the level)",
            5,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack ENHANCED_EXPERIENCER = EnchantmentStack.register(
            "Enhanced Experiencer",
            "Weapons",
            "Increases the Effort Points dropped from mobs (0.15x Per Level)",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack REFINED_EXPERIENCER = EnchantmentStack.register(
            "Refined Experiencer",
            "Weapons",
            "Increases the Effort Points dropped from mobs (0.2x Per Level)",
            100,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack ELITE_EXPERIENCER = EnchantmentStack.register(
            "Elite Experiencer",
            "Weapons",
            "Increases the Effort Points dropped from mobs (0.3x Per Level)",
            100,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack MYTHIC_EXPERIENCER = EnchantmentStack.register(
            "Mythic Experiencer",
            "Weapons",
            "Increases the Effort Points dropped from mobs (0.4x Per Level)",
            100,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack BASIC_PROSPECTOR = EnchantmentStack.register(
            "Basic Prospector",
            "Tools",
            "Increases the dropped Effort Points from mining blocks (0.1x Per Level)",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack ENHANCED_PROSPECTOR = EnchantmentStack.register(
            "Enhanced Prospector",
            "Tools",
            "Increases the dropped Effort Points from mining blocks (0.15x Per Level)",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack REFINED_PROSPECTOR = EnchantmentStack.register(
            "Refined Prospector",
            "Tools",
            "Increases the dropped Effort Points from mining blocks (0.2x Per Level)",
            100,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack CORPSE_SINGULARITY = EnchantmentStack.register(
            "Corpse Singularity",
            "Sharp Weapons",
            "On Enemy kill, pull nearby enemies to the corpse and explode dealing damage, fire and giving nausea",
            13,
            BookTiers.ELDRITCH,
            true
    );
    public static final EnchantmentStack ECLIPSE_EDGE = EnchantmentStack.register(
            "Eclipse Edge",
            "Swords",
            "Critical Strikes Damage while sneaking are multiplied with 2.5x",
            100,
            BookTiers.ELDRITCH,
            true
    );
    public static final HumaneEnchantment PHANTOM_STEP = HumaneEnchantment.register(
            "Phantom Step",
            "Become invisible when getting hit for 2s",
            25,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack ARCANE_ECHO = EnchantmentStack.register(
            "Arcane Echo",
            "Swords",
            "Another attack will be done dealing 40% of the original damage after 2s delay",
            10,
            BookTiers.ELITE,
            true
    );

    public static final EnchantmentStack ELITE_PROSPECTOR = EnchantmentStack.register(
            "Elite Prospector",
            "Tools",
            "Increases the Effort Points dropped from mining blocks (0.3x Per Level)",
            100,
            BookTiers.ELITE,
            true
    );
    public static final EnchantmentStack MYTHIC_PROSPECTOR = EnchantmentStack.register(
            "Mythic Prospector",
            "Tools",
            "Increases the Effort Points dropped from mining blocks (0.45x Per Level)",
            100,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack WITHERING_CUT = EnchantmentStack.register(
            "Withering Cut",
            "Swords",
            "Applies wither effect on enemies",
            8,
            BookTiers.REFINED,
            true
    );

    public static final EnchantmentStack MOMENTUM = EnchantmentStack.register(
            "Momentum",
            "Boots",
            "Slightly increases sprinting speed",
            100,
            BookTiers.BASIC,
            true
    );

    public static final EnchantmentStack UNBREAKABLE = EnchantmentStack.register(
            "Unbreakable",
            "Tools",
            "Makes the item unbreakable",
            100,
            BookTiers.MYTHIC,
            true
    );
    public static final EnchantmentStack THRIFTY_TOUCH = EnchantmentStack.register(
            "Thrifty Touch",
            "Tools",
            "No durability will be consumed",
            3,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack THUMP = EnchantmentStack.register(
            "Thump",
            "Axes",
            "Slows down enemies",
            12.5,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack FIREWOORD = EnchantmentStack.register(
            "Firewood",
            "Axes",
            "Logs broken drop charcoal",
            2.5,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack ALCHEMISTS_TOUCH = EnchantmentStack.register(
            "Alchemist's Touch",
            "Swords",
            "Applies random debuff on enemies",
            4,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack GRAVEL_SENSE = EnchantmentStack.register(
            "Gravel Sense",
            "Shovels",
            "Drops flint from gravel",
            10,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack SECOND_WIND = EnchantmentStack.register(
            "Second Wind",
            "Chestplates",
            "Gives Speed I when hit",
            3,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack PRESERVE = EnchantmentStack.register(
            "Preserve",
            "Durability",
            "Saves the item on break and repairs it a bit, and the enchantment gets lost",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack TORCH_FLICK = EnchantmentStack.register(
            "Torch Flick",
            "Tools",
            "Sneak + Right-Click places a torch from the inventory",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack FISHER_GRACE = EnchantmentStack.register(
            "Fisher's Grace",
            "Fishing Rods",
            "Reduces Fish Catch Time (2.5% Per Level)",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack OREFLOW = EnchantmentStack.register(
            "Oreflow",
            "Pickaxes",
            "Grants haste while mining ores",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack FISHERMAN_LUCK = EnchantmentStack.register(
            "Fisherman's Luck",
            "Fishing Rods",
            "Doubles the items caught from fishing",
            1.25,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack OPENING_WOUND = EnchantmentStack.register(
            "Opening Wound",
            "Swords",
            "Deals 10% more damage to full health enemies",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack OVERCHARGE = EnchantmentStack.register(
            "Overcharge",
            "Swords",
            "Increases the attack speed of the player on hit for 5 seconds",
            5,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack COMMON_HARVEST = EnchantmentStack.register(
            "Common Harvest",
            "Tools",
            "Increases the chance of getting Common Mystic Resources (5% per level)",
            100,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack COMMON_FORTUNE = EnchantmentStack.register(
            "Common Fortune",
            "Tools",
            "+1 Mystic Resources On Drop",
            5,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack WINDSTEP = EnchantmentStack.register(
            "Windstep",
            "Boots",
            "Applies Speed I for 3s on the player after a fall (+5 blocks)",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack HUNTER_FOCUS = EnchantmentStack.register(
            "Hunter's Focus",
            "Swords",
            "Increases damage towards animals",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack REINFORCED_PLATING = EnchantmentStack.register(
            "Reinforced Plating",
            "Chestplates",
            "Reduces damage from explosions (5% per level)",
            100,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack VENOMSTRIKE = EnchantmentStack.register(
            "Venomstrike",
            "Swords and Bows",
            "Applies poison I on enemies (+2s per level)",
            10,
            BookTiers.ENHANCED,
            true
    );
    public static final HumaneEnchantment SCAVENGER = HumaneEnchantment.register(
            "Scavenger",
            "Get extra drops from mobs.",
            8,
            BookTiers.ENHANCED,
            true
    );

    public static final HumaneEnchantment COMBAT_INTUITION = HumaneEnchantment.register(
            "Combat Intuition",
            "Grants Strength I for (0:05) when health falls below 20% (30s CD).",
            BookTiers.ENHANCED,
            true
    );
    public static final HumaneEnchantment ELDRITCH_KEEN_EYE = HumaneEnchantment.register(
            "Eldritch Keen Eye",
            "Triple Block Drops",
            20,
            BookTiers.ELDRITCH,
            true
    );
    public static final HumaneEnchantment FINAL_RECKONING = HumaneEnchantment.register(
            "Final Reckoning",
            "On death, leave a bomb behind that will deal massive damage to nearby entities (One Time Use)",
            BookTiers.ELDRITCH,
            true
    );

    public static HumaneEnchantment ENDER_POUCH = HumaneEnchantment.register(
            "Ender Pouch",
            "Sends dropped items from mined blocks to the ender chest (While sneaking)",
            BookTiers.ENHANCED,
            true
    );
    public static final HumaneEnchantment SLEEPER_LUCK = HumaneEnchantment.register(
            "Sleeper’s Luck",
            "After sleeping, gain +15% Effort Points from any source for 5 minutes.",
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack FORTUNE_TOUCH = EnchantmentStack.register(
            "Fortune Touch",
            "Pickaxes",
            "Doubles the drops from ores",
            3,
            BookTiers.ENHANCED,
            true
    );
    public static final EnchantmentStack EXECUTIONER_EDGE = EnchantmentStack.register(
            "Executioner's Edge",
            "Weapons",
            "Deals double damage to mobs with 25% health or lower",
            33,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack LAST_STAND = EnchantmentStack.register(
            "Last Stand",
            "Chestplates",
            "Survive dying from +1.5 hearts damage with 1 heart and regeneration",
            100,
            BookTiers.REFINED,
            true
    );

    public static final EnchantmentStack SAPLING_SEEKER = EnchantmentStack.register(
            "Sapling Seeker",
            "Axes",
            "Drop Saplings From leaves",
            2.5,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack COMBUSTION = EnchantmentStack.register(
            "Combustion",
            "Swords",
            "After 2 seconds, causes a small non-block-breaking explosion in the target.",
            2.5,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack CRITICAL_PULSE = EnchantmentStack.register(
            "Critical Pulse",
            "Swords",
            "Deals magic damage to enemies ignoring armor",
            8,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack LAUNCH_STEP = EnchantmentStack.register(
            "Launch Step",
            "Durability",
            "Right-Click + Sneak to launch yourself forward, negates fall damage if it's in 3s (30s cooldown and consumes durability)",
            100,
            BookTiers.REFINED,
            true
    );

    public static final EnchantmentStack CROP_BOOST = EnchantmentStack.register(
            "Crop Boost",
            "Hoes",
            "Drops An Extra Crop",
            10,
            BookTiers.BASIC,
            true
    );
    public static final EnchantmentStack SOUL_REAVER = EnchantmentStack.register(
            "Soul Reaver",
            "Sharp Weapons",
            "Steals life from killed enemies",
            10,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack SHATTER_STRIKE = EnchantmentStack.register(
            "Shatterstrike",
            "Sharp Weapons",
            "Takes 10% of the enemy's armor durability",
            50,
            BookTiers.REFINED,
            true
    );
    public static final EnchantmentStack[] EXP_INCREASE_BLOCKS = {
            BASIC_PROSPECTOR,
            ENHANCED_PROSPECTOR,
            REFINED_PROSPECTOR,
            ELITE_PROSPECTOR,
            MYTHIC_PROSPECTOR
    };
}
