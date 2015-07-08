package com.godhc.gifsy;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.godhc.gifsy.adapters.PopularTagsAdapter;
import com.godhc.gifsy.api.AllTagsApi;
import com.godhc.gifsy.api.PopularTagsApi;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.models.PopularTag;
import com.orhanobut.logger.Logger;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PopularTagsAdapter popularTagsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_rv_popularTags);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        popularTagsAdapter = new PopularTagsAdapter(this, null);
        recyclerView.setAdapter(popularTagsAdapter);

        recyclerView.setHasFixedSize(false);

        loadData();

        AllTagsApi allTagsApi = new AllTagsApi(this);
        allTagsApi.getAllTags(new AllTagsApi.AllTagsDataLoadedListener() {
            @Override
            public void onAllTagsDataLoaded(List<String> allTags, ApplicationError applicationError) {
                if (applicationError == null) {
                    Logger.d("Got all tags (%d)", allTags.size());
                } else {
                    Logger.e(applicationError.getMessage());
                }

            }
        });
        SimpleDraweeView mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.main_image);

        Uri uri;
        uri = Uri.parse("http://i.imgur.com/Io3s81m.gif");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)

                .build();
        mSimpleDraweeView.setController(controller);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void loadData() {
        PopularTagsApi popularTagsApi = new PopularTagsApi(this);
        popularTagsApi.getPopularTags(new PopularTagsApi.PopularTagsDataLoadedListener() {
            @Override
            public void onPopularTagsDataLoaded(List<PopularTag> popularTags, ApplicationError applicationError) {
                if (applicationError == null) {
                    popularTagsAdapter.setData(popularTags);
                } else {
                    Logger.e(applicationError.getMessage());
                    Logger.e(applicationError.getInternalMessage());
                    // TODO: Show error on ui
                }
            }
        });
    }
}
