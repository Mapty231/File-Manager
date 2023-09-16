package me.tye.filemanager.commands;

import me.tye.filemanager.FileManager;
import me.tye.filemanager.util.yamlClasses.PluginData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static me.tye.filemanager.FileManager.log;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (label.equals("plugin")) {
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], Arrays.asList("help", "install", "remove", "browse"), completions);
            }
            if (args.length == 2 && args[0].equals("install") && args[1].isEmpty()) {
                return List.of("<pluginName | URL>");
            }
            if (args.length == 2 && args[0].equals("remove")) {
                ArrayList<String> plugins = new ArrayList<>();

                try {
                    for (PluginData data : FileManager.readPluginData())
                        plugins.add(data.getName());
                } catch (IOException e) {
                    log(e, sender, Level.WARNING, "There was an error reading the plugin names from the pluginData file.");
                }

                StringUtil.copyPartialMatches(args[1], plugins, completions);
            }
        }

        if (label.equals("file")) {
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], Arrays.asList("help", "chat", "gui"), completions);
            }
        }
        return completions;
    }
}
