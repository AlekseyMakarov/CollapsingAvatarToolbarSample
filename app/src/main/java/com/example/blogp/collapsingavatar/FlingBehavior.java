package com.example.blogp.collapsingavatar;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public final class FlingBehavior extends com.google.android.material.appbar.AppBarLayout.Behavior {

	private static final int TOP_CHILD_FLING_THRESHOLD = 3;
	private boolean isPositive;

	public FlingBehavior() {
	}

	public FlingBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout,
                                 @NonNull AppBarLayout child, @NonNull View target, float velocityX,
                                 float velocityY, boolean consumed) {
		if (velocityY > 0 && !isPositive || velocityY < 0 && isPositive) {
			velocityY = velocityY * -1;
		}
		if (target instanceof androidx.recyclerview.widget.RecyclerView && velocityY < 0) {
			final androidx.recyclerview.widget.RecyclerView recyclerView = (androidx.recyclerview.widget.RecyclerView) target;
			final View firstChild = recyclerView.getChildAt(0);
			final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
			consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
		}
		return super
				.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
	}

	@Override
	public void onNestedPreScroll(androidx.coordinatorlayout.widget.CoordinatorLayout coordinatorLayout, com.google.android.material.appbar.AppBarLayout child,
								  View target, int dx, int dy, int[] consumed, int type) {
		super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
		isPositive = dy > 0;
	}
}