package com.yasin.view.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2021/11/28.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private int orientation;
    private int dividerHeight;
    private int drawableId;
    private int dividerColor;
    private Context context;
    private int dividerWidth = 0;
    private Paint mPaint;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};


    private DividerItemDecoration(Builder builder) {
        this.dividerHeight = builder.dividerHeight;
        this.dividerWidth = builder.dividerWidth;
        this.drawableId = builder.drawableId;
        this.dividerColor = builder.dividerColor;
        this.context = builder.context;
        this.orientation = builder.orientation;


        if (drawableId != 0) {
            this.divider = context.getResources().getDrawable(drawableId);
        } else {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            divider = a.getDrawable(0);
            a.recycle();
        }

        if (dividerColor != 0) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(context.getResources().getColor(dividerColor));
            mPaint.setStyle(Paint.Style.FILL);
        }
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, divider.getIntrinsicHeight() + dividerHeight);
        } else {
            outRect.set(0, 0, divider.getIntrinsicWidth() + dividerWidth, 0);
        }
    }

    /**
     * 绘制间隔
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        // 遍历
        for (int i = 0; i < childCount; i++) {
            if (i == childCount - 1) continue;

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divider.getIntrinsicHeight() + dividerHeight;

            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 绘制间隔
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == childCount - 1) continue;
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + divider.getIntrinsicWidth() + dividerWidth;
            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    public static class Builder {
        private int orientation = -1;
        private int dividerHeight = 0;
        private int dividerWidth = 0;
        private int drawableId;
        private int dividerColor;
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(DividerItemDecoration dividerItemDecoration) {
            this.orientation = dividerItemDecoration.orientation;
            this.dividerHeight = dividerItemDecoration.dividerHeight;
            this.context = dividerItemDecoration.context;
            this.drawableId = dividerItemDecoration.drawableId;
            this.dividerColor = dividerItemDecoration.dividerColor;
        }

        public Builder dividerHeight(int dividerHeight) {
            this.dividerHeight = dividerHeight;
            return this;
        }

        public Builder dividerWidth(int dividerWidth) {
            this.dividerWidth = dividerWidth;
            return this;
        }

        public Builder drawableId(int drawableId) {
            this.drawableId = drawableId;
            return this;
        }

        public Builder dividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public Builder orientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public DividerItemDecoration build() {
            if (orientation == -1) {
                orientation = LinearLayoutManager.VERTICAL;
            }

            if (dividerColor == 0) {
                dividerColor = Color.TRANSPARENT;
            }

            return new DividerItemDecoration(this);
        }
    }
}
