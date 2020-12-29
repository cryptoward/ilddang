package com.ilddang.job.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.ilddang.job.R;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private int mProgressCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mProgressBar = findViewById(R.id.splash_progress);
        PrepareDataTask task =  new PrepareDataTask(this);
        task.forceLoad();
    }

    public class PrepareDataTask extends AsyncTaskLoader<Integer> {
        int data;
        public PrepareDataTask(Context context) {
            super(context);
        }

        @Override
        public Integer loadInBackground() {

            try {
                Thread.sleep( 100 );
                mProgressBar.setProgress(mProgressCount, true);
                mProgressCount += 10;
                if (mProgressCount > 100) {
                    stopLoading();
                    return 1;
                }
                loadInBackground();
                // 데이터 읽기 대신
            } catch(InterruptedException e) {
                 e.printStackTrace();
            }
            return 1;
        }

        @Override
        protected void onStartLoading() {
        }

        @Override
        protected void onForceLoad() {
            super.onForceLoad();
        }

        @Override
        public void deliverResult(Integer data) {
            super.deliverResult(this.data = data);
        }

        @Override
        protected void onStopLoading() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onCanceled(Integer unneeded) {
        }

        @Override
        protected void onReset() {
        }
    }

}
