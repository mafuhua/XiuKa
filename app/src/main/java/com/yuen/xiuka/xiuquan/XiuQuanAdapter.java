package com.yuen.xiuka.xiuquan;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuen.baselib.utils.AppUtil;
import com.yuen.baselib.utils.SPUtil;
import com.yuen.xiuka.MyApplication;
import com.yuen.xiuka.R;
import com.yuen.xiuka.activity.PingLunActivity;
import com.yuen.xiuka.activity.ZhuBoXiangXiActivity;
import com.yuen.xiuka.beans.XiuQuanDataBean;
import com.yuen.xiuka.utils.URLProvider;
import com.yuen.xiuka.utils.XUtils;

import org.xutils.ImageManager;
import org.xutils.common.Callback;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

class XiuQuanAdapter extends BaseAdapter {
    private final int windowwidth;
    private List<XiuQuanDataBean.ImageBean> imageBeanList;
    private Context context;
    private List<XiuQuanDataBean> xiuquanBeanData;
    private boolean youOrmy;

    public XiuQuanAdapter(Context context, List<XiuQuanDataBean> xiuquanBeanData, boolean youOrmy) {
        this.context = context;
        this.xiuquanBeanData = xiuquanBeanData;
        this.youOrmy = youOrmy;
        windowwidth = AppUtil.getWidth(context);
        // windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
    }

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
        final XiuQuanViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.mix_view, null);
            viewHolder = new XiuQuanViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (XiuQuanViewHolder) convertView.getTag();
        }
        viewHolder.add.setText(xiuquanBeanData.get(position).getAdd());
        viewHolder.time.setText(xiuquanBeanData.get(position).getTime());
        viewHolder.username.setText(xiuquanBeanData.get(position).getName());
        viewHolder.usercontent.setText(xiuquanBeanData.get(position).getContent());
        viewHolder.tv_dianzan.setText(Integer.parseInt(xiuquanBeanData.get(position).getZan())==0?"赞":xiuquanBeanData.get(position).getZan());
        viewHolder.tv_pinglun.setText(Integer.parseInt(xiuquanBeanData.get(position).getComments())==0?"评论":xiuquanBeanData.get(position).getComments());
        viewHolder.tv_zhuanfa.setText(Integer.parseInt(xiuquanBeanData.get(position).getShare())==0?"分享":xiuquanBeanData.get(position).getShare());

        if (xiuquanBeanData.get(position).getUid().equals(SPUtil.getInt("uid")+"")){
            viewHolder.lajitong.setVisibility(View.VISIBLE);
        }else {
            viewHolder.lajitong.setVisibility(View.GONE);
        }

        x.image().bind(viewHolder.listuserimg, URLProvider.BaseImgUrl + xiuquanBeanData.get(position).getImg(), MyApplication.optionscache);
        //    Glide.with(context).load(URLProvider.BaseImgUrl + xiuquanBeanData.get(position).getImg()).centerCrop().error(R.drawable.cuowu).crossFade().into(viewHolder.listuserimg);
        imageBeanList = xiuquanBeanData.get(position).getImage();
          /*  if (xiuquanBeanData.get(position).isZanflag()){
                viewHolder.ic_dianzan.setBackgroundResource(R.drawable.dianzan_pressed);
            }else {
                viewHolder.ic_dianzan.setBackgroundResource(R.drawable.dianzan_normal);
            }*/
        if (imageBeanList.size() == 1) {
            viewHolder.showimage.setVisibility(View.VISIBLE);
            viewHolder.gridview.setVisibility(View.GONE);
            viewHolder.showimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                    List<XiuQuanDataBean.ImageBean> image = xiuquanBeanData.get(position).getImage();
                    Intent intent = new Intent(context, PagersImgActivity.class);
                    intent.putExtra("data", (Serializable) image);
                    intent.putExtra("index", "0");
                    context.startActivity(intent);
                }
            });
            ImageManager imageManager = x.image();

            // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), viewHolder.showimage);
            x.image().bind(viewHolder.showimage, URLProvider.BaseImgUrl + imageBeanList.get(0).getImg(), MyApplication.optionsxq);
            // Glide.with(context).load(URLProvider.BaseImgUrl + imageBeanList.get(0).getImg()).centerCrop().error(R.drawable.cuowu).crossFade().into(viewHolder.showimage);
        } else {
            viewHolder.showimage.setVisibility(View.GONE);
            viewHolder.gridview.setVisibility(View.VISIBLE);
            int a = imageBeanList.size() / 3;
            int b = imageBeanList.size() % 3;
            if (b > 0) {
                a++;
            }
            float width = (windowwidth - AppUtil.dp2Px(context, 40)) / 3;
            viewHolder.gridview.getLayoutParams().height = (int) (a * width);

            for (int i = 0; i < 9; i++) {
                viewHolder.imgview[i].setVisibility(View.GONE);
            }

            for (int i = 0; i < imageBeanList.size(); i++) {
                XiuQuanDataBean.ImageBean imageBean = imageBeanList.get(i);
                viewHolder.imgview[i].setVisibility(View.VISIBLE);
                viewHolder.imgview[i].getLayoutParams().width = (int) width;
                viewHolder.imgview[i].getLayoutParams().height = (int) width;
                // ImageLoaders.setsendimg(URLProvider.BaseImgUrl + imageBean.getImg(), viewHolder.imgview[i]);

                x.image().bind(viewHolder.imgview[i], URLProvider.BaseImgUrl + imageBean.getImg(), MyApplication.optionsxq);
                //  Glide.with(context).load(URLProvider.BaseImgUrl + imageBean.getImg()).centerCrop().error(R.drawable.cuowu).crossFade().into(viewHolder.imgview[i]);
                viewHolder.imgview[i].setOnClickListener(new GridOnclick(position, viewHolder.imgview[i], imageBeanList, i, viewHolder.gridview));
            }
        }
        viewHolder.lajitong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lajitong(position);


            }
        });

        viewHolder.listuserimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youOrmy){
                    Intent intent = new Intent(context, MyXiuQuanActivity.class);
                    intent.putExtra("id", xiuquanBeanData.get(position).getUid());
                    intent.putExtra("name", xiuquanBeanData.get(position).getName());
                    context.startActivity(intent);
             //       Toast.makeText(context, "list_img" + position, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent2 = new Intent(context, ZhuBoXiangXiActivity.class);
                    intent2.putExtra("uid",xiuquanBeanData.get(position).getUid());
                    context.startActivity(intent2);
                }

            }
        });
        viewHolder.iv_zhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "tv_zhuanfa" + position, Toast.LENGTH_SHORT).show();
                String id = xiuquanBeanData.get(position).getId();
                showShare(xiuquanBeanData.get(position).getContent(),id);
                dianzanhefenxiang(URLProvider.ADD_SHARE,id);
            }
        });
        viewHolder.iv_pinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PingLunActivity.class);
                intent.putExtra("data", xiuquanBeanData.get(position));
                context.startActivity(intent);
               // Toast.makeText(context, "tv_pinglun" + position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.iv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = xiuquanBeanData.get(position).getId();
                dianzanhefenxiang(URLProvider.ADD_ZAN,id);
                if (xiuquanBeanData.get(position).isZanflag()) {
                    xiuquanBeanData.get(position).setZanflag(false);
                    xiuquanBeanData.get(position).setZan(Integer.parseInt(xiuquanBeanData.get(position).getZan())-1 + "");
                  //  viewHolder.tv_dianzan.setText(xiuquanBeanData.get(position).getZan());
                   // viewHolder.ic_dianzan.setBackgroundResource(R.drawable.dianzan_normal);
                } else {
                    xiuquanBeanData.get(position).setZanflag(true);
                    xiuquanBeanData.get(position).setZan(Integer.parseInt(xiuquanBeanData.get(position).getZan()) + 1 + "");
                  //  viewHolder.tv_dianzan.setText(Integer.parseInt(xiuquanBeanData.get(position).getZan()) + 1 + "");
                  //  viewHolder.ic_dianzan.setBackgroundResource(R.drawable.dianzan_pressed);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    public void lajitong(final int position){
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        //设置它的ContentView
        dialog.setContentView(R.layout.task_saveimg_dialog);
        TextView viewById = (TextView) dialog.findViewById(R.id.dialog_title);
        viewById.setText("确定删除这条秀圈吗？");
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                shanchuxiuquan(xiuquanBeanData.get(position).getId());
                xiuquanBeanData.remove(position);
                notifyDataSetChanged();
            }

        });
        dialog.show();
    }
    private void dianzanhefenxiang(String url,String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        XUtils.xUtilsPost(url, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
 private void shanchuxiuquan(String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        XUtils.xUtilsPost(URLProvider.DEL, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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

    private void showShare(String content, String id) {
        ShareSDK.initSDK(context);// 初始化sdk（校验appkey）
        OnekeyShare oks = new OnekeyShare();

        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("秀咖");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://139.196.175.144/xiuka/index/index/id/"+id);// 自己公司主页（分析文章的url）
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        oks.setImageUrl("http://139.196.175.144/xiuka/logo.png");
       // oks.setImageUrl("/storage/emulated/0/imagcacahe/0.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://139.196.175.144/xiuka/index/index/id/"+id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
      //  oks.setComment("服务上班一族，博您闲暇时一笑~顺便提供点儿与企业服务、工作生活相关的咨询与服务");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://139.196.175.144/xiuka/index/index/id/"+id);
        // 启动分享GUI
        oks.show(context);

    }

    class GridOnclick implements View.OnClickListener {

        private List<XiuQuanDataBean.ImageBean> imageBeanList;
        private int index;
        private int position;
        private ImageView imageView;
        private GridLayout gridLayout;

        public GridOnclick(int position, ImageView imageView, List<XiuQuanDataBean.ImageBean> imageBeanList, int index, GridLayout gridLayout) {
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
