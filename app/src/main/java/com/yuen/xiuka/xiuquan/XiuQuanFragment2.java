package com.yuen.xiuka.xiuquan;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class XiuQuanFragment2 extends BaseFragment implements View.OnClickListener {
    public ListView mixlist;
    private Context context;
    private TextView tv_fensi;
    private TextView tv_guanzhu;
    private TextView tv_renzheng;
    private TextView tv_name;
    private ImageView iv_user_icon;
    private RelativeLayout header;
    private ImageView iv_bj;
    private List<XIUQUANBean.XiuQuanDataBean> xiuquanBeanData;
    private XiuQuanAdapter myAdapter;
    private Button btn_fanhui;
    private TextView tv_titlecontent;
    private Button btn_jia;

    @Override
    public View initView() {
        context = getActivity();

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
                myAdapter = new XiuQuanAdapter(context,xiuquanBeanData);
                mixlist.setAdapter(myAdapter);
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
        //Glide.with(context).load(URLProvider.BaseImgUrl + SPUtil.getString("icon")).centerCrop().error(R.drawable.cuowu).crossFade().into(iv_user_icon);


      //  Toast.makeText(context, "initheader"+SPUtil.getString("icon"), Toast.LENGTH_LONG).show();
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

}
