package com.godhc.gifsy.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.godhc.gifsy.adapters.GifsAdapter;
import com.godhc.gifsy.api.SearchByTagApi;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.models.TagResponse;
import com.godhc.gifsy.utlis.Constants.*;

import com.godhc.gifsy.R;
import com.orhanobut.logger.Logger;

public class TagGifsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GifsAdapter gifsAdapter;
    TagResponse gifData;
    LinearLayoutManager layoutManager;
    private boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_gifs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_tag_gifs_toolbar_main);
        setSupportActionBar(toolbar);

        final String selectedTag = getIntent().getExtras().getString(GlobalConstants.ACTIVITY_TAG_GIFS_TAG_NAME, "cool");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(selectedTag);
        }

        recyclerView = (RecyclerView) findViewById(R.id.activity_tag_gifs_rv_gifs);
        recyclerView.setHasFixedSize(false);

        gifsAdapter = new GifsAdapter(this, null);
        recyclerView.setAdapter(gifsAdapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                        if (gifData.getPageCurrent() + 1 <= gifData.getPageCount()){
                            Logger.d("Loading more data with page %d of %d ", gifData.getPageCurrent() + 1, gifData.getPageCount());
                            loadData(selectedTag, gifData.getPageCurrent() + 1, true);

                        }
                        else
                            loading = true;

                    }
                }
            }
        });

        // Get the first page of data
        loadData(selectedTag, 1, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tag_gifs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData(String selectedTag, final int page, final boolean appendData){
        loading = true;
        SearchByTagApi searchByTagApi = new SearchByTagApi(this);
        searchByTagApi.searchGifByTag(selectedTag, page, new SearchByTagApi.SearchGifByTagResponseListener() {
            @Override
            public void onSearchGifByTagResponse(TagResponse tagResponse, ApplicationError applicationError) {
                if (applicationError == null) {
                    gifData = tagResponse;
                    if (!appendData)
                        gifsAdapter.setData(gifData.getGifInfoList());
                    else
                        gifsAdapter.appendData(gifData.getGifInfoList());

                    loading = false;
                    //showToast("Page " + page + " of " + tagResponse.getPageCount());
                    Logger.d("Total gifs = %d; page count = %d; current page = %d ", tagResponse.getGifCount(), tagResponse.getPageCount(), tagResponse.getPageCurrent());
                } else {
                    Logger.e("id = %s; Error Message: %s, Internal Error Message: ", applicationError.getErrorId(),
                            applicationError.getMessage(),
                            applicationError.getInternalMessage());

                    loading = false;
                }

            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }
}
