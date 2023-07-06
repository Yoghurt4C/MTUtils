package ru.lionzxy.damagetweaker.mods;

import gregapi.recipes.Recipe;
import gregapi.recipes.Recipe.RecipeMap;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class GTListCommand implements ICommand {

    @Override
    public int compareTo(Object pArg0) {
        int result = 0;
        if (pArg0 instanceof ICommand) {

        }
        return 0;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender arg0, String[] arg1) {
        return null;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender arg0) {
        return true;
    }

    @Override
    public List getCommandAliases() {
        List<String> alias = new LinkedList<String>();
        alias.add("mtutils");
        alias.add("mtu");
        return alias;
    }

    @Override
    public String getCommandName() {
        return "MTUtils";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "Usage: /MTUtils GTkeys [1..." + ((RecipeMap.RECIPE_MAPS.keySet().size() / 10) + 1)
                + "] or GTkey \"machine name\"";
    }

    @Override
    public boolean isUsernameIndex(String[] arg0, int arg1) {
        return false;
    }

    @Override
    public void processCommand(ICommandSender pSender, String[] arg1) {
        if (arg1.length > 0) {
            if ("gtkeys".equalsIgnoreCase(arg1[0])) {
                MinecraftServer server = MinecraftServer.getServer();
                ChatComponentText message = new ChatComponentText("");
                server.addChatMessage(message);
                pSender.addChatMessage(message);
                message = new ChatComponentText("Gregtech recipe keys");
                server.addChatMessage(message);
                pSender.addChatMessage(message);

                List<String> keys = new LinkedList<String>();
                keys.addAll(Recipe.RecipeMap.RECIPE_MAPS.keySet());
                Collections.sort(keys);

                int maxLength = 0;
                for (String key : keys) {
                    maxLength = Math.max(maxLength, key.length());
                }

                List<ChatComponentText> messages = new LinkedList<ChatComponentText>();
                for (String key : keys) {
                    if (!key.isEmpty()) {
                        message = buildMessage(key, Recipe.RecipeMap.RECIPE_MAPS.get(key).mNameLocal);
                        server.addChatMessage(message);
                        messages.add(message);
                    }
                }

                sendMessages(pSender, messages, arg1.length > 1 ? arg1[1] : null, null);
            } else if ("gtkey".equalsIgnoreCase(arg1[0])) {
                if (arg1.length > 1) {
                    String filter = arg1[1].toLowerCase();
                    if (filter.startsWith("\"")) {
                        filter = filter.substring(1);
                    }
                    if (filter.endsWith("\"")) {
                        filter = filter.substring(0, filter.length() - 1);
                    }

                    List<ChatComponentText> messages = new LinkedList<ChatComponentText>();
                    List<String> keys = new LinkedList<String>();
                    keys.addAll(Recipe.RecipeMap.RECIPE_MAPS.keySet());
                    Collections.sort(keys);
                    for (String key : keys) {
                        String localName = Recipe.RecipeMap.RECIPE_MAPS.get(key).mNameLocal;
                        if (localName.toLowerCase().matches(filter)) {
                            messages.add(buildMessage(key, localName));
                        }
                    }
                    sendMessages(pSender, messages, null, filter);
                } else {
                    pSender.addChatMessage(new ChatComponentText(getCommandUsage(pSender)));
                }
            } else if ("gtfluids".equalsIgnoreCase(arg1[0])) {
                MinecraftServer server = MinecraftServer.getServer();
                ChatComponentText message = new ChatComponentText("");
                server.addChatMessage(message);
                pSender.addChatMessage(message);
                message = new ChatComponentText("Input fluids of GT-recipes");
                server.addChatMessage(message);
                pSender.addChatMessage(message);

                Set<String> fluids = new HashSet<String>();
                for (RecipeMap recipes : Recipe.RecipeMap.RECIPE_MAPS.values()) {
                    for (Recipe recipe : recipes.getNEIAllRecipes()) {
                        if (recipe.mFluidInputs != null) {
                            for (FluidStack fluid : recipe.mFluidInputs) {
                                fluids.add(fluid.getFluid().getName());
                            }
                        }
                    }
                }
                List<String> sortedFluids = new LinkedList<String>();
                sortedFluids.addAll(fluids);
                Collections.sort(sortedFluids);

                List<ChatComponentText> messages = new LinkedList<ChatComponentText>();
                for (String fluid : sortedFluids) {
                    if (!fluid.isEmpty()) {
                        message = new ChatComponentText(fluid);
                        server.addChatMessage(message);
                        messages.add(message);
                    }
                }

                sendMessages(pSender, messages, arg1.length > 1 ? arg1[1] : null, null);

            }
        } else {
            pSender.addChatMessage(new ChatComponentText(getCommandUsage(pSender)));
        }
    }

    private ChatComponentText buildMessage(String key, String mNameLocal) {
        ChatComponentText message = new ChatComponentText(key);
        ChatComponentText name = new ChatComponentText(" for " + mNameLocal);
        name.getChatStyle().setItalic(true);
        message.appendSibling(name);
        return message;
    }

    private void sendMessages(ICommandSender pSender, List<ChatComponentText> messages, String pSet, String pFilter) {
        int startIndex = 0;
        int endIndex = messages.size();
        if (pSet != null) {
            try {
                startIndex = Math.max(0, Integer.valueOf(pSet) - 1) * 10;
                endIndex = startIndex + 10;
            } catch (Exception pException) {
            }
        }

        if (startIndex < messages.size()) {
            for (int i = startIndex; i < Math.min(messages.size(), endIndex); i++) {
                pSender.addChatMessage(messages.get(i));
            }
        } else {
            if (pFilter != null) {
                pSender.addChatMessage(new ChatComponentText("No key found for " + pFilter));
            } else {
                pSender.addChatMessage(new ChatComponentText(
                        "No keys found, use smaller index: 1 	... " + ((messages.size() / 10) + 1)));
            }
        }
    }
}
