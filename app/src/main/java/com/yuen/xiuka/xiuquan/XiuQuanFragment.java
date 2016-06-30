package com.yuen.xiuka.xiuquan;

import android.content.Context;
import android.view.View;
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
import com.yuen.xiuka.beans.XIUQUANBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class XiuQuanFragment extends BaseFragment implements View.OnClickListener {
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

    @Override
    public View initView() {
        context = getActivity();
        View view = View.inflate(getActivity(), R.layout.layout_xiuquanfragment, null);
        mixlist = (ListView) view.findViewById(R.id.mixlist);
        header = (RelativeLayout) View.inflate(getActivity(), R.layout.layout_xiuquan_header, null);
        tv_fensi = (TextView) header.findViewById(R.id.tv_fensi);
        tv_guanzhu = (TextView) header.findViewById(R.id.tv_guanzhu);
        tv_renzheng = (TextView) header.findViewById(R.id.tv_renzheng);
        tv_name = (TextView) header.findViewById(R.id.tv_name);
        iv_user_icon = (ImageView) header.findViewById(R.id.iv_user_icon);
        iv_bj = (ImageView) header.findViewById(R.id.iv_bj);
        mixlist.addHeaderView(header);
        tv_fensi.setOnClickListener(this);
        tv_guanzhu.setOnClickListener(this);
        tv_renzheng.setOnClickListener(this);
        iv_user_icon.setOnClickListener(this);
        iv_bj.setOnClickListener(this);
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
                List<XIUQUANBean.DataBean> xiuquanBeanData = xiuquanBean.getData();
                Toast.makeText(context, URLProvider.BaseImgUrl + bj_image, Toast.LENGTH_SHORT).show();
                System.out.println(URLProvider.BaseImgUrl + bj_image);
                x.image().bind(iv_bj, URLProvider.BaseImgUrl + bj_image, MyApplication.options);
                    Mixinfo mixinfos[] = new Mixinfo[xiuquanBeanData.size()];
                for (int i = 0; i < xiuquanBeanData.size(); i++) {
                    XIUQUANBean.DataBean dataBean = xiuquanBeanData.get(i);
                    mixinfos[i].content = dataBean.getContent();
                    mixinfos[i].username = dataBean.getName();
                    mixinfos[i].add = dataBean.getAdd();
                    mixinfos[i].time = dataBean.getTime();
                }
                adapterData = new MixListAdapter(getActivity(), data);
                mixlist.setAdapter(adapterData);
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
        xiuquan();
        data = new ArrayList<Mixinfo>();
        Mixinfo info1 = new Mixinfo();
        info1.username = "DavidWang";
        // info1.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
        info1.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
        info1.content = "这是一个单张的演示";
        info1.data = AddData(1, 0);
        data.add(info1);

        Mixinfo info2 = new Mixinfo();
        info2.username = "DavidWang";
        info2.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";


        info2.content = "这是一个单张的演示";
        info2.data = AddData(1, 1);
        data.add(info2);

        Mixinfo info3 = new Mixinfo();
        info3.username = "DavidWang";
        info3.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
        info3.content = "这是一个单张的演示";
        info3.data = AddData(1, 2);
        data.add(info3);

        for (int i = 2; i < 10; i++) {
            Mixinfo info4 = new Mixinfo();
            info4.username = "DavidWang";
            info4.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
            info4.content = "这是" + i + "个单张的演示";
            info4.data = AddData(i, 2);
            data.add(info4);
        }
        data.add(info1);
        data.add(info2);
        data.add(info3);

        for (int i = 2; i < 10; i++) {
            Mixinfo info4 = new Mixinfo();
            info4.username = "DavidWang";
            info4.userimg = "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg";
            info4.content = "这是" + i + "个单张的演示";
            info4.data = AddData(i, 2);
            data.add(info4);
        }

    }

    private ArrayList<ImageInfo> AddData(int num, int type) {
        ArrayList<ImageInfo> data = new ArrayList<ImageInfo>();
        for (int i = 0; i < num; i++) {
            if (type == 0) {
                ImageInfo info = new ImageInfo();
                info.url = "http://img4q.duitang.com/uploads/item/201408/11/20140811141753_iNtAF.jpeg";
                info.width = 1280;
                info.height = 720;
                data.add(info);
            } else if (type == 1) {
                ImageInfo info = new ImageInfo();
                info.url = "http://article.joyme.com/article/uploads/allimg/140812/101I01291-10.jpg";
                info.width = 640;
                info.height = 960;
                data.add(info);
            } else {
                ImageInfo info = new ImageInfo();
                info.url = "http://h.hiphotos.baidu.com/album/scrop%3D236%3Bq%3D90/sign=2fab0be130adcbef056a3959dc921cee/4b90f603738da977c61bb40eb151f8198618e3db.jpg";
                info.width = 236;
                info.height = 236;
                data.add(info);
            }
        }
        return data;
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
        }
    }
}
