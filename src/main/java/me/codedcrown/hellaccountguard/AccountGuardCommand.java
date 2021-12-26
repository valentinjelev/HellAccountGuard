package me.codedcrown.hellaccountguard;

import java.io.File;
import java.io.IOException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;

public class AccountGuardCommand implements CommandExecutor {
    HellAccountGuard plugin;
    public String noperm;

    public AccountGuardCommand(HellAccountGuard plugin, String noperm) {
        this.plugin = plugin;
        this.noperm = noperm;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§7----------= §cHellAccountGuard §7=----------");
            sender.sendMessage("§7/hag §8➜ §aПоказване на тези команди.");
            sender.sendMessage("§7/hag add <играч> <ип адрес> §8➜ §aАктивира защитата и предотвратява влизането в системата, ако IP адресът не съвпада с дадения.");
            sender.sendMessage("§7/hag remove <играч> <ип адрес> §8➜ §aПремахва защитата.");
            sender.sendMessage("§7/hag info <играч> §8➜ §aПроверява дали играча има защита.");
            sender.sendMessage("§7/hag save §8➜ §aСъхранява файла ip.yml в plugins/HellAccountGuard.");
            sender.sendMessage("§7/hag reload §8➜ §aПрезарежда файловете config,messages,ip от plugins/HellAccountGuard");
            sender.sendMessage("§7-------= §cПредоставен от §4HELLHOST.EU §7=-------");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("add") && this.checkPermission(sender, "add")) {
            args[1] = args[1].toLowerCase();
            if (!this.plugin.ip.contains(args[1])) {
                this.plugin.ip.set(args[1], args[2] + "|");
            } else {
                this.plugin.ip.set(args[1], this.plugin.ip.getString(args[1]) + args[2] + "|");
            }

            sender.sendMessage("§aВие успешно защитихте този акаунт!");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove") && this.checkPermission(sender, "remove")) {
            args[1] = args[1].toLowerCase();
            if (this.plugin.ip.getString(args[1]) == null) {
                sender.sendMessage("§cТози играч няма текуща защита!");
            } else if (args.length == 3) {
                if (args[2].equals("*")) {
                    this.plugin.ip.set(args[1], (Object)null);
                    sender.sendMessage("§cУспешно премахнахте защитата.");
                } else if (!this.plugin.ip.getString(args[1]).contains(args[2] + "|")) {
                    sender.sendMessage("§cТози IP адрес, който се опитвате да премахнете, не съществува.");
                } else {
                    if (this.plugin.ip.getString(args[1]).replaceAll(args[2] + "\\|", "") != "") {
                        this.plugin.ip.set(args[1], this.plugin.ip.getString(args[1]).replaceAll(args[2] + "\\|", ""));
                    } else {
                        this.plugin.ip.set(args[1], (Object)null);
                    }

                    sender.sendMessage("§cВие успешно премахнахте защитата.");
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("info") && this.checkPermission(sender, "info")) {
            if (!this.plugin.ip.isSet(args[1].toLowerCase())) {
                sender.sendMessage("§cТози играч няма текуща защита!");
            } else {
                sender.sendMessage("§aТози акаунт е защитен със следните IP адреси §8➜ §7 " + this.plugin.ip.getString(args[1].toLowerCase()).replaceAll("\\|", ", ").replaceFirst(".$", "").replaceFirst(".$", ""));
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            if (!this.plugin.ip.isSet(sender.getName().toLowerCase())) {
                sender.sendMessage("§cВие не сте защитен.");
            } else {
                sender.sendMessage("§aВашият акаунт е защитен със следните IP адреси §8➜ §7 " + this.plugin.ip.getString(sender.getName().toLowerCase()).replaceAll("\\|", ", ").replaceFirst(".$", "").replaceFirst(".$", ""));
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("save") && this.checkPermission(sender, "save")) {
            try {
                this.plugin.ip.save(this.plugin.ipFile);
            } catch (IOException var6) {
                sender.sendMessage("§cГрешка при записването на ip.yml файла, моля, свържете се с HellHost.EU за повече помощ.");
            }

            sender.sendMessage("§aВие успешно запазихте ip.yml файла в plugins/HellAccountGuard.");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload") && this.checkPermission(sender, "reload")) {
            this.plugin.createDefaultFiles();
            this.plugin.reloadConfig();
            this.plugin.ipFile = new File(this.plugin.getDataFolder(), "ip.yml");
            this.plugin.ip = YamlConfiguration.loadConfiguration(this.plugin.ipFile);
            HandlerList.unregisterAll(this.plugin);
            this.plugin.registerListener();
            sender.sendMessage("§aВие успешно презаредихте приставката..");
        } else {
            sender.sendMessage("§cНевалиден синтаксис §8➜ §7/ag §aза да видите командите.");
        }

        return true;
    }

    public boolean checkPermission(CommandSender sender, String permission) {
        if (sender.hasPermission("accountguard." + permission)) {
            return true;
        } else {
            sender.sendMessage(this.noperm);
            return false;
        }
    }
}
