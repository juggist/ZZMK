package com.juggist.jcore.view.chooseImg;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.juggist.jcore.base.BackBaseActivity;
import com.juggist.jcore.R;
import com.juggist.jcore.utils.ActivityUtils;
import com.juggist.jcore.view.chooseImg.adapter.ImageGridAdapter;
import com.juggist.jcore.view.chooseImg.config.Bimp;
import com.juggist.jcore.view.chooseImg.model.ImageItem;
import com.juggist.jcore.view.chooseImg.ustils.AlbumHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ImageGridActivity extends BackBaseActivity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	// ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
	AlbumHelper helper;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择"+Bimp.MAX_SIZE+"张图片", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};


	@Override
	protected int getLayoutId() {
		return R.layout.pickimg_activity_image_grid;
	}

	/**
	 * 鍒濆鍖杤iew瑙嗗浘
	 */
	public void initView() {


		tv_right.setVisibility(View.VISIBLE);
		tv_right.setText("完成");

		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));



	}

	@Override
	protected void initListener() {

		tv_right.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				if (Bimp.act_bool) {
//					Intent intent = new Intent(ImageGridActivity.this,
//							PublishActivity.class);
//					startActivity(intent);
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() < Bimp.MAX_SIZE) {
						Bimp.drr.add(list.get(i));
					}
				}
				ActivityUtils.finishAcitityBackStack(2);
			}

		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				/**
				 * 鏍规嵁position鍙傛暟锛屽彲浠ヨ幏寰楄窡GridView鐨勫瓙View鐩哥粦瀹氱殑瀹炰綋绫伙紝鐒跺悗鏍规嵁瀹冪殑isSelected鐘舵
				 * �锛� 鏉ュ垽鏂槸鍚︽樉绀洪�涓晥鏋溿� 鑷充簬閫変腑鏁堟灉鐨勮鍒欙紝涓嬮潰閫傞厤鍣ㄧ殑浠ｇ爜涓細鏈夎鏄�
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 閫氱煡閫傞厤鍣紝缁戝畾鐨勬暟鎹彂鐢熶簡鏀瑰彉锛屽簲褰撳埛鏂拌鍥�
				 */
				adapter.notifyDataSetChanged();
			}

		});


	}

	@Override
	protected void initData() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
			public void onListen(int count) {
				tv_right.setText("完成" + "(" + count + ")");
			}
		});
	}

	@Override
	protected String getTitile() {
		return getResources().getString(R.string.pickimg_album);
	}
}
