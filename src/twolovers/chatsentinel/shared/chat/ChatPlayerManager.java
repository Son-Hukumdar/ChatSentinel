package twolovers.chatsentinel.shared.chat;

import java.util.*;

public class ChatPlayerManager {
  final private Map<UUID, ChatPlayer> chatPlayers = new HashMap<>();
  final private Collection<ChatPlayer> offlinePlayers = new HashSet<>();
  private boolean offlinePlayersChanged = false;

  public ChatPlayer getPlayer(final UUID uuid) {
    ChatPlayer chatPlayer = chatPlayers.getOrDefault(uuid, null);

    if (chatPlayer == null) {
      chatPlayer = new ChatPlayer(uuid);
      chatPlayers.put(uuid, chatPlayer);
    }

    return chatPlayer;
  }

  public void setOffline(final ChatPlayer chatPlayer) {
    this.offlinePlayers.add(chatPlayer);
    this.offlinePlayersChanged = true;
  }

  public void setOnline(final ChatPlayer chatPlayer) {
    this.offlinePlayers.remove(chatPlayer);
    this.offlinePlayersChanged = true;
  }

  public void clear() {
    if (this.offlinePlayersChanged) {
      final Iterator<ChatPlayer> offlinePlayersIterator = offlinePlayers.iterator();

      while (offlinePlayersIterator.hasNext()) {
        final ChatPlayer chatPlayer = offlinePlayersIterator.next();

        chatPlayers.remove(chatPlayer.getUniqueId());
        offlinePlayersIterator.remove();
      }

      this.offlinePlayersChanged = false;
    }
  }
}