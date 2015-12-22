package com.gmail.pdnghiadev.ex7_1takeaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.pdnghiadev.ex7_1takeaction.ultils.UserInfoContract;

public class PostDetailActivity extends AppCompatActivity {
    private TextView mContent;
    private Intent intent;
    private String html, url;
    private Spanned parseHtml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContent = (TextView) findViewById(R.id.tv_selfttext_html);

        intent = getIntent();
        html = intent.getStringExtra(UserInfoContract.SELFTEXT_HTML);
        url = intent.getStringExtra(UserInfoContract.URL);
        getSupportActionBar().setTitle(intent.getStringExtra(UserInfoContract.TITLE));
        getSupportActionBar().setSubtitle(intent.getStringExtra(UserInfoContract.AUTHOR));

        if (html != null) parseHtml = Html.fromHtml(html);

        launchBarDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(String.valueOf(parseHtml)));
            startActivity(Intent.createChooser(intent, "Share"));
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchBarDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(PostDetailActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (html != null) {
                    mContent.setText(Html.fromHtml(String.valueOf(parseHtml)));
                } else {
                    mContent.setText(url);
                }
                progressDialog.dismiss();
            }
        }, 2000);
    }

    private CharSequence parceHtml(Spannable html) {
        return Html.fromHtml(String.valueOf(html));
    }
}
