package me.eeshe.itemfilter.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");

    /**
     * Translates all the color codes on the passed message and returns it back.
     *
     * @param text Text whose color codes will be translated.
     * @return String with all the translated color codes.
     */
    public static String formatColor(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace(color, String.valueOf(net.md_5.bungee.api.ChatColor.of(color.replace("&", ""))));
            matcher = HEX_PATTERN.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static String formatEnum(Enum<?> enumeration) {
        String[] split = enumeration.name().split("_");
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                String word = split[i];
                if (word.equalsIgnoreCase("of") || word.equalsIgnoreCase("the")) {
                    split[i] = word.toLowerCase();
                    continue;
                }

                split[i] = capitalize(word);
            }
            return String.join(" ", split);

        } else
            return capitalize(enumeration.name());
    }

    public static String millisToText(long millis) {
        // Compute time in each unit
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= days * TimeUnit.DAYS.toMillis(1);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= hours * TimeUnit.HOURS.toMillis(1);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= minutes * TimeUnit.MINUTES.toMillis(1);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        String text;
        if (days > 0) {
            text = String.format("%s days %s hours %s minutes %s seconds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            text = String.format("%s hours %s minutes %s seconds", hours, minutes, seconds);
        } else if (minutes > 0) {
            text = String.format("%s minutes %s seconds", minutes, seconds);
        } else {
            text = seconds + " seconds";
        }
        return text;
    }

    public static Integer parseInteger(CommandSender sender, String string) {
        Integer number;
        try {
            number = Integer.parseInt(string);
        } catch (Exception e) {
            Messager.sendErrorMessage(sender, "&cYou must enter a numeric value with no decimals.");
            return null;
        }
        return number;
    }

    public static Double parseDouble(CommandSender sender, String string) {
        Double number;
        try {
            number = Double.parseDouble(string);
        } catch (Exception e) {
            Messager.sendErrorMessage(sender, "&cYou must enter a numeric value.");
            return null;
        }
        return number;
    }
}
