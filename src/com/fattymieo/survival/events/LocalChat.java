package com.fattymieo.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.fattymieo.survival.Survival;

@SuppressWarnings("deprecation")
public class LocalChat implements Listener
{
	double maxDist = 64;
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(PlayerChatEvent event)
	{
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		String msg = event.getMessage();
		
		if(Survival.settings.getBoolean("LegendaryItems.GoldArmorBuff"))
		{
			if(player.getInventory().getHelmet() != null)
			{
				if(player.getInventory().getHelmet().getType() == Material.GOLD_HELMET)
				{
					event.setCancelled(false);
					event.setFormat(ChatColor.GOLD + "<%1$s> " + ChatColor.YELLOW + "%2$s");
					return;
				}
			}
		}
		
		int channel = Survival.board.getObjective("Chat").getScore(player).getScore();
		if(channel > 0)
		{
			event.setFormat(ChatColor.GREEN + "<%1$s> " + ChatColor.RESET + "%2$s");
			return;
		}
		
		event.setCancelled(true);

		Bukkit.getConsoleSender().sendMessage("<" + player.getDisplayName() + "> " + msg);
		for(Player other : Bukkit.getServer().getOnlinePlayers())
        {
			if(other.getLocation().getWorld() == player.getLocation().getWorld())
			{
	            if(other.getLocation().distance(player.getLocation()) <= maxDist)
	            {
	                other.sendMessage(ChatColor.RESET + "<" + player.getDisplayName() + "> " + msg);
	            }
			}
        }
	}
}
