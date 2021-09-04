package com.calmdawn.boostcoursemobileproject.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.calmdawn.boostcoursemobileproject.network.NetworkManager;

import java.util.Map;

public class WriteCommentViewModel extends ViewModel {


    public void sendMovieComment(Context context, int movieId, Map<String, String> param) {
        String query = "";
        NetworkManager.getInstance(context).sendPostRequest(
                "/movie/createComment",
                query,
                param, new NetworkManager.RequestResultListener() {
                    @Override
                    public void requestSuccess(String response) {
                        Toast.makeText(context, "한줄평 작성성공", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void requestError(VolleyError error) {
                        Toast.makeText(context, "한줄평 작성실패.\n잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
