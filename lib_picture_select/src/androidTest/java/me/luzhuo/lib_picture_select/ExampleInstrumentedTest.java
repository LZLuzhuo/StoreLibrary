package me.luzhuo.lib_picture_select;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import me.luzhuo.lib_file.FileStoreManager;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.store.FileStore;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = ExampleInstrumentedTest.class.getSimpleName();

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // assertEquals("me.luzhuo.lib_picture_select.test", appContext.getPackageName());

        List<FileBean> fileBeans = new FileStoreManager().queryList(FileStore.TypeImage);
        for (FileBean fileBean : fileBeans) {
            Log.e(TAG, "" + fileBean);
        }
    }
}