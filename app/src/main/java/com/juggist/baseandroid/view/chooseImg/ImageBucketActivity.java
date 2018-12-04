package com.juggist.baseandroid.view.chooseImg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.view.chooseImg.adapter.ImageBucketAdapter;
import com.juggist.baseandroid.view.chooseImg.model.ImageBucket;
import com.juggist.baseandroid.view.chooseImg.ustils.AlbumHelper;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Observable;


public class ImageBucketActivity extends BackBaseActivity {
	// ArrayList<Entity> dataList;//用来装载数据源的列表
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;// 自定义的适配器
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;

	private Observable<String> finishEvent;

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	protected int getLayoutId() {
		return R.layout.activity_image_bucket;
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		// /**
		// * 这里，我们假设已经从网络或者本地解析好了数据，所以直接在这里模拟了10个实体类，直接装进列表中
		// */
		// dataList = new ArrayList<Entity>();
		// for(int i=-0;i<10;i++){
		// Entity entity = new Entity(R.drawable.picture, false);
		// dataList.add(entity);
		// }
		dataList = helper.getImagesBucketList(false);	
		bimap= BitmapFactory.decodeResource(
				getResources(),
				R.drawable.add_pic);

		adapter = new ImageBucketAdapter(ImageBucketActivity.this, dataList);
		gridView.setAdapter(adapter);
	}

	/**
	 * 初始化view视图
	 */
	public void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
	}

	@Override
	protected void initListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(ImageBucketActivity.this,
						ImageGridActivity.class);
				intent.putExtra(ImageBucketActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				startActivity(intent);
			}

		});
	}

	@Override
	protected String getTitile() {
		return getResources().getString(R.string.album);
	}
}
