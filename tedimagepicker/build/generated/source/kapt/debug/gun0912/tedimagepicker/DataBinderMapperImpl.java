package gun0912.tedimagepicker;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import gun0912.tedimagepicker.databinding.ActivityTedImagePickerBindingImpl;
import gun0912.tedimagepicker.databinding.ActivityZoomOutBindingImpl;
import gun0912.tedimagepicker.databinding.BottomsheetPartialAccessManageBindingImpl;
import gun0912.tedimagepicker.databinding.ItemAlbumBindingImpl;
import gun0912.tedimagepicker.databinding.ItemGalleryCameraBindingImpl;
import gun0912.tedimagepicker.databinding.ItemGalleryMediaBindingImpl;
import gun0912.tedimagepicker.databinding.ItemSelectedMediaBindingImpl;
import gun0912.tedimagepicker.databinding.LayoutDoneButtonBindingImpl;
import gun0912.tedimagepicker.databinding.LayoutScrollerBindingImpl;
import gun0912.tedimagepicker.databinding.LayoutSelectedAlbumDropDownBindingImpl;
import gun0912.tedimagepicker.databinding.LayoutTedImagePickerContentBindingImpl;
import gun0912.tedimagepicker.databinding.LayoutTedImagePickerPartialAccessManageBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYTEDIMAGEPICKER = 1;

  private static final int LAYOUT_ACTIVITYZOOMOUT = 2;

  private static final int LAYOUT_BOTTOMSHEETPARTIALACCESSMANAGE = 3;

  private static final int LAYOUT_ITEMALBUM = 4;

  private static final int LAYOUT_ITEMGALLERYCAMERA = 5;

  private static final int LAYOUT_ITEMGALLERYMEDIA = 6;

  private static final int LAYOUT_ITEMSELECTEDMEDIA = 7;

  private static final int LAYOUT_LAYOUTDONEBUTTON = 8;

  private static final int LAYOUT_LAYOUTSCROLLER = 9;

  private static final int LAYOUT_LAYOUTSELECTEDALBUMDROPDOWN = 10;

  private static final int LAYOUT_LAYOUTTEDIMAGEPICKERCONTENT = 11;

  private static final int LAYOUT_LAYOUTTEDIMAGEPICKERPARTIALACCESSMANAGE = 12;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(12);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.activity_ted_image_picker, LAYOUT_ACTIVITYTEDIMAGEPICKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.activity_zoom_out, LAYOUT_ACTIVITYZOOMOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.bottomsheet_partial_access_manage, LAYOUT_BOTTOMSHEETPARTIALACCESSMANAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.item_album, LAYOUT_ITEMALBUM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.item_gallery_camera, LAYOUT_ITEMGALLERYCAMERA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.item_gallery_media, LAYOUT_ITEMGALLERYMEDIA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.item_selected_media, LAYOUT_ITEMSELECTEDMEDIA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.layout_done_button, LAYOUT_LAYOUTDONEBUTTON);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.layout_scroller, LAYOUT_LAYOUTSCROLLER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.layout_selected_album_drop_down, LAYOUT_LAYOUTSELECTEDALBUMDROPDOWN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.layout_ted_image_picker_content, LAYOUT_LAYOUTTEDIMAGEPICKERCONTENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(gun0912.tedimagepicker.R.layout.layout_ted_image_picker_partial_access_manage, LAYOUT_LAYOUTTEDIMAGEPICKERPARTIALACCESSMANAGE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYTEDIMAGEPICKER: {
          if ("layout/activity_ted_image_picker_0".equals(tag)) {
            return new ActivityTedImagePickerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_ted_image_picker is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYZOOMOUT: {
          if ("layout/activity_zoom_out_0".equals(tag)) {
            return new ActivityZoomOutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_zoom_out is invalid. Received: " + tag);
        }
        case  LAYOUT_BOTTOMSHEETPARTIALACCESSMANAGE: {
          if ("layout/bottomsheet_partial_access_manage_0".equals(tag)) {
            return new BottomsheetPartialAccessManageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for bottomsheet_partial_access_manage is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMALBUM: {
          if ("layout/item_album_0".equals(tag)) {
            return new ItemAlbumBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_album is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMGALLERYCAMERA: {
          if ("layout/item_gallery_camera_0".equals(tag)) {
            return new ItemGalleryCameraBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_gallery_camera is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMGALLERYMEDIA: {
          if ("layout/item_gallery_media_0".equals(tag)) {
            return new ItemGalleryMediaBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_gallery_media is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMSELECTEDMEDIA: {
          if ("layout/item_selected_media_0".equals(tag)) {
            return new ItemSelectedMediaBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_selected_media is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTDONEBUTTON: {
          if ("layout/layout_done_button_0".equals(tag)) {
            return new LayoutDoneButtonBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_done_button is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSCROLLER: {
          if ("layout/layout_scroller_0".equals(tag)) {
            return new LayoutScrollerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_scroller is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSELECTEDALBUMDROPDOWN: {
          if ("layout/layout_selected_album_drop_down_0".equals(tag)) {
            return new LayoutSelectedAlbumDropDownBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_selected_album_drop_down is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTTEDIMAGEPICKERCONTENT: {
          if ("layout/layout_ted_image_picker_content_0".equals(tag)) {
            return new LayoutTedImagePickerContentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_ted_image_picker_content is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTTEDIMAGEPICKERPARTIALACCESSMANAGE: {
          if ("layout/layout_ted_image_picker_partial_access_manage_0".equals(tag)) {
            return new LayoutTedImagePickerPartialAccessManageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_ted_image_picker_partial_access_manage is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(25);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "album");
      sKeys.put(2, "background");
      sKeys.put(3, "buttonBackground");
      sKeys.put(4, "buttonDrawableOnly");
      sKeys.put(5, "buttonGravity");
      sKeys.put(6, "buttonText");
      sKeys.put(7, "buttonTextColor");
      sKeys.put(8, "duration");
      sKeys.put(9, "imageCountFormat");
      sKeys.put(10, "isAlbumOpened");
      sKeys.put(11, "isOpened");
      sKeys.put(12, "isSelected");
      sKeys.put(13, "items");
      sKeys.put(14, "media");
      sKeys.put(15, "mediaCountText");
      sKeys.put(16, "selectType");
      sKeys.put(17, "selectedAlbum");
      sKeys.put(18, "selectedNumber");
      sKeys.put(19, "showButton");
      sKeys.put(20, "showDuration");
      sKeys.put(21, "showZoom");
      sKeys.put(22, "text");
      sKeys.put(23, "textColor");
      sKeys.put(24, "uri");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(12);

    static {
      sKeys.put("layout/activity_ted_image_picker_0", gun0912.tedimagepicker.R.layout.activity_ted_image_picker);
      sKeys.put("layout/activity_zoom_out_0", gun0912.tedimagepicker.R.layout.activity_zoom_out);
      sKeys.put("layout/bottomsheet_partial_access_manage_0", gun0912.tedimagepicker.R.layout.bottomsheet_partial_access_manage);
      sKeys.put("layout/item_album_0", gun0912.tedimagepicker.R.layout.item_album);
      sKeys.put("layout/item_gallery_camera_0", gun0912.tedimagepicker.R.layout.item_gallery_camera);
      sKeys.put("layout/item_gallery_media_0", gun0912.tedimagepicker.R.layout.item_gallery_media);
      sKeys.put("layout/item_selected_media_0", gun0912.tedimagepicker.R.layout.item_selected_media);
      sKeys.put("layout/layout_done_button_0", gun0912.tedimagepicker.R.layout.layout_done_button);
      sKeys.put("layout/layout_scroller_0", gun0912.tedimagepicker.R.layout.layout_scroller);
      sKeys.put("layout/layout_selected_album_drop_down_0", gun0912.tedimagepicker.R.layout.layout_selected_album_drop_down);
      sKeys.put("layout/layout_ted_image_picker_content_0", gun0912.tedimagepicker.R.layout.layout_ted_image_picker_content);
      sKeys.put("layout/layout_ted_image_picker_partial_access_manage_0", gun0912.tedimagepicker.R.layout.layout_ted_image_picker_partial_access_manage);
    }
  }
}
