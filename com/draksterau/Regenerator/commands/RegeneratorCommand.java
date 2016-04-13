/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.draksterau.Regenerator.commands;

import com.draksterau.Regenerator.RegeneratorPlugin;
import com.draksterau.Regenerator.config.chunkConfigHandler;
import com.draksterau.Regenerator.config.worldConfigHandler;
import com.draksterau.Regenerator.integration.Integration;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author draks
 */
public class RegeneratorCommand {
    
    // Main command name (args[0] on the main command thread)
    CommandSender sender;
    Command cmd;
    String label;
    String[] args;
    RegeneratorPlugin plugin;
    
    public RegeneratorCommand(RegeneratorPlugin plugin, CommandSender sender, Command cmd, String label, String[] args) {
        this.sender = sender;
        this.cmd = cmd;
        this.label = label;
        this.args = args;
        this.plugin = plugin;
    }
    
    public boolean doCommand() {
        switch (this.args.length) {
            case 0:
                sender.sendMessage(plugin.getFancyName() + ChatColor.GOLD + "Running v" + ChatColor.GREEN + plugin.getDescription().getVersion() + ChatColor.GOLD + " of Regenerator.");
                sender.sendMessage(plugin.getFancyName() + ChatColor.GOLD + plugin.getDescription().getDescription());
                sender.sendMessage(plugin.getFancyName() + ChatColor.GOLD + "Author: " + ChatColor.AQUA + plugin.getDescription().getAuthors().get(0));
                sender.sendMessage(plugin.getFancyName() + ChatColor.GOLD + "Website: " + ChatColor.BLUE + plugin.getDescription().getWebsite());
                sender.sendMessage(plugin.getFancyName() + ChatColor.GRAY + "Type " + ChatColor.RED + "/regenerator help" + ChatColor.GRAY + " for more information on how to use the plugin");
                return true;
            default:
                switch (args[0].toLowerCase()) {
                    case "help":
                        sender.sendMessage(plugin.getFancyName() + ChatColor.GREEN + "regen" + ChatColor.GRAY + ": The regen command handles manual regeneration. Valid options are: chunk or unclaimed <world>");
                        sender.sendMessage(plugin.getFancyName() + ChatColor.GREEN + "map" + ChatColor.GRAY + ": The map command will show a chunk map of the nearby area. It will be colored according to whether or not it regenerates automatically or you can manually regenerate it.");
                        sender.sendMessage(plugin.getFancyName() + ChatColor.GREEN + "integrations" + ChatColor.GRAY + ": The integrations command will return whether or not the various supported plugins are active and running with Regenerator.");
                        break;
                    case "regen":
                        if (sender.hasPermission("regenerator.regen")) {
                            new regenCommand(this).doCommand();
                        } else {
                            sender.sendMessage(plugin.getFancyName() + ChatColor.RED + "This command requires the regenerator.regen permission node.");
                        }
                        break;
                    case "map":
                        if (sender.hasPermission("regenerator.map")) {
                           new mapCommand(this).doCommand();
                        } else {
                            sender.sendMessage(plugin.getFancyName() + ChatColor.RED + "This command requires the regenerator.map permission node.");
                        }
                        break;
                    case "integrations":
                        if (sender.hasPermission("regenerator.integrations")) {
                            new integrationCommand(this).doCommand();
                        } else {
                            sender.sendMessage(plugin.getFancyName() + ChatColor.RED + "This command requires the regenerator.integrations permission node.");
                        }
                        break;
                    case "info":
                        new infoCommand(this).doCommand();
                        break;
                    default:
                        sender.sendMessage(plugin.getFancyName() + ChatColor.RED + "Error.. You must specify a valid option, they are: help, regen, map, info, integrations");
                        break;
                }
        }
        return true;
    }
    
}