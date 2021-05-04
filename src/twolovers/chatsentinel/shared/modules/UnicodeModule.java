package twolovers.chatsentinel.shared.modules;

import twolovers.chatsentinel.shared.chat.ChatPlayer;
import twolovers.chatsentinel.shared.interfaces.Module;
import twolovers.chatsentinel.shared.utils.PlaceholderUtil;

import java.util.List;

public class UnicodeModule implements Module {
  private boolean enabled;
  private int maxWarns;
  private String warnNotification;
  private List<String> whitelist;
  private String[] commands;

  final public void loadData(final boolean enabled, final int maxWarns, final String warnNotification,
                             final List<String> whitelist, final String[] commands) {
    this.enabled = enabled;
    this.maxWarns = maxWarns;
    this.warnNotification = warnNotification;
    this.whitelist = whitelist;
    this.commands = commands;
  }

  final public boolean isWhitelisted(final String message) {
    return whitelist.contains(message);
  }

  @Override
  public boolean meetsCondition(final ChatPlayer chatPlayer, final String message) {
    return (enabled && hasUnicode(message.toLowerCase()));
  }

  @Override
  final public String getName() {
    return "Unicode";
  }

  @Override
  final public String[] getCommands(final String[][] placeholders) {
    if (this.commands.length > 0) {
      final String[] commands = this.commands.clone();

      for (int i = 0; i < commands.length; i++) {
        commands[i] = PlaceholderUtil.replacePlaceholders(commands[i], placeholders);
      }

      return commands;
    }
    else
      return new String[0];
  }

  @Override
  final public String getWarnNotification(final String[][] placeholders) {
    if (!this.warnNotification.isEmpty()) {
      return PlaceholderUtil.replacePlaceholders(this.warnNotification, placeholders);
    }
    else
      return null;
  }

  @Override
  public int getMaxWarns() {
    return maxWarns;
  }

  private boolean hasUnicode(final String message) {
    for (int i = 0; i < message.length(); i++) {
      int c = message.charAt(i);
      if (c > 15000 && !isWhitelisted(String.valueOf(message.charAt(i))))
        return true;
    }
    return false;
  }
}
