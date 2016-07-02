package com.yuen.xiuka.xiuquan;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuen.baselib.activity.BaseFragment;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.FaBuActivity;
import com.yuen.xiuka.activity.PingLunActivity;
import com.yuen.xiuka.beans.XIUQUANBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class XiuQuanFragment2 extends BaseFragment implements View.OnClickListener {
    public ListView mixlist;
    private MixListAdapter adapterData;
    private ArrayList<Mixinfo> data;
    private Context context;
    private TextView tv_fensi;
    private TextView tv_guanzhu;
    private TextView tv_renzheng;
    private TextView tv_name;
    private ImageView iv_user_icon;
    private RelativeLayout header;
    private ImageView iv_bj;
    private List<XIUQUANBean.DataBean> xiuquanBeanData;
    private int ImagaId[] = {R.id.img_0, R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7, R.id.img_8};
    private int windowwidth;
    private List<XIUQUANBean.DataBean.ImageBean> imageBeanList;
    private MyAdapter myAdapter;
    private List<XIUQUANBean.DataBean.ImageBean> imagepagerList;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private Button btn_jia;

    @Override
    public View initView() {
        context = getActivity();
        windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        View view = View.inflate(getActivity(), R.layout.layout_xiuquanfragment, null);
        mixlist = (ListView) view.findViewById(R.id.mixlist);
        btn_fanhui = (Button) view.findViewById(R.id.btn_fanhui);
        btn_jia = (Button) view.findViewById(R.id.btn_jia);
        tv_titlecontent = (TextView) view.findViewById(R.id.tv_titlecontent);
        btn_fanhui.setVisibility(View.GONE);
        btn_jia.setVisibility(View.VISIBLE);
        tv_titlecontent.setText("秀圈");
        header = (RelativeLayout) View.inflate(getActivity(), R.layout.layout_xiuquan_header, null);
        tv_fensi = (TextView) header.findViewById(R.id.tv_fensi);
        tv_guanzhu = (TextView) header.findViewById(R.id.tv_guanzhu);
        tv_renzheng = (TextView) header.findViewById(R.id.tv_renzheng);
        tv_name = (TextView) header.findViewById(R.id.tv_user_name);
        iv_user_icon = (ImageView) header.findViewById(R.id.iv_user_icon);
        iv_bj = (ImageView) header.findViewById(R.id.iv_bj);
        mixlist.addHeaderView(header);
        tv_fensi.setOnClickListener(this);
        tv_guanzhu.setOnClickListener(this);
        tv_renzheng.setOnClickListener(this);
        iv_user_icon.setOnClickListener(this);
        iv_bj.setOnClickListener(this);
        btn_jia.setOnClickListener(this);

        myAdapter = new MyAdapter();
        mixlist.setAdapter(myAdapter);
        mixlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanBeanData.get(position - 1));
                context.startActivity(intent);
            }
        });
        return view;
    }

    public void xiuquan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPUtil.getInt("uid") + "");
        map.put("page", 0 + "");
        XUtils.xUtilsPost(URLProvider.LOOK_CIRCLE, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                XIUQUANBean xiuquanBean = gson.fromJson(result, XIUQUANBean.class);
                String bj_image = xiuquanBean.getBj_image();
                xiuquanBeanData = xiuquanBean.getData();
                // Toast.makeText(context, URLProvider.BaseImgUrl + bj_image, Toast.LENGTH_SHORT).show();
                System.out.println(URLProvider.BaseImgUrl + bj_image);
                x.image().bind(iv_bj, URLProvider.BaseImgUrl + bj_image, MyApplication.options);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void initData() {


    }

    @Override
    public void onResume() {
        super.onResume();
        initheader();
        xiuquan();

    }

    public void initheader() {
        tv_fensi.setText("粉丝" + SPUtil.getString("fensi"));
        tv_guanzhu.setText("关注" + SPUtil.getString("guanzhu"));
        tv_name.setText(SPUtil.getString("name"));
        tv_renzheng.setText("认证平台" + SPUtil.getString("platform"));
        x.image().bind(iv_user_icon, URLProvider.BaseImgUrl + SPUtil.getString("icon"), MyApplication.options);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fensi:
                Toast.makeText(context, "tv_fens", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_guanzhu:
                Toast.makeText(context, "tv_guanzhu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_renzheng:
                Toast.makeText(context, "tv_renzheng", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_bj:
                Toast.makeText(context, "iv_bj", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_user_icon:
                Toast.makeText(context, "iv_user_icon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jia:
                startActivity(FaBuActivity.class);
                break;
        }
    }

    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return xiuquanBeanData == null ? 0 : xiuquanBeanData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.mix_view, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.add.setText(xiuquanBeanData.get(position).getAdd());
            viewHolder.time.setText(xiuquanBeanData.get(position).getTime());
            viewHolder.username.setText(xiuquanBeanData.get(position).getName());
            viewHolder.usercontent.setText(xiuquanBeanData.get(position).getContent());
            viewHolder.tv_dianzan.setText(xiuquanBeanData.get(position).getZan());
            viewHolder.tv_pinglun.setText(xiuquanBeanData.get(position).getComments());
            viewHolder.tv_zhuanfa.setText(xiuquanBeanData.get(position).getShare());
            x.image().bind(viewHolder.listuserimg, URLProvider.BaseImgUrl + xiuquanBeanData.get(position).getImg(), MyApplication.options);

            imageBeanList = xiuquanBeanData.get(position).getImage();
          /*  if (position>0){
                imagepagerList = xiuquanBeanData.get(position - 1).getImage();
            }else {

            }*/

            if (imageBeanList.size() == 1) {
                viewHolder.showimage.setVisibility(View.VISIBLE);
                viewHolder.gridview.setVisibility(View.GONE);
                viewHolder.showimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                        List<XIUQUANBean.DataBean.ImageBean> image = xiuquanBeanData.get(position).getImage();
                        Intent intent = new Intent(context, PagersImgActivity.class);
                        intent.putExtra("data", (Serializable) image);
                        intent.putExtra("index", "0");
                        context.startActivity(intent);
                    }
                });
                // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), viewHolder.showimage);
                x.image().bind(viewHolder.showimage, URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), MyApplication.optionsxq);
            } else {
                viewHolder.showimage.setVisibility(View.GONE);
                viewHolder.gridview.setVisibility(View.VISIBLE);
                int a = imageBeanList.size() / 3;
                int b = imageBeanList.size() % 3;
                if (b > 0) {
                    a++;
                }
                float width = (windowwidth - dip2px(40)) / 3;
                viewHolder.gridview.getLayoutParams().height = (int) (a * width);

                for (int i = 0; i < 9; i++) {
                    viewHolder.imgview[i].setVisibility(View.GONE);
                }

                for (int i = 0; i < imageBeanList.size(); i++) {
                    XIUQUANBean.DataBean.ImageBean imageBean = imageBeanList.get(i);
                    viewHolder.imgview[i].setVisibility(View.VISIBLE);
                    viewHolder.imgview[i].getLayoutParams().width = (int) width;
                    viewHolder.imgview[i].getLayoutParams().height = (int) width;
                    // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBean.getImg(), viewHolder.imgview[i]);
                    x.image().bind(viewHolder.imgview[i], URLProvider.BaseImgUrl + imageBean.getImg(), MyApplication.optionsxq);
                    viewHolder.imgview[i].setOnClickListener(new GridOnclick(position, viewHolder.imgview[i], imageBeanList, i, viewHolder.gridview));
                }
            }
            viewHolder.listuserimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "list_img" + position, Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.iv_zhuanfa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "tv_zhuanfa" + position, Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.iv_pinlun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PingLunActivity.class);
                    intent.putExtra("data", xiuquanBeanData.get(position));
                    context.startActivity(intent);
                    Toast.makeText(context, "tv_pinglun" + position, Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.iv_dianzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "tv_dianzan" + position, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }


    }

    public class ViewHolder {
        public ImageView iv_dianzan;
        public ImageView listuserimg;
        public TextView username;
        public TextView usercontent;
        public TextView add;
        public TextView time;
        public ImageView showimage;
        public GridLayout gridview;
        public TextView tv_zhuanfa;
        public TextView tv_pinglun;
        public TextView tv_dianzan;
        public ImageView imgview[] = new ImageView[9];
        public ImageView iv_zhuanfa;
        public ImageView iv_pinlun;

        public ViewHolder(View rootView) {
            listuserimg = (ImageView) rootView.findViewById(R.id.listuserimg);
            username = (TextView) rootView.findViewById(R.id.username);
            usercontent = (TextView) rootView.findViewById(R.id.usercontent);
            add = (TextView) rootView.findViewById(R.id.add);
            time = (TextView) rootView.findViewById(R.id.time);
            showimage = (ImageView) rootView.findViewById(R.id.showimage);
            for (int i = 0; i < 9; i++) {
                imgview[i] = (ImageView) rootView.findViewById(ImagaId[i]);
            }
            gridview = (GridLayout) rootView.findViewById(R.id.gridview);
            tv_zhuanfa = (TextView) rootView.findViewById(R.id.tv_zhuanfa);
            iv_zhuanfa = (ImageView) rootView.findViewById(R.id.iv_zhuanfa);
            iv_pinlun = (ImageView) rootView.findViewById(R.id.iv_pinlun);
            iv_dianzan = (ImageView) rootView.findViewById(R.id.iv_dianzan);
            tv_pinglun = (TextView) rootView.findViewById(R.id.tv_pinglun);
            tv_dianzan = (TextView) rootView.findViewById(R.id.tv_dianzan);
        }

    }

    class GridOnclick implements View.OnClickListener {

        private List<XIUQUANBean.DataBean.ImageBean> imageBeanList;
        private int index;
        private int position;
        private ImageView imageView;
        private GridLayout gridLayout;

        public GridOnclick(int position, ImageView imageView, List<XIUQUANBean.DataBean.ImageBean> imageBeanList, int index, GridLayout gridLayout) {
            this.imageBeanList = imageBeanList;
            this.index = index;
            this.position = position;
            this.imageView = imageView;
            this.gridLayout = gridLayout;
        }

        @Override
        public void onClick(View v) {
            //    Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, PagersImgActivity.class);
            intent.putExtra("data", (Serializable) imageBeanList);
            intent.putExtra("index", index);
            context.startActivity(intent);
        }
    }

}
