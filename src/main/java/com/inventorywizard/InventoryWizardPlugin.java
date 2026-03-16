package com.inventorywizard;

import org.bukkit.plugin.java.JavaPlugin;

public class InventoryWizardPlugin extends JavaPlugin {
    
    private PlayerDataManager playerDataManager;
    private RateLimiter rateLimiter;
    
    @Override
    public void onEnable() {
        // Initialize player data manager
        playerDataManager = new PlayerDataManager(this);
        
        // Initialize rate limiter
        rateLimiter = new RateLimiter(playerDataManager);
        
        // Register event listener
        getServer().getPluginManager().registerEvents(new SortListener(this), this);
        
        // Register command
        SortCommand sortCommand = new SortCommand(this);
        getCommand("iwiz").setExecutor(sortCommand);
        getCommand("iwiz").setTabCompleter(sortCommand);
        
        // Save default config
        saveDefaultConfig();
        
        getLogger().info("InventoryWizard enabled");
        getLogger().info("Commands: /iwiz [hotbar|inventory|all]");
        getLogger().info("Hotbar: Shift+Right-click in hotbar or Double-click in hotbar");
        getLogger().info("Inventory: Shift+Right-click in main inventory");
        getLogger().info("Both: Shift+Right-click in hotbar (with all permission)");
        getLogger().info("New: Shift+Right-click in hotbar slot 4 to cycle sorting modes");
        getLogger().info("Using H2 database for storage");
        getLogger().info("Rate limiting enabled");
        getLogger().info("Use /iwiz to sort inventory");
    }
    
    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
    
    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }
    
    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            playerDataManager.close();
        }
        getLogger().info("InventoryWizard disabled");
    }
}