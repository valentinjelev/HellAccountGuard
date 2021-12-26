package me.codedcrown.hellaccountguard;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class HellAccountGuard extends JavaPlugin {
    public File ipFile = new File("plugins/HellAccountGuard", "ip.yml");
    public FileConfiguration ip;
    public File messagesFile;
    public FileConfiguration messages;
    public FileConfiguration config;
    public String languageFileName;
    public String doesntmatch;

    public HellAccountGuard() {
        this.ip = YamlConfiguration.loadConfiguration(this.ipFile);
    }

    public void onEnable() {
        if(!new AdvancedLicense("KX1O-8Y8P-XE2E-Q6YV", "https://hell-license.cooking/verify.php", this).register()) return;
        this.saveDefaultConfig();
        this.updateConfig();
        final File file = this.getFile();

        this.languageFileName = "messages_" + this.getConfig().getString("language") + ".yml";
        this.createDefaultFiles();
        this.registerListener();
    }

    public void onDisable() {
        try {
            this.ip.save(this.ipFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void createDefaultFiles() {
        this.saveDefaultConfig();
        this.messagesFile = new File(this.getDataFolder(), this.languageFileName);

        try {
            if (!this.messagesFile.exists()) {
                this.saveResource(this.languageFileName, true);
            }

            this.messages = YamlConfiguration.loadConfiguration(this.messagesFile);
            this.doesntmatch = this.repl(this.messages.getString("doesntmatch"));
            this.getCommand("accountguard").setExecutor(new AccountGuardCommand(this, this.repl(this.messages.getString("noperm"))));
        } catch (IllegalArgumentException var2) {
            this.getLogger().info("Грешка: Няма файл " + this.languageFileName + " за превод. Използва се файл за превод по подразбиране messages_en.yml.");
            this.languageFileName = "messages_en.yml";
            this.createDefaultFiles();
        }

    }

    public void updateConfig() {
        if (this.getConfig().getInt("cfg-version") != 4) {
            this.saveResource("config.yml", true);
            this.reloadConfig();
        }

    }

    public void registerListener() {
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public String repl(String r) {
        return r.replace("&", "§");
    }
}
