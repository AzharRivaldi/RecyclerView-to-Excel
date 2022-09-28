package com.azhar.rvtoexcel.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.azhar.rvtoexcel.model.ModelMain;
import com.azhar.rvtoexcel.network.ApiClient;
import com.azhar.rvtoexcel.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Azhar Rivaldi on 07-09-2022
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class MainViewModel extends AndroidViewModel {

    private ApiInterface apiInterface;
    private final MutableLiveData<ArrayList<ModelMain>> modelMainList = new MutableLiveData<>();

    public void setListData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = apiInterface.getDiscoverTv(ApiClient.APIKEY, "en-US");
            final ArrayList<ModelMain> modelListItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray listData = responseObject.getJSONArray("results");

                            for (int i = 0; i < listData.length(); i++) {
                                JSONObject jsonObjectData = listData.getJSONObject(i);
                                ModelMain modelMain = new ModelMain(jsonObjectData);
                                modelListItems.add(modelMain);
                            }
                            modelMainList.postValue(modelListItems);
                        } catch (Exception e) {
                            Toast.makeText(getApplication(), "Ups, Gagal menampilkan data!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Ups, Terjadi kesalahan!", Toast.LENGTH_SHORT).show();
        }
    }

    public MainViewModel(Application application) {
        super(application);
    }

    public LiveData<ArrayList<ModelMain>> getListData() {
        return modelMainList;
    }

}
