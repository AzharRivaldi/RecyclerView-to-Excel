package com.azhar.rvtoexcel.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.azhar.rvtoexcel.R;
import com.azhar.rvtoexcel.adapter.MainAdapter;
import com.azhar.rvtoexcel.model.ModelMain;
import com.azhar.rvtoexcel.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListData;
    private FloatingActionButton fabSave;
    private MainAdapter mainAdapter;
    private MainViewModel mainViewModel;
    private ProgressDialog progressDialog;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data");

        rvListData = findViewById(R.id.rvListData);
        fabSave = findViewById(R.id.fabSave);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getListData().observe(this, getListObserverData);
        mainViewModel.setListData();

        mainAdapter = new MainAdapter(this);
        mainAdapter.notifyDataSetChanged();

        showRecyclerList();
        showLoading(true);

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createXlsx(mainViewModel.getListData().getValue());
            }
        });
    }

    private final Observer<ArrayList<ModelMain>> getListObserverData = new Observer<ArrayList<ModelMain>>() {
        @Override
        public void onChanged(ArrayList<ModelMain> modelMainArrayList) {
            if (modelMainArrayList != null) {
                mainAdapter.setListDataMain(modelMainArrayList);
                showRecyclerList();
                showLoading(false);
            } else {
                showLoading(false);
            }
        }
    };

    private void showRecyclerList() {
        rvListData.setLayoutManager(new LinearLayoutManager(this));
        rvListData.setAdapter(mainAdapter);
        rvListData.setHasFixedSize(true);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    private void createXlsx(List<ModelMain> modelHistoryAbsen) {
        try {
            String strDate = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss", Locale.getDefault()).format(new Date());
            File root = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "FileExcel");
            if (!root.exists())
                root.mkdirs();
            File path = new File(root, "/" + strDate + ".xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream outputStream = new FileOutputStream(path);

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.MEDIUM);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setBorderRight(BorderStyle.MEDIUM);
            headerStyle.setBorderLeft(BorderStyle.MEDIUM);

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setBold(true);
            headerStyle.setFont(font);

            XSSFSheet sheet = workbook.createSheet("Data Movie");
            XSSFRow row = sheet.createRow(0);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Judul Film");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(1);
            cell.setCellValue("Tanggal Rilis");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(2);
            cell.setCellValue("Deskripsi");
            cell.setCellStyle(headerStyle);

            for (int i = 0; i < modelHistoryAbsen.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(modelHistoryAbsen.get(i).getStrName());
                sheet.setColumnWidth(0, (modelHistoryAbsen.get(i).getStrName().length() + 30) * 256);

                cell = row.createCell(1);
                cell.setCellValue(modelHistoryAbsen.get(i).getStrRelease());
                sheet.setColumnWidth(1, modelHistoryAbsen.get(i).getStrRelease().length() * 256);

                cell = row.createCell(2);
                cell.setCellValue(modelHistoryAbsen.get(i).getStrDesc());
            }

            workbook.write(outputStream);
            outputStream.close();
            Toast.makeText(MainActivity.this, "Data berhasil di ekspor!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}