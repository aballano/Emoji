package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EmojiUtilsTest {
  private static final String EMOJI_1 = "\u1234";
  private static final String EMOJI_2 = "\u4321";

  @Before public void setUp() {
    EmojiManager.install(new EmojiProvider() {
      @NonNull @Override public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{new EmojiCategory() {
          @NonNull @Override public Emoji[] getEmojis() {
            return new Emoji[]{
                  new Emoji(EMOJI_1.codePointAt(0), R.drawable.emoji_recent),
                  new Emoji(EMOJI_2.codePointAt(0), R.drawable.emoji_backspace),
            };
          }

          @Override public int getIcon() {
            return R.drawable.emoji_recent;
          }
        }};
      }
    });
  }

  @Test public void constructorShouldBePrivate() {
    PrivateConstructorChecker.forClass(EmojiUtils.class)
          .expectedTypeOfException(AssertionError.class)
          .expectedExceptionMessage("No instances.")
          .check();
  }

  @Test public void isOnlyEmojis_empty() {
    assertThat(EmojiUtils.isOnlyEmojis("")).isFalse();
  }

  @Test public void isOnlyEmojis_null() {
    assertThat(EmojiUtils.isOnlyEmojis(null)).isFalse();
  }

  @Test public void isOnlyEmojis_text() {
    assertThat(EmojiUtils.isOnlyEmojis("hello world!")).isFalse();
  }

  @Test public void isOnlyEmojisSingleEmoji() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1)).isTrue();
  }

  @Test public void isOnlyEmojisMultipleEmojis() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1 + EMOJI_2)).isTrue();
  }

  @Test public void isOnlyEmojisMultipleEmojisWithSpaces() {
    assertThat(EmojiUtils.isOnlyEmojis(" \t" + EMOJI_1 + "\t    " + EMOJI_2 + "  \r\n")).isTrue();
  }

  @Test public void isOnlyEmojisSingleEmojiAndText() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello")).isFalse();
  }

  @Test public void isOnlyEmojisSingleTextAndEmoji() {
    assertThat(EmojiUtils.isOnlyEmojis("hello" + EMOJI_1)).isFalse();
  }

  @Test public void isOnlyEmojisMultipleEmojisAndText() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello" + EMOJI_2)).isFalse();
  }

  @Test public void isOnlyEmojisMultipleTextAndEmojis() {
    assertThat(EmojiUtils.isOnlyEmojis("hello" + EMOJI_1 + "world")).isFalse();
  }

  @Test public void emojisCount_empty() {
    assertThat(EmojiUtils.emojisCount("")).isEqualTo(0);
  }

  @Test public void emojisCount_null() {
    assertThat(EmojiUtils.emojisCount(null)).isEqualTo(0);
  }

  @Test public void emojisCount_text() {
    assertThat(EmojiUtils.emojisCount("hello world!")).isEqualTo(0);
  }

  @Test public void emojisCountSingleEmoji() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1)).isEqualTo(1);
  }

  @Test public void emojisCountMultipleEmoji() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1 + EMOJI_2)).isEqualTo(2);
  }

  @Test public void emojisCountMultipleEmojisWithSpaces() {
    assertThat(EmojiUtils.emojisCount(" " + EMOJI_1 + "    " + EMOJI_2 + "  \n")).isEqualTo(2);
  }

  @Test public void emojisCountSingleEmojiAndText() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1 + "hello")).isEqualTo(1);
  }

  @Test public void emojisCountSingleTextAndEmoji() {
    assertThat(EmojiUtils.emojisCount("hello" + EMOJI_1)).isEqualTo(1);
  }

  @Test public void emojisCountMultipleEmojisAndText() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1 + "hello" + EMOJI_2)).isEqualTo(2);
  }

  @Test public void emojisCountMultipleTextAndEmojis() {
    assertThat(EmojiUtils.emojisCount("hello" + EMOJI_1 + "world")).isEqualTo(1);
  }
}