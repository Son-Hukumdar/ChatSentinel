package twolovers.chatsentinel.shared.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Locale;

public class VersionUtil {

  public static String getLocale(final ProxiedPlayer player) {
    final Locale locale = player.getLocale();

    if (locale != null) {
      final String localeString = locale.toString();

      if (localeString.length() > 1) {
        return localeString.substring(0, 2);
      }
    }

    return "en";
  }
}