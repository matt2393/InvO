package com.matt2393.invo.Vista.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.matt2393.invo.R;
import com.matt2393.invo.Vista.Adapters.ViewPagerAdapter;
import com.matt2393.invo.Vista.Fragments.ColasFragment;
import com.matt2393.invo.Vista.Fragments.InventariosFragment;
import com.matt2393.invo.Vista.Fragments.JuegosFragment;
import com.matt2393.invo.Vista.Fragments.TomaDecFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BubbleTabBar bubbleTabBar;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=findViewById(R.id.viewpager_main);
        bubbleTabBar=findViewById(R.id.bubbleTabBar_main);



        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(TomaDecFragment.newInstance());
        viewPagerAdapter.addFragment(JuegosFragment.newInstance());
        viewPagerAdapter.addFragment(InventariosFragment.newInatance());
        viewPagerAdapter.addFragment(ColasFragment.newInstance());

        viewPager.setAdapter(viewPagerAdapter);

        bubbleTabBar.setupBubbleTabBar(viewPager);

        bubbleTabBar.addBubbleListener(i -> {
            switch (i){
                case R.id.item_decision:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.item_juegos:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.item_inventario:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.item_colas:
                    viewPager.setCurrentItem(3);
                    break;
            }
        });
    }
}
