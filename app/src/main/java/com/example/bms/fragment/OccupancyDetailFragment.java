package com.example.bms.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bms.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class OccupancyDetailFragment extends Fragment {

    private ProgressDialog mprogressDialog;
    private WebView wv_OccupancyDetile;
    private SwipeRefreshLayout mswipeRefreshLayout;
    private String token;


    //url
    private String pageUrl = "http://portal-bams.mncgroup.com:8008/occupancydetail?token=";

    private String DEFAULT_ERROR_PAGE_PATH = "file:///android_asset/html/colorlib_error_404_10/index.html";

    private static final String TAG = "WebViewCustomization";

    public OccupancyDetailFragment(String token) {
        this.token = token;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_occupancy_detile, container, false);

        //this.token = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE).getString("x", "");

        wv_OccupancyDetile = (WebView) view.findViewById(R.id.wv_OccupancyDetile);

        SwipeRefresh(view);

        LoadWeb();

        return view;
    }

    //refresh
    private void SwipeRefresh(View view) {

        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadWeb();
            }
        });
    }

    //load Web
    private void LoadWeb() {

        wv_OccupancyDetile.getSettings().setJavaScriptEnabled(true);
        wv_OccupancyDetile.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_OccupancyDetile.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_OccupancyDetile.getSettings().setDomStorageEnabled(true);
        wv_OccupancyDetile.getSettings().setAppCacheEnabled(true);
        wv_OccupancyDetile.getSettings().setSupportZoom(true);
        wv_OccupancyDetile.getSettings().setSupportMultipleWindows(true);
        mprogressDialog = ProgressDialog.show(getActivity(), "", "Please wait for a moment...");
        wv_OccupancyDetile.loadUrl(pageUrl+token);
        //wbSummry.addJavascriptInterface(new SimpleWebJavascriptInterface(getActivity()), "Android");
        mswipeRefreshLayout.setRefreshing(true);
        mswipeRefreshLayout.setColorSchemeResources(R.color.greenPrimary, R.color.yellowPrimary, R.color.redPrimary, R.color.blueSecondary);
        wv_OccupancyDetile.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                wv_OccupancyDetile.loadUrl(DEFAULT_ERROR_PAGE_PATH);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (mprogressDialog.isShowing()) {
                    mprogressDialog.dismiss();
                }

                //clear cache
                wv_OccupancyDetile.clearCache(true);

                mswipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}
