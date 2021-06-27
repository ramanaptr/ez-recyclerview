package com.ramanaptr.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.ramanaptr.widget.constant.EzViewType;
import com.ramanaptr.widget.model.EzBaseData;
import com.ramanaptr.widget.model.EzMultipleLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * EzRecyclerView
 * Created by ramanaptr
 */
public class EzRecyclerView<Data extends EzBaseData> extends RecyclerView {

    private EzMultipleLayout ezMultipleLayout = new EzMultipleLayout();
    private BaseAdapter<Data> baseAdapter;
    private Listener<Data> listener;
    private EzPaginationListener ezPaginationListener;
    private boolean isFirstLoadEzRecyclerView = true;
    private int tempShimmerSize = 0;
    private int startShimmerSize = 0;
    private int endShimmerSize = 0;
    private boolean isRecyclerViewLoading = true;
    private int offset = 0;
    private int limit = 0;
    private int currentPage = 0;
    private Data data;

    public EzRecyclerView(@NonNull Context context) {
        super(context);
        initComponent();
    }

    public EzRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    public EzRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent();
    }

    private void initComponent() {
        if (baseAdapter == null) {
            baseAdapter = new BaseAdapter<>(ezMultipleLayout);
            baseAdapter.setHasStableIds(true);
            setAdapter(baseAdapter);
            settingAnimator();
        }
    }

    public void destroy() {
        flagEzRecyclerViewFirstLoad();
        flagOnLoading();
        removeOnScrollListener(onScrollListener);
        ezPaginationListener = null;
        baseAdapter = null;
        listener = null;
        data = null;
        tempShimmerSize = 0;
        startShimmerSize = 0;
        endShimmerSize = 0;
        offset = 0;
        limit = 0;
        currentPage = 0;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setViewHolderLayout(@NonNull Listener<Data> listener) {
        this.listener = listener;
        baseAdapter.setListener(listener::setDataOnViewHolder);
        baseAdapter.setEzMultipleLayout(ezMultipleLayout);
    }

    public void setViewHolderLayout(@NonNull EzMultipleLayout ezMultipleLayout, @NonNull Listener<Data> listener) {
        this.listener = listener;
        this.ezMultipleLayout = ezMultipleLayout;
        baseAdapter.setListener(listener::setDataOnViewHolder);
        baseAdapter.setEzMultipleLayout(ezMultipleLayout);
    }

    public void setViewHolderLayout(@LayoutRes int layout, @NonNull Listener<Data> listener) {
        this.listener = listener;
        ezMultipleLayout.setLayout1(layout);
        baseAdapter.setListener(listener::setDataOnViewHolder);
        baseAdapter.setEzMultipleLayout(ezMultipleLayout);
    }

    public void setViewHolderLayout(@LayoutRes int layout, boolean isAdUnit, @NonNull Listener<Data> listener) {
        this.listener = listener;
        if (isAdUnit) {
            ezMultipleLayout.setLayoutAdUnit(layout);
        } else {
            ezMultipleLayout.setLayout1(layout);
        }
        baseAdapter.setListener(listener::setDataOnViewHolder);
        baseAdapter.setEzMultipleLayout(ezMultipleLayout);
    }

    public void setDefaultLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(linearLayoutManager);
    }

    public void setHorizontalLinearLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(linearLayoutManager);
    }

    public void setGridLayoutManager(int span) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), span);
        setLayoutManager(gridLayoutManager);
    }

    public void setFlexBoxLayoutManager() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext(), FlexDirection.ROW);
        setLayoutManager(flexboxLayoutManager);
    }

    public void setFlexBoxLayoutManager(@FlexDirection int flexDirection) {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext(), flexDirection);
        setLayoutManager(flexboxLayoutManager);
    }

    public void startShimmer(int shimmerSize) {
        if (data == null) {
            throw new IllegalArgumentException("Please construct your empty object extended from EzBaseData into the function #setData(data) or you can use #startShimmer(size, data)");
        }
        if (isFirstLoadEzRecyclerView) {
            this.startShimmerSize = baseAdapter.getItemCount();
            this.endShimmerSize = shimmerSize;
            this.tempShimmerSize = shimmerSize;
            final List<Data> shimmer = new ArrayList<>();
            for (int i = 0; i < shimmerSize; i++) {
                data.setEzViewType(EzViewType.SHIMMER_EFFECT);
                shimmer.add(data);
            }
            addAll(shimmer);
            flagEzRecyclerViewFirstLoadDone();
            flagOnLoading();
        }
    }

    public void startShimmer(int shimmerSize, Data data) {
        if (isFirstLoadEzRecyclerView) {
            this.data = data;
            this.startShimmerSize = baseAdapter.getItemCount();
            this.endShimmerSize = shimmerSize;
            this.tempShimmerSize = shimmerSize;
            final List<Data> shimmer = new ArrayList<>();
            for (int i = 0; i < shimmerSize; i++) {
                data.setEzViewType(EzViewType.SHIMMER_EFFECT);
                shimmer.add(data);
            }
            addAll(shimmer);
            flagEzRecyclerViewFirstLoadDone();
            flagOnLoading();
        }
    }

    public void hideShimmer() {
        if (endShimmerSize > 0) {
            baseAdapter.removeRange(startShimmerSize, (endShimmerSize + startShimmerSize));
            endShimmerSize = 0;
        }
        resetShimmer();
        flagCompleteLoading();
    }

    private void settingAnimator() {
    }

    public void add(@NonNull Data data) {
        baseAdapter.add(data);
    }

    public void addAll(@NonNull List<Data> dataList) {
        baseAdapter.addAll(dataList);
    }

    public void replace(@NonNull Data data) {
        baseAdapter.replace(data);
    }

    public void replaceAll(@NonNull List<Data> dataList) {
        removeAllViews();
        baseAdapter.replaceAll(dataList);
    }

    public void remove(int position) {
        removeViewAt(position);
        baseAdapter.remove(position);
    }

    public void remove(@NonNull Data data) {
        baseAdapter.remove(data);
    }

    public void removeAll() {
        removeAllViews();
        baseAdapter.removeAll();
    }

    public void refresh() {
        baseAdapter.refresh();
    }

    public void resetAllViewsAndShimmer() {
        offset = 0;
        limit = 0;
        endShimmerSize = tempShimmerSize;
        flagOnLoading();
        flagEzRecyclerViewFirstLoad();
        removeAll();
        removeAllViews();
    }

    public void resetShimmer() {
        flagEzRecyclerViewFirstLoad();
        endShimmerSize = tempShimmerSize;
    }

    public void setEzPaginationListener(EzPaginationListener ezPaginationListener) {
        addOnScrollListener(onScrollListener);
        this.ezPaginationListener = ezPaginationListener;
        this.offset = 0;
        this.limit = 0;
        this.currentPage = 0;
    }

    public void setEzPaginationListener(int initialLimit, int initialOffset, EzPaginationListener ezPaginationListener) {
        addOnScrollListener(onScrollListener);
        this.ezPaginationListener = ezPaginationListener;
        this.offset = initialOffset;
        this.limit = initialLimit;
    }

    private final OnScrollListener onScrollListener = new OnScrollListener() {

        int pastVisibleItems, visibleItemCount, totalItemCount;

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int horizontalScrollScore, int verticalScrollScore) {
            if (verticalScrollScore > 0) {
                LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (mLayoutManager == null) return;

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                } else if (layoutManager instanceof FlexboxLayoutManager) {
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (mLayoutManager == null) return;

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                }

                if (!isRecyclerViewLoading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        isRecyclerViewLoading = true;
                        offset += limit;
                        currentPage++;
                        ezPaginationListener.onNext(limit, offset, currentPage);
                    }
                }
            } else if (horizontalScrollScore > 0) {
                LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (mLayoutManager == null) return;

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                } else if (layoutManager instanceof FlexboxLayoutManager) {
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (mLayoutManager == null) return;

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                }

                if (!isRecyclerViewLoading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        isRecyclerViewLoading = true;
                        offset += limit;
                        currentPage++;
                        ezPaginationListener.onNext(limit, offset, currentPage);
                    }
                }
            }
        }
    };

    private void flagEzRecyclerViewFirstLoad() {
        isFirstLoadEzRecyclerView = true;
    }

    private void flagEzRecyclerViewFirstLoadDone() {
        isFirstLoadEzRecyclerView = false;
    }

    public void flagCompleteLoading() {
        isRecyclerViewLoading = false;
    }

    public void flagOnLoading() {
        isRecyclerViewLoading = true;
    }

    public void setCustomShimmerLayout(@LayoutRes int layoutShimmer, @IdRes int shimmerViewId) {
        ezMultipleLayout.setCustomShimmerLayout(layoutShimmer, shimmerViewId);
    }

    public void setLayoutAdUnit(@LayoutRes int layoutAdUnit) {
        ezMultipleLayout.setLayoutAdUnit(layoutAdUnit);
    }

    public void setLayoutLoading(@LayoutRes int layoutLoading) {
        ezMultipleLayout.setLayoutLoading(layoutLoading);
    }

    public void setLayout1(@LayoutRes int layout1) {
        ezMultipleLayout.setLayout1(layout1);
    }

    public void setLayout2(@LayoutRes int layout2) {
        ezMultipleLayout.setLayout2(layout2);
    }

    public void setLayout3(@LayoutRes int layout3) {
        ezMultipleLayout.setLayout3(layout3);
    }

    public void setLayout4(@LayoutRes int layout4) {
        ezMultipleLayout.setLayout4(layout4);
    }

    public void setLayout5(@LayoutRes int layout5) {
        ezMultipleLayout.setLayout5(layout5);
    }

    public void setLayout6(@LayoutRes int layout6) {
        ezMultipleLayout.setLayout6(layout6);
    }

    public void setLayout7(@LayoutRes int layout7) {
        ezMultipleLayout.setLayout7(layout7);
    }

    public interface EzPaginationListener {
        void onNext(int limit, int newOffset, int currentPage);
    }

    public interface Listener<Obj extends EzBaseData> {
        void setDataOnViewHolder(@NonNull View itemView, @NonNull Obj data);
    }

    private final static class BaseAdapter<Data extends EzBaseData> extends Adapter<BaseViewHolder> {

        private final static class ListData<Data> extends ArrayList<Data> {
            @Override
            protected void removeRange(int fromIndex, int toIndex) {
                if (toIndex > fromIndex && this.size() >= toIndex) {
                    super.removeRange(fromIndex, toIndex);
                }
            }
        }

        private final ListData<Data> dataList;
        private Listener<Data> listener;
        private EzMultipleLayout ezMultipleLayout;

        public BaseAdapter(
                @NonNull EzMultipleLayout ezMultipleLayout
        ) {
            this.dataList = new ListData<>();
            this.ezMultipleLayout = ezMultipleLayout;
        }

        public void setListener(Listener<Data> listener) {
            this.listener = listener;
        }

        public void setEzMultipleLayout(EzMultipleLayout ezMultipleLayout) {
            this.ezMultipleLayout = ezMultipleLayout;
        }


        public void add(@NonNull Data data) {
            this.dataList.add(data);
            notifyDataSetChanged();
        }

        public void addAll(@NonNull List<Data> dataList) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }

        public void replaceAll(@NonNull List<Data> dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }

        public void replace(@NonNull Data data) {
            this.dataList.remove(data);
            this.dataList.add(data);
            notifyDataSetChanged();
        }

        public void remove(@NonNull int position) {
            this.dataList.remove(position);
            notifyDataSetChanged();
        }

        public void remove(@NonNull Data data) {
            this.dataList.remove(data);
            notifyDataSetChanged();
        }

        public int getPosition(@NonNull Data data) {
            return this.dataList.lastIndexOf(data);
        }

        public void removeAll() {
            this.dataList.clear();
            notifyDataSetChanged();
        }

        public void removeRange(int min, int max) {
            if (dataList.size() > max) {
                this.dataList.removeRange(min, max);
                notifyItemRangeRemoved(min, max);
            }
        }

        public void refresh() {
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            Data data = dataList.get(position);

            // Check ViewType If Available
            if (data.getEzViewType() == EzViewType.LAYOUT_1) {
                return EzViewType.LAYOUT_1.getViewType();
            } else if (data.getEzViewType() == EzViewType.LAYOUT_2) {
                return EzViewType.LAYOUT_2.getViewType();
            } else if (data.getEzViewType() == EzViewType.LAYOUT_3) {
                return EzViewType.LAYOUT_3.getViewType();
            } else if (data.getEzViewType() == EzViewType.LAYOUT_4) {
                return EzViewType.LAYOUT_4.getViewType();
            } else if (data.getEzViewType() == EzViewType.LAYOUT_5) {
                return EzViewType.LAYOUT_5.getViewType();
            } else if (data.getEzViewType() == EzViewType.LAYOUT_6) {
                return EzViewType.LAYOUT_6.getViewType();
            } else if (data.getEzViewType() == EzViewType.LAYOUT_7) {
                return EzViewType.LAYOUT_7.getViewType();
            } else if (data.getEzViewType() == EzViewType.AD_UNIT) {
                return EzViewType.AD_UNIT.getViewType();
            } else if (data.getEzViewType() == EzViewType.LOADING) {
                return EzViewType.LOADING.getViewType();
            }

            // Default
            return EzViewType.SHIMMER_EFFECT.getViewType();
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(hasStableIds);
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Populate condition by view type
            if (viewType == EzViewType.LAYOUT_1.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayout1());
            } else if (viewType == EzViewType.LAYOUT_2.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayout2());
            } else if (viewType == EzViewType.LAYOUT_3.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayout3());
            } else if (viewType == EzViewType.LAYOUT_4.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayout4());
            } else if (viewType == EzViewType.LAYOUT_5.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayout5());
            } else if (viewType == EzViewType.LAYOUT_6.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayout6());
            } else if (viewType == EzViewType.LAYOUT_7.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayout7());
            } else if (viewType == EzViewType.AD_UNIT.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayoutAdUnit());
            } else if (viewType == EzViewType.LOADING.getViewType()) {
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getLayoutLoading());
            } else {
                // Default
                return BaseViewHolder.newInstance(parent, ezMultipleLayout.getCustomShimmerLayout());
            }
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            final Data data = dataList.get(position);
            listener.setDataOnViewHolder(holder.itemView, data);
            startShimmerItem(holder, data);
        }

        private boolean startShimmerItem(BaseViewHolder holder, Data data) {
            if (data.isCustomShimmerLayout() && ezMultipleLayout.getShimmerViewId() > 0) {
                ShimmerFrameLayout shimmerEffect = holder.itemView.findViewById(ezMultipleLayout.getShimmerViewId());
                shimmerEffect.startShimmer();
                return true;
            }
            return false;
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public interface Listener<Obj extends EzBaseData> {
            void setDataOnViewHolder(@NonNull View itemView, Obj data);
        }

    }

    private final static class BaseViewHolder extends ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public static BaseViewHolder newInstance(@NonNull ViewGroup parent, @LayoutRes int layout) {
            try {
                View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                return new BaseViewHolder(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new IllegalArgumentException("No layout found, please check your multiple layouts viewType and data! Please to implement setLayout(); method properly");
        }
    }
}
