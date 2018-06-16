// Generated code from Butter Knife. Do not modify!
package jmhh.reproductormelodiame;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class inicial$$ViewInjector {
  public static void inject(Finder finder, final jmhh.reproductormelodiame.inicial target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361993, "field 'toolbar'");
    target.toolbar = (android.support.v7.widget.Toolbar) view;
    view = finder.findRequiredView(source, 2131361991, "field 'tabs'");
    target.tabs = (com.astuetz.PagerSlidingTabStrip) view;
    view = finder.findRequiredView(source, 2131361992, "field 'pager'");
    target.pager = (android.support.v4.view.ViewPager) view;
  }

  public static void reset(jmhh.reproductormelodiame.inicial target) {
    target.toolbar = null;
    target.tabs = null;
    target.pager = null;
  }
}
