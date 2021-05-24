package twolovers.chatsentinel.shared.modules;

import twolovers.chatsentinel.shared.chat.ChatPlayer;
import twolovers.chatsentinel.shared.interfaces.Module;
import twolovers.chatsentinel.shared.utils.PlaceholderUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloodModule implements Module {
  private boolean enabled, replace;
  private int maxWarns;
  private String warnNotification;
  private String[] punishments;
  private Pattern pattern;

  final public void loadData(final boolean enabled, final boolean replace, final int maxWarns, final String pattern,
                             final String warnNotification, final String[] punishments) {
    this.enabled = enabled;
    this.replace = replace;
    this.maxWarns = maxWarns;
    this.warnNotification = warnNotification;
    this.punishments = punishments;
    this.pattern = Pattern.compile(pattern);
  }

  public boolean isReplace() {
    return this.replace;
  }

  final public String replace(String string) {
    final Matcher matcher = pattern.matcher(string);

    if (matcher.find()) {
      final String group = matcher.group();

      string.replace(group.substring(0, group.length()), "");
    }

    return string;
  }

  @Override
  public boolean meetsCondition(final ChatPlayer chatPlayer, final String message) {
    return this.enabled && pattern.matcher(message).find();
  }

  @Override
  final public String getName() {
    return "Flood";
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
