package com.ramanaptr.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * EzRecyclerView
 * Created by ramanaptr
 */
public class EzRecyclerView<Data extends Serializable> extends RecyclerView {

    private final EzShimmerEffect<Data> customShimmer = new EzShimmerEffect<>(getContext());
    private BaseAdapter<Data> baseAdapter;
    private Listener<Data> listener;

    public EzRecyclerView(@NonNull Context context) {
        super(context);
    }

    public EzRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EzRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public void setViewHolderLayout(@LayoutRes int layout, @NonNull List<Data> dataList, @NonNull Listener<Data> listener) {
        this.listener = listener;
        baseAdapter = new BaseAdapter<>(listener::setDataOnViewHolder, layout);
        baseAdapter.setHasStableIds(true);
        setAdapter(baseAdapter);
        settingAnimator();
        replaceAll(dataList);
    }

    public void setViewHolderLayout(@LayoutRes int layout, @NonNull Listener<Data> listener) {
        this.listener = listener;
        baseAdapter = new BaseAdapter<>(listener::setDataOnViewHolder, layout);
        baseAdapter.setHasStableIds(true);
        setAdapter(baseAdapter);
        settingAnimator();
    }

    public void setViewHolderLayout(@LayoutRes int[] layouts, @NonNull Listener<Data> listener) {
        if (layouts.length >= 3) {
            throw new IllegalArgumentException("Layouts maximal is 2");
        }

        this.listener = listener;
        baseAdapter = new BaseAdapter<>(listener::setDataOnViewHolder, layouts);
        baseAdapter.setHasStableIds(true);
        setAdapter(baseAdapter);
        settingAnimator();
    }

    public void setViewHolderLayout(@LayoutRes int[] layouts, @NonNull List<Data> dataList, @NonNull Listener<Data> listener) {
        if (layouts.length >= 3) {
            throw new IllegalArgumentException("Layouts maximal is 2");
        }

        this.listener = listener;
        baseAdapter = new BaseAdapter<>(listener::setDataOnViewHolder, layouts);
        baseAdapter.setHasStableIds(true);
        setAdapter(baseAdapter);
        settingAnimator();
        replaceAll(dataList);
    }

    private void settingAnimator() {
//        final SimpleItemAnimator simpleItemAnimator = ((SimpleItemAnimator) getItemAnimator());
//        if (simpleItemAnimator != null) {
//            simpleItemAnimator.setSupportsChangeAnimations(false);
//        }
//        setItemAnimator(new Slide());
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

    public void startShimmer(@NonNull int counts) {
        setViewHolderLayout(R.layout.default_shimmer_ez_recyclerview, (itemView, data) -> {
        });
        customShimmer.startShimmer(this, counts, R.layout.default_shimmer_ez_recyclerview);
    }

    public void startShimmer(@NonNull int counts, @LayoutRes int shimmerLayout, @NonNull int shimmerViewId) {
        setViewHolderLayout(shimmerLayout, (itemView, data) -> {
        });

        customShimmer.startShimmer(this, counts, shimmerLayout, shimmerViewId);
    }

    public interface Listener<Obj extends Serializable> {
        void setDataOnViewHolder(@NonNull View itemView, @NonNull Obj data);
    }

    private final static class BaseAdapter<Data extends Serializable> extends Adapter<BaseViewHolder> {

        private final Listener<Data> listener;
        private final List<Data> dataList;
        private int layout;
        private int[] layouts;

        public BaseAdapter(
                @NonNull Listener<Data> listener,
                @LayoutRes int layout
        ) {
            this.dataList = new ArrayList<>();
            this.listener = listener;
            this.layout = layout;
        }

        public BaseAdapter(
                @NonNull Listener<Data> listener,
                @NonNull int[] layouts
        ) {
            this.dataList = new ArrayList<>();
            this.listener = listener;
            this.layouts = layouts;
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

        public void refresh() {
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2 * 2;
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(hasStableIds);
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (layouts == null) {
                return BaseViewHolder.newInstance(parent, layout);
            }

            switch (viewType) {
                case 0:
                    return BaseViewHolder.newInstance(parent, layouts[0]);
                case 2:
                    return BaseViewHolder.newInstance(parent, layouts[1]);
                default:
                    return new BaseViewHolder(new View(parent.getContext()));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            final Data data = dataList.get(position);
            listener.setDataOnViewHolder(holder.itemView, data);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public interface Listener<Obj extends Serializable> {
            void setDataOnViewHolder(@NonNull View itemView, Obj data);
        }

    }

    private final static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public static BaseViewHolder newInstance(@NonNull ViewGroup parent, @LayoutRes int layout) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            return new BaseViewHolder(view);
        }
    }

}
