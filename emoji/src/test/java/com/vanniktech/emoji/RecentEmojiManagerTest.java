package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import java.util.Collection;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public class RecentEmojiManagerTest {
  private RecentEmoji recentEmojiManager;

  @Before public void setUp() {
    recentEmojiManager = new RecentEmojiManager(RuntimeEnvironment.application);
  }

  @Test public void getRecentEmojis() {
    assertThat(recentEmojiManager.getRecentEmojis()).isEmpty();
  }

  @Test public void addEmoji() {
    recentEmojiManager.addEmoji(new Emoji(0x1f437, R.drawable.emoji_recent));
    recentEmojiManager.addEmoji(new Emoji(0x1f43d, R.drawable.emoji_recent));

    assertThat(recentEmojiManager.getRecentEmojis()).hasSize(2)
        .containsExactly(
            new Emoji(0x1f43d, R.drawable.emoji_recent),
            new Emoji(0x1f437, R.drawable.emoji_recent));
  }

  @Test public void persist() {
    final Emoji firstEmoji = new Emoji(0x1f437, R.drawable.emoji_recent);
    recentEmojiManager.addEmoji(firstEmoji);
    final Emoji secondEmoji = new Emoji(0x1f43d, R.drawable.emoji_recent);
    recentEmojiManager.addEmoji(secondEmoji);

    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).hasSize(2).containsExactly(secondEmoji, firstEmoji);
  }

  @Test public void duplicateEmojis() {
    final Emoji emoji = new Emoji(0x1f437, R.drawable.emoji_recent);
    recentEmojiManager.addEmoji(emoji);
    recentEmojiManager.addEmoji(emoji);
    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).hasSize(1).containsExactly(emoji);
  }

  @Test public void inOrder() {
    recentEmojiManager.addEmoji(new Emoji(0x1f55a, R.drawable.emoji_recent));
    recentEmojiManager.addEmoji(new Emoji(0x1f561, R.drawable.emoji_recent));
    recentEmojiManager.addEmoji(new Emoji(0x1f4e2, R.drawable.emoji_recent));
    recentEmojiManager.addEmoji(new Emoji(0x1f562, R.drawable.emoji_recent));
    recentEmojiManager.addEmoji(new Emoji(0xe535, R.drawable.emoji_recent));
    recentEmojiManager.addEmoji(new Emoji(0x1f563, R.drawable.emoji_recent));

    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).containsExactly(
        new Emoji(0x1f563, R.drawable.emoji_recent),
        new Emoji(0xe535, R.drawable.emoji_recent),
        new Emoji(0x1f562, R.drawable.emoji_recent),
        new Emoji(0x1f4e2, R.drawable.emoji_recent),
        new Emoji(0x1f561, R.drawable.emoji_recent),
        new Emoji(0x1f55a, R.drawable.emoji_recent));
  }

  @Test public void newShouldReplaceOld() {
    recentEmojiManager.addEmoji(new Emoji(0x2764, R.drawable.emoji_recent));
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(
        new Emoji(0x2764, R.drawable.emoji_recent));

    recentEmojiManager.addEmoji(new Emoji(0x1f577, R.drawable.emoji_recent));
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(
        new Emoji(0x1f577, R.drawable.emoji_recent),
        new Emoji(0x2764, R.drawable.emoji_recent));

    recentEmojiManager.addEmoji(new Emoji(0x2764, R.drawable.emoji_recent));
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(
        new Emoji(0x2764, R.drawable.emoji_recent),
        new Emoji(0x1f577, R.drawable.emoji_recent));
  }

  @Test public void maxRecents() {
    for (int i = 0; i < 500; i++) {
      recentEmojiManager.addEmoji(new Emoji(i, R.drawable.emoji_recent));
    }

    assertThat(recentEmojiManager.getRecentEmojis()).hasSize(40);
  }
}
