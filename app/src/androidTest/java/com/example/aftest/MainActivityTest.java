package com.example.aftest;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.robotium.solo.By;
import com.robotium.solo.Solo;

/**
 * Created by sahasra on 6/21/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testMainActivity() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testCase() throws Exception {

        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.my_recycler_view);
        solo.waitForView(recyclerView);

        View view = recyclerView.getChildAt(0);

        ImageView img0 = (ImageView) solo.getView(R.id.image);
        assertNotNull(img0);
        solo.waitForView(img0);

        TextView textView0 = (TextView) view.findViewById(R.id.name);
        solo.waitForText(String.valueOf(textView0));
        assertEquals(String.valueOf(textView0.getText()), String.valueOf("Shorts Starting at $25"));

        TextView textView1 = (TextView) view.findViewById(R.id.address);
        solo.waitForText(String.valueOf(textView1));
        assertEquals(String.valueOf(textView1.getText()),String.valueOf("GET READY FOR SUMMER DAYS"));

        View view1 = recyclerView.getChildAt(1);

        ImageView img1 = (ImageView) solo.getView(R.id.image);
        assertNotNull(img1);
        solo.waitForView(img1);

        TextView textView2 = (TextView) view1.findViewById(R.id.name);
        solo.waitForText(String.valueOf(textView2));
        assertEquals(String.valueOf(textView2.getText()), String.valueOf("Dolce Vita"));

        TextView textView3 = (TextView) view1.findViewById(R.id.address);
        solo.waitForText(String.valueOf(textView3));
        assertEquals(String.valueOf(textView3.getText()), String.valueOf("Our Favorite Brands"));
//      solo.clickInRecyclerView(0);
//      solo.assertCurrentActivity("abcd",PromotionDetailsActivity.class);
//      solo.waitForActivity(PromotionDetailsActivity.class);
        solo.clickOnView(view);
        solo.waitForActivity(PromotionDetailsActivity.class);
        solo.assertCurrentActivity("abcd", PromotionDetailsActivity.class);

        TextView tv2 = (TextView) solo.getView(R.id.promotion_details_header);
        solo.waitForText(String.valueOf(tv2));
        assertEquals(String.valueOf(tv2.getText()), String.valueOf("Promotion Details:"));

        ImageView img2 = (ImageView) solo.getView(R.id.promotion_image);
        assertNotNull(img2);
        solo.waitForView(img2);

        TextView tv3 = (TextView) solo.getView(R.id.promotion_details);
        solo.waitForText(String.valueOf(tv3));
        assertEquals(String.valueOf(tv3.getText()), String.valueOf("Title :Shorts Starting at $25\\n\" +\n" +
                "                \"Description :GET READY FOR SUMMER DAYS\\n\" +\n" +
                "                \"Footer :In stores & online. Exclusions apply. See details\n"));

//        if (solo.searchButton("Submit", true)) {
//            Button btn = solo.getButton("Submit");
//            if (btn.isClickable()) {
        solo.clickOnButton(0);
        solo.waitForWebElement(By.id(String.valueOf(R.id.web_view)));
        solo.goBackToActivity(String.valueOf(PromotionDetailsActivity.class.getSimpleName()));

        solo.assertCurrentActivity("asdf",PromotionDetailsActivity.class);
        solo.waitForActivity(PromotionDetailsActivity.class);
        solo.goBackToActivity(String.valueOf(MainActivity.class.getSimpleName()));

        solo.clickOnView(view1);
        solo.waitForActivity(PromotionDetailsActivity.class.getName());
        solo.assertCurrentActivity("abcd", PromotionDetailsActivity.class);
//        RecyclerView recyclerView1= (RecyclerView) solo.getView(R.id.my_recycler_view);
//        solo.waitForView(recyclerView1);
//        View v=recyclerView1.getChildAt(0);
        TextView tv4 = (TextView) solo.getView(R.id.promotion_details_header);
        solo.waitForText(String.valueOf(tv4));
        assertEquals(String.valueOf(tv4.getText()), String.valueOf("Promotion Details:"));

        ImageView img3 = (ImageView) solo.getView(R.id.promotion_image);
        assertNotNull(img3);
        solo.waitForView(img3);

        TextView tv5 = (TextView) solo.getView(R.id.promotion_details);
        solo.waitForText(String.valueOf(tv5));
        assertEquals(String.valueOf(tv5.getText()), String.valueOf("Title :Dolce Vita\n" +
                "Description :Our Favorite Brands\n" +
                "Footer :null\n"));

        solo.clickOnButton(0);
        solo.waitForWebElement(By.id(String.valueOf(R.id.web_view)));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
