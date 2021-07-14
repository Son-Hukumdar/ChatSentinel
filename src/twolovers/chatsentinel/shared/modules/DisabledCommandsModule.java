package twolovers.chatsentinel.shared.modules;

import twolovers.chatsentinel.shared.chat.ChatPlayer;
import twolovers.chatsentinel.shared.interfaces.Module;
import twolovers.chatsentinel.shared.utils.PlaceholderUtil;

public class DisabledCommandsModule implements Module {
  private boolean enabled;
  private int maxWarns;
  private String warnNotification;
  private String[] commands, punishments;
  private boolean bypassable = false;

  final public void loadData(final boolean enabled, final int maxWarns, final String warnNotification,
                             final String[] commands, final String[] punishments, final boolean bypassable) {
    this.enabled = enabled;
    this.maxWarns = maxWarns;
    this.warnNotification = warnNotification;
    this.commands = commands;
    this.punishments = punishments;
    this.bypassable = bypassable;
  }

  final public boolean isDisabled(String message) {
    if (commands.length > 0 && message.startsWith("/"))
      message = message.toLowerCase().split(" ")[0];
    for (final String command : commands)
      if (message.equals(command))
        return true;

    return false;
  }

  @Override
  public boolean meetsCondition(final ChatPlayer chatPlayer, final String message) {
    return (enabled && isDisabled(message));
  }

  @Override
  final public String getName() {
    return "DisabledCommands";
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
