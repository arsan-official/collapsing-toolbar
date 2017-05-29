# collapsing-toolbar
collapsing toolbar android material design

<img src="http://i.makeagif.com/media/5-28-2017/mO8jb7.gif" />

## garis besar turorial 
1. tambahkan support design library pada gradle project 
```
  dependencies{
    ...
    compile 'com.android.support:design:25.3.1'
  }
```
2. buat layout structure seperti berikut: 
```
  CoordinatorLayout
  |___AppBarLayout
  |   |___CollapsingToolbarLayout
  |      |___ImageView
  |      |___Toolbar
  |___RecyclerView      
```
  keterangan: 
  * **Coordinator Layout**  
    A powerful FrameLayout that specifies behavior for child views for various interactions. It also allows anchoring of floating views in your layout.  
  * **AppBarLayout**  
    It is a special kind of vertical LinearLayout. It helps respond to its children’s scroll events (scroll gestures). Additionally, it’s responsible for implementing many features of Material Design’s AppBarLayout.  
  * **CollapsingToolbarLayout**  
  It is a Toolbar wrapper which makes the ‘Flexible Space’ pattern possible. It collapses the image header while decreasing the expanded title to a Toolbar title.  
  
3. Detail XML collapsing toolbar
```
  <...CoordinatorLayout>  
    <...AppBarLayout>  
      <android.support.design.widget.CollapsingToolbarLayout
            android:id="@+id/collapsing_toolbar"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:expandedTitleMarginStart="@dimen/activity_margin_content"
            app:layout_scrollFlags="scroll|exitUntilCollapsed|snap">
          
          <ImageView/>
          <Toolbar />
          
      </android.support.design.widget.CollapsingToolbarLayout />
   </...AppBarLayout>
 </..CoordinatorLayout>
```
4. gunakan Scrim agar text pada collapsing toolbar. Scrim adalah semi transparent gradient yang diperlukan sebagai gradasi pada sisi atas dan sisi bawah ImageView agar text pada collapsing toolbar menjadi lebih jelas. 
```
  <View 
    android:layout_width="match_parent" 
    android:layout_height="160dp" 
    android:layout_gravity="bottom"               
    android:background="@drawable/scrim"/>
```

drawable scrim.xml
```
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">
    <gradient
        android:angle="90"
        android:endColor="@android:color/transparent"
        android:startColor="#66000000"/>
</shape>
```

5. tempatkan FAB(FloatingActionButton) di pojok-kanan-bawah pada AppBarLayout: 
```
  <android.support.design.widget.FloatingActionButton
        ...
        app:layout_anchor="@+id/appbar"
        app:layout_anchorGravity="bottom|right|end" />
```
6. buat hidden action add
   agar FAB add pada collapsing button, pada saat ditutup(collapsed) tetap bisa diakses, tambahkan menu add pada options menu. Untuk melakukannya kita perlu listener berikut: **OffsetChangedListener**. 
   
 * implementasi OffsetChangedListener: 
 ```
  appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
 ```
 * **update toolbar menu**
 pertama-tama tambahkan menu default ke dalam toolbar dengan mengimplementasikan onCreateOptionsMenu()  
 ```
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_main, menu);
      collapsedMenu = menu;
      return true;
  }
 ```
 kemudian tambahkan menu add pada toolbar ketika collapsing toolbar tertutup. 
 ```
 @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            //collapsed
            collapsedMenu.add("Add")
                    .setIcon(R.drawable.ic_action_add)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            //expanded

        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }
 ```
 
 7. warna dinamis dengan ColorPalette API
 ketika collapsing toolbar tertutup, maka warna toolbar seharusnya mengikuti warna yang paling dominan dari gambar. Untuk melakukannya kita perlu menggunkan ColorPalette API dari android support library. Tambahkan dulu dependency ke dalam file build.gradle. 
 ```
dependencies {
    …
    compile 'com.android.support:palette-v7:25.0.2'
}
 ```
 kemudian untuk  menggunakannya seperti berikut: 
```
  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });
```
 ada beberapa pilihan warna variant palette seperti berikut:  
 * Light
 * Vibrant 
 * Dark
 * Muted 
 
 8. sumber original tutorial dijelaskan dengan sangat baik disini: http://blog.iamsuleiman.com/toolbar-animation-with-android-design-support-library/
 
