package twolovers.chatsentinel.shared.modules;

import twolovers.chatsentinel.shared.chat.ChatPlayer;
import twolovers.chatsentinel.shared.interfaces.Module;
import twolovers.chatsentinel.shared.utils.PlaceholderUtil;

public class SyntaxModule implements Module {
  private boolean enabled;
  private int maxWarns;
  private String warnNotification;
  private String[] whitelist, punishments;

  final public void loadData(final boolean enabled, final int maxWarns, final String warnNotification,
                             final String[] whitelist, final String[] punishments) {
    this.enabled = enabled;
    this.maxWarns = maxWarns;
    this.warnNotification = warnNotification;
    this.whitelist = whitelist;
    this.punishments = punishments;
  }

  final public boolean isWhitelisted(final String message) {
    if (whitelist.length > 0)
      for (final String string : whitelist)
        if (message.startsWith(string))
          return true;

    return false;
  }

  @Override
  public boolean meetsCondition(final ChatPlayer chatPlayer, final String message) {
    return (enabled && hasSyntax(message) && !isWhitelisted(message));
  }

  @Override
  final public String getName() {
    return "Syntax";
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

  private boolean hasSyntax(final String message) {
    if (message.startsWith("/")) {
      final String[] subCommands = message.split(" ");
      final String command = subCommands[0];
      final String[] syntax = command.split(":");

      if (syntax.length > 1)
        return true;
    }

    return false;
  }
}
