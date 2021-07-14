package twolovers.chatsentinel.shared.modules;

import twolovers.chatsentinel.shared.chat.ChatPlayer;
import twolovers.chatsentinel.shared.interfaces.Module;
import twolovers.chatsentinel.shared.utils.PlaceholderUtil;

import java.util.regex.Pattern;

public class BlacklistModule implements Module {
  private boolean enabled, fakeMessage, hideWords;
  private int maxWarns;
  private String warnNotification;
  private String[] punishments;
  private Pattern pattern;
  private boolean bypassable = false;

  final public void loadData(final boolean enabled, final boolean fakeMessage, final boolean hideWords,
                             final int maxWarns, final String warnNotification, final String[] punishments, final String[] patterns, final boolean bypassable) {
    String patternString = "";

    for (final String string : patterns) {
      patternString = String.format("%s(%s)|", patternString, string);
    }

    this.enabled = enabled;
    this.fakeMessage = fakeMessage;
    this.hideWords = hideWords;
    this.maxWarns = maxWarns;
    this.warnNotification = warnNotification;
    this.punishments = punishments;
    this.pattern = Pattern.compile("(?i)(" + patternString + "(?!x)x)");
    this.bypassable = bypassable;
  }

  final public boolean isFakeMessage() {
    return this.fakeMessage;
  }

  final public boolean isHideWords() {
    return this.hideWords;
  }

  final public Pattern getPattern() {
    return pattern;
  }

  @Override
  public boolean meetsCondition(final ChatPlayer chatPlayer, String message) {
    message = message.startsWith("/") && message.contains(" ") ? message.substring(message.indexOf(" ")) : message;

    return enabled && pattern.matcher(message).find();
  }

  @Override
  public String getName() {
    return "Blacklist";
  }

  @Override
  final public boolean isBypassable() {
    return this.bypassable;
  }

  @Override
  final public String[] getPunishments(final String[][] placeholders) {
    if (this.punishments.length > 0) {
      final String[] punishments = this.punishments.clone();

      for (int i = 0; i < punishments.length; i++) {
        punishments[i] = PlaceholderUtil.replacePlaceholders(punishments[i], placeholders);
      }

      return punishments;
    } else
      return new String[0];
  }

  @Override
  final public String getWarnNotification(final String[][] placeholders) {
    if (!this.warnNotification.isEmpty()) {
      return PlaceholderUtil.replacePlaceholders(this.warnNotification, placeholders);
    } else
      return null;
  }

  @Override
  public int getMaxWarns() {
    return maxWarns;
  }
}
