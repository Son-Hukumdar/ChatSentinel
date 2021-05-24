package twolovers.chatsentinel.shared.modules;

import twolovers.chatsentinel.shared.chat.ChatPlayer;
import twolovers.chatsentinel.shared.interfaces.Module;
import twolovers.chatsentinel.shared.utils.PlaceholderUtil;

public class CapsModule implements Module {
  private boolean enabled, replace;
  private int max, maxWarns;
  private String warnNotification;
  private String[] punishments;

  final public void loadData(final boolean enabled, final boolean replace, final int max, final int maxWarns,
                             final String warnNotification, final String[] punishments) {
    this.enabled = enabled;
    this.replace = replace;
    this.max = max;
    this.maxWarns = maxWarns;
    this.warnNotification = warnNotification;
    this.punishments = punishments;
  }

  final public boolean isReplace() {
    return this.replace;
  }

  final public long capsCount(final String string) {
    return string.codePoints().filter(c -> c >= 'A' && c <= 'Z').count();
  }

  @Override
  final public boolean meetsCondition(final ChatPlayer chatPlayer, final String message) {
    if (this.enabled && this.capsCount(message) > max)
      return true;

    return false;
  }

  @Override
  final public String getName() {
    return "Caps";
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
