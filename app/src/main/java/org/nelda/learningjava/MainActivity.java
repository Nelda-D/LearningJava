package org.nelda.learningjava;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import org.apache.http.impl.cookie.RFC2965CommentUrlAttributeHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class MainActivity extends Activity {

    private MenuDrawer mMenuDrawer;
    private ListView mMenu;
    private List<ViewInfo> list;
    private InputStream inputStream;
    private StringBuffer stringBuffer;
    private static int mPosition = 0 ;

    private WebView mWebView;
    //private TextView mTextView_Title_Head;
    //private TextView mTextView_Num_Head;
    //private TextView mTextView_Author_Head;
   // private TextView mTextView_Website_Head;
   // private static String mImageSrc;
    private TextView mTextView_Write_Time;
    private static String mButton_Url = "http://v.youku.com/v_show/id_XNjg0NjE3Mjg4.html";
    private Button mButton_Play;
    private TextView mTextView_Title_Content;
    private TextView mTextView_Text_Content;
    //private LinearLayout mLinearLayout_bg;
    private Animation translate ;
    private LayoutAnimationController layoutAnimationController;
    private LinearLayout mLinearLayoutContent;
    private String html = "<html><head></head><body><div>"
                    +"<iframe height=250 width=310 src=\"http://player.youku.com/embed/XNjg0NjE3Mjg4\" frameborder=0 allowfullscreen></iframe>"
                    +"</div></body></html>";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new JSONObjectParse().parseJSON(getJsonString());

        LayoutInflater layoutInflater =this.getLayoutInflater();
        View menuview = layoutInflater.inflate(R.layout.menu_layout,null);
        mMenu = (ListView)menuview.findViewById(R.id.listview_menu);
        mMenu.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, new MenuData().getMenuData()));
        String[] array = new MenuData().getMenuData();
        mMenuDrawer = MenuDrawer.attach(this,MenuDrawer.Type.BEHIND, Position.START,MenuDrawer.MENU_DRAG_CONTENT);
        mMenuDrawer.setMenuView(menuview);
        mMenuDrawer.setContentView(R.layout.ui_content);
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        mMenuDrawer.setSlideDrawable(R.drawable.ic_drawer);
        mMenuDrawer.setDrawerIndicatorEnabled(true);

        mTextView_Write_Time = (TextView)findViewById(R.id.write_time);
        mTextView_Title_Content = (TextView)findViewById(R.id.title_content);
        mTextView_Text_Content = (TextView)findViewById(R.id.text_content);

        mWebView = (WebView)findViewById(R.id.webview_video);
        mWebView.loadDataWithBaseURL(null, html, "text/html", "uft-8", null);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());


        mLinearLayoutContent = (LinearLayout)findViewById(R.id.linearlayout_content);
        translate = AnimationUtils.loadAnimation(this,R.anim.translate_anim);
        layoutAnimationController = new LayoutAnimationController(translate);

        mButton_Play = (Button)findViewById(R.id.button_play);
        mButton_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mButton_Url));
                startActivity(intent);
            }
        });

        mMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                mLinearLayoutContent.setLayoutAnimation(layoutAnimationController);
                mLinearLayoutContent.startLayoutAnimation();
                mMenuDrawer.toggleMenu();
                updateUI(position);
            }
        });
        if(savedInstanceState != null){
            mPosition = savedInstanceState.getInt("OLD_DATA");
            updateUI(mPosition);
        }

    }

    //读取data.txt的数据返回字符串数据
    private String getJsonString(){
        inputStream = getResources().openRawResource(R.raw.data);
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String line ="";
            stringBuffer = new StringBuffer("");

            while((line = reader.readLine()) !=null){
                 stringBuffer.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    //更新UI控件
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateUI(int i){
        reseltWebView();
        mWebView.loadDataWithBaseURL(null,list.get(i).getHtmlString(),"text/html","uft-8",null);
        mTextView_Write_Time.setText(list.get(i).getWriteTime());
        mButton_Url = list.get(i).getButtonUrl();
        mTextView_Title_Content.setText(list.get(i).getTitleContent());
        mTextView_Text_Content.setText(list.get(i).getTextContent());
    }

    private void reseltWebView(){
        mWebView = null;
        mWebView = (WebView)findViewById(R.id.webview_video);
        mWebView.loadDataWithBaseURL(null, html, "text/html", "uft-8", null);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("OLD_DATA",mPosition);
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
        if (id == android.R.id.home) {
            mMenuDrawer.toggleMenu();
            return true;
        }
        switch (item.getItemId()){
            case android.R.id.home:
                mMenuDrawer.toggleMenu();
                break;
            case R.id.action_about:
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if(drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING ){
            mMenuDrawer.closeMenu();
            return ;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mWebView.pauseTimers();
        Log.e("--------------->", "onPause()");
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mWebView.resumeTimers();
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Log.e("--------------->", "onResume()");
    }
}
