package me.nilsb20.navigationcompass;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class NavigationCompassManager implements Listener, CommandExecutor {

    private static final Set<PlayerActionBar> playerActionBars = new HashSet<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "You can't use this command!");
            return true;
        }

        Player p = (Player) sender;
        PlayerActionBar actionBar = getPlayerActionBar(p);
        if(actionBar == null) {
            p.sendMessage(ChatColor.DARK_RED + "You don't have a compass!");
            return true;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("stop")) {
                actionBar.navigateToLocation(null);
                p.sendMessage(ChatColor.GREEN + "You're compass doesn't point at a location anymore!");
                return true;
            }
        } else if(args.length == 2) {
            if(!(isInt(args[0]) && isInt(args[1]))) {
                p.sendMessage(ChatColor.DARK_RED + "This isn't a location!");
                return true;
            }
            int x = Integer.parseInt(args[0]);
            int z = Integer.parseInt(args[1]);

            actionBar.navigateToLocation(new Location(p.getWorld(), x+0.5, 0, z+0.5));
            p.sendMessage(ChatColor.GREEN +"You made you're compass point at the direction X: " + x + " Z: " + z);
            return true;
        }

        p.sendMessage(ChatColor.DARK_RED + "You didn't use this command the right way!");
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerActionBar playerActionBar = new PlayerActionBar(p);
        playerActionBars.add(playerActionBar);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        playerActionBars.removeIf(playerActionBar -> playerActionBar.getP().equals(e.getPlayer()));
    }

    /**
     * Update the compasses of all players
     */
    public static void updateAllCompasses() {
        for(PlayerActionBar playerActionBar : playerActionBars) {
            playerActionBar.update();
        }
    }

    /**
     * Get the compass from a player from the set
     * @param p the player
     * @return  the playeractionbar
     */
    private PlayerActionBar getPlayerActionBar(Player p) {
        for(PlayerActionBar playerActionBar : playerActionBars) {
            if(playerActionBar.getP().equals(p)) {
                return playerActionBar;
            }
        }
        return null;
    }

    /**
     * Check if a string is a integer
     * @param string    the string to check
     * @return  a boolean
     */
    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
