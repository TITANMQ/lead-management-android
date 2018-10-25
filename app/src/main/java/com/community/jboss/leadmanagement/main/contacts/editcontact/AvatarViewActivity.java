package com.community.jboss.leadmanagement.main.contacts.editcontact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.community.jboss.leadmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvatarViewActivity extends AppCompatActivity
{

    @BindView (R.id.contact_avatar)
    ImageView avatar;

    private String number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.avatar_view);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bitmap avatarImg = (Bitmap) intent.getParcelableExtra(EditContactActivity.AVATAR_IMG);
        avatar.setImageBitmap(avatarImg);

        number = intent.getStringExtra("NUMBER");

    }

    public void backBtnOnClick(View view)
    {

        super.onBackPressed();


    }


}
