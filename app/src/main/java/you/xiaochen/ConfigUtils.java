package you.xiaochen;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by you on 16/8/31.
 * 配置辅助类  Resources.getSystem()
 */
public class ConfigUtils {

    private ConfigUtils() {
    }

    /**
     * 获取状态栏高度
     */
    public static final int getStatusHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /**
     * 4.4以上版本
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 由于全屏时转非全屏时界面中的布局大小不能改变,所以不能使用流行的在windows中添加一个
     * Status 高度的控件来填充内容,  直接使用toolbar设置padding效果
     * @param activity
     * @param toolbar
     */
    public static void setStatusBarColor(Activity activity, Toolbar toolbar) {
        if (toolbar.getTag() == null ) {
            ViewGroup.LayoutParams params = toolbar.getLayoutParams();
            int statusHeight = getStatusHeight(activity);
            params.height += statusHeight;
            toolbar.setLayoutParams(params);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusHeight,
                    toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.setTag(true);
            //toolbar默认有16dp的margin值,可以设置为0,或在布局中配置
            toolbar.setContentInsetsRelative(0,0);
        }
        if (hasKitKat()) {  //设置显示toolbar时的样式
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
