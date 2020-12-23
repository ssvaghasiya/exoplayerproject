package com.newproject.apputils


import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessListRecycler {

    private var isLock = false
    private var enable = true
    private var stackFromEnd = false
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var nestedScrollView: NestedScrollView? = null

    constructor(recyclerView: RecyclerView,linearLayoutManager: LinearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager
        this.recyclerView = recyclerView
        recyclerView.addOnScrollListener(onScrollListener)
    }

    constructor(nestedScrollView: NestedScrollView,linearLayoutManager: LinearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager
        //        this.recyclerView = recyclerView;
        this.nestedScrollView = nestedScrollView

        //        this.recyclerView.addOnScrollListener(onScrollListener);
        nestedScrollView.setOnScrollChangeListener(object : EndlessParentScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int,totalItemsCount: Int) {
                if (loadMoreListener != null) {
                    loadMoreListener!!.onLoadMore()
                }
            }
        })
    }

    internal var onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView,dx: Int,dy: Int) {
            super.onScrolled(recyclerView,dx,dy)

            when {
                enable -> {
                    when {
                        stackFromEnd -> {

                            firstVisibleItem = mLinearLayoutManager!!.findFirstVisibleItemPosition()
                            val loadMore = firstVisibleItem == 0

                            if (loadMore && !isLock && loadMoreListener != null) {
                                loadMoreListener!!.onLoadMore()
                            }

                        }
                        else -> {
                            visibleItemCount = recyclerView.childCount
                            totalItemCount = mLinearLayoutManager!!.itemCount
                            firstVisibleItem = mLinearLayoutManager!!.findFirstVisibleItemPosition()
                            val loadMore = firstVisibleItem + visibleItemCount >= totalItemCount

                            if (loadMore && !isLock && loadMoreListener != null) {
                                loadMoreListener!!.onLoadMore()
                            }
                        }
                    }

                    // is scrolled bottom
                    val firstVisibleItem = mLinearLayoutManager!!.findFirstVisibleItemPosition()

                    if (firstVisibleItem >= 1) {
                        //Show FAB
                        if (loadMoreListener != null) {
                            loadMoreListener!!.onToggleBackToTop(true)
                        }
                    } else {
                        //Hide FAB
                        if (loadMoreListener != null) {
                            loadMoreListener!!.onToggleBackToTop(false)
                        }
                    }
                }
            }
        }

    }

    internal var loadMoreListener: OnLoadMoreListener? = null


    fun setStackFromEnd(stackFromEnd: Boolean) {
        this.stackFromEnd = stackFromEnd
    }

    //    NestedScrollView.OnScrollChangeListener onNestedScrollListener = new NestedScrollView.OnScrollChangeListener() {
    //        @Override
    //        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    //            if (enable) {
    //                if (stackFromEnd) {
    //
    //                    firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
    //                    boolean loadMore = firstVisibleItem == 0;
    //
    //                    if (loadMore && !isLock) {
    //                        if (loadMoreListener != null) {
    //                            loadMoreListener.onLoadMore();
    //                        }
    //                    }
    //
    //                } else {
    //                    visibleItemCount = recyclerView.getChildCount();
    //                    totalItemCount = mLinearLayoutManager.getItemCount();
    //                    firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
    //                    boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
    //
    //                    if (loadMore && !isLock) {
    //                        if (loadMoreListener != null) {
    //                            loadMoreListener.onLoadMore();
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //
    //
    //    };


    fun lockMoreLoading() {
        isLock = true
    }

    fun releaseLock() {
        isLock = false
    }

    fun disableLoadMore() {
        enable = false
    }

    fun setOnLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }

    interface OnLoadMoreListener {
        fun onLoadMore()

        fun onToggleBackToTop(show: Boolean)
    }

}