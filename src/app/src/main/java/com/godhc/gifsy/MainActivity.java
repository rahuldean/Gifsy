package com.godhc.gifsy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.godhc.gifsy.adapters.MainSectionPagerAdapter;
import com.godhc.gifsy.api.AllTagsApi;
import com.godhc.gifsy.api.SearchByTagApi;
import com.godhc.gifsy.models.ApplicationError;
import com.godhc.gifsy.models.TagResponse;
import com.orhanobut.logger.Logger;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activityMainToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.activityMainTabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new MainSectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);


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

        SearchByTagApi searchByTagApi = new SearchByTagApi(this);
        searchByTagApi.searchGifByTag("smile", 2, new SearchByTagApi.SearchGifByTagResponseListener() {
            @Override
            public void onSearchGifByTagResponse(TagResponse tagResponse, ApplicationError applicationError) {
                if (applicationError == null) {
                    Logger.d("Total gifs = %d; page count = %d; current page = %d ", tagResponse.getGifCount(), tagResponse.getPageCount(), tagResponse.getPageCurrent());
                } else {
                    Logger.e("id = %s; Error Message: %s, Internal Error Message: ", applicationError.getErrorId(),
                            applicationError.getMessage(),
                            applicationError.getInternalMessage());
                }

            }
        });

        //SimpleDraweeView mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.main_image);

//        Uri uri;
//        uri = Uri.parse("http://i.imgur.com/Io3s81m.gif");
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setAutoPlayAnimations(true)
//                .build();
//
//        mSimpleDraweeView.setController(controller);
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
}
