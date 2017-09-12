package com.vanniktech.emoji.googlecompat;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.EmojiSpan;
import android.text.Spanned;
import android.text.TextPaint;

/**
 * An emoji drawable backed by a span generated by the Google emoji support library.
 */
final class GoogleCompatEmojiDrawable extends Drawable {
  private static final float TEXT_SIZE_FACTOR = 0.8f;
  private static final float BASELINE_OFFSET_FACTOR = 0.225f;

  private EmojiSpan emojiSpan;
  private boolean processed;
  private CharSequence emojiCharSequence;
  private final TextPaint textPaint = new TextPaint();

  GoogleCompatEmojiDrawable(@NonNull final String unicode) {
    emojiCharSequence = unicode;
    textPaint.setStyle(Paint.Style.FILL);
    textPaint.setColor(0x0ffffffff);
    textPaint.setAntiAlias(true);
  }

  private void process() {
    emojiCharSequence = EmojiCompat.get().process(emojiCharSequence);
    if (emojiCharSequence instanceof Spanned) {
      final Object[] spans = ((Spanned) emojiCharSequence).getSpans(0, emojiCharSequence.length(), EmojiSpan.class);
      if (spans.length > 0) {
        emojiSpan = (EmojiSpan) spans[0];
      }
    }
  }

  @Override public void draw(final Canvas canvas) {
    final Rect bounds = getBounds();
    textPaint.setTextSize(bounds.height() * TEXT_SIZE_FACTOR);
    final int y = Math.round(bounds.bottom - bounds.height() * BASELINE_OFFSET_FACTOR);

    if (!processed && EmojiCompat.get().getLoadState() != EmojiCompat.LOAD_STATE_LOADING) {
      processed = true;
      if (EmojiCompat.get().getLoadState() != EmojiCompat.LOAD_STATE_FAILED) {
        process();
      }
    }

    if (emojiSpan == null) {
      canvas.drawText(emojiCharSequence, 0, emojiCharSequence.length(), bounds.left, y, textPaint);
    } else {
      emojiSpan.draw(canvas, emojiCharSequence, 0, emojiCharSequence.length(), bounds.left, bounds.top, y, bounds.bottom, textPaint);
    }
  }

  @Override public void setAlpha(final int alpha) {
    textPaint.setAlpha(alpha);
  }

  @Override public void setColorFilter(final ColorFilter colorFilter) {
    textPaint.setColorFilter(colorFilter);
  }

  @Override public int getOpacity() {
    return PixelFormat.UNKNOWN;
  }
}