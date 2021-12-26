package me.codedcrown.hellaccountguard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class EventListener implements Listener {
    HellAccountGuard plugin;

    EventListener(HellAccountGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void AGLoginEvent(PlayerLoginEvent e) {
        if (this.plugin.ip.isSet(e.getPlayer().getName().toLowerCase())) {
            String[] var5;
            int var4 = (var5 = this.plugin.ip.getString(e.getPlayer().getName().toLowerCase()).split("\\|")).length;

            for(int var3 = 0; var3 < var4; ++var3) {
                String se = var5[var3];
                if (se.equals(e.getAddress().getHostAddress())) {
                    return;
                }
            }

            e.disallow(Result.KICK_OTHER, this.plugin.doesntmatch);
        }

    }
}
