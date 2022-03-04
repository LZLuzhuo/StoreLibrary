//package me.luzhuo.storedemo;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import java.io.FileNotFoundException;
//import java.io.OutputStream;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import me.luzhuo.lib_file.my.FileManager;
//
///**
// * Description:
// *
// * @Author: Luzhuo
// * @Creation Date: 2020/11/1 13:50
// * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
// **/
//public class SecondActivity extends AppCompatActivity {
//    private FileManager fileManager;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        fileManager = new FileManager(this, "Luzhuo", "file");
//    }
//
//    public void createFolder(View view) {
//        // 插入图片
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
//        boolean b = fileManager.saveBitmap("newImage.jpg", bitmap);
//        Toast.makeText(this, b ? "成功" : "失败", Toast.LENGTH_SHORT).show();
//    }
//
//    public void createImage(View view) {
//        // 删除图片
//        boolean b = fileManager.delete("newImage.jpg");
//        Toast.makeText(this, b ? "成功" : "失败", Toast.LENGTH_SHORT).show();
//    }
//
//    public void queryImage(View view) {
//    }
//
//    public void updateImage(View view) {
//    }
//
//    public void deleteImage(View view) {
//    }
//}
